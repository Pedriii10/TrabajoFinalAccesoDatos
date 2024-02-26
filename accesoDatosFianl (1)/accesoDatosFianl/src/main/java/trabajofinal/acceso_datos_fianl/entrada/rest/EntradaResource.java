package trabajofinal.acceso_datos_fianl.entrada.rest;

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
import trabajofinal.acceso_datos_fianl.entrada.model.EntradaDTO;
import trabajofinal.acceso_datos_fianl.entrada.service.EntradaService;


@RestController
@RequestMapping(value = "/api/entradas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntradaResource {

    private final EntradaService entradaService;

    public EntradaResource(final EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> getAllEntradas() {
        return ResponseEntity.ok(entradaService.findAll());
    }

    @GetMapping("/{entradaId}")
    public ResponseEntity<EntradaDTO> getEntrada(
            @PathVariable(name = "entradaId") final Integer entradaId) {
        return ResponseEntity.ok(entradaService.get(entradaId));
    }

    @PostMapping
    public ResponseEntity<Integer> createEntrada(@RequestBody @Valid final EntradaDTO entradaDTO) {
        final Integer createdEntradaId = entradaService.create(entradaDTO);
        return new ResponseEntity<>(createdEntradaId, HttpStatus.CREATED);
    }

    @PutMapping("/{entradaId}")
    public ResponseEntity<Integer> updateEntrada(
            @PathVariable(name = "entradaId") final Integer entradaId,
            @RequestBody @Valid final EntradaDTO entradaDTO) {
        entradaService.update(entradaId, entradaDTO);
        return ResponseEntity.ok(entradaId);
    }

    @DeleteMapping("/{entradaId}")
    public ResponseEntity<Void> deleteEntrada(
            @PathVariable(name = "entradaId") final Integer entradaId) {
        entradaService.delete(entradaId);
        return ResponseEntity.noContent().build();
    }

}
