package trabajofinal.acceso_datos_fianl;

import ch.qos.logback.core.LayoutBase;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;

import java.util.Base64;


@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    private UsuarioRepository usuarioRepository;
    @Autowired
    private EventoService eventoService;

    public HomeController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/")
    public String index() {
        return "home/login";
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

    @GetMapping("/index/vermas/{id}")
    public String verDetalleEvento(@PathVariable("id") Long id, Model model) {
        model.addAttribute("evento", eventoService.buscarPorId(id));
        return "home/eventoData";
    }

    @PostMapping("/login")
    public String login(@RequestParam(required = false) String correoElectronico,
                        @RequestParam(required = false) String contrasena, Model model, HttpSession session) {
        Usuario user = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
        if (user != null) {
            session.setAttribute("nombreUsuario", user.getNombre()); // Guarda el nombre del usuario en la sesión
            return "redirect:/index";
        } else {
            model.addAttribute("loginError", "Credenciales inválidas.");
            return "home/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Limpia la sesión
        return "redirect:/login";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        // Obtener el objeto usuario del modelo, que fue agregado por GlobalControllerAdvice
        Usuario usuario = (Usuario) model.getAttribute("user");

        if (usuario != null && usuario.getFotoPerfil() != null) {
            String imagenBase64 = Base64.getEncoder().encodeToString(usuario.getFotoPerfil());
            model.addAttribute("imagenBase64", imagenBase64);
        }

        return "home/perfil";
    }


    @GetMapping("/index/verEventos/{usuarioId}")
    public String verEventosPorOrganizador(@PathVariable Integer usuarioId, Model model) {
        // Utiliza el usuarioId para buscar eventos organizados por este usuario
        model.addAttribute("eventos", eventoService.findAllByOrganizadorId(usuarioId));
        return "home/misEventos"; // Asegúrate de que la vista 'home/misEventos' exista y esté configurada correctamente
    }



}
