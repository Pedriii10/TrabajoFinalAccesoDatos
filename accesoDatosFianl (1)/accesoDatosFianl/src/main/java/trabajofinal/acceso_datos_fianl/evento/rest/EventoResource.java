package trabajofinal.acceso_datos_fianl.evento.rest;

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
import trabajofinal.acceso_datos_fianl.evento.model.EventoDTO;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.util.ReferencedException;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;
/**
 * EventoResource es un controlador REST que proporciona endpoints para la gestión de eventos.
 * Permite realizar operaciones CRUD sobre eventos, incluyendo la obtención de todos los eventos,
 * la obtención de un evento específico por su ID, la creación de nuevos eventos, la actualización
 * de eventos existentes y la eliminación de eventos.
 */

@RestController
@RequestMapping(value = "/api/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventoResource {

    private final EventoService eventoService;
    /**
     * Construye el controlador con el servicio necesario para operaciones sobre eventos.
     *
     * @param eventoService El servicio que proporciona operaciones sobre eventos.
     */
    public EventoResource(final EventoService eventoService) {
        this.eventoService = eventoService;
    }
    /**
     * Obtiene una lista de todos los eventos disponibles.
     *
     * @return Una respuesta entidad conteniendo la lista de todos los eventos.
     */
    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        return ResponseEntity.ok(eventoService.findAll());
    }
    /**
     * Obtiene los detalles de un evento específico identificado por su ID.
     *
     * @param eventoId El ID del evento a obtener.
     * @return Una respuesta entidad conteniendo los detalles del evento solicitado.
     */
    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoDTO> getEvento(
            @PathVariable(name = "eventoId") final Integer eventoId) {
        return ResponseEntity.ok(eventoService.get(eventoId));
    }
    /**
     * Crea un nuevo evento con la información proporcionada en el DTO de evento.
     *
     * @param eventoDTO El DTO conteniendo la información del evento a crear.
     * @return Una respuesta entidad con el ID del evento creado.
     */
    @PostMapping
    public ResponseEntity<Integer> createEvento(@RequestBody @Valid final EventoDTO eventoDTO) {
        final Integer createdEventoId = eventoService.create(eventoDTO);
        return new ResponseEntity<>(createdEventoId, HttpStatus.CREATED);
    }
    /**
     * Actualiza un evento existente identificado por su ID con la información proporcionada en el DTO de evento.
     *
     * @param eventoId  El ID del evento a actualizar.
     * @param eventoDTO El DTO conteniendo la nueva información del evento.
     * @return Una respuesta entidad con el ID del evento actualizado.
     */
    @PutMapping("/{eventoId}")
    public ResponseEntity<Integer> updateEvento(
            @PathVariable(name = "eventoId") final Integer eventoId,
            @RequestBody @Valid final EventoDTO eventoDTO) {
        eventoService.update(eventoId, eventoDTO);
        return ResponseEntity.ok(eventoId);
    }
    /**
     * Elimina un evento existente identificado por su ID.
     *
     * @param eventoId El ID del evento a eliminar.
     * @return Una respuesta entidad indicando que el contenido ha sido eliminado.
     */
    @DeleteMapping("/{eventoId}")
    public ResponseEntity<Void> deleteEvento(
            @PathVariable(name = "eventoId") final Integer eventoId) {
        final ReferencedWarning referencedWarning = eventoService.getReferencedWarning(eventoId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        eventoService.delete(eventoId);
        return ResponseEntity.noContent().build();
    }

}
