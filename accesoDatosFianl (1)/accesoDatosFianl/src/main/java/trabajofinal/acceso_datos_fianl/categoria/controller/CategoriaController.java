package trabajofinal.acceso_datos_fianl.categoria.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.categoria.model.CategoriaDTO;
import trabajofinal.acceso_datos_fianl.categoria.service.CategoriaService;
import trabajofinal.acceso_datos_fianl.util.WebUtils;


@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        return "categoria/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("categoria") final CategoriaDTO categoriaDTO) {
        return "categoria/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("categoria") @Valid final CategoriaDTO categoriaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "categoria/add";
        }
        categoriaService.create(categoriaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("categoria.create.success"));
        return "redirect:/categorias";
    }

    @GetMapping("/edit/{categoriaId}")
    public String edit(@PathVariable(name = "categoriaId") final Integer categoriaId,
            final Model model) {
        model.addAttribute("categoria", categoriaService.get(categoriaId));
        return "categoria/edit";
    }

    @PostMapping("/edit/{categoriaId}")
    public String edit(@PathVariable(name = "categoriaId") final Integer categoriaId,
            @ModelAttribute("categoria") @Valid final CategoriaDTO categoriaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "categoria/edit";
        }
        categoriaService.update(categoriaId, categoriaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("categoria.update.success"));
        return "redirect:/categorias";
    }

    @PostMapping("/delete/{categoriaId}")
    public String delete(@PathVariable(name = "categoriaId") final Integer categoriaId,
            final RedirectAttributes redirectAttributes) {
        categoriaService.delete(categoriaId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("categoria.delete.success"));
        return "redirect:/categorias";
    }

}
