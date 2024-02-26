package trabajofinal.acceso_datos_fianl.resena.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class ResenaDTO {

    private Integer resenaId;

    @NotNull
    private String texto;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fechaHora;

    @NotNull
    private Integer usuario;

    @NotNull
    private Integer evento;

}
