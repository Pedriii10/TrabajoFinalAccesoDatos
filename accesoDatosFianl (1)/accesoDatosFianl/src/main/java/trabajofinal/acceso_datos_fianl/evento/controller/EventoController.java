package trabajofinal.acceso_datos_fianl.evento.controller;

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
import trabajofinal.acceso_datos_fianl.evento.model.EventoDTO;
import trabajofinal.acceso_datos_fianl.evento.service.EventoService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.CustomCollectors;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;
import trabajofinal.acceso_datos_fianl.util.WebUtils;


@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;
    private final UsuarioRepository usuarioRepository;

    public EventoController(final EventoService eventoService,
            final UsuarioRepository usuarioRepository) {
        this.eventoService = eventoService;
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("organizadorValues", usuarioRepository.findAll(Sort.by("usuarioId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getUsuarioId, Usuario::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("eventoes", eventoService.findAll());
        return "evento/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("evento") final EventoDTO eventoDTO) {
        return "evento/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("evento") @Valid final EventoDTO eventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "evento/add";
        }
        eventoService.create(eventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("evento.create.success"));
        return "redirect:/index";
    }

    @GetMapping("/edit/{eventoId}")
    public String edit(@PathVariable(name = "eventoId") final Integer eventoId, final Model model) {
        model.addAttribute("evento", eventoService.get(eventoId));
        return "evento/edit";
    }

    @PostMapping("/edit/{eventoId}")
    public String edit(@PathVariable(name = "eventoId") final Integer eventoId,
            @ModelAttribute("evento") @Valid final EventoDTO eventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "evento/edit";
        }
        eventoService.update(eventoId, eventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("evento.update.success"));
        return "redirect:/index";
    }

    @PostMapping("/delete/{eventoId}")
    public String delete(@PathVariable(name = "eventoId") final Integer eventoId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = eventoService.getReferencedWarning(eventoId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            eventoService.delete(eventoId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("evento.delete.success"));
        }
        return "redirect:/index";
    }

}
