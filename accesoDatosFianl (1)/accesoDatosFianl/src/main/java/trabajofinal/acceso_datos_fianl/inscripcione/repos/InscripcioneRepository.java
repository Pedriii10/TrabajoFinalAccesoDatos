package trabajofinal.acceso_datos_fianl.inscripcione.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.inscripcione.domain.Inscripcione;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;


public interface InscripcioneRepository extends JpaRepository<Inscripcione, Integer> {

    Inscripcione findFirstByUsuario(Usuario usuario);

    Inscripcione findFirstByEvento(Evento evento);

    boolean existsByEventoAndUsuario(Evento evento, Usuario usuario);
}
