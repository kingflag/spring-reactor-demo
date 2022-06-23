package com.paypal.ppcn.spring.reactor.demo.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ReactorWebClient {

    public Mono<ResponseEntity<String>> get() {
        String url = "https://a1b158da-0f49-44b3-afb4-7c44aa9bb70c.mock.pstmn.io/v1/mock/ekyc/benefit/getBeneficiariesByName";
        Mono<ResponseEntity<String>> mono = WebClient.create().get().uri(url)
                .retrieve()
                .toEntity(String.class);
        return mono;
    }

}
