package trabajofinal.acceso_datos_fianl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicia la aplicación Spring Boot. Configura el entorno de ejecución y lanza
 * la aplicación utilizando la configuración de Spring Boot.
 */
@SpringBootApplication
public class AccesoDatosFianlApplication {

    /**
     * Método principal que sirve como punto de entrada para la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos pasados al iniciar la aplicación.
     */
    public static void main(final String[] args) {
        SpringApplication.run(AccesoDatosFianlApplication.class, args);
    }
}

