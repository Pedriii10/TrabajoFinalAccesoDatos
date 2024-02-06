package accesodatos.acceso_datos_trabajo_final.entrada.controller;

import accesodatos.acceso_datos_trabajo_final.entrada.model.EntradaDTO;
import accesodatos.acceso_datos_trabajo_final.entrada.service.EntradaService;
import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.util.CustomCollectors;
import accesodatos.acceso_datos_trabajo_final.util.WebUtils;
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


@Controller
@RequestMapping("/entradas")
public class EntradaController {

    private final EntradaService entradaService;
    private final EventoRepository eventoRepository;

    public EntradaController(final EntradaService entradaService,
            final EventoRepository eventoRepository) {
        this.entradaService = entradaService;
        this.eventoRepository = eventoRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("eventoValues", eventoRepository.findAll(Sort.by("eventoId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Evento::getEventoId, Evento::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("entradas", entradaService.findAll());
        return "entrada/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("entrada") final EntradaDTO entradaDTO) {
        return "entrada/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("entrada") @Valid final EntradaDTO entradaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "entrada/add";
        }
        entradaService.create(entradaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("entrada.create.success"));
        return "redirect:/entradas";
    }

    @GetMapping("/edit/{entradaId}")
    public String edit(@PathVariable(name = "entradaId") final Integer entradaId,
            final Model model) {
        model.addAttribute("entrada", entradaService.get(entradaId));
        return "entrada/edit";
    }

    @PostMapping("/edit/{entradaId}")
    public String edit(@PathVariable(name = "entradaId") final Integer entradaId,
            @ModelAttribute("entrada") @Valid final EntradaDTO entradaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "entrada/edit";
        }
        entradaService.update(entradaId, entradaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("entrada.update.success"));
        return "redirect:/entradas";
    }

    @PostMapping("/delete/{entradaId}")
    public String delete(@PathVariable(name = "entradaId") final Integer entradaId,
            final RedirectAttributes redirectAttributes) {
        entradaService.delete(entradaId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("entrada.delete.success"));
        return "redirect:/entradas";
    }

}
