package question.with.resilience4j.service;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

@Service
public class FraudService {

    private static final String BACKEND_FRAUD_SERVICE = "backendFraudService";
    private final WebClient webClient;

    public FraudService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    }

    @Retry(name = BACKEND_FRAUD_SERVICE, fallbackMethod = "monoFallback")
    @TimeLimiter(name = BACKEND_FRAUD_SERVICE, fallbackMethod = "monoFallback")
    public Mono<String> getFraudData(String user) {
        return webClient.get()
                .uri("/getFraudData?user=" + user)
                .retrieve()
                .bodyToMono(String.class);
    }

}

