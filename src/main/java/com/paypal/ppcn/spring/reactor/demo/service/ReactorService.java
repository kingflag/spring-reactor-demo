package com.paypal.ppcn.spring.reactor.demo.service;

import com.paypal.ppcn.spring.reactor.demo.client.ReactorWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.retry.Repeat;

import java.time.Duration;

@Service
public class ReactorService {

    @Autowired
    private ReactorWebClient webClient;

    public String blockData(){
        Mono<String> resp = getValue();
        return resp.block();
    }

    public Mono<String> getValue() {
        Mono<String> monoString = getMono();
        monoString.doOnNext(val -> {
            System.out.println("doOnNext value:" + val);
        });
        return monoString;
    }


    public Mono<String> getMono() {
        Mono mono = Mono.defer(() -> webClient.get())
                .repeatWhen(Repeat.times(5L).fixedBackoff(Duration.ofSeconds(2)))
                .takeUntil(resp -> resp.getBody().contains("202"))
                .filter(resp -> resp.getBody().contains("202"))
                .map(resp -> resp.getBody())
                .log()
                .last()
                .doOnError(System.out::println);
        return mono;
    }

    public void setValue( String value) {
        System.out.println("subscribe result" + value);
    }

}
