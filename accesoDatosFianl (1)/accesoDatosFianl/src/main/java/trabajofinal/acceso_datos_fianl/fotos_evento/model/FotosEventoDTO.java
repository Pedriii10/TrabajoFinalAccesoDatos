package trabajofinal.acceso_datos_fianl.fotos_evento.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FotosEventoDTO {

    private Integer fotoId;

    @NotNull
    @Size(max = 255)
    private String urlfoto;

    @NotNull
    private Integer evento;

}
