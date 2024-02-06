package accesodatos.acceso_datos_trabajo_final.entrada.repos;

import accesodatos.acceso_datos_trabajo_final.entrada.domain.Entrada;
import accesodatos.acceso_datos_trabajo_final.evento.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntradaRepository extends JpaRepository<Entrada, Integer> {

    Entrada findFirstByEvento(Evento evento);

}
