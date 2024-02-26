package trabajofinal.acceso_datos_fianl.entrada.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.entrada.domain.Entrada;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;


public interface EntradaRepository extends JpaRepository<Entrada, Integer> {

    Entrada findFirstByEvento(Evento evento);

}
