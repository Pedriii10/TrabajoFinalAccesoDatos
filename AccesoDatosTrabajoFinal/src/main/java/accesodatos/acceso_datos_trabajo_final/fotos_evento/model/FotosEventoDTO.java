package accesodatos.acceso_datos_trabajo_final.fotos_evento.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class FotosEventoDTO {

    private Integer fotoId;

    @NotNull
    @Size(max = 255)
    private String urlfoto;

    @NotNull
    private Integer evento;

    public Integer getFotoId() {
        return fotoId;
    }

    public void setFotoId(final Integer fotoId) {
        this.fotoId = fotoId;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(final String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(final Integer evento) {
        this.evento = evento;
    }

}
