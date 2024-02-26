package trabajofinal.acceso_datos_fianl.resena.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.resena.domain.Resena;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;


public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    Resena findFirstByUsuario(Usuario usuario);

    Resena findFirstByEvento(Evento evento);

}
