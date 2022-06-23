package com.paypal.ppcn.spring.reactor.demo.controller;

import com.paypal.ppcn.spring.reactor.demo.service.ReactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactor")
public class ReactorTestController {

    @Autowired
    private ReactorService reactorService;

    @GetMapping("/blockApi")
    public String blockApi() {
        String resp = reactorService.blockData();
        return resp;
    }

    @GetMapping("/nioApi")
    public String nioApi() {
        Mono<String> resp = reactorService.getValue();
        resp.subscribe(reactorService::setValue);
        return resp.toString();
    }
}
