package com.peplink.ecommerce.workflow.reactive.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Configuration
public class ReactiveElasticsearchConfiguration {

    @Bean
    public ReactiveElasticsearchClient client() {

        
//        RestClient.builder(new HttpHost(m_elasticSearchHostname, m_elasticSearchPort, "http"))
//        .build()
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withWebClientConfigurer(webClient -> {
                    ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                            .build();
                    return webClient.mutate().exchangeStrategies(exchangeStrategies).build();
                })
                .build();

        return ReactiveRestClients.create(clientConfiguration);
    }
}


//// ...
//
//Mono<IndexResponse> response = client.index(request ->
//
//  request.index("spring-data")
//    .type("elasticsearch")logback.xml
//    .id(randomID())
//    .source(singletonMap("feature", "reactive-client"))
//    .setRefreshPolicy(IMMEDIATE);
//);