package question.without.resilience4j.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import question.without.resilience4j.service.FraudService;
import question.without.resilience4j.service.PurchaseService;
import reactor.core.publisher.Mono;

/**
 * This Service shows how to use the CircuitBreaker annotation.
 */
@Service
public class BusinessService {

    private final FraudService fraudService;
    private final PurchaseService purchaseService;

    @Autowired
    public BusinessService(final FraudService fraudService, final PurchaseService purchaseService) {
        this.fraudService = fraudService;
        this.purchaseService = purchaseService;
    }

    public Mono<String> businessLogic() {
        Mono<String> fraudData = fraudService.getFraudData("2");
        return fraudData.flatMap(x -> purchaseService.getPurchaseData(x));
    }

}
