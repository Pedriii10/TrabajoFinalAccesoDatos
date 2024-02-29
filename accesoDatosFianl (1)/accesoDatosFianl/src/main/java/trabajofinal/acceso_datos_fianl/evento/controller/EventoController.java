package trabajofinal.acceso_datos_fianl.evento.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.model.EventoDTO;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.CustomCollectors;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;
import trabajofinal.acceso_datos_fianl.util.WebUtils;

import java.util.List;

/**
 * EventoController es responsable de manejar las solicitudes relacionadas con las operaciones CRUD de eventos.
 * Esto incluye listar eventos, agregar nuevos eventos, editar eventos existentes y eliminar eventos.
 * Además, proporciona funcionalidades para visualizar eventos específicos de un organizador.
 */
@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que inyecta las dependencias necesarias para el controlador, incluyendo los servicios y repositorios.
     *
     * @param eventoService     El servicio que proporciona las operaciones de negocio para los eventos.
     * @param eventoRepository  El repositorio para acceder directamente a los datos de eventos.
     * @param usuarioRepository El repositorio para acceder directamente a los datos de usuarios.
     */
    public EventoController(final EventoService eventoService, EventoRepository eventoRepository,
                            final UsuarioRepository usuarioRepository) {
        this.eventoService = eventoService;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Prepara el contexto para cada solicitud, agregando al modelo los valores necesarios como los organizadores.
     *
     * @param model El modelo al que se agregarán los atributos.
     */
    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("organizadorValues", usuarioRepository.findAll(Sort.by("usuarioId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getUsuarioId, Usuario::getNombre)));
    }
    /**
     * Maneja la solicitud GET para listar todos los eventos disponibles.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista que muestra la lista de eventos.
     */
    @GetMapping
    public String list(final Model model) {
        model.addAttribute("eventoes", eventoService.findAll());
        return "evento/list";
    }
    /**
     * Muestra el formulario para agregar un nuevo evento.
     *
     * @param eventoDTO El DTO que respalda el formulario de nuevo evento.
     * @return La vista del formulario para agregar un nuevo evento.
     */
    @GetMapping("/add")
    public String add(@ModelAttribute("evento") final EventoDTO eventoDTO) {
        return "evento/add";
    }
    /**
     * Procesa el formulario de envío para agregar un nuevo evento. Si hay errores en el formulario, se vuelve a mostrar el formulario.
     * En caso de éxito, se redirige al usuario a la lista de eventos.
     *
     * @param eventoDTO          El DTO del evento a agregar.
     * @param bindingResult      El resultado de la validación del formulario.
     * @param redirectAttributes Atributos para pasar mensajes entre redirecciones.
     * @param model              El modelo para pasar datos a la vista.
     * @return La vista a la que se redirige al usuario después de procesar el formulario.
     */
    @PostMapping("/add")
    public String add(@ModelAttribute("evento") @Valid final EventoDTO eventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            return "evento/add";
        }
        Usuario u = (Usuario) model.getAttribute("user");
        assert u != null;
        eventoDTO.setOrganizador(u.getUsuarioId());
        eventoService.create(eventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("evento.create.success"));
        return "redirect:/index";
    }
    /**
     * Muestra el formulario para editar un evento existente, identificado por su ID.
     *
     * @param eventoId El ID del evento a editar.
     * @param model    El modelo para pasar datos a la vista.
     * @return La vista del formulario de edición del evento.
     */
    @GetMapping("/edit/{eventoId}")
    public String edit(@PathVariable(name = "eventoId") final Integer eventoId, final Model model) {
        model.addAttribute("evento", eventoService.get(eventoId));
        return "evento/edit";
    }
    /**
     * Procesa el formulario de envío para editar un evento existente. Si hay errores en el formulario, se vuelve a mostrar el formulario.
     * En caso de éxito, se redirige al usuario a la lista de eventos.
     *
     * @param eventoId           El ID del evento a editar.
     * @param eventoDTO          El DTO del evento editado.
     * @param bindingResult      El resultado de la validación del formulario.
     * @param redirectAttributes Atributos para pasar mensajes entre redirecciones.
     * @return La vista a la que se redirige al usuario después de procesar el formulario.
     */
    @PostMapping("/edit/{eventoId}")
    public String edit(@PathVariable(name = "eventoId") final Integer eventoId,
            @ModelAttribute("evento") @Valid final EventoDTO eventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "evento/edit";
        }
        eventoService.update(eventoId, eventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("evento.update.success"));
        return "redirect:/index";
    }
    /**
     * Maneja la solicitud POST para eliminar un evento, identificado por su ID. Si el evento está referenciado por otras entidades,
     * se muestra un mensaje de advertencia. En caso contrario, se elimina el evento y se muestra un mensaje de éxito.
     *
     * @param eventoId           El ID del evento a eliminar.
     * @param redirectAttributes Atributos para pasar mensajes entre redirecciones.
     * @return La vista a la que se redirige al usuario después de procesar la solicitud.
     */
    @PostMapping("/delete/{eventoId}")
    public String delete(@PathVariable(name = "eventoId") final Integer eventoId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = eventoService.getReferencedWarning(eventoId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            eventoService.delete(eventoId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("evento.delete.success"));
        }
        return "redirect:/index";
    }
    /**
     * Muestra los eventos organizados por un usuario específico, identificado por su ID de usuario.
     *
     * @param usuarioId ID del usuario cuyos eventos se quieren visualizar.
     * @param model     El modelo para pasar datos a la vista.
     * @return La vista que muestra los eventos del usuario.
     */
    @GetMapping("/index/verEventos/{usuarioId}")
    public String verMisEventos(@PathVariable("usuarioId") Integer usuarioId, Model model) {
        List<Evento> eventosDelUsuario = eventoRepository.buscarPorOrganizador(usuarioId);
        model.addAttribute("eventos", eventosDelUsuario);
        return "home/misEventos"; // Asegúrate de que la ruta a la plantilla sea correcta
    }

}
