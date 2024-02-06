package accesodatos.acceso_datos_trabajo_final.resena.rest;

import accesodatos.acceso_datos_trabajo_final.resena.model.ResenaDTO;
import accesodatos.acceso_datos_trabajo_final.resena.service.ResenaService;
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
@RequestMapping(value = "/api/resenas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResenaResource {

    private final ResenaService resenaService;

    public ResenaResource(final ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    public ResponseEntity<List<ResenaDTO>> getAllResenas() {
        return ResponseEntity.ok(resenaService.findAll());
    }

    @GetMapping("/{resenaId}")
    public ResponseEntity<ResenaDTO> getResena(
            @PathVariable(name = "resenaId") final Integer resenaId) {
        return ResponseEntity.ok(resenaService.get(resenaId));
    }

    @PostMapping
    public ResponseEntity<Integer> createResena(@RequestBody @Valid final ResenaDTO resenaDTO) {
        final Integer createdResenaId = resenaService.create(resenaDTO);
        return new ResponseEntity<>(createdResenaId, HttpStatus.CREATED);
    }

    @PutMapping("/{resenaId}")
    public ResponseEntity<Integer> updateResena(
            @PathVariable(name = "resenaId") final Integer resenaId,
            @RequestBody @Valid final ResenaDTO resenaDTO) {
        resenaService.update(resenaId, resenaDTO);
        return ResponseEntity.ok(resenaId);
    }

    @DeleteMapping("/{resenaId}")
    public ResponseEntity<Void> deleteResena(
            @PathVariable(name = "resenaId") final Integer resenaId) {
        resenaService.delete(resenaId);
        return ResponseEntity.noContent().build();
    }

}
