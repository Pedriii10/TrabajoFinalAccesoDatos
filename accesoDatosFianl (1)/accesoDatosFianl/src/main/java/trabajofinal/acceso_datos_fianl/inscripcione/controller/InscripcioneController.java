package trabajofinal.acceso_datos_fianl.inscripcione.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.model.EventoDTO;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.inscripcione.model.InscripcioneDTO;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.service.InscripcioneService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.CustomCollectors;
import trabajofinal.acceso_datos_fianl.util.WebUtils;

import java.util.List;

/**
 * InscripcioneController es un controlador que maneja las operaciones relacionadas con las inscripciones a eventos.
 * Proporciona métodos para listar, agregar, editar y eliminar inscripciones, así como para mostrar formularios de inscripción.
 */
@Controller
@RequestMapping("/inscripciones")
public class InscripcioneController {

    private final InscripcioneService inscripcioneService;
    private final InscripcioneRepository inscripcioneRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final EventoService eventoService;
    /**
     * Construye el controlador con los servicios y repositorios necesarios.
     *
     * @param inscripcioneService    Servicio para operaciones relacionadas con inscripciones.
     * @param inscripcioneRepository Repositorio para la entidad de inscripción.
     * @param usuarioRepository      Repositorio para la entidad de usuario.
     * @param eventoRepository       Repositorio para la entidad de evento.
     * @param eventoService          Servicio para operaciones relacionadas con eventos.
     */
    public InscripcioneController(final InscripcioneService inscripcioneService, InscripcioneRepository inscripcioneRepository,
                                  final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository, EventoService eventoService) {
        this.inscripcioneService = inscripcioneService;
        this.inscripcioneRepository = inscripcioneRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.eventoService = eventoService;
    }
    /**
     * Prepara el contexto del modelo agregando valores de usuario y evento para su uso en las vistas.
     *
     * @param model El modelo donde se agregarán los valores.
     */
    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usuarioValues", usuarioRepository.findAll(Sort.by("usuarioId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getUsuarioId, Usuario::getNombre)));
        model.addAttribute("eventoValues", eventoRepository.findAll(Sort.by("eventoId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Evento::getEventoId, Evento::getNombre)));
    }
    /**
     * Muestra la lista de inscripciones.
     *
     * @param model El modelo donde se agregarán las inscripciones.
     * @return La vista que muestra la lista de inscripciones.
     */
    @GetMapping
    public String list(final Model model) {
        model.addAttribute("inscripciones", inscripcioneService.findAll());
        return "inscripcione/list";
    }
    /**
     * Muestra el formulario para agregar una nueva inscripción.
     *
     * @param inscripcioneDTO El DTO de inscripción para vincular al formulario.
     * @return La vista que muestra el formulario de inscripción.
     */
    @GetMapping("/add")
    public String add(@ModelAttribute("inscripcione") final InscripcioneDTO inscripcioneDTO) {
        return "inscripcione/add";
    }
    /**
     * Procesa la solicitud para agregar una nueva inscripción.
     *
     * @param inscripcioneDTO      El DTO de inscripción enviado desde el formulario.
     * @param bindingResult        Resultado del proceso de validación.
     * @param redirectAttributes  Atributos para redirigir la solicitud.
     * @param model                El modelo de la vista.
     * @return La URL de redirección después de agregar la inscripción.
     */
    @PostMapping("/add")
    public String add(@ModelAttribute("inscripcione") @Valid final InscripcioneDTO inscripcioneDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            return "inscripcione/add";
        }

        Usuario u = (Usuario) model.getAttribute("user");
        assert u != null;
        inscripcioneDTO.setUsuario(u.getUsuarioId());
        inscripcioneService.create(inscripcioneDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inscripcione.create.success"));
        return "redirect:/index";
    }
    /**
     * Muestra el formulario para editar una inscripción existente.
     *
     * @param inscripcionId El ID de la inscripción a editar.
     * @param model         El modelo donde se agregarán los detalles de la inscripción.
     * @return La vista que muestra el formulario de edición de la inscripción.
     */
    @GetMapping("/edit/{inscripcionId}")
    public String edit(@PathVariable(name = "inscripcionId") final Integer inscripcionId,
            final Model model) {
        model.addAttribute("inscripcione", inscripcioneService.get(inscripcionId));
        return "inscripcione/edit";
    }
    /**
     * Procesa la solicitud para editar una inscripción existente.
     *
     * @param inscripcionId       El ID de la inscripción a editar.
     * @param inscripcioneDTO     El DTO de inscripción enviado desde el formulario.
     * @param bindingResult       Resultado del proceso de validación.
     * @param redirectAttributes Atributos para redirigir la solicitud.
     * @return La URL de redirección después de editar la inscripción.
     */
    @PostMapping("/edit/{inscripcionId}")
    public String edit(@PathVariable(name = "inscripcionId") final Integer inscripcionId,
            @ModelAttribute("inscripcione") @Valid final InscripcioneDTO inscripcioneDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inscripcione/edit";
        }
        inscripcioneService.update(inscripcionId, inscripcioneDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inscripcione.update.success"));
        return "redirect:/inscripciones";
    }
    /**
     * Procesa la solicitud para eliminar una inscripción existente.
     *
     * @param inscripcionId       El ID de la inscripción a eliminar.
     * @param redirectAttributes Atributos para redirigir la solicitud.
     * @return La URL de redirección después de eliminar la inscripción.
     */
    @PostMapping("/delete/{inscripcionId}")
    public String delete(@PathVariable(name = "inscripcionId") final Integer inscripcionId,
            final RedirectAttributes redirectAttributes) {
        inscripcioneService.delete(inscripcionId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inscripcione.delete.success"));
        return "redirect:/inscripciones";
    }
    /**
     * Muestra el formulario de inscripción para un evento específico.
     *
     * @param eventoId El ID del evento para el cual se mostrará el formulario de inscripción.
     * @param id       El ID del evento (se repite para mantener consistencia con el código original).
     * @param model    El modelo donde se agregarán los detalles del evento y el formulario de inscripción.
     * @param session  La sesión HTTP para obtener el ID del usuario actual.
     * @return La vista que muestra el formulario de inscripción para el evento especificado.
     */
    @GetMapping("/add/{eventoId}")
    public String mostrarFormularioInscripcion(@PathVariable("eventoId") Integer eventoId, @PathVariable("eventoId") Long id, Model model, HttpSession session) {
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
        if (evento.getOrganizador().getUsuarioId().equals(usuarioId)) {
            model.addAttribute("mensaje", "Eres el organizador de este evento y no puedes inscribirte.");
            return "evento/eventoData"; // o la vista que muestre el mensaje
        }
        model.addAttribute("inscripcione", new InscripcioneDTO());
        model.addAttribute("evento", eventoService.buscarPorId(id));
        return "inscripcione/add";
    }
}
