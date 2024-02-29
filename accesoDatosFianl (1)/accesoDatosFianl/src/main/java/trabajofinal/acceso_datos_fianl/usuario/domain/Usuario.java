package trabajofinal.acceso_datos_fianl.usuario.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;

/**
 * Clase que representa la entidad Usuario en la base de datos.
 */
@Entity
@Table(name = "Usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
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

    @Lob
    @Column(name = "fotoPerfil")
    private byte[] fotoPerfil;

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

}
