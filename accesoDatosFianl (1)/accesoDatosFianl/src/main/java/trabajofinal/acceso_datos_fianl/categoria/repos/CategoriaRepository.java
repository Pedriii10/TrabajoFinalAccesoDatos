package trabajofinal.acceso_datos_fianl.categoria.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.categoria.domain.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
