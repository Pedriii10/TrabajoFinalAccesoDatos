package accesodatos.acceso_datos_trabajo_final.entrada.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class EntradaDTO {

    private Integer entradaId;

    @NotNull
    @Size(max = 255)
    private String tipo;

    @NotNull
    private Double precio;

    @NotNull
    private Integer cantidadDisponible;

    @NotNull
    private Integer evento;

    public Integer getEntradaId() {
        return entradaId;
    }

    public void setEntradaId(final Integer entradaId) {
        this.entradaId = entradaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(final Double precio) {
        this.precio = precio;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(final Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(final Integer evento) {
        this.evento = evento;
    }

}
