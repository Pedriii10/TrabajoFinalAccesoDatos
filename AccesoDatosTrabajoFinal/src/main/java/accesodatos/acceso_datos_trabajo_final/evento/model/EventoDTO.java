package accesodatos.acceso_datos_trabajo_final.evento.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;


public class EventoDTO {

    private Integer eventoId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fechaInicio;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fechaFin;

    @NotNull
    @Size(max = 255)
    private String ubicacion;

    @NotNull
    private Boolean esExterior;

    @NotNull
    private Boolean esGratis;

    @Size(max = 255)
    private String logo;

    @Size(max = 255)
    private String banner;

    @NotNull
    private Integer organizador;

    public Integer getEventoId() {
        return eventoId;
    }

    public void setEventoId(final Integer eventoId) {
        this.eventoId = eventoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(final OffsetDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(final OffsetDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(final String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Boolean getEsExterior() {
        return esExterior;
    }

    public void setEsExterior(final Boolean esExterior) {
        this.esExterior = esExterior;
    }

    public Boolean getEsGratis() {
        return esGratis;
    }

    public void setEsGratis(final Boolean esGratis) {
        this.esGratis = esGratis;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(final String banner) {
        this.banner = banner;
    }

    public Integer getOrganizador() {
        return organizador;
    }

    public void setOrganizador(final Integer organizador) {
        this.organizador = organizador;
    }

}
