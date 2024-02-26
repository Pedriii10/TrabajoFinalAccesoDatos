package trabajofinal.acceso_datos_fianl;

import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;


@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String index() {
        return "home/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        // Aquí deberías verificar las credenciales del usuario, por ejemplo:
        if ("usuario@example.com".equals(email) && "contraseña".equals(password)) {
            return "redirect:/home"; // Redirigir a la página principal si el login es exitoso
        } else {
            model.addAttribute("loginError");
            return "home/login"; // Mantener al usuario en la página de login si falla
        }
    }

    // Método para mostrar el formulario de registro
    @GetMapping("/register")
    public String showRegistrationForm(Model model, RedirectAttributes redirectAttributes) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new UsuarioDTO());
        }
        // No es necesario añadir los errores aquí manualmente si están en el flash attributes,
        // ya que Spring los recogerá automáticamente después de la redirección
        return "home/register";
    }


    // Método para procesar el formulario de registro
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("usuario") @Valid UsuarioDTO usuarioDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "home/register"; // Si hay errores, vuelve a mostrar el formulario de registro
        }
        usuarioService.create(usuarioDTO); // Crea el nuevo usuario
        redirectAttributes.addFlashAttribute("successMessage", "Registro exitoso");
        return "redirect:/login"; // Redirige al login tras un registro exitoso
    }

}