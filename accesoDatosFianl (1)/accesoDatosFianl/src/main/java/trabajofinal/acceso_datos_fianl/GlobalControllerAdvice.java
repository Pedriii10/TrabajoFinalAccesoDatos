package trabajofinal.acceso_datos_fianl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
/**
 * GlobalControllerAdvice es una clase de asesoramiento que aplica ciertas lógicas a todos los controladores
 * dentro de la aplicación. En este caso, proporciona un método que automáticamente añade un objeto de usuario
 * a todos los modelos de las vistas, permitiendo un fácil acceso a la información del usuario en todas las vistas.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que inyecta el repositorio de usuarios en este asesoramiento global, lo que permite
     * acceder a la información del usuario en la base de datos.
     *
     * @param usuarioRepository Repositorio de usuarios que se utilizará para obtener información de usuario.
     */
    @Autowired
    public GlobalControllerAdvice(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Añade automáticamente un atributo de usuario al modelo de todas las respuestas de los controladores
     * si existe una sesión con un nombre de usuario. Esto permite que las vistas accedan a la información
     * del usuario actual sin necesidad de pasar explícitamente el usuario a cada controlador.
     *
     * @param session La sesión HTTP actual, que puede contener atributos de usuario como el nombre de usuario.
     * @return Un objeto {@link Usuario} que representa al usuario actualmente autenticado, o {@code null}
     * si no hay un usuario autenticado o el nombre de usuario no está en la sesión.
     */
    @ModelAttribute("user")
    public Usuario globalUser(HttpSession session) {
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        if (nombreUsuario != null) {
            return usuarioRepository.findByNombre(nombreUsuario);
        } else {
            return null;
        }
    }
}


