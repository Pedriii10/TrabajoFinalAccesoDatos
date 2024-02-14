package accesodatos.acceso_datos_trabajo_final.usuario.service;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.evento.repos.EventoRepository;
import accesodatos.acceso_datos_trabajo_final.inscripcione.domain.Inscripcione;
import accesodatos.acceso_datos_trabajo_final.inscripcione.repos.InscripcioneRepository;
import accesodatos.acceso_datos_trabajo_final.resena.domain.Resena;
import accesodatos.acceso_datos_trabajo_final.resena.repos.ResenaRepository;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import accesodatos.acceso_datos_trabajo_final.usuario.model.UsuarioDTO;
import accesodatos.acceso_datos_trabajo_final.usuario.repos.UsuarioRepository;
import accesodatos.acceso_datos_trabajo_final.util.NotFoundException;
import accesodatos.acceso_datos_trabajo_final.util.WebUtils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Integer create(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena())); // Codifica la contraseña
        usuario = usuarioRepository.save(usuario); // Guarda el usuario
        return usuario.getUsuarioId(); // Devuelve el ID del usuario creado
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
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setFotoPerfil(usuario.getFotoPerfil());
        usuarioDTO.setDescripcion(usuario.getDescripcion());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        usuario.setFotoPerfil(usuarioDTO.getFotoPerfil());
        usuario.setDescripcion(usuarioDTO.getDescripcion());
        return usuario;
    }

    public String getReferencedWarning(final Integer usuarioId) {
        final Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(NotFoundException::new);
        final Evento organizadorEvento = eventoRepository.findFirstByOrganizador(usuario);
        if (organizadorEvento != null) {
            return WebUtils.getMessage("usuario.evento.organizador.referenced", organizadorEvento.getEventoId());
        }
        final Inscripcione usuarioInscripcione = inscripcioneRepository.findFirstByUsuario(usuario);
        if (usuarioInscripcione != null) {
            return WebUtils.getMessage("usuario.inscripcione.usuario.referenced", usuarioInscripcione.getInscripcionId());
        }
        final Resena usuarioResena = resenaRepository.findFirstByUsuario(usuario);
        if (usuarioResena != null) {
            return WebUtils.getMessage("usuario.resena.usuario.referenced", usuarioResena.getResenaId());
        }
        return null;
    }

}
