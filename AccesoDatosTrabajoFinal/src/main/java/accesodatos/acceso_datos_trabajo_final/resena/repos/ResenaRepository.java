package accesodatos.acceso_datos_trabajo_final.resena.repos;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.resena.domain.Resena;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    Resena findFirstByUsuario(Usuario usuario);

    Resena findFirstByEvento(Evento evento);

}
