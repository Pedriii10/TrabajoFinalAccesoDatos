package trabajofinal.acceso_datos_fianl.evento.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * EventoDTO es un objeto de transferencia de datos que se utiliza para transportar información de eventos entre
 * la capa de presentación y la capa de servicio de la aplicación. Incluye validaciones para asegurar que los datos
 * recibidos sean válidos antes de proceder con operaciones de negocio.
 */
@Getter
@Setter
public class EventoDTO {

    private Integer eventoId;

    /**
     * El nombre del evento, no puede ser null y su longitud máxima es de 255 caracteres.
     */
    @NotNull
    @Size(max = 255)
    private String nombre;

    /**
     * La descripción detallada del evento, no puede ser null.
     */
    @NotNull
    private String descripcion;

    /**
     * La fecha de inicio del evento, debe ser proporcionada en el formato "yyyy-MM-dd".
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    /**
     * La fecha de finalización del evento, debe ser proporcionada en el formato "yyyy-MM-dd".
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    /**
     * La ubicación donde se llevará a cabo el evento, no puede ser null y su longitud máxima es de 255 caracteres.
     */
    @NotNull
    @Size(max = 255)
    private String ubicacion;

    /**
     * Indica si el evento se realizará al aire libre, no puede ser null.
     */
    @NotNull
    private Boolean esExterior;

    /**
     * Indica si el evento es gratuito, no puede ser null.
     */
    @NotNull
    private Boolean esGratis;

    /**
     * La URL o referencia al logo del evento, su longitud máxima es de 255 caracteres.
     */
    @Size(max = 255)
    private String logo;

    /**
     * La URL o referencia al banner del evento, su longitud máxima es de 255 caracteres.
     */
    @Size(max = 255)
    private String banner;

    /**
     * El ID del usuario que organiza el evento, no puede ser null.
     */
    @NotNull
    private Integer organizador;
}

