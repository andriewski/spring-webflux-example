package by.mark.webflux.controller;

import by.mark.webflux.config.JwtUtils;
import by.mark.webflux.constant.UserParams;
import by.mark.webflux.domain.User;
import by.mark.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

@RequestMapping(UserController.USER_PATH)
@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String USER_PATH = "/api/v1/user";

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping(value = "/login", produces = APPLICATION_JSON)
    public Mono<ResponseEntity<?>> login(ServerWebExchange swe) {
        return swe.getFormData().flatMap(credentials -> {
            ResponseEntity<Object> unauthorized = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            return userService
                    .findByUsername(credentials.getFirst(UserParams.USERNAME))
                    .cast(User.class)
                    .map(user ->
                            passwordEncoder.matches(credentials.getFirst(UserParams.PASSWORD), user.getPassword())
                                    ? ResponseEntity.ok(jwtUtils.generateToken(user))
                                    : unauthorized
                    )
                    .defaultIfEmpty(unauthorized);
        });
    }
}
