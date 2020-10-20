package by.mark.webflux.config;

import by.mark.webflux.constant.UserParams;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtils jwtUtils;

    @SuppressWarnings("unchecked")
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;

        try {
            username = jwtUtils.extractUsername(authToken);
        } catch (Exception e) {
            username = null;
            log.error("Something went wrong", e);
        }

        if (username != null && jwtUtils.validateToken(authToken)) {
            Claims claims = jwtUtils.getClaimsFromToken(authToken);
            List<String> roles = claims.get(UserParams.ROLE, List.class);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());


            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );

            return Mono.just(authenticationToken);
        }

        return Mono.empty();
    }
}
