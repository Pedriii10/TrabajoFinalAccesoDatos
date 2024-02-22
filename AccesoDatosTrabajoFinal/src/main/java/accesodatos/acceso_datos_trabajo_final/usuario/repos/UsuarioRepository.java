package accesodatos.acceso_datos_trabajo_final.usuario.repos;

import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


}
