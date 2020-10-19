package by.mark.webflux.repo;

import by.mark.webflux.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepo extends ReactiveCrudRepository<Message, Long> {
}
