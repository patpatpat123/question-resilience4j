package question.without.resilience4j.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class FraudService {

    private final WebClient webClient;

    public FraudService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public Mono<String> getFraudData(String user) {
        return webClient.get()
                .uri("/getFraudData?user=" + user)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3L, Duration.ofSeconds(1L)))
                .timeout(Duration.ofSeconds(5L))
                .doOnError(error -> { throw new RuntimeException("failure at step one" + this.getClass().getCanonicalName()); });
    }

}
