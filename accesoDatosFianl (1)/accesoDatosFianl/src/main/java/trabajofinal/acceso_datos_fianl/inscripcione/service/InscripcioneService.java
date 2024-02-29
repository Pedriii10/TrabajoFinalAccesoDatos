package trabajofinal.acceso_datos_fianl.inscripcione.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.inscripcione.model.InscripcioneDTO;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.NotFoundException;

/**
 * Servicio para realizar operaciones relacionadas con las inscripciones.
 */
@Service
public class InscripcioneService {

    private final InscripcioneRepository inscripcioneRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    /**
     * Constructor del servicio que recibe los repositorios necesarios.
     *
     * @param inscripcioneRepository Repositorio de inscripciones.
     * @param usuarioRepository     Repositorio de usuarios.
     * @param eventoRepository      Repositorio de eventos.
     */
    public InscripcioneService(final InscripcioneRepository inscripcioneRepository,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.inscripcioneRepository = inscripcioneRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }
    /**
     * Obtiene todas las inscripciones.
     *
     * @return Lista de inscripciones.
     */
    public List<InscripcioneDTO> findAll() {
        final List<Inscripcione> inscripciones = inscripcioneRepository.findAll(Sort.by("inscripcionId"));
        return inscripciones.stream()
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .toList();
    }
    /**
     * Obtiene una inscripción por su ID.
     *
     * @param inscripcionId ID de la inscripción.
     * @return Inscripción encontrada.
     * @throws NotFoundException Si no se encuentra la inscripción.
     */
    public InscripcioneDTO get(final Integer inscripcionId) {
        return inscripcioneRepository.findById(inscripcionId)
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .orElseThrow(NotFoundException::new);
    }
    /**
     * Crea una nueva inscripción.
     *
     * @param inscripcioneDTO Datos de la inscripción a crear.
     * @return ID de la inscripción creada.
     */
    public Integer create(final InscripcioneDTO inscripcioneDTO) {
        final Inscripcione inscripcione = new Inscripcione();
        mapToEntity(inscripcioneDTO, inscripcione);
        return inscripcioneRepository.save(inscripcione).getInscripcionId();
    }
    /**
     * Actualiza una inscripción existente.
     *
     * @param inscripcionId   ID de la inscripción a actualizar.
     * @param inscripcioneDTO Nuevos datos de la inscripción.
     */
    public void update(final Integer inscripcionId, final InscripcioneDTO inscripcioneDTO) {
        final Inscripcione inscripcione = inscripcioneRepository.findById(inscripcionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(inscripcioneDTO, inscripcione);
        inscripcioneRepository.save(inscripcione);
    }
    /**
     * Elimina una inscripción existente.
     *
     * @param inscripcionId ID de la inscripción a eliminar.
     */
    public void delete(final Integer inscripcionId) {
        inscripcioneRepository.deleteById(inscripcionId);
    }
    /**
     * Mapea una entidad Inscripcione a un DTO InscripcioneDTO.
     *
     * @param inscripcione   Entidad Inscripcione.
     * @param inscripcioneDTO DTO InscripcioneDTO.
     * @return InscripcioneDTO mapeado.
     */
    private InscripcioneDTO mapToDTO(final Inscripcione inscripcione,
            final InscripcioneDTO inscripcioneDTO) {
        inscripcioneDTO.setInscripcionId(inscripcione.getInscripcionId());
        inscripcioneDTO.setFechaInscripcion(inscripcione.getFechaInscripcion());
        inscripcioneDTO.setEstado(inscripcione.getEstado());
        inscripcioneDTO.setUsuario(inscripcione.getUsuario() == null ? null : inscripcione.getUsuario().getUsuarioId());
        inscripcioneDTO.setEvento(inscripcione.getEvento() == null ? null : inscripcione.getEvento().getEventoId());
        return inscripcioneDTO;
    }
    /**
     * Mapea un DTO InscripcioneDTO a una entidad Inscripcione.
     *
     * @param inscripcioneDTO DTO InscripcioneDTO.
     * @param inscripcione   Entidad Inscripcione.
     * @return Inscripcione mapeado.
     */
    private Inscripcione mapToEntity(final InscripcioneDTO inscripcioneDTO,
            final Inscripcione inscripcione) {
        inscripcione.setFechaInscripcion(inscripcioneDTO.getFechaInscripcion());
        inscripcione.setEstado(inscripcioneDTO.getEstado());
        final Usuario usuario = inscripcioneDTO.getUsuario() == null ? null : usuarioRepository.findById(inscripcioneDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        inscripcione.setUsuario(usuario);
        final Evento evento = inscripcioneDTO.getEvento() == null ? null : eventoRepository.findById(inscripcioneDTO.getEvento())
                .orElseThrow(() -> new NotFoundException("evento not found"));
        inscripcione.setEvento(evento);
        return inscripcione;
    }
    /**
     * Realiza la inscripción de un usuario en un evento.
     *
     * @param eventoId  ID del evento.
     * @param usuarioId ID del usuario.
     * @return Mensaje indicando el resultado de la inscripción.
     * @throws NotFoundException Si el usuario o el evento no existen.
     */
    public String inscribirse(Integer eventoId, Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado"));

        if (evento.getOrganizador().equals(usuario)) {
            return "No puedes inscribirte en tu propio evento.";
        }

        if (inscripcioneRepository.existsByEventoAndUsuario(evento, usuario)) {
            return "Ya estás inscrito en este evento.";
        }

        Inscripcione inscripcione = new Inscripcione();
        inscripcione.setUsuario(usuario);
        inscripcione.setEvento(evento);
        inscripcione.setFechaInscripcion(LocalDate.now());
        inscripcione.setEstado("PENDIENTE"); // O el estado inicial que desees

        inscripcioneRepository.save(inscripcione);
        return "Inscripción realizada con éxito.";
    }
    /**
     * Busca las inscripciones de un usuario por su ID.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de inscripciones del usuario.
     */
    public List<InscripcioneDTO> findByUsuarioId(Integer usuarioId) {
        final List<Inscripcione> inscripcionesUsuario = inscripcioneRepository.findByUsuarioUsuarioId(usuarioId);

        return inscripcionesUsuario.stream()
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .toList();
    }
    /**
     * Busca las inscripciones de un evento por su ID.
     *
     * @param eventoId ID del evento.
     * @return Lista de inscripciones del evento.
     */
    public List<InscripcioneDTO> findByEventoEventoId(Integer eventoId) {
        final List<Inscripcione> inscripcionesEvento = inscripcioneRepository.findByEventoEventoId((eventoId));

        return inscripcionesEvento.stream()
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .toList();
    }
}
