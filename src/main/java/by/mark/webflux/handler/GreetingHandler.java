package by.mark.webflux.handler;

import by.mark.webflux.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        Long start = request.queryParam("start")
                .map(Long::valueOf)
                .orElse(0L);
        Long count = request.queryParam("count")
                .map(Long::valueOf)
                .orElse(3L);


        Flux<Message> data = Flux
                .just(
                        "First",
                        "Second",
                        "Third",
                        "Fourth",
                        "Fifth",
                        "Sixth",
                        "Seventh"
                )
                .skip(start)
                .take(count)
                .map(Message::new);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, Message.class);
    }

    public Mono<ServerResponse> index(ServerRequest request) {
        String userName = request.queryParam("user").orElse("stranger");

        return ServerResponse
                .ok()
                .render("index", Map.of("user", userName));
    }
}
