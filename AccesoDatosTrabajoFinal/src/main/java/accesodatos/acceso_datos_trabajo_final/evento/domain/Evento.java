package accesodatos.acceso_datos_trabajo_final.evento.domain;

import accesodatos.acceso_datos_trabajo_final.entrada.domain.Entrada;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.domain.FotosEvento;
import accesodatos.acceso_datos_trabajo_final.inscripcione.domain.Inscripcione;
import accesodatos.acceso_datos_trabajo_final.resena.domain.Resena;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Evento {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventoId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "longtext")
    private String descripcion;

    @Column(nullable = false)
    private OffsetDateTime fechaInicio;

    @Column(nullable = false)
    private OffsetDateTime fechaFin;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private Boolean esExterior;

    @Column(nullable = false)
    private Boolean esGratis;

    @Column
    private String logo;

    @Column
    private String banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    @OneToMany(mappedBy = "evento")
    private Set<Inscripcione> eventoInscripciones;

    @OneToMany(mappedBy = "evento")
    private Set<FotosEvento> eventoFotosEventoes;

    @OneToMany(mappedBy = "evento")
    private Set<Entrada> eventoEntradas;

    @OneToMany(mappedBy = "evento")
    private Set<Resena> eventoResenas;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

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

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(final Usuario organizador) {
        this.organizador = organizador;
    }

    public Set<Inscripcione> getEventoInscripciones() {
        return eventoInscripciones;
    }

    public void setEventoInscripciones(final Set<Inscripcione> eventoInscripciones) {
        this.eventoInscripciones = eventoInscripciones;
    }

    public Set<FotosEvento> getEventoFotosEventoes() {
        return eventoFotosEventoes;
    }

    public void setEventoFotosEventoes(final Set<FotosEvento> eventoFotosEventoes) {
        this.eventoFotosEventoes = eventoFotosEventoes;
    }

    public Set<Entrada> getEventoEntradas() {
        return eventoEntradas;
    }

    public void setEventoEntradas(final Set<Entrada> eventoEntradas) {
        this.eventoEntradas = eventoEntradas;
    }

    public Set<Resena> getEventoResenas() {
        return eventoResenas;
    }

    public void setEventoResenas(final Set<Resena> eventoResenas) {
        this.eventoResenas = eventoResenas;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
