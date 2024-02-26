package trabajofinal.acceso_datos_fianl.categoria.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoriaDTO {

    private Integer categoriaId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    private String descripcion;

}
