package accesodatos.acceso_datos_trabajo_final.login.controllers;

import accesodatos.acceso_datos_trabajo_final.usuario.model.UsuarioDTO;
import accesodatos.acceso_datos_trabajo_final.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    @Autowired
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login/login"; // Asegúrate de que esta ruta corresponde a la ubicación de tu plantilla de login
    }

    @PostMapping("/login")
    public String customLogin(String nombre, String password, RedirectAttributes redirectAttributes) {
        try {
            UsuarioDTO usuario = usuarioService.findByNombreAndContrasena(nombre, password);
            // Lógica después de una autenticación exitosa
            return "redirect:/home"; // Asegúrate de que esta ruta exista y esté configurada correctamente
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Las credenciales son inválidas. Por favor, intente de nuevo.");
            return "redirect:/login"; // Usa la ruta correcta para el formulario de login
        }
    }
}
