package trabajofinal.acceso_datos_fianl.usuario.repos;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreoElectronicoAndContrasena(String correoElectronico, String contrasena);

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombreUsuario")
    Usuario findByNombre(@Param("nombreUsuario") String nombreUsuario);


}
