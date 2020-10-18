package by.mark.webflux.controller;

import by.mark.webflux.domain.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/controller")
@RestController
public class MainController {

    @GetMapping
    public Flux<Message> list(
            @RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "3") Long count
    ) {
        return Flux
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
    }
}
