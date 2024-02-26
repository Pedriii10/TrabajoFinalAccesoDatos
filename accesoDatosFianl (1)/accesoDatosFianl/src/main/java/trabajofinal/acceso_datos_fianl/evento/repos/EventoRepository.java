package trabajofinal.acceso_datos_fianl.evento.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import trabajofinal.acceso_datos_fianl.evento.domain.Evento;
import trabajofinal.acceso_datos_fianl.usuario.domain.Usuario;


public interface EventoRepository extends JpaRepository<Evento, Integer> {

    Evento findFirstByOrganizador(Usuario usuario);

}
