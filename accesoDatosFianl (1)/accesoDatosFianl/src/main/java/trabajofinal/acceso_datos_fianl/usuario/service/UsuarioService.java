package trabajofinal.acceso_datos_fianl.usuario.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

/**
 * Servicio que maneja la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final InscripcioneRepository inscripcioneRepository;
    private final ResenaRepository resenaRepository;
    /**
     * Constructor de la clase.
     *
     * @param usuarioRepository      Repositorio de usuarios.
     * @param eventoRepository       Repositorio de eventos.
     * @param inscripcioneRepository Repositorio de inscripciones.
     * @param resenaRepository       Repositorio de reseñas.
     */
    public UsuarioService(final UsuarioRepository usuarioRepository,
            final EventoRepository eventoRepository,
            final InscripcioneRepository inscripcioneRepository,
            final ResenaRepository resenaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.inscripcioneRepository = inscripcioneRepository;
        this.resenaRepository = resenaRepository;
    }
    /**
     * Recupera todos los usuarios.
     *
     * @return Lista de DTO de usuario.
     */
    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("usuarioId"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }
    /**
     * Obtiene un usuario por su ID.
     *
     * @param usuarioId ID del usuario a buscar.
     * @return DTO del usuario encontrado.
     * @throws NotFoundException Si el usuario no se encuentra.
     */
    public UsuarioDTO get(final Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }
    /**
     * Crea un nuevo usuario.
     *
     * @param usuarioDTO DTO del usuario a crear.
     * @return ID del usuario creado.
     */
    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getUsuarioId();
    }
    /**
     * Actualiza un usuario existente.
     *
     * @param usuarioId  ID del usuario a actualizar.
     * @param usuarioDTO DTO con los nuevos datos del usuario.
     */
    public void update(final Integer usuarioId, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }
    /**
     * Elimina un usuario por su ID.
     *
     * @param usuarioId ID del usuario a eliminar.
     */
    public void delete(final Integer usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
    /**
     * Mapea un objeto Usuario a un DTO UsuarioDTO.
     *
     * @param usuario    Objeto Usuario a mapear.
     * @param usuarioDTO DTO de Usuario a llenar.
     * @return DTO de Usuario mapeado.
     */
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

    /**
     * Mapea un DTO UsuarioDTO a un objeto Usuario.
     *
     * @param usuarioDTO DTO de Usuario a mapear.
     * @param usuario    Objeto Usuario a llenar.
     * @return Objeto Usuario mapeado.
     */
    public Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreoElectronico(usuarioDTO.getCorreoElectronico());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        usuario.setFotoPerfil(usuarioDTO.getFotoPerfil());
        usuario.setDescripcion(usuarioDTO.getDescripcion());
        return usuario;
    }
    /**
     * Obtiene una advertencia si el usuario tiene referencias en otras entidades.
     *
     * @param usuarioId ID del usuario a verificar.
     * @return Advertencia de referencia si existe, de lo contrario, nulo.
     */
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
