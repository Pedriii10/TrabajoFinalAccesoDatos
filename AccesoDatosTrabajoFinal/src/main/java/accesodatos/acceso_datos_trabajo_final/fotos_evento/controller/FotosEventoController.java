package accesodatos.acceso_datos_trabajo_final.fotos_evento.controller;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.model.FotosEventoDTO;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.service.FotosEventoService;
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
@RequestMapping("/fotosEventos")
public class FotosEventoController {

    private final FotosEventoService fotosEventoService;
    private final EventoRepository eventoRepository;

    public FotosEventoController(final FotosEventoService fotosEventoService,
            final EventoRepository eventoRepository) {
        this.fotosEventoService = fotosEventoService;
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
        model.addAttribute("fotosEventoes", fotosEventoService.findAll());
        return "fotosEvento/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("fotosEvento") final FotosEventoDTO fotosEventoDTO) {
        return "fotosEvento/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("fotosEvento") @Valid final FotosEventoDTO fotosEventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fotosEvento/add";
        }
        fotosEventoService.create(fotosEventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("fotosEvento.create.success"));
        return "redirect:/fotosEventos";
    }

    @GetMapping("/edit/{fotoId}")
    public String edit(@PathVariable(name = "fotoId") final Integer fotoId, final Model model) {
        model.addAttribute("fotosEvento", fotosEventoService.get(fotoId));
        return "fotosEvento/edit";
    }

    @PostMapping("/edit/{fotoId}")
    public String edit(@PathVariable(name = "fotoId") final Integer fotoId,
            @ModelAttribute("fotosEvento") @Valid final FotosEventoDTO fotosEventoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fotosEvento/edit";
        }
        fotosEventoService.update(fotoId, fotosEventoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("fotosEvento.update.success"));
        return "redirect:/fotosEventos";
    }

    @PostMapping("/delete/{fotoId}")
    public String delete(@PathVariable(name = "fotoId") final Integer fotoId,
            final RedirectAttributes redirectAttributes) {
        fotosEventoService.delete(fotoId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("fotosEvento.delete.success"));
        return "redirect:/fotosEventos";
    }

}
