package by.mark.webflux.controller;

import by.mark.webflux.domain.Message;
import by.mark.webflux.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(MessageController.MESSAGE_V1_PATH)
@RestController
@RequiredArgsConstructor
public class MessageController {

    public static final String MESSAGE_V1_PATH = "/api/v1/message";

    private final MessageService messageService;

    @GetMapping
    public Flux<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping
    public Mono<Message> saveMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }
}
