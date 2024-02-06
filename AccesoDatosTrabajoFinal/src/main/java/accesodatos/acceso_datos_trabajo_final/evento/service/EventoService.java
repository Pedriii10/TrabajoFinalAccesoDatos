package accesodatos.acceso_datos_trabajo_final.evento.service;

import accesodatos.acceso_datos_trabajo_final.entrada.domain.Entrada;
import accesodatos.acceso_datos_trabajo_final.entrada.repos.EntradaRepository;
import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.model.EventoDTO;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.domain.FotosEvento;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.repos.FotosEventoRepository;
import accesodatos.acceso_datos_trabajo_final.inscripcione.domain.Inscripcione;
import accesodatos.acceso_datos_trabajo_final.inscripcione.repos.InscripcioneRepository;
import accesodatos.acceso_datos_trabajo_final.resena.domain.Resena;
import accesodatos.acceso_datos_trabajo_final.resena.repos.ResenaRepository;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import accesodatos.acceso_datos_trabajo_final.usuario.repos.UsuarioRepository;
import accesodatos.acceso_datos_trabajo_final.util.NotFoundException;
import accesodatos.acceso_datos_trabajo_final.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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

    public String getReferencedWarning(final Integer eventoId) {
        final Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(NotFoundException::new);
        final Inscripcione eventoInscripcione = inscripcioneRepository.findFirstByEvento(evento);
        if (eventoInscripcione != null) {
            return WebUtils.getMessage("evento.inscripcione.evento.referenced", eventoInscripcione.getInscripcionId());
        }
        final FotosEvento eventoFotosEvento = fotosEventoRepository.findFirstByEvento(evento);
        if (eventoFotosEvento != null) {
            return WebUtils.getMessage("evento.fotosEvento.evento.referenced", eventoFotosEvento.getFotoId());
        }
        final Entrada eventoEntrada = entradaRepository.findFirstByEvento(evento);
        if (eventoEntrada != null) {
            return WebUtils.getMessage("evento.entrada.evento.referenced", eventoEntrada.getEntradaId());
        }
        final Resena eventoResena = resenaRepository.findFirstByEvento(evento);
        if (eventoResena != null) {
            return WebUtils.getMessage("evento.resena.evento.referenced", eventoResena.getResenaId());
        }
        return null;
    }

}
