package trabajofinal.acceso_datos_fianl.usuario.rest;

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
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;
import trabajofinal.acceso_datos_fianl.util.ReferencedException;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;

/**
 * Controlador REST que maneja las solicitudes relacionadas con los usuarios.
 */
@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;
    /**
     * Constructor de la clase.
     *
     * @param usuarioService El servicio de usuarios a inyectar.
     */
    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    /**
     * Maneja las solicitudes GET para obtener todos los usuarios.
     *
     * @return ResponseEntity con la lista de usuarios y el código de estado HTTP OK.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
    /**
     * Maneja las solicitudes GET para obtener un usuario por su ID.
     *
     * @param usuarioId El ID del usuario a buscar.
     * @return ResponseEntity con el usuario encontrado y el código de estado HTTP OK.
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "usuarioId") final Integer usuarioId) {
        return ResponseEntity.ok(usuarioService.get(usuarioId));
    }
    /**
     * Maneja las solicitudes POST para crear un nuevo usuario.
     *
     * @param usuarioDTO Los datos del usuario a crear.
     * @return ResponseEntity con el ID del usuario creado y el código de estado HTTP CREATED.
     */
    @PostMapping
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdUsuarioId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdUsuarioId, HttpStatus.CREATED);
    }
    /**
     * Maneja las solicitudes PUT para actualizar un usuario existente.
     *
     * @param usuarioId  El ID del usuario a actualizar.
     * @param usuarioDTO Los nuevos datos del usuario.
     * @return ResponseEntity con el ID del usuario actualizado y el código de estado HTTP OK.
     */
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Integer> updateUsuario(
            @PathVariable(name = "usuarioId") final Integer usuarioId,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(usuarioId, usuarioDTO);
        return ResponseEntity.ok(usuarioId);
    }
    /**
     * Maneja las solicitudes DELETE para eliminar un usuario.
     *
     * @param usuarioId El ID del usuario a eliminar.
     * @return ResponseEntity con el código de estado HTTP NO_CONTENT si se elimina con éxito.
     * @throws ReferencedException Si hay una referencia al usuario en otras entidades.
     */
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "usuarioId") final Integer usuarioId) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(usuarioId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(usuarioId);
        return ResponseEntity.noContent().build();
    }


    static class LoginRequest {
        private String correo;
        private String contrasena;

        // Getters y setters
        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }



}
