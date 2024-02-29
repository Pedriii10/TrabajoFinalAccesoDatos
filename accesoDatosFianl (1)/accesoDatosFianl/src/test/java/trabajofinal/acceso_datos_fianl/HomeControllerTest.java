package trabajofinal.acceso_datos_fianl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.service.InscripcioneService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private InscripcioneRepository inscripcioneRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EventoService eventoService;
    @Mock
    private InscripcioneService inscripcioneService;
    @Mock
    private Model model;
    @Mock
    private HttpSession session;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void index() {
        String viewName = homeController.index();
        assertEquals("home/login", viewName);
    }

    @Test
    void showRegistrationForm() {
        when(model.containsAttribute("usuario")).thenReturn(false);
        String viewName = homeController.showRegistrationForm(model);
        assertEquals("home/register", viewName);
        verify(model, times(1)).addAttribute(eq("usuario"), any(UsuarioDTO.class));
    }

    @Test
    void registerUser() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = homeController.registerUser(usuarioDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/login", viewName);
        verify(usuarioService, times(1)).create(usuarioDTO);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("successMessage"), anyString());
    }

    @Test
    void home() {
        when(eventoService.findAll()).thenReturn(null); // Simula la devolución de una lista de eventos
        String viewName = homeController.home(model);
        assertEquals("home/index", viewName);
        verify(model, times(1)).addAttribute(eq("eventos"), any());
    }

    @Test
    void verDetalleEvento() {
        Long eventoId = 1L;
        when(eventoService.buscarPorId(eventoId)).thenReturn(new Evento()); // Simula la respuesta del servicio
        String viewName = homeController.verDetalleEvento(eventoId, model);
        assertEquals("home/eventoData", viewName);
        verify(model, times(1)).addAttribute(eq("evento"), any(Evento.class));
    }

    @Test
    void login() {
        String correoElectronico = "usuario@example.com";
        String contrasena = "password";
        Usuario user = new Usuario();
        user.setNombre("NombreUsuario");

        when(usuarioRepository.findByCorreoElectronicoAndContrasena(correoElectronico, contrasena)).thenReturn(user);

        String viewName = homeController.login(correoElectronico, contrasena, model, session);

        assertEquals("redirect:/index", viewName);
        verify(session, times(1)).setAttribute(eq("nombreUsuario"), anyString());
    }

    @Test
    void logout() {
        String viewName = homeController.logout(session);
        assertEquals("redirect:/", viewName);
        verify(session, times(1)).invalidate();
    }

    @Test
    void perfil() {
        Usuario usuario = new Usuario();
        usuario.setFotoPerfil(new byte[1]);
        when(model.getAttribute("user")).thenReturn(usuario);

        String viewName = homeController.perfil(model, session);

        assertEquals("home/perfil", viewName);
        verify(model, times(1)).addAttribute(eq("imagenBase64"), anyString());
    }

    @Test
    void verEventosPorOrganizador() {
        Integer usuarioId = 1;
        when(eventoService.findAllByOrganizadorId(usuarioId)).thenReturn(null); // Simula la devolución de una lista de eventos

        String viewName = homeController.verEventosPorOrganizador(usuarioId, model);

        assertEquals("home/misEventos", viewName);
        verify(model, times(1)).addAttribute(eq("eventos"), any());
    }

    @Test
    void verMisInscripciones() {
        Integer usuarioId = 1;
        when(inscripcioneRepository.findByUsuarioUsuarioId(usuarioId)).thenReturn(null); // Simula la devolución de una lista de inscripciones

        String viewName = homeController.verMisInscripciones(usuarioId, model);

        assertEquals("home/misInscripciones", viewName);
        verify(model, times(1)).addAttribute(eq("inscripciones"), any());
        verify(model, times(1)).addAttribute(eq("eventos"), any());
    }

    @Test
    void cancelarInscripcion() {
        Integer inscripcionId = 1;
        String viewName = homeController.cancelarInscripcion(inscripcionId, model);

        assertEquals("home/perfil", viewName);
        verify(inscripcioneService, times(1)).delete(inscripcionId);
    }

    @Test
    void verParticipantes() {
        Integer eventoId = 1;
        when(inscripcioneRepository.findByEventoEventoId(eventoId)).thenReturn(null); // Simula la devolución de una lista de inscripciones

        String viewName = homeController.verParticipantes(eventoId, model);

        assertEquals("home/verParticipantes", viewName);
        verify(model, times(1)).addAttribute(eq("inscripciones"), any());
    }

}