package trabajofinal.acceso_datos_fianl.evento.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.model.EventoDTO;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.fotos_evento.domain.FotosEvento;
import trabajofinal.acceso_datos_fianl.fotos_evento.repos.FotosEventoRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;
import trabajofinal.acceso_datos_fianl.resena.repos.ResenaRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.NotFoundException;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;

/**
 * EventoService proporciona servicios para operaciones relacionadas con eventos en la aplicación.
 * Esto incluye métodos para crear, leer, actualizar y eliminar eventos, así como otros métodos auxiliares.
 */
@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscripcioneRepository inscripcioneRepository;
    private final FotosEventoRepository fotosEventoRepository;

    private final ResenaRepository resenaRepository;

    /**
     * Construye el servicio con los repositorios necesarios para operaciones sobre eventos y sus entidades relacionadas.
     *
     * @param eventoRepository       Repositorio para la entidad de evento.
     * @param usuarioRepository      Repositorio para la entidad de usuario.
     * @param inscripcioneRepository Repositorio para la entidad de inscripción.
     * @param fotosEventoRepository Repositorio para la entidad de fotos de evento.
     * @param resenaRepository      Repositorio para la entidad de reseña.
     */

    public EventoService(final EventoRepository eventoRepository,
            final UsuarioRepository usuarioRepository,
            final InscripcioneRepository inscripcioneRepository,
            final FotosEventoRepository fotosEventoRepository, final ResenaRepository resenaRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscripcioneRepository = inscripcioneRepository;
        this.fotosEventoRepository = fotosEventoRepository;
        this.resenaRepository = resenaRepository;
    }
    /**
     * Obtiene una lista de todos los eventos disponibles.
     *
     * @return Una lista de eventos DTO.
     */
    public List<EventoDTO> findAll() {
        final List<Evento> eventoes = eventoRepository.findAll(Sort.by("eventoId"));
        return eventoes.stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }
    /**
     * Obtiene una lista de eventos organizados por un usuario específico.
     *
     * @param organizadorId El ID del organizador.
     * @return Una lista de eventos DTO organizados por el usuario especificado.
     */
    public List<EventoDTO> findAllByOrganizadorId(Integer organizadorId) {
        // Utiliza el nuevo método del repositorio para obtener eventos por el ID del organizador
        final List<Evento> eventosDelOrganizador = eventoRepository.findByOrganizadorUsuarioId(organizadorId);
        // Convierte la lista de eventos a EventoDTO y devuélvela
        return eventosDelOrganizador.stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }
    /**
     * Obtiene los detalles de un evento específico identificado por su ID.
     *
     * @param eventoId El ID del evento a obtener.
     * @return Un DTO con los detalles del evento solicitado.
     * @throws NotFoundException si el evento no se encuentra.
     */
    public EventoDTO get(final Integer eventoId) {
        return eventoRepository.findById(eventoId)
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .orElseThrow(NotFoundException::new);
    }
    /**
     * Crea un nuevo evento con la información proporcionada en el DTO de evento.
     *
     * @param eventoDTO El DTO conteniendo la información del evento a crear.
     * @return El ID del evento creado.
     */
    public Integer create(final EventoDTO eventoDTO) {
        final Evento evento = new Evento();
        mapToEntity(eventoDTO, evento);
        return eventoRepository.save(evento).getEventoId();
    }
    /**
     * Actualiza un evento existente identificado por su ID con la información proporcionada en el DTO de evento.
     *
     * @param eventoId  El ID del evento a actualizar.
     * @param eventoDTO El DTO conteniendo la nueva información del evento.
     * @throws NotFoundException si el evento no se encuentra.
     */
    public void update(final Integer eventoId, final EventoDTO eventoDTO) {
        final Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventoDTO, evento);
        eventoRepository.save(evento);
    }
    /**
     * Elimina un evento existente identificado por su ID.
     *
     * @param eventoId El ID del evento a eliminar.
     */
    public void delete(final Integer eventoId) {
        eventoRepository.deleteById(eventoId);
    }
    /**
     * Busca un evento por su ID.
     *
     * @param id El ID del evento a buscar.
     * @return El evento encontrado, o null si no se encuentra.
     */
    public Evento buscarPorId(Long id) {
        return eventoRepository.findByEventoId(id);
    }
    /**
     * Mapea un objeto Evento a un DTO de evento.
     *
     * @param evento    El evento a mapear.
     * @param eventoDTO El DTO de evento donde se mapearán los datos.
     * @return El DTO de evento mapeado.
     */
    private EventoDTO mapToDTO(final Evento evento, final EventoDTO eventoDTO) {
        eventoDTO.setEventoId(evento.getEventoId());
        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setFechaInicio(evento.getFechaInicio());
        eventoDTO.setFechaFin(evento.getFechaFin());
        eventoDTO.setUbicacion(evento.getUbicacion());
        eventoDTO.setEsExterior(evento.getEsExterior());
        eventoDTO.setEsGratis(evento.getEsGratis());
        eventoDTO.setLogo(evento.getLogo());
        eventoDTO.setBanner(evento.getBanner());
        eventoDTO.setOrganizador(evento.getOrganizador() == null ? null : evento.getOrganizador().getUsuarioId());
        return eventoDTO;
    }
    /**
     * Mapea un DTO de evento a un objeto Evento.
     *
     * @param eventoDTO El DTO de evento a mapear.
     * @param evento    El evento donde se mapearán los datos.
     * @return El evento mapeado.
     */
    private Evento mapToEntity(final EventoDTO eventoDTO, final Evento evento) {
        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        evento.setUbicacion(eventoDTO.getUbicacion());
        evento.setEsExterior(eventoDTO.getEsExterior());
        evento.setEsGratis(eventoDTO.getEsGratis());
        evento.setLogo(eventoDTO.getLogo());
        evento.setBanner(eventoDTO.getBanner());
        final Usuario organizador = eventoDTO.getOrganizador() == null ? null : usuarioRepository.findById(eventoDTO.getOrganizador())
                .orElseThrow(() -> new NotFoundException("organizador not found"));
        evento.setOrganizador(organizador);
        return evento;
    }
    /**
     * Obtiene una advertencia si el evento está siendo referenciado por otras entidades.
     *
     * @param eventoId El ID del evento.
     * @return Una advertencia si el evento está siendo referenciado, o null si no lo está.
     */
    public ReferencedWarning getReferencedWarning(final Integer eventoId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(NotFoundException::new);
        final Inscripcione eventoInscripcione = inscripcioneRepository.findFirstByEvento(evento);
        if (eventoInscripcione != null) {
            referencedWarning.setKey("evento.inscripcione.evento.referenced");
            referencedWarning.addParam(eventoInscripcione.getInscripcionId());
            return referencedWarning;
        }
        final FotosEvento eventoFotosEvento = fotosEventoRepository.findFirstByEvento(evento);
        if (eventoFotosEvento != null) {
            referencedWarning.setKey("evento.fotosEvento.evento.referenced");
            referencedWarning.addParam(eventoFotosEvento.getFotoId());
            return referencedWarning;
        }

        final Resena eventoResena = resenaRepository.findFirstByEvento(evento);
        if (eventoResena != null) {
            referencedWarning.setKey("evento.resena.evento.referenced");
            referencedWarning.addParam(eventoResena.getResenaId());
            return referencedWarning;
        }
        return null;
    }


}
