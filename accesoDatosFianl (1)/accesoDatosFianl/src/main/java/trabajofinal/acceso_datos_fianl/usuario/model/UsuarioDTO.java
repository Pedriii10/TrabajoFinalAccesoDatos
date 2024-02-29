package trabajofinal.acceso_datos_fianl.usuario.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa el modelo de datos de un usuario utilizado para transferencia de datos.
 */
@Getter
@Setter
public class UsuarioDTO {

    private Integer usuarioId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String correoElectronico;

    @NotNull
    @Size(max = 255)
    private String contrasena;

    @NotNull
    private LocalDate fechaRegistro;

    @Size(max = 255)
    private byte[] fotoPerfil;

    private String descripcion;

}
