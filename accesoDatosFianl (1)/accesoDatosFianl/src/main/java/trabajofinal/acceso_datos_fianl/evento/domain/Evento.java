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
import trabajofinal.acceso_datos_fianl.fotos_evento.domain.FotosEvento;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
/**
 * La clase Evento representa la entidad de un evento en la aplicación, incluyendo todos los detalles relevantes
 * como el nombre, descripción, fechas de inicio y fin, ubicación, y si el evento es al aire libre o gratuito.
 * Además, establece relaciones con otras entidades importantes como el organizador (Usuario), inscripciones (Inscripcione),
 * fotos del evento (FotosEvento), y reseñas (Resena). Esta entidad también es auditada para registrar las fechas de creación
 * y última modificación del registro.
 */
@Entity
@Table(name = "Evento")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
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

    /**
     * El usuario que organiza el evento. Esta relación es de muchos a uno, indicando que cada evento tiene un único organizador.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    /**
     * Las inscripciones asociadas a este evento. Esta es una relación de uno a muchos, indicando que un evento puede tener
     * varias inscripciones.
     */
    @OneToMany(mappedBy = "evento")
    private Set<Inscripcione> eventoInscripciones;

    /**
     * Las fotos asociadas a este evento. Esta es una relación de uno a muchos, indicando que un evento puede tener varias fotos.
     */
    @OneToMany(mappedBy = "evento")
    private Set<FotosEvento> eventoFotosEventoes;

    /**
     * Las reseñas asociadas a este evento. Esta es una relación de uno a muchos, indicando que un evento puede tener varias reseñas.
     */
    @OneToMany(mappedBy = "evento")
    private Set<Resena> eventoResenas;

    /**
     * La fecha y hora en que se creó el registro del evento. Este campo es gestionado automáticamente por Spring Data JPA.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    /**
     * La última fecha y hora en que se actualizó el registro del evento. Este campo es gestionado automáticamente por Spring Data JPA.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}

