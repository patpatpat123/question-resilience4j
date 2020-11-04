package question.flakyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Random;

@SpringBootApplication
@RestController
public class FlakyApplication {

    Random random = new Random(1234567890L);

    public static void main(String[] args) {
        SpringApplication.run(FlakyApplication.class, args);
    }

    @GetMapping("/getFraudData")
    public Mono<String> getFraudData(@RequestParam String user) {
        if (1 != random.nextInt(2)) {
            System.out.println("getFraudDataBad");
            return Mono.error(new IOException("getFraudDataIOException"));
        } else {
            System.out.println("getFraudDataGood");
            return Mono.just("someFraudData" + user);
        }
    }

    @GetMapping("/getPurchaseData")
    public Mono<String> getPurchaseData(@RequestParam String user) {
        if (1 != random.nextInt(3)) {
            System.out.println("getPurchaseDataBad");
            return Mono.error(new IOException("getPurchaseDataIOException"));
        } else {
            System.out.println("getPurchaseDataGood");
            return Mono.just("somePurchaseData" + user);
        }
    }

}

