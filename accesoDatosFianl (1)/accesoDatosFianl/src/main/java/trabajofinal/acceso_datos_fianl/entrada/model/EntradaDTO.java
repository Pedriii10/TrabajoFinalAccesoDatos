package trabajofinal.acceso_datos_fianl.entrada.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EntradaDTO {

    private Integer entradaId;

    @NotNull
    @Size(max = 255)
    private String tipo;

    @NotNull
    private Double precio;

    @NotNull
    private Integer cantidadDisponible;

    @NotNull
    private Integer evento;

}
