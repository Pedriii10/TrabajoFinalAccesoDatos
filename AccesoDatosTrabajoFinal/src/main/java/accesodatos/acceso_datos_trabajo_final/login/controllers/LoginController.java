package accesodatos.acceso_datos_trabajo_final.login.controllers; // actualiza el paquete si es necesario

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login"; // Actualiza la ruta del template si es necesario
    }

}
