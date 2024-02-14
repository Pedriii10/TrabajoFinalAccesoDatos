package accesodatos.acceso_datos_trabajo_final.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests) {
        authorizeRequests
                .antMatchers("/login", "/register", "/oauth2/authorization/google").permitAll() // Permitir acceso sin autenticación a estas rutas
                .anyRequest().authenticated();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(SecurityConfig::customize // Todas las demás solicitudes requieren autenticación
        );
        http.oauth2Login(oauth2Login ->
                oauth2Login
                        .loginPage("/login") // Página de inicio de sesión personalizada
                        .defaultSuccessUrl("/home/index", true) // Página a la que se redirige después de un inicio de sesión exitoso
                        .failureUrl("/login?error=true") // Página de inicio de sesión en caso de fallo
        );
        http.logout(logout ->
                logout
                        .logoutSuccessUrl("/login?logout=true") // Página a la que se redirige después de cerrar sesión
        );
        HttpSecurity logout1 = http;
        return http.build();
    }
}


