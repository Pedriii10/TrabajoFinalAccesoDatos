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


@Service
public class InscripcioneService {

    private final InscripcioneRepository inscripcioneRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public InscripcioneService(final InscripcioneRepository inscripcioneRepository,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.inscripcioneRepository = inscripcioneRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<InscripcioneDTO> findAll() {
        final List<Inscripcione> inscripciones = inscripcioneRepository.findAll(Sort.by("inscripcionId"));
        return inscripciones.stream()
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .toList();
    }

    public InscripcioneDTO get(final Integer inscripcionId) {
        return inscripcioneRepository.findById(inscripcionId)
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final InscripcioneDTO inscripcioneDTO) {
        final Inscripcione inscripcione = new Inscripcione();
        mapToEntity(inscripcioneDTO, inscripcione);
        return inscripcioneRepository.save(inscripcione).getInscripcionId();
    }

    public void update(final Integer inscripcionId, final InscripcioneDTO inscripcioneDTO) {
        final Inscripcione inscripcione = inscripcioneRepository.findById(inscripcionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(inscripcioneDTO, inscripcione);
        inscripcioneRepository.save(inscripcione);
    }

    public void delete(final Integer inscripcionId) {
        inscripcioneRepository.deleteById(inscripcionId);
    }

    private InscripcioneDTO mapToDTO(final Inscripcione inscripcione,
            final InscripcioneDTO inscripcioneDTO) {
        inscripcioneDTO.setInscripcionId(inscripcione.getInscripcionId());
        inscripcioneDTO.setFechaInscripcion(inscripcione.getFechaInscripcion());
        inscripcioneDTO.setEstado(inscripcione.getEstado());
        inscripcioneDTO.setUsuario(inscripcione.getUsuario() == null ? null : inscripcione.getUsuario().getUsuarioId());
        inscripcioneDTO.setEvento(inscripcione.getEvento() == null ? null : inscripcione.getEvento().getEventoId());
        return inscripcioneDTO;
    }

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


    public List<InscripcioneDTO> findByUsuarioId(Integer usuarioId) {
        final List<Inscripcione> inscripcionesUsuario = inscripcioneRepository.findByUsuarioUsuarioId(usuarioId);

        return inscripcionesUsuario.stream()
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .toList();
    }

    public List<InscripcioneDTO> findByEventoEventoId(Integer eventoId) {
        final List<Inscripcione> inscripcionesEvento = inscripcioneRepository.findByEventoEventoId((eventoId));

        return inscripcionesEvento.stream()
                .map(inscripcione -> mapToDTO(inscripcione, new InscripcioneDTO()))
                .toList();
    }

}
