package accesodatos.acceso_datos_trabajo_final.usuario.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class UsuarioDTO {

    private Integer usuarioId;

    @NotNull
    @Size(max = 255)
    private String nombre;


    @Size(max = 255)
    private String correo_electronico;

    @NotNull
    @Size(max = 255)
    private String contrasena;


    @Size(max = 255)
    private String fotoPerfil;

    private String descripcion;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(final Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correo_electronico;
    }

    public void setCorreoElectronico(final String correoElectronico) {
        this.correo_electronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = contrasena;
    }


    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(final String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

}
