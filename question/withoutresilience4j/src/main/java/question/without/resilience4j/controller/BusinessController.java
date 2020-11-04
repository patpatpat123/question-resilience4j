package question.without.resilience4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import question.without.resilience4j.service.BusinessService;
import reactor.core.publisher.Mono;

@RestController
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService){
        this.businessService = businessService;
    }

    @GetMapping("/businessLogic")
    public Mono<String> businessLogic(){
        return businessService.businessLogic();
    }

    @ExceptionHandler
    public Mono<String> exception(RuntimeException runtimeException) {
        return Mono.just("caught " + runtimeException.getLocalizedMessage());
    }

}
