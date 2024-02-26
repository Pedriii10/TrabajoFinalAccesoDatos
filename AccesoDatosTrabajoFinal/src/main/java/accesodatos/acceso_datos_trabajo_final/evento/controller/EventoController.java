package accesodatos.acceso_datos_trabajo_final.evento.controller;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.model.EventoDTO;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.evento.service.EventoService;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import accesodatos.acceso_datos_trabajo_final.usuario.repos.UsuarioRepository;
import accesodatos.acceso_datos_trabajo_final.util.CustomCollectors;
import accesodatos.acceso_datos_trabajo_final.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public EventoController(final EventoService eventoService,
                            final UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
        this.eventoService = eventoService;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }
    @GetMapping("/formCreate")
    public String crearEventoFormulario(Model model) {
        Evento evento = new Evento();
        model.addAttribute("evento", evento);
        return "evento/formCreate";

    }

    @PostMapping("/evento")
    public String guardarEvento(@ModelAttribute("evento") Evento evento){
        EventoService.create(evento);
        return "redirect:/evento";
    }



    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("organizadorValues", usuarioRepository.findAll(Sort.by("usuarioId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getUsuarioId, Usuario::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        List<EventoDTO> eventos = eventoService.findAll();
        model.addAttribute("eventoes", eventos);
        model.addAttribute("hasEventos", !eventos.isEmpty());
        return "evento/list";
    }

}