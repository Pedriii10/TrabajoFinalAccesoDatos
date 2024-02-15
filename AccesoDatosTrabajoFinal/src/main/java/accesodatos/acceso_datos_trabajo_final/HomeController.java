package accesodatos.acceso_datos_trabajo_final;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHomePage(Model model){
        // Puedes agregar atributos al modelo si es necesario
        // model.addAttribute("attributeName", attributeValue);

        // Retorna el nombre de la plantilla Thymeleaf sin la extensión .html
        return "home";
    }
}
