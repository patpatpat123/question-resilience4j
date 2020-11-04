package question.with.resilience4j.service;

import reactor.core.publisher.Mono;

public class Util {

    private Mono<String> monoFallback(Exception ex) {
        System.out.println("fail");
        throw new RuntimeException("failure at step ??? " + this.getClass().getCanonicalName() + " - - " + ex);
    }

}
