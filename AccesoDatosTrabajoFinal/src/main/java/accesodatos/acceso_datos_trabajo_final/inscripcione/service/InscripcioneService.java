package accesodatos.acceso_datos_trabajo_final.inscripcione.service;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.inscripcione.domain.Inscripcione;
import accesodatos.acceso_datos_trabajo_final.inscripcione.model.InscripcioneDTO;
import accesodatos.acceso_datos_trabajo_final.inscripcione.repos.InscripcioneRepository;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import accesodatos.acceso_datos_trabajo_final.usuario.repos.UsuarioRepository;
import accesodatos.acceso_datos_trabajo_final.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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

}
