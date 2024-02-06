package accesodatos.acceso_datos_trabajo_final.usuario.repos;

import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
