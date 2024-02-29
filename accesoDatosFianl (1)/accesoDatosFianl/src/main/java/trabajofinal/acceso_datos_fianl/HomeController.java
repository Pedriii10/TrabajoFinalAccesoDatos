package trabajofinal.acceso_datos_fianl;

import ch.qos.logback.core.LayoutBase;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
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
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.service.InscripcioneService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;

import java.util.Base64;
import java.util.List;
/**
 * HomeController es el controlador principal de la aplicación, manejando las operaciones de navegación
 * principales como el inicio de sesión, registro, visualización de eventos y gestión de perfiles de usuario.
 * Este controlador interactúa con varios servicios y repositorios para realizar sus funciones.
 */
@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final InscripcioneRepository inscripcioneRepository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private InscripcioneService inscripcioneService;

    /**
     * Constructor para HomeController que inicializa los servicios y repositorios necesarios para
     * las operaciones del controlador.
     *
     * @param usuarioService         Servicio para operaciones relacionadas con usuarios.
     * @param inscripcioneRepository Repositorio para operaciones relacionadas con inscripciones.
     * @param usuarioRepository      Repositorio para operaciones de búsqueda de usuarios.
     * @param inscripcioneService    Servicio para operaciones relacionadas con inscripciones.
     */
    public HomeController(UsuarioService usuarioService, InscripcioneRepository inscripcioneRepository,
                          UsuarioRepository usuarioRepository, InscripcioneService inscripcioneService) {
        this.usuarioService = usuarioService;
        this.inscripcioneRepository = inscripcioneRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscripcioneService = inscripcioneService;
    }

    /**
     * Maneja la solicitud GET para la raíz del sitio y muestra la página de inicio de sesión.
     *
     * @return Nombre de la vista de inicio de sesión.
     */
    @GetMapping("/")
    public String index() {
        return "home/login";
    }

    /**
     * Muestra el formulario de registro para nuevos usuarios. Si el modelo ya contiene un atributo "usuario",
     * lo reutiliza; de lo contrario, agrega uno nuevo.
     *
     * @param model Modelo para pasar atributos a la vista.
     * @return Nombre de la vista del formulario de registro.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new UsuarioDTO());
        }
        return "home/register";
    }

    /**
     * Procesa la solicitud POST para registrar un nuevo usuario. Si hay errores en el formulario, vuelve a mostrar
     * el formulario de registro. Si el registro es exitoso, redirige al usuario a la página de inicio de sesión.
     *
     * @param usuarioDTO           DTO del usuario a registrar.
     * @param bindingResult        Resultado de la validación del formulario.
     * @param redirectAttributes   Atributos para pasar mensajes entre redireccionamientos.
     * @return Redirección a la vista de inicio de sesión o nombre de la vista del formulario de registro en caso de error.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("usuario") @Valid UsuarioDTO usuarioDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "home/register";
        }
        usuarioService.create(usuarioDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Registro exitoso");
        return "redirect:/login";
    }

    /**
     * Maneja la solicitud GET para mostrar la página principal de la aplicación, incluyendo una lista de eventos.
     *
     * @param model Modelo para pasar atributos a la vista.
     * @return Nombre de la vista principal de la aplicación.
     */
    @GetMapping("/index")
    public String home(Model model) {
        model.addAttribute("eventos", eventoService.findAll());
        return "home/index";
    }

    /**
     * Muestra el detalle de un evento específico, identificado por su ID.
     *
     * @param id    ID del evento a visualizar.
     * @param model Modelo para pasar atributos a la vista.
     * @return Nombre de la vista de detalle del evento.
     */
    @GetMapping("/index/vermas/{id}")
    public String verDetalleEvento(@PathVariable("id") Long id, Model model) {
        model.addAttribute("evento", eventoService.buscarPorId(id));
        return "home/eventoData";
    }

    /**
     * Procesa la solicitud de inicio de sesión del usuario, verificando las credenciales y estableciendo la sesión
     * en caso de éxito, o mostrando un mensaje de error en caso contrario.
     *
     * @param correoElectronico Correo electrónico del usuario.
     * @param contrasena        Contraseña del usuario.
     * @param model             Modelo para pasar atributos a la vista.
     * @param session           Sesión HTTP.
     * @return Redirección a la página principal en caso de éxito, o nombre de la vista de inicio de sesión con mensaje de error.
     */
    @PostMapping("/login")
    public String login(@RequestParam(required = false) String correoElectronico,
                        @RequestParam(required = false) String contrasena, Model model, HttpSession session) {
        Usuario user = usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena);
        if (user != null) {
            session.setAttribute("nombreUsuario", user.getNombre());
            return "redirect:/index";
        } else {
            model.addAttribute("loginError", "Credenciales inválidas.");
            return "home/login";
        }
    }

    /**
     * Maneja la solicitud GET para cerrar la sesión del usuario actual, invalidando la sesión HTTP.
     *
     * @param session Sesión HTTP a invalidar.
     * @return Redirección a la página de inicio de sesión.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * Muestra el perfil del usuario actual, incluyendo la foto de perfil si está disponible.
     *
     * @param model   Modelo para pasar atributos a la vista.
     * @param session Sesión HTTP que contiene información del usuario.
     * @return Nombre de la vista de perfil del usuario.
     */
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario usuario = (Usuario) model.getAttribute("user");
        if (usuario != null && usuario.getFotoPerfil() != null) {
            String imagenBase64 = Base64.getEncoder().encodeToString(usuario.getFotoPerfil());
            model.addAttribute("imagenBase64", imagenBase64);
        }
        return "home/perfil";
    }

    /**
     * Muestra los eventos organizados por un usuario específico, identificado por su ID.
     *
     * @param usuarioId ID del usuario organizador de eventos.
     * @param model     Modelo para pasar atributos a la vista.
     * @return Nombre de la vista que muestra los eventos del usuario.
     */
    @GetMapping("/index/verEventos/{usuarioId}")
    public String verEventosPorOrganizador(@PathVariable Integer usuarioId, Model model) {
        model.addAttribute("eventos", eventoService.findAllByOrganizadorId(usuarioId));
        return "home/misEventos";
    }

    /**
     * Muestra las inscripciones de un usuario a diferentes eventos, identificado por su ID de usuario.
     *
     * @param usuarioId ID del usuario cuyas inscripciones se quieren visualizar.
     * @param model     Modelo para pasar atributos a la vista.
     * @return Nombre de la vista de inscripciones del usuario.
     */
    @GetMapping("/index/misInscripciones/{usuarioId}")
    @Transactional
    public String verMisInscripciones(@PathVariable("usuarioId") Integer usuarioId, Model model) {
        List<Inscripcione> inscripcionesUsuario = inscripcioneRepository.findByUsuarioUsuarioId(usuarioId);
        model.addAttribute("inscripciones", inscripcionesUsuario);
        model.addAttribute("eventos", eventoService.findAllByOrganizadorId(usuarioId));
        return "home/misInscripciones";
    }

    /**
     * Cancela la inscripción de un usuario a un evento específico, identificado por el ID de la inscripción.
     *
     * @param inscripcionId ID de la inscripción a cancelar.
     * @param model         Modelo para pasar atributos a la vista.
     * @return Redirección a la vista de perfil del usuario.
     */
    @GetMapping("/index/cancelarInscripcion/{inscripcionId}")
    public String cancelarInscripcion(@PathVariable("inscripcionId") Integer inscripcionId, Model model) {
        inscripcioneService.delete(inscripcionId);
        return "home/perfil";
    }

    /**
     * Muestra los participantes de un evento específico, identificado por su ID.
     *
     * @param eventoId ID del evento cuyos participantes se quieren visualizar.
     * @param model    Modelo para pasar atributos a la vista.
     * @return Nombre de la vista que muestra los participantes del evento.
     */
    @GetMapping("/verParticipantes/{eventoId}")
    public String verParticipantes(@PathVariable("eventoId") Integer eventoId, Model model) {
        List<Inscripcione> misInscripcionesDelEvento = inscripcioneRepository.findByEventoEventoId(eventoId);
        model.addAttribute("inscripciones", misInscripcionesDelEvento);
        return "home/verParticipantes";
    }
}

