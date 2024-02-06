package accesodatos.acceso_datos_trabajo_final.fotos_evento.rest;

import accesodatos.acceso_datos_trabajo_final.fotos_evento.model.FotosEventoDTO;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.service.FotosEventoService;
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
@RequestMapping(value = "/api/fotosEventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class FotosEventoResource {

    private final FotosEventoService fotosEventoService;

    public FotosEventoResource(final FotosEventoService fotosEventoService) {
        this.fotosEventoService = fotosEventoService;
    }

    @GetMapping
    public ResponseEntity<List<FotosEventoDTO>> getAllFotosEventos() {
        return ResponseEntity.ok(fotosEventoService.findAll());
    }

    @GetMapping("/{fotoId}")
    public ResponseEntity<FotosEventoDTO> getFotosEvento(
            @PathVariable(name = "fotoId") final Integer fotoId) {
        return ResponseEntity.ok(fotosEventoService.get(fotoId));
    }

    @PostMapping
    public ResponseEntity<Integer> createFotosEvento(
            @RequestBody @Valid final FotosEventoDTO fotosEventoDTO) {
        final Integer createdFotoId = fotosEventoService.create(fotosEventoDTO);
        return new ResponseEntity<>(createdFotoId, HttpStatus.CREATED);
    }

    @PutMapping("/{fotoId}")
    public ResponseEntity<Integer> updateFotosEvento(
            @PathVariable(name = "fotoId") final Integer fotoId,
            @RequestBody @Valid final FotosEventoDTO fotosEventoDTO) {
        fotosEventoService.update(fotoId, fotosEventoDTO);
        return ResponseEntity.ok(fotoId);
    }

    @DeleteMapping("/{fotoId}")
    public ResponseEntity<Void> deleteFotosEvento(
            @PathVariable(name = "fotoId") final Integer fotoId) {
        fotosEventoService.delete(fotoId);
        return ResponseEntity.noContent().build();
    }

}
