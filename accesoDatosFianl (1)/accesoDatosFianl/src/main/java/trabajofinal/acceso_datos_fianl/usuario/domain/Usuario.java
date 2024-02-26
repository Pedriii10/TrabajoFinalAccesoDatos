package trabajofinal.acceso_datos_fianl.usuario.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;


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

}
