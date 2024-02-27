package trabajofinal.acceso_datos_fianl.usuario.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreoElectronicoAndContrasena(String correoElectronico, String contrasena);
}
