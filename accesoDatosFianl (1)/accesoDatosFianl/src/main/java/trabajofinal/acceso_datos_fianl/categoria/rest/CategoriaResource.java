package trabajofinal.acceso_datos_fianl.categoria.rest;

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
import trabajofinal.acceso_datos_fianl.categoria.model.CategoriaDTO;
import trabajofinal.acceso_datos_fianl.categoria.service.CategoriaService;


@RestController
@RequestMapping(value = "/api/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaDTO> getCategoria(
            @PathVariable(name = "categoriaId") final Integer categoriaId) {
        return ResponseEntity.ok(categoriaService.get(categoriaId));
    }

    @PostMapping
    public ResponseEntity<Integer> createCategoria(
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        final Integer createdCategoriaId = categoriaService.create(categoriaDTO);
        return new ResponseEntity<>(createdCategoriaId, HttpStatus.CREATED);
    }

    @PutMapping("/{categoriaId}")
    public ResponseEntity<Integer> updateCategoria(
            @PathVariable(name = "categoriaId") final Integer categoriaId,
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        categoriaService.update(categoriaId, categoriaDTO);
        return ResponseEntity.ok(categoriaId);
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> deleteCategoria(
            @PathVariable(name = "categoriaId") final Integer categoriaId) {
        categoriaService.delete(categoriaId);
        return ResponseEntity.noContent().build();
    }

}
