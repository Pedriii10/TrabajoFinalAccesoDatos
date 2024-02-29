package trabajofinal.acceso_datos_fianl.inscripcione.rest;

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
import trabajofinal.acceso_datos_fianl.inscripcione.model.InscripcioneDTO;
import trabajofinal.acceso_datos_fianl.inscripcione.service.InscripcioneService;

/**
 * Controlador REST para manejar las operaciones CRUD de las inscripciones.
 */
@RestController
@RequestMapping(value = "/api/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
public class InscripcioneResource {

    private final InscripcioneService inscripcioneService;
    /**
     * Constructor del controlador que recibe el servicio de inscripciones.
     *
     * @param inscripcioneService Servicio de inscripciones a inyectar.
     */
    public InscripcioneResource(final InscripcioneService inscripcioneService) {
        this.inscripcioneService = inscripcioneService;
    }
    /**
     * Obtiene todas las inscripciones.
     *
     * @return ResponseEntity con la lista de inscripciones y estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<InscripcioneDTO>> getAllInscripciones() {
        return ResponseEntity.ok(inscripcioneService.findAll());
    }
    /**
     * Obtiene una inscripción por su ID.
     *
     * @param inscripcionId ID de la inscripción a obtener.
     * @return ResponseEntity con la inscripción y estado HTTP 200 (OK).
     */
    @GetMapping("/{inscripcionId}")
    public ResponseEntity<InscripcioneDTO> getInscripcione(
            @PathVariable(name = "inscripcionId") final Integer inscripcionId) {
        return ResponseEntity.ok(inscripcioneService.get(inscripcionId));
    }
    /**
     * Crea una nueva inscripción.
     *
     * @param inscripcioneDTO Datos de la nueva inscripción.
     * @return ResponseEntity con el ID de la nueva inscripción y estado HTTP 201 (Creado).
     */
    @PostMapping
    public ResponseEntity<Integer> createInscripcione(
            @RequestBody @Valid final InscripcioneDTO inscripcioneDTO) {
        final Integer createdInscripcionId = inscripcioneService.create(inscripcioneDTO);
        return new ResponseEntity<>(createdInscripcionId, HttpStatus.CREATED);
    }
    /**
     * Actualiza una inscripción existente.
     *
     * @param inscripcionId   ID de la inscripción a actualizar.
     * @param inscripcioneDTO Nuevos datos de la inscripción.
     * @return ResponseEntity con el ID de la inscripción actualizada y estado HTTP 200 (OK).
     */
    @PutMapping("/{inscripcionId}")
    public ResponseEntity<Integer> updateInscripcione(
            @PathVariable(name = "inscripcionId") final Integer inscripcionId,
            @RequestBody @Valid final InscripcioneDTO inscripcioneDTO) {
        inscripcioneService.update(inscripcionId, inscripcioneDTO);
        return ResponseEntity.ok(inscripcionId);
    }
    /**
     * Elimina una inscripción existente.
     *
     * @param inscripcionId ID de la inscripción a eliminar.
     * @return ResponseEntity con estado HTTP 204 (Sin contenido).
     */
    @DeleteMapping("/{inscripcionId}")
    public ResponseEntity<Void> deleteInscripcione(
            @PathVariable(name = "inscripcionId") final Integer inscripcionId) {
        inscripcioneService.delete(inscripcionId);
        return ResponseEntity.noContent().build();
    }

}
