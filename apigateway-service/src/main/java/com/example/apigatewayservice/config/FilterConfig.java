package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@Configuration -> 애노테이션을 달면 스프링부트가 부트스트랩에 의해서 작동될 때 해당 클래스에 선언된 @Bean을 메모리에 빈으로 먼저 등록함. Property대신 JavaCode로 설정을 해주는 것.
public class FilterConfig {
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**") // 해당 path와 같은 requst가 들어오면
                            .filters(f -> f.addRequestHeader("first-request", "first-request-header") //requestHeader에 이와 같은 값을 추가하고
                                           .addResponseHeader("first-response", "first-response-header")) //responseHeader에 왼쪽과 같은 값을 추가하고
                            .uri("http://localhost:8081")) // 해당 주소로 보내겠다. application.yml의 spring.cloud.gateway.routes와 같은 역할
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082"))
                .build(); //build()로 마무리하면 실제 메모리에 반영 됨.
    }
}
