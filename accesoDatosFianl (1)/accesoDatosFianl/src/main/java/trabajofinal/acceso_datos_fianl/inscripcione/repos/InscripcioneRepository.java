package trabajofinal.acceso_datos_fianl.inscripcione.repos;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;

import java.util.List;

/**
 * InscripcioneRepository es una interfaz que extiende JpaRepository para la entidad Inscripcione.
 * Proporciona métodos para realizar operaciones de persistencia relacionadas con las inscripciones a eventos.
 */
public interface InscripcioneRepository extends JpaRepository<Inscripcione, Integer> {
    /**
     * Encuentra la primera inscripción asociada a un usuario específico.
     *
     * @param usuario El usuario para el cual se busca la inscripción.
     * @return La primera inscripción asociada al usuario, o null si no se encuentra ninguna.
     */
    Inscripcione findFirstByUsuario(Usuario usuario);
    /**
     * Encuentra la primera inscripción asociada a un evento específico.
     *
     * @param evento El evento para el cual se busca la inscripción.
     * @return La primera inscripción asociada al evento, o null si no se encuentra ninguna.
     */
    Inscripcione findFirstByEvento(Evento evento);
    /**
     * Comprueba si existe una inscripción asociada a un evento y un usuario específicos.
     *
     * @param evento  El evento para el cual se comprueba la inscripción.
     * @param usuario El usuario para el cual se comprueba la inscripción.
     * @return true si existe una inscripción asociada al evento y usuario dados, false en caso contrario.
     */
    boolean existsByEventoAndUsuario(Evento evento, Usuario usuario);
    /**
     * Obtiene todas las inscripciones asociadas a un usuario específico, cargando también la información de usuario y evento.
     *
     * @param usuarioId El ID del usuario para el cual se buscan las inscripciones.
     * @return Una lista de inscripciones asociadas al usuario, con la información de usuario y evento cargada.
     */
    @EntityGraph(attributePaths = {"usuario", "evento"})
    List<Inscripcione> findByUsuarioUsuarioId(Integer usuarioId);
    /**
     * Obtiene todas las inscripciones asociadas a un evento específico, cargando también la información de usuario y evento.
     *
     * @param eventoId El ID del evento para el cual se buscan las inscripciones.
     * @return Una lista de inscripciones asociadas al evento, con la información de usuario y evento cargada.
     */
    @EntityGraph(attributePaths = {"usuario", "evento"})
    List<Inscripcione> findByEventoEventoId(Integer eventoId);
}
