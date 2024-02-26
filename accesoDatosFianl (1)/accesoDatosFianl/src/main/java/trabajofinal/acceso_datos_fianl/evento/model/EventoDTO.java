package trabajofinal.acceso_datos_fianl.evento.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class EventoDTO {

    private Integer eventoId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fechaInicio;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime fechaFin;

    @NotNull
    @Size(max = 255)
    private String ubicacion;

    @NotNull
    private Boolean esExterior;

    @NotNull
    private Boolean esGratis;

    @Size(max = 255)
    private String logo;

    @Size(max = 255)
    private String banner;

    @NotNull
    private Integer organizador;

}
