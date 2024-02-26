package trabajofinal.acceso_datos_fianl.fotos_evento.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.fotos_evento.domain.FotosEvento;


public interface FotosEventoRepository extends JpaRepository<FotosEvento, Integer> {

    FotosEvento findFirstByEvento(Evento evento);

}
