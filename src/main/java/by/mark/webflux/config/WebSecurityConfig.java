package by.mark.webflux.config;

import by.mark.webflux.controller.MessageController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static by.mark.webflux.controller.UserController.USER_PATH;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .httpBasic().and()
                .authorizeExchange()
                .pathMatchers("/", "/login", USER_PATH, "favicon.ico").permitAll()
                .pathMatchers(MessageController.MESSAGE_V1_PATH).hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
