package accesodatos.acceso_datos_trabajo_final.inscripcione.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class InscripcioneDTO {

    private Integer inscripcionId;

    @NotNull
    private LocalDate fechaInscripcion;

    @NotNull
    @Size(max = 255)
    private String estado;

    @NotNull
    private Integer usuario;

    @NotNull
    private Integer evento;

    public Integer getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(final Integer inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(final LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(final Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(final Integer evento) {
        this.evento = evento;
    }

}
