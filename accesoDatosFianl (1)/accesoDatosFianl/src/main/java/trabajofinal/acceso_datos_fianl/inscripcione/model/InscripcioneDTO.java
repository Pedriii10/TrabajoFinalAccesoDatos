package trabajofinal.acceso_datos_fianl.inscripcione.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * InscripcioneDTO es un objeto de transferencia de datos (DTO) que representa
 * la información de una inscripción a un evento en el sistema.
 */
@Getter
@Setter
@ToString
public class InscripcioneDTO {

    private Integer inscripcionId;

    @NotNull
    private LocalDate fechaInscripcion;

    @NotNull
    @Size(max = 255)
    private String estado;

    @NotNull
    private Integer usuario;

    @NotNull
    private Integer evento;

}
