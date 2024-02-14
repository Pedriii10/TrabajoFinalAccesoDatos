package accesodatos.acceso_datos_trabajo_final.register.controller;

import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import accesodatos.acceso_datos_trabajo_final.usuario.model.UsuarioDTO;
import accesodatos.acceso_datos_trabajo_final.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UsuarioService usuarioService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new UsuarioDTO());
        }
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("usuario") @Valid UsuarioDTO usuarioDTO,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Maneja los errores de validación aquí
            return "login/register";
        }

        usuarioDTO.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        usuarioService.create(usuarioDTO); // Este método debe aceptar UsuarioDTO y devolver el ID del usuario creado
        redirectAttributes.addFlashAttribute("success", "Registro exitoso. Por favor inicie sesión.");
        return "redirect:/login"; // Redirige al usuario a la página de inicio de sesión
    }
}
