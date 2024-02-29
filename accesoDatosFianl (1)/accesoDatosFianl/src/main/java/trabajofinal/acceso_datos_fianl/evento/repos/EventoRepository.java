package trabajofinal.acceso_datos_fianl.evento.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;

import java.util.List;
/**
 * EventoRepository proporciona un mecanismo de abstracción para realizar operaciones CRUD sobre la entidad Evento.
 * Incluye métodos personalizados además de los proporcionados por JpaRepository para consultar eventos
 * basados en ciertos criterios como el organizador.
 */
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    /**
     * Busca un evento por su ID.
     *
     * @param id El ID del evento a buscar.
     * @return El evento encontrado o null si no existe un evento con el ID proporcionado.
     */
    Evento findByEventoId(Long id);

    /**
     * Encuentra el primer evento organizado por un usuario específico.
     *
     * @param usuario El usuario organizador del evento.
     * @return El primer evento organizado por el usuario dado o null si el usuario no ha organizado ningún evento.
     */
    Evento findFirstByOrganizador(Usuario usuario);

    /**
     * Realiza una búsqueda personalizada para encontrar todos los eventos organizados por un usuario específico,
     * identificado por su ID de usuario.
     *
     * @param usuarioId El ID del usuario organizador de los eventos.
     * @return Una lista de eventos organizados por el usuario especificado.
     */
    @Query("SELECT e FROM Evento e WHERE e.organizador.usuarioId = :usuarioId")
    List<Evento> buscarPorOrganizador(@Param("usuarioId") Integer usuarioId);

    /**
     * Encuentra eventos basándose en el ID del usuario organizador.
     *
     * @param organizadorId El ID del usuario organizador de los eventos.
     * @return Una lista de eventos organizados por el usuario con el ID especificado.
     */
    List<Evento> findByOrganizadorUsuarioId(Integer organizadorId);
}

