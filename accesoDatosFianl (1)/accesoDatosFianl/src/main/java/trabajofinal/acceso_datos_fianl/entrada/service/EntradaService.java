package trabajofinal.acceso_datos_fianl.entrada.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import trabajofinal.acceso_datos_fianl.entrada.domain.Entrada;
import trabajofinal.acceso_datos_fianl.entrada.model.EntradaDTO;
import trabajofinal.acceso_datos_fianl.entrada.repos.EntradaRepository;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.util.NotFoundException;


@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final EventoRepository eventoRepository;

    public EntradaService(final EntradaRepository entradaRepository,
            final EventoRepository eventoRepository) {
        this.entradaRepository = entradaRepository;
        this.eventoRepository = eventoRepository;
    }

    public List<EntradaDTO> findAll() {
        final List<Entrada> entradas = entradaRepository.findAll(Sort.by("entradaId"));
        return entradas.stream()
                .map(entrada -> mapToDTO(entrada, new EntradaDTO()))
                .toList();
    }

    public EntradaDTO get(final Integer entradaId) {
        return entradaRepository.findById(entradaId)
                .map(entrada -> mapToDTO(entrada, new EntradaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final EntradaDTO entradaDTO) {
        final Entrada entrada = new Entrada();
        mapToEntity(entradaDTO, entrada);
        return entradaRepository.save(entrada).getEntradaId();
    }

    public void update(final Integer entradaId, final EntradaDTO entradaDTO) {
        final Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(entradaDTO, entrada);
        entradaRepository.save(entrada);
    }

    public void delete(final Integer entradaId) {
        entradaRepository.deleteById(entradaId);
    }

    private EntradaDTO mapToDTO(final Entrada entrada, final EntradaDTO entradaDTO) {
        entradaDTO.setEntradaId(entrada.getEntradaId());
        entradaDTO.setTipo(entrada.getTipo());
        entradaDTO.setPrecio(entrada.getPrecio());
        entradaDTO.setCantidadDisponible(entrada.getCantidadDisponible());
        entradaDTO.setEvento(entrada.getEvento() == null ? null : entrada.getEvento().getEventoId());
        return entradaDTO;
    }

    private Entrada mapToEntity(final EntradaDTO entradaDTO, final Entrada entrada) {
        entrada.setTipo(entradaDTO.getTipo());
        entrada.setPrecio(entradaDTO.getPrecio());
        entrada.setCantidadDisponible(entradaDTO.getCantidadDisponible());
        final Evento evento = entradaDTO.getEvento() == null ? null : eventoRepository.findById(entradaDTO.getEvento())
                .orElseThrow(() -> new NotFoundException("evento not found"));
        entrada.setEvento(evento);
        return entrada;
    }

}
