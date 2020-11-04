package question.without.resilience4j.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class PurchaseService {

    private final WebClient webClient;

    public PurchaseService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    public Mono<String> getPurchaseData(String user) {
        return webClient.get()
                .uri("/getPurchaseData?user=" + user)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(reactor.util.retry.Retry.backoff(3L, Duration.ofSeconds(1L)))
                .timeout(Duration.ofSeconds(5L))
                .doOnError(error -> { throw new RuntimeException("failure at step two " + this.getClass().getCanonicalName(), error); });
    }

}

