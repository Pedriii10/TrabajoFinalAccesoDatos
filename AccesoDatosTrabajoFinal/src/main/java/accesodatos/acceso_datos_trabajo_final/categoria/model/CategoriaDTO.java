package accesodatos.acceso_datos_trabajo_final.categoria.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CategoriaDTO {

    private Integer categoriaId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    private String descripcion;

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(final Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

}
