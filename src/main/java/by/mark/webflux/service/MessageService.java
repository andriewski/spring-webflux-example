package by.mark.webflux.service;

import by.mark.webflux.domain.Message;
import by.mark.webflux.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepo messageRepo;

    public Flux<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Mono<Message> saveMessage(Message message) {
        return messageRepo.save(message);
    }
}
