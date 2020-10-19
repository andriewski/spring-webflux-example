package by.mark.webflux.controller;

import by.mark.webflux.domain.User;
import by.mark.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

@RequestMapping(UserController.USER_PATH)
@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String USER_PATH = "/api/v1/user";

    private final UserService userService;

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
