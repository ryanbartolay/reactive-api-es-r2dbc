package com.peplink.ecommerce.workflow.reactive.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.peplink.ecommerce.workflow.reactive.api" })
@EnableWebFlux
//@EnableReactiveElasticsearchRepositories
@PropertySource("classpath:profiles/${spring.profiles.active}/application.properties")
public class Application  {
    
  
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DisposableServer disposableServer(ApplicationContext context) {
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer httpServer = HttpServer.create();
        httpServer.host("localhost");
        httpServer.port(8080);
        return httpServer.handle(adapter).bindNow();
    }

}
