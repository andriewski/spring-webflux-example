package by.mark.webflux.repo;

import by.mark.webflux.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByUsername(String name);
}
