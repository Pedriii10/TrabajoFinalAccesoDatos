package trabajofinal.acceso_datos_fianl.inscripcione.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
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
