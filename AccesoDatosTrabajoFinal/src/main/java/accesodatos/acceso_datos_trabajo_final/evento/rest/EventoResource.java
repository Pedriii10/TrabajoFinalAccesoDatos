package accesodatos.acceso_datos_trabajo_final.evento.rest;

import accesodatos.acceso_datos_trabajo_final.evento.model.EventoDTO;
import accesodatos.acceso_datos_trabajo_final.evento.service.EventoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventoResource {

    private final EventoService eventoService;

    public EventoResource(final EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoDTO> getEvento(
            @PathVariable(name = "eventoId") final Integer eventoId) {
        return ResponseEntity.ok(eventoService.get(eventoId));
    }

    @PostMapping
    public ResponseEntity<Integer> createEvento(@RequestBody @Valid final EventoDTO eventoDTO) {
        final Integer createdEventoId = eventoService.create(eventoDTO);
        return new ResponseEntity<>(createdEventoId, HttpStatus.CREATED);
    }

    @PutMapping("/{eventoId}")
    public ResponseEntity<Integer> updateEvento(
            @PathVariable(name = "eventoId") final Integer eventoId,
            @RequestBody @Valid final EventoDTO eventoDTO) {
        eventoService.update(eventoId, eventoDTO);
        return ResponseEntity.ok(eventoId);
    }

    @DeleteMapping("/{eventoId}")
    public ResponseEntity<Void> deleteEvento(
            @PathVariable(name = "eventoId") final Integer eventoId) {
        eventoService.delete(eventoId);
        return ResponseEntity.noContent().build();
    }

}
