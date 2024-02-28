package trabajofinal.acceso_datos_fianl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;


@ControllerAdvice
public class GlobalControllerAdvice {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public GlobalControllerAdvice(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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

