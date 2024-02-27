package trabajofinal.acceso_datos_fianl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;


@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    @Autowired
    private EventoService eventoService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String index() {
        return "home/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        // Aquí deberías verificar las credenciales del usuario
        if ("usuario@example.com".equals(email) && "contraseña".equals(password)) {
            return "redirect:/index"; // Redirigir a la página principal si el login es exitoso
        } else {
            model.addAttribute("loginError", "Error en las credenciales.");
            return "home/login"; // Mantener al usuario en la página de login si falla
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new UsuarioDTO());
        }
        return "home/register";
    }

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

    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("eventos", eventoService.findAll()); // Agrega la lista de eventos al modelo
        return "home/index";
    }
}
