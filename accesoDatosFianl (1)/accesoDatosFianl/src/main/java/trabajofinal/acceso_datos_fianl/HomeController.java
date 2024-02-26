package trabajofinal.acceso_datos_fianl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/login";
    }

    @PostMapping("/login")
    public String login() {
        return "home/index";
    }

}
