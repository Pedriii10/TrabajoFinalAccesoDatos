package accesodatos.acceso_datos_trabajo_final.fotos_evento.service;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.domain.FotosEvento;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.model.FotosEventoDTO;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.repos.FotosEventoRepository;
import accesodatos.acceso_datos_trabajo_final.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FotosEventoService {

    private final FotosEventoRepository fotosEventoRepository;
    private final EventoRepository eventoRepository;

    public FotosEventoService(final FotosEventoRepository fotosEventoRepository,
            final EventoRepository eventoRepository) {
        this.fotosEventoRepository = fotosEventoRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<FotosEventoDTO> findAll() {
        final List<FotosEvento> fotosEventoes = fotosEventoRepository.findAll(Sort.by("fotoId"));
        return fotosEventoes.stream()
                .map(fotosEvento -> mapToDTO(fotosEvento, new FotosEventoDTO()))
                .toList();
    }

    public FotosEventoDTO get(final Integer fotoId) {
        return fotosEventoRepository.findById(fotoId)
                .map(fotosEvento -> mapToDTO(fotosEvento, new FotosEventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FotosEventoDTO fotosEventoDTO) {
        final FotosEvento fotosEvento = new FotosEvento();
        mapToEntity(fotosEventoDTO, fotosEvento);
        return fotosEventoRepository.save(fotosEvento).getFotoId();
    }

    public void update(final Integer fotoId, final FotosEventoDTO fotosEventoDTO) {
        final FotosEvento fotosEvento = fotosEventoRepository.findById(fotoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fotosEventoDTO, fotosEvento);
        fotosEventoRepository.save(fotosEvento);
    }

    public void delete(final Integer fotoId) {
        fotosEventoRepository.deleteById(fotoId);
    }

    private FotosEventoDTO mapToDTO(final FotosEvento fotosEvento,
            final FotosEventoDTO fotosEventoDTO) {
        fotosEventoDTO.setFotoId(fotosEvento.getFotoId());
        fotosEventoDTO.setUrlfoto(fotosEvento.getUrlfoto());
        fotosEventoDTO.setEvento(fotosEvento.getEvento() == null ? null : fotosEvento.getEvento().getEventoId());
        return fotosEventoDTO;
    }

    private FotosEvento mapToEntity(final FotosEventoDTO fotosEventoDTO,
            final FotosEvento fotosEvento) {
        fotosEvento.setUrlfoto(fotosEventoDTO.getUrlfoto());
        final Evento evento = fotosEventoDTO.getEvento() == null ? null : eventoRepository.findById(fotosEventoDTO.getEvento())
                .orElseThrow(() -> new NotFoundException("evento not found"));
        fotosEvento.setEvento(evento);
        return fotosEvento;
    }

}
