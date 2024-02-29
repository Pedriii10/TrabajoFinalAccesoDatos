package trabajofinal.acceso_datos_fianl.usuario.repos;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
/**
 * Interfaz que proporciona métodos para acceder a la base de datos y realizar operaciones CRUD en la tabla de usuarios.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Busca un usuario por su correo electrónico y contraseña.
     *
     * @param correoElectronico El correo electrónico del usuario.
     * @param contrasena        La contraseña del usuario.
     * @return El usuario encontrado, o null si no se encuentra ninguno.
     */
    Usuario findByCorreoElectronicoAndContrasena(String correoElectronico, String contrasena);
    /**
     * Busca un usuario por su nombre.
     *
     * @param nombreUsuario El nombre del usuario.
     * @return El usuario encontrado, o null si no se encuentra ninguno.
     */
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombreUsuario")
    Usuario findByNombre(@Param("nombreUsuario") String nombreUsuario);


}
