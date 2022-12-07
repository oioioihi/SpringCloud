package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    // CustomFilter는 반드시 AbstractGatewayFilterFactory<자신의 클래스>를 상속받아서 선언해야한다.
    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) { // 구현해야할 메소드는 하나 -> apply()
        // Custom Pre Filter. Suppose we can extract JWT and perform Authentication.
        return (exchange, chain) -> {

            //Netty(비동기 내장서버,Spring5 부터 지원)는 ServletHttpRequest가 아닌 ServerRequest(ServerHttpRequest)객체를 사용함
            ServerHttpRequest request = exchange.getRequest();
            //exchange를 통해 Request객체를 받아올 수 있음.
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter: request id -> {}", request.getId());

            // Custom Post Filter. Suppose we can call error response handler based on errror code.
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { //Spring Framework5부터 지원되는 webflux구조에 의한 Mono 데이터 타입으로 리턴.
                // webflux구조란 서버에서 reactive 스타일의 애플리케이션 개발을 도와주는 모듈이다.
                log.info("Custom POST filter: response code -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}
