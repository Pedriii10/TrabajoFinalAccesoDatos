package trabajofinal.acceso_datos_fianl.usuario.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreoElectronicoAndContrasena(String correoElectronico, String contrasena);


}
