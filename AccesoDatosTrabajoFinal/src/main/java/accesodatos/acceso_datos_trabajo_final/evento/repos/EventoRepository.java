package accesodatos.acceso_datos_trabajo_final.evento.repos;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EventoRepository extends JpaRepository<Evento, Integer> {

    Evento findFirstByOrganizador(Usuario usuario);

    @Query("")
    Evento findEvento2Person();

}
