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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public Integer create(UsuarioDTO usuarioDTO) {
        System.out.println("Nombre: " + usuarioDTO.getNombre());
        System.out.println("Correo Electrónico: " + usuarioDTO.getCorreoElectronico());
        System.out.println("Contraseña: " + usuarioDTO.getContrasena());
        try {
            // Codifica la contraseña antes de guardarla
            String encodedPassword = passwordEncoder.encode(usuarioDTO.getContrasena());

            // Crear una consulta nativa para insertar solo los campos deseados
            Query query = entityManager.createNativeQuery("INSERT INTO usuarios (Nombre, CorreoElectronico, Contrasena) VALUES (?, ?, ?)");
            query.setParameter(1, usuarioDTO.getNombre());
            query.setParameter(2, usuarioDTO.getCorreoElectronico());
            query.setParameter(3, encodedPassword);

            query.executeUpdate();

            // Si necesitas el ID generado, tendrás que realizar una consulta adicional para obtenerlo,
            // o cambiar la configuración para que el ID se devuelva después de la inserción.

            // Retorna el ID del usuario o null si no necesitas el ID
            return null;

        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar el usuario", e);
        }
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
        usuarioDTO.setFotoPerfil(usuario.getFotoPerfil());
        usuarioDTO.setDescripcion(usuario.getDescripcion());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        usuario.setContrasena(usuarioDTO.getContrasena());
        // No mapear fechaRegistro ya que es manejada automáticamente por la base de datos
        usuario.setFotoPerfil(usuarioDTO.getFotoPerfil());
        usuario.setDescripcion(usuarioDTO.getDescripcion());
        // La fecha de creación y actualización son manejadas automáticamente por la auditoría de JPA
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
