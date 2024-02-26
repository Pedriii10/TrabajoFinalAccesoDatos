package trabajofinal.acceso_datos_fianl.evento.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import trabajofinal.acceso_datos_fianl.entrada.domain.Entrada;
import trabajofinal.acceso_datos_fianl.entrada.repos.EntradaRepository;
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


@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscripcioneRepository inscripcioneRepository;
    private final FotosEventoRepository fotosEventoRepository;
    private final EntradaRepository entradaRepository;
    private final ResenaRepository resenaRepository;

    public EventoService(final EventoRepository eventoRepository,
            final UsuarioRepository usuarioRepository,
            final InscripcioneRepository inscripcioneRepository,
            final FotosEventoRepository fotosEventoRepository,
            final EntradaRepository entradaRepository, final ResenaRepository resenaRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscripcioneRepository = inscripcioneRepository;
        this.fotosEventoRepository = fotosEventoRepository;
        this.entradaRepository = entradaRepository;
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

    public Integer create(final EventoDTO eventoDTO) {
        final Evento evento = new Evento();
        mapToEntity(eventoDTO, evento);
        return eventoRepository.save(evento).getEventoId();
    }

    public void update(final Integer eventoId, final EventoDTO eventoDTO) {
        final Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventoDTO, evento);
        eventoRepository.save(evento);
    }

    public void delete(final Integer eventoId) {
        eventoRepository.deleteById(eventoId);
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
        eventoDTO.setLogo(evento.getLogo());
        eventoDTO.setBanner(evento.getBanner());
        eventoDTO.setOrganizador(evento.getOrganizador() == null ? null : evento.getOrganizador().getUsuarioId());
        return eventoDTO;
    }

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
        final Entrada eventoEntrada = entradaRepository.findFirstByEvento(evento);
        if (eventoEntrada != null) {
            referencedWarning.setKey("evento.entrada.evento.referenced");
            referencedWarning.addParam(eventoEntrada.getEntradaId());
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
