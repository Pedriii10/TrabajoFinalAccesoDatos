package accesodatos.acceso_datos_trabajo_final.usuario.rest;

import accesodatos.acceso_datos_trabajo_final.usuario.model.UsuarioDTO;
import accesodatos.acceso_datos_trabajo_final.usuario.service.UsuarioService;
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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "usuarioId") final Integer usuarioId) {
        return ResponseEntity.ok(usuarioService.get(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdUsuarioId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdUsuarioId, HttpStatus.CREATED);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Integer> updateUsuario(
            @PathVariable(name = "usuarioId") final Integer usuarioId,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(usuarioId, usuarioDTO);
        return ResponseEntity.ok(usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "usuarioId") final Integer usuarioId) {
        usuarioService.delete(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
