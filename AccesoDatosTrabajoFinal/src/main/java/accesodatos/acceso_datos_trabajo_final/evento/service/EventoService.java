package accesodatos.acceso_datos_trabajo_final.evento.service;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.model.EventoDTO;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.inscripcione.repos.InscripcioneRepository;
import accesodatos.acceso_datos_trabajo_final.resena.repos.ResenaRepository;
import accesodatos.acceso_datos_trabajo_final.usuario.repos.UsuarioRepository;
import accesodatos.acceso_datos_trabajo_final.util.NotFoundException;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventoService {

    private static EventoRepository eventoRepository = null;
    private static UsuarioRepository usuarioRepository = null;
    private final InscripcioneRepository inscripcioneRepository;
    private final ResenaRepository resenaRepository;

    public EventoService(final EventoRepository eventoRepository,
                         final UsuarioRepository usuarioRepository,
                         final InscripcioneRepository inscripcioneRepository,
                         final ResenaRepository resenaRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscripcioneRepository = inscripcioneRepository;
        this.resenaRepository = resenaRepository;
    }

    public List<EventoDTO> findAll() {
        final List<Evento> eventoes = eventoRepository.findAll(Sort.by("eventoId"));
        return eventoes.stream()
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .toList();
    }

    public EventoDTO get(final Integer eventoId) {
        return eventoRepository.findById(eventoId)
                .map(evento -> mapToDTO(evento, new EventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public static Integer create(final Evento eventoDTO) {
        try {
            final Evento evento = new Evento();
            mapToEntity(eventoDTO, evento);
            Evento savedEvento = eventoRepository.save(evento);
            return savedEvento.getEventoId();
        } catch (Exception e) {
            // Log the exception
            // This logging is just a placeholder, use a proper logger in your project
            System.out.println("An error occurred while saving the event: " + e.getMessage());
            throw new RuntimeException("Failed to save the event", e);
        }
    }




    private EventoDTO mapToDTO(final Evento evento, final EventoDTO eventoDTO) {
        eventoDTO.setEventoId(evento.getEventoId());
        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setFechaInicio(evento.getFechaInicio());
        eventoDTO.setFechaFin(evento.getFechaFin());
        eventoDTO.setUbicacion(evento.getUbicacion());
        eventoDTO.setEsExterior(evento.getEsExterior());
        eventoDTO.setEsGratis(evento.getEsGratis());
        return eventoDTO;
    }

    private static Evento mapToEntity(final Evento eventoDTO, final Evento evento) {
        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        evento.setUbicacion(eventoDTO.getUbicacion());
        evento.setEsExterior(eventoDTO.getEsExterior());
        evento.setEsGratis(eventoDTO.getEsGratis());
        return evento;
    }



}