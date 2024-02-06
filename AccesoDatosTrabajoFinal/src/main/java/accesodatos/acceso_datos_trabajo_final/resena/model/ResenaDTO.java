package accesodatos.acceso_datos_trabajo_final.resena.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;


public class ResenaDTO {

    private Integer resenaId;

    @NotNull
    private String texto;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fechaHora;

    @NotNull
    private Integer usuario;

    @NotNull
    private Integer evento;

    public Integer getResenaId() {
        return resenaId;
    }

    public void setResenaId(final Integer resenaId) {
        this.resenaId = resenaId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(final String texto) {
        this.texto = texto;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(final OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
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
