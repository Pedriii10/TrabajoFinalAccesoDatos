package trabajofinal.acceso_datos_fianl.usuario.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;
import trabajofinal.acceso_datos_fianl.util.WebUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    /**
     * Constructor del controlador que recibe el servicio de usuarios.
     *
     * @param usuarioService Servicio de usuarios.
     */
    public UsuarioController(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    /**
     * Muestra la lista de usuarios.
     *
     * @param model Modelo para agregar atributos.
     * @return Vista de la lista de usuarios.
     */
    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/list";
    }
    /**
     * Muestra el formulario para agregar un nuevo usuario.
     *
     * @param usuarioDTO DTO del usuario a agregar.
     * @return Vista para agregar un usuario.
     */
    @GetMapping("/add")
    public String add(@ModelAttribute("usuario") final UsuarioDTO usuarioDTO) {
        return "usuario/add";
    }
    /**
     * Procesa la adición de un nuevo usuario.
     *
     * @param usuarioDTO         DTO del usuario a agregar.
     * @param bindingResult      Resultado del enlace de datos.
     * @param redirectAttributes Atributos de redirección.
     * @return Redirecciona a la página principal o a la página de registro si hay errores.
     */
    @PostMapping("/add")
    public String add(@ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Aquí se deberían incluir los errores en el RedirectAttributes para que estén disponibles después de la redirección
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "usuario", bindingResult);
            redirectAttributes.addFlashAttribute("usuario", usuarioDTO);
            return "redirect:/register"; // Redirige de nuevo a la página de registro
        }
        usuarioService.create(usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.create.success"));
        return "redirect:/";
    }

    /**
     * Muestra el formulario para editar un usuario.
     *
     * @param usuarioId ID del usuario a editar.
     * @param model     Modelo para agregar atributos.
     * @return Vista para editar un usuario.
     */
    @GetMapping("/edit/{usuarioId}")
    public String edit(@PathVariable(name = "usuarioId") final Integer usuarioId,
            final Model model) {
        model.addAttribute("usuario", usuarioService.get(usuarioId));
        return "usuario/edit";
    }
    /**
     * Procesa la edición de un usuario.
     *
     * @param usuarioId          ID del usuario a editar.
     * @param usuarioDTO         DTO del usuario con los nuevos datos.
     * @param bindingResult      Resultado del enlace de datos.
     * @param redirectAttributes Atributos de redirección.
     * @return Redirecciona a la lista de usuarios.
     */
    @PostMapping("/edit/{usuarioId}")
    public String edit(@PathVariable(name = "usuarioId") final Integer usuarioId,
            @ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuario/edit";
        }
        usuarioService.update(usuarioId, usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.update.success"));
        return "redirect:/usuarios";
    }
    /**
     * Elimina un usuario.
     *
     * @param usuarioId          ID del usuario a eliminar.
     * @param redirectAttributes Atributos de redirección.
     * @return Redirecciona a la lista de usuarios.
     */
    @PostMapping("/delete/{usuarioId}")
    public String delete(@PathVariable(name = "usuarioId") final Integer usuarioId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(usuarioId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            usuarioService.delete(usuarioId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("usuario.delete.success"));
        }
        return "redirect:/usuarios";
    }
    /**
     * Muestra el formulario para agregar una foto de perfil.
     *
     * @return Vista para agregar una foto de perfil.
     */
    @GetMapping("/foto/add")
    public String mostrarFormularioDeFoto() {
        return "usuario/addFoto";
    }
    /**
     * Procesa la adición de una foto de perfil.
     *
     * @param foto              Archivo de la foto de perfil.
     * @param session           Sesión HTTP.
     * @param redirectAttributes Atributos de redirección.
     * @return Redirecciona al perfil del usuario.
     */
    @PostMapping("/foto/add")
    public String agregarFoto(@RequestParam("foto") MultipartFile foto, HttpSession session, RedirectAttributes redirectAttributes) {
        return "redirect:/perfil";
    }
}
