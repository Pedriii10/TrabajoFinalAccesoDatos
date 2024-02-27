package trabajofinal.acceso_datos_fianl.evento.domain;

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
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import trabajofinal.acceso_datos_fianl.entrada.domain.Entrada;
import trabajofinal.acceso_datos_fianl.fotos_evento.domain.FotosEvento;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;


@Entity
@Table(name = "Eventoes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
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
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

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


}
