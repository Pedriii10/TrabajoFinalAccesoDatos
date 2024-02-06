package accesodatos.acceso_datos_trabajo_final.usuario.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


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
        return correoElectronico;
    }

    public void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(final LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
