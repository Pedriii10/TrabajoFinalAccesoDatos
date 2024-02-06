package accesodatos.acceso_datos_trabajo_final.usuario.controller;

import accesodatos.acceso_datos_trabajo_final.usuario.model.UsuarioDTO;
import accesodatos.acceso_datos_trabajo_final.usuario.service.UsuarioService;
import accesodatos.acceso_datos_trabajo_final.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("usuario") final UsuarioDTO usuarioDTO) {
        return "usuario/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("usuario") @Valid final UsuarioDTO usuarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "usuario/add";
        }
        usuarioService.create(usuarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("usuario.create.success"));
        return "redirect:/usuarios";
    }

    @GetMapping("/edit/{usuarioId}")
    public String edit(@PathVariable(name = "usuarioId") final Integer usuarioId,
            final Model model) {
        model.addAttribute("usuario", usuarioService.get(usuarioId));
        return "usuario/edit";
    }

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

    @PostMapping("/delete/{usuarioId}")
    public String delete(@PathVariable(name = "usuarioId") final Integer usuarioId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = usuarioService.getReferencedWarning(usuarioId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            usuarioService.delete(usuarioId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("usuario.delete.success"));
        }
        return "redirect:/usuarios";
    }

}
