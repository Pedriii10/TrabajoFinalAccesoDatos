package accesodatos.acceso_datos_trabajo_final.inscripcione.repos;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.inscripcione.domain.Inscripcione;
import accesodatos.acceso_datos_trabajo_final.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InscripcioneRepository extends JpaRepository<Inscripcione, Integer> {

    Inscripcione findFirstByUsuario(Usuario usuario);

    Inscripcione findFirstByEvento(Evento evento);

}
