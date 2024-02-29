package trabajofinal.acceso_datos_fianl.usuario.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.service.UsuarioService;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void list() {
        String viewName = usuarioController.list(model);
        assertEquals("usuario/list", viewName);
        verify(model, times(1)).addAttribute(eq("usuarios"), any());
    }

    @Test
    void add() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        String viewName = usuarioController.add(usuarioDTO);
        assertEquals("usuario/add", viewName);
    }

    @Test
    void testAdd() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = usuarioController.add(usuarioDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/", viewName);
        verify(usuarioService, times(1)).create(usuarioDTO);
        verify(redirectAttributes, times(1)).addFlashAttribute(anyString(), anyString());
    }

    @Test
    void edit() {
        Integer usuarioId = 1;
        when(usuarioService.get(usuarioId)).thenReturn(new UsuarioDTO());

        String viewName = usuarioController.edit(usuarioId, model);

        assertEquals("usuario/edit", viewName);
        verify(model, times(1)).addAttribute(eq("usuario"), any(UsuarioDTO.class));
    }

    @Test
    void testEdit() {
        Integer usuarioId = 1;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = usuarioController.edit(usuarioId, usuarioDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/usuarios", viewName);
        verify(usuarioService, times(1)).update(eq(usuarioId), any(UsuarioDTO.class));
        verify(redirectAttributes, times(1)).addFlashAttribute(anyString(), anyString());
    }

    @Test
    void delete() {
        Integer usuarioId = 1;

        String viewName = usuarioController.delete(usuarioId, redirectAttributes);

        assertEquals("redirect:/usuarios", viewName);
        verify(usuarioService, times(1)).delete(usuarioId);
        verify(redirectAttributes, times(1)).addFlashAttribute(anyString(), anyString());
    }

    @Test
    void mostrarFormularioDeFoto() {
        String viewName = usuarioController.mostrarFormularioDeFoto();
        assertEquals("usuario/addFoto", viewName);
    }

    @Test
    void agregarFoto() {
        // Esta prueba podría requerir más detalles dependiendo de la implementación de `agregarFoto`
        String viewName = usuarioController.agregarFoto(null, null, redirectAttributes);
        assertEquals("redirect:/perfil", viewName);
        // Aquí podrías verificar que se haya llamado a algún método en el servicio si eso forma parte de la implementación
    }
}
