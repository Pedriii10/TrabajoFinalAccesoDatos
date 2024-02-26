package trabajofinal.acceso_datos_fianl.resena.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.resena.model.ResenaDTO;
import trabajofinal.acceso_datos_fianl.resena.service.ResenaService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.CustomCollectors;
import trabajofinal.acceso_datos_fianl.util.WebUtils;


@Controller
@RequestMapping("/resenas")
public class ResenaController {

    private final ResenaService resenaService;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public ResenaController(final ResenaService resenaService,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.resenaService = resenaService;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usuarioValues", usuarioRepository.findAll(Sort.by("usuarioId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getUsuarioId, Usuario::getNombre)));
        model.addAttribute("eventoValues", eventoRepository.findAll(Sort.by("eventoId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Evento::getEventoId, Evento::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("resenas", resenaService.findAll());
        return "resena/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("resena") final ResenaDTO resenaDTO) {
        return "resena/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("resena") @Valid final ResenaDTO resenaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "resena/add";
        }
        resenaService.create(resenaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("resena.create.success"));
        return "redirect:/resenas";
    }

    @GetMapping("/edit/{resenaId}")
    public String edit(@PathVariable(name = "resenaId") final Integer resenaId, final Model model) {
        model.addAttribute("resena", resenaService.get(resenaId));
        return "resena/edit";
    }

    @PostMapping("/edit/{resenaId}")
    public String edit(@PathVariable(name = "resenaId") final Integer resenaId,
            @ModelAttribute("resena") @Valid final ResenaDTO resenaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "resena/edit";
        }
        resenaService.update(resenaId, resenaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("resena.update.success"));
        return "redirect:/resenas";
    }

    @PostMapping("/delete/{resenaId}")
    public String delete(@PathVariable(name = "resenaId") final Integer resenaId,
            final RedirectAttributes redirectAttributes) {
        resenaService.delete(resenaId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("resena.delete.success"));
        return "redirect:/resenas";
    }

}
