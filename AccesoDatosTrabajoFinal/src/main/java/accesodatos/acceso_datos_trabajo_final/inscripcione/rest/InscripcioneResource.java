package accesodatos.acceso_datos_trabajo_final.inscripcione.rest;

import accesodatos.acceso_datos_trabajo_final.inscripcione.model.InscripcioneDTO;
import accesodatos.acceso_datos_trabajo_final.inscripcione.service.InscripcioneService;
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
@RequestMapping(value = "/api/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
public class InscripcioneResource {

    private final InscripcioneService inscripcioneService;

    public InscripcioneResource(final InscripcioneService inscripcioneService) {
        this.inscripcioneService = inscripcioneService;
    }

    @GetMapping
    public ResponseEntity<List<InscripcioneDTO>> getAllInscripciones() {
        return ResponseEntity.ok(inscripcioneService.findAll());
    }

    @GetMapping("/{inscripcionId}")
    public ResponseEntity<InscripcioneDTO> getInscripcione(
            @PathVariable(name = "inscripcionId") final Integer inscripcionId) {
        return ResponseEntity.ok(inscripcioneService.get(inscripcionId));
    }

    @PostMapping
    public ResponseEntity<Integer> createInscripcione(
            @RequestBody @Valid final InscripcioneDTO inscripcioneDTO) {
        final Integer createdInscripcionId = inscripcioneService.create(inscripcioneDTO);
        return new ResponseEntity<>(createdInscripcionId, HttpStatus.CREATED);
    }

    @PutMapping("/{inscripcionId}")
    public ResponseEntity<Integer> updateInscripcione(
            @PathVariable(name = "inscripcionId") final Integer inscripcionId,
            @RequestBody @Valid final InscripcioneDTO inscripcioneDTO) {
        inscripcioneService.update(inscripcionId, inscripcioneDTO);
        return ResponseEntity.ok(inscripcionId);
    }

    @DeleteMapping("/{inscripcionId}")
    public ResponseEntity<Void> deleteInscripcione(
            @PathVariable(name = "inscripcionId") final Integer inscripcionId) {
        inscripcioneService.delete(inscripcionId);
        return ResponseEntity.noContent().build();
    }

}
