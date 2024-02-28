package trabajofinal.acceso_datos_fianl.evento.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;

import java.util.List;


public interface EventoRepository extends JpaRepository<Evento, Integer> {
    Evento findByEventoId(Long id);
    Evento findFirstByOrganizador(Usuario usuario);

    @Query("SELECT e FROM Evento e WHERE e.organizador.usuarioId = :usuarioId")
    List<Evento> buscarPorOrganizador(@Param("usuarioId") Integer usuarioId);


}
