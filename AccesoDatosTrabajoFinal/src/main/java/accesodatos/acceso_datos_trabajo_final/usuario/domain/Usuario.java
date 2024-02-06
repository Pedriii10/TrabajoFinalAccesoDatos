package accesodatos.acceso_datos_trabajo_final.usuario.domain;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.inscripcione.domain.Inscripcione;
import accesodatos.acceso_datos_trabajo_final.resena.domain.Resena;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String correoElectronico;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column
    private String fotoPerfil;

    @Column(columnDefinition = "longtext")
    private String descripcion;

    @OneToMany(mappedBy = "organizador")
    private Set<Evento> organizadorEventoes;

    @OneToMany(mappedBy = "usuario")
    private Set<Inscripcione> usuarioInscripciones;

    @OneToMany(mappedBy = "usuario")
    private Set<Resena> usuarioResenas;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

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

    public Set<Evento> getOrganizadorEventoes() {
        return organizadorEventoes;
    }

    public void setOrganizadorEventoes(final Set<Evento> organizadorEventoes) {
        this.organizadorEventoes = organizadorEventoes;
    }

    public Set<Inscripcione> getUsuarioInscripciones() {
        return usuarioInscripciones;
    }

    public void setUsuarioInscripciones(final Set<Inscripcione> usuarioInscripciones) {
        this.usuarioInscripciones = usuarioInscripciones;
    }

    public Set<Resena> getUsuarioResenas() {
        return usuarioResenas;
    }

    public void setUsuarioResenas(final Set<Resena> usuarioResenas) {
        this.usuarioResenas = usuarioResenas;
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
