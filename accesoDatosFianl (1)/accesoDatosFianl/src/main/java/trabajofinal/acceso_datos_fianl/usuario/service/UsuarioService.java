package trabajofinal.acceso_datos_fianl.usuario.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.evento.repos.EventoRepository;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.inscripcione.repos.InscripcioneRepository;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;
import trabajofinal.acceso_datos_fianl.resena.repos.ResenaRepository;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;
import trabajofinal.acceso_datos_fianl.usuario.model.UsuarioDTO;
import trabajofinal.acceso_datos_fianl.usuario.repos.UsuarioRepository;
import trabajofinal.acceso_datos_fianl.util.NotFoundException;
import trabajofinal.acceso_datos_fianl.util.ReferencedWarning;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final InscripcioneRepository inscripcioneRepository;
    private final ResenaRepository resenaRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final EventoRepository eventoRepository,
            final InscripcioneRepository inscripcioneRepository,
            final ResenaRepository resenaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.inscripcioneRepository = inscripcioneRepository;
        this.resenaRepository = resenaRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("usuarioId"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getUsuarioId();
    }

    public void update(final Integer usuarioId, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Integer usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setCorreoElectronico(usuario.getCorreoElectronico());
        // Omitir la contraseña por seguridad
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setFotoPerfil(usuario.getFotoPerfil());
        usuarioDTO.setDescripcion(usuario.getDescripcion());
        // Asegúrate de mapear todos los campos necesarios
        return usuarioDTO;
    }


    public Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        usuario.setFotoPerfil(usuarioDTO.getFotoPerfil());
        usuario.setDescripcion(usuarioDTO.getDescripcion());
        return usuario;
    }

    public ReferencedWarning getReferencedWarning(final Integer usuarioId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(NotFoundException::new);
        final Evento organizadorEvento = eventoRepository.findFirstByOrganizador(usuario);
        if (organizadorEvento != null) {
            referencedWarning.setKey("usuario.evento.organizador.referenced");
            referencedWarning.addParam(organizadorEvento.getEventoId());
            return referencedWarning;
        }
        final Inscripcione usuarioInscripcione = inscripcioneRepository.findFirstByUsuario(usuario);
        if (usuarioInscripcione != null) {
            referencedWarning.setKey("usuario.inscripcione.usuario.referenced");
            referencedWarning.addParam(usuarioInscripcione.getInscripcionId());
            return referencedWarning;
        }
        final Resena usuarioResena = resenaRepository.findFirstByUsuario(usuario);
        if (usuarioResena != null) {
            referencedWarning.setKey("usuario.resena.usuario.referenced");
            referencedWarning.addParam(usuarioResena.getResenaId());
            return referencedWarning;
        }
        return null;
    }



}
