package trabajofinal.acceso_datos_fianl.inscripcione.controller;

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
import trabajofinal.acceso_datos_fianl.inscripcione.model.InscripcioneDTO;
import trabajofinal.acceso_datos_fianl.inscripcione.service.InscripcioneService;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.CustomCollectors;
import trabajofinal.acceso_datos_fianl.util.WebUtils;


@Controller
@RequestMapping("/inscripciones")
public class InscripcioneController {

    private final InscripcioneService inscripcioneService;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public InscripcioneController(final InscripcioneService inscripcioneService,
            final UsuarioRepository usuarioRepository, final EventoRepository eventoRepository) {
        this.inscripcioneService = inscripcioneService;
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
        model.addAttribute("inscripciones", inscripcioneService.findAll());
        return "inscripcione/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("inscripcione") final InscripcioneDTO inscripcioneDTO) {
        return "inscripcione/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("inscripcione") @Valid final InscripcioneDTO inscripcioneDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inscripcione/add";
        }
        inscripcioneService.create(inscripcioneDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inscripcione.create.success"));
        return "redirect:/inscripciones";
    }

    @GetMapping("/edit/{inscripcionId}")
    public String edit(@PathVariable(name = "inscripcionId") final Integer inscripcionId,
            final Model model) {
        model.addAttribute("inscripcione", inscripcioneService.get(inscripcionId));
        return "inscripcione/edit";
    }

    @PostMapping("/edit/{inscripcionId}")
    public String edit(@PathVariable(name = "inscripcionId") final Integer inscripcionId,
            @ModelAttribute("inscripcione") @Valid final InscripcioneDTO inscripcioneDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inscripcione/edit";
        }
        inscripcioneService.update(inscripcionId, inscripcioneDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inscripcione.update.success"));
        return "redirect:/inscripciones";
    }

    @PostMapping("/delete/{inscripcionId}")
    public String delete(@PathVariable(name = "inscripcionId") final Integer inscripcionId,
            final RedirectAttributes redirectAttributes) {
        inscripcioneService.delete(inscripcionId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inscripcione.delete.success"));
        return "redirect:/inscripciones";
    }

}
