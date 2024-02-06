package accesodatos.acceso_datos_trabajo_final.categoria.repos;

import accesodatos.acceso_datos_trabajo_final.categoria.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
