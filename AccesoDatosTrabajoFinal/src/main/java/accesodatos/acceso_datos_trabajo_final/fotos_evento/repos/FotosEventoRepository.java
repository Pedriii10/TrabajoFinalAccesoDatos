package accesodatos.acceso_datos_trabajo_final.fotos_evento.repos;

import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import accesodatos.acceso_datos_trabajo_final.fotos_evento.domain.FotosEvento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FotosEventoRepository extends JpaRepository<FotosEvento, Integer> {

    FotosEvento findFirstByEvento(Evento evento);

}
