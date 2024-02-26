package trabajofinal.acceso_datos_fianl.resena.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;
import trabajofinal.acceso_datos_fianl.resena.model.ResenaDTO;
import trabajofinal.acceso_datos_fianl.resena.repos.ResenaRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.NotFoundException;


@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public ResenaService(final ResenaRepository resenaRepository,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.resenaRepository = resenaRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<ResenaDTO> findAll() {
        final List<Resena> resenas = resenaRepository.findAll(Sort.by("resenaId"));
        return resenas.stream()
                .map(resena -> mapToDTO(resena, new ResenaDTO()))
                .toList();
    }

    public ResenaDTO get(final Integer resenaId) {
        return resenaRepository.findById(resenaId)
                .map(resena -> mapToDTO(resena, new ResenaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ResenaDTO resenaDTO) {
        final Resena resena = new Resena();
        mapToEntity(resenaDTO, resena);
        return resenaRepository.save(resena).getResenaId();
    }

    public void update(final Integer resenaId, final ResenaDTO resenaDTO) {
        final Resena resena = resenaRepository.findById(resenaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(resenaDTO, resena);
        resenaRepository.save(resena);
    }

    public void delete(final Integer resenaId) {
        resenaRepository.deleteById(resenaId);
    }

    private ResenaDTO mapToDTO(final Resena resena, final ResenaDTO resenaDTO) {
        resenaDTO.setResenaId(resena.getResenaId());
        resenaDTO.setTexto(resena.getTexto());
        resenaDTO.setFechaHora(resena.getFechaHora());
        resenaDTO.setUsuario(resena.getUsuario() == null ? null : resena.getUsuario().getUsuarioId());
        resenaDTO.setEvento(resena.getEvento() == null ? null : resena.getEvento().getEventoId());
        return resenaDTO;
    }

    private Resena mapToEntity(final ResenaDTO resenaDTO, final Resena resena) {
        resena.setTexto(resenaDTO.getTexto());
        resena.setFechaHora(resenaDTO.getFechaHora());
        final Usuario usuario = resenaDTO.getUsuario() == null ? null : usuarioRepository.findById(resenaDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        resena.setUsuario(usuario);
        final Evento evento = resenaDTO.getEvento() == null ? null : eventoRepository.findById(resenaDTO.getEvento())
                .orElseThrow(() -> new NotFoundException("evento not found"));
        resena.setEvento(evento);
        return resena;
    }

}
