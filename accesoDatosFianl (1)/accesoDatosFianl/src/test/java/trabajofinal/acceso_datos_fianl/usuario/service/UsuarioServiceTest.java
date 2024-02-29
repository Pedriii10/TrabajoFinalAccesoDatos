package trabajofinal.acceso_datos_fianl.usuario.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.resena.repos.ResenaRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private InscripcioneRepository inscripcioneRepository;
    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void findAll() {
        usuarioService.findAll();
        verify(usuarioRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void get() {
        Integer usuarioId = 1;
        when(usuarioRepository.findById(usuarioId)).thenReturn(java.util.Optional.of(new Usuario()));
        assertNotNull(usuarioService.get(usuarioId));
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    void create() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Nombre de Usuario");
        usuarioDTO.setCorreoElectronico("usuario@example.com");
        usuarioDTO.setContrasena("contraseña");

        usuarioService.create(usuarioDTO);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void update() {
        Integer usuarioId = 1;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Nombre Actualizado");
        usuarioDTO.setCorreoElectronico("usuarioactualizado@example.com");

        when(usuarioRepository.findById(usuarioId)).thenReturn(java.util.Optional.of(new Usuario()));

        usuarioService.update(usuarioId, usuarioDTO);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void delete() {
        Integer usuarioId = 1;
        doNothing().when(usuarioRepository).deleteById(usuarioId);
        usuarioService.delete(usuarioId);
        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }

    @Test
    void mapToEntity() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setCorreoElectronico("usuario@example.com");
        usuarioDTO.setContrasena("contraseña");

        Usuario usuario = usuarioService.mapToEntity(usuarioDTO, new Usuario());

        assertNotNull(usuario);
        assertEquals(usuarioDTO.getNombre(), usuario.getNombre());
        assertEquals(usuarioDTO.getCorreoElectronico(), usuario.getCorreoElectronico());
        assertEquals(usuarioDTO.getContrasena(), usuario.getContrasena());
    }

    @Test
    void getReferencedWarning() {
        Integer usuarioId = 1;
        when(usuarioRepository.findById(usuarioId)).thenReturn(java.util.Optional.of(new Usuario()));

        assertNull(usuarioService.getReferencedWarning(usuarioId));
        verify(eventoRepository, times(1)).findFirstByOrganizador(any(Usuario.class));
        verify(inscripcioneRepository, times(1)).findFirstByUsuario(any(Usuario.class));
        verify(resenaRepository, times(1)).findFirstByUsuario(any(Usuario.class));
    }
}
