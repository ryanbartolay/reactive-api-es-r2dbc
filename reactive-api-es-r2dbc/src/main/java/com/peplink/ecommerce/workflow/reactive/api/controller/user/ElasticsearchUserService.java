package com.peplink.ecommerce.workflow.reactive.api.controller.user;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import com.peplink.ecommerce.workflow.reactive.api.util.ControllerHelperService;
import com.peplink.ecommerce.workflow.reactive.api.util.JsonUtility;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ElasticsearchUserService {

//    @Autowired
//    private ReactiveElasticsearchClient m_client;

    public Mono<ElasticsearchUserEntity> save(ElasticsearchUserEntity esUser) {
        ReactiveElasticsearchClient m_client = ReactiveRestClients.create(ClientConfiguration.localhost());
        
        String documentId = esUser.getUuid().toString();
        
        String source = JsonUtility.objectToJsonString(esUser);
        
        IndexRequest request = new IndexRequest("workflow-user", "user", documentId);
        request.source(source, XContentType.JSON);
        request.setRefreshPolicy(RefreshPolicy.IMMEDIATE);

        Mono<IndexResponse> response = m_client.index(request);
        return response.map(resultEntity -> {
            return ControllerHelperService.mapper(resultEntity, new ElasticsearchUserEntity());
        });
    }
    
    public Mono<ElasticsearchUserEntity> check(ElasticsearchUserEntity user) throws IOException {
        RestClient restClient = null;

            String source = JsonUtility.objectToJsonString(user);
            String documentId = user.getUuid().toString();
            
            restClient = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                    .build();

            ReactiveElasticsearchClient client = ReactiveRestClients.create(ClientConfiguration.create("http://localhost:9200"));
            IndexRequest request = new IndexRequest("workflow-user", "user", documentId);
            request.source(source, XContentType.JSON);

            Mono<IndexResponse> response = client.index(request);
            return response.map(resultEntity -> {
                return ControllerHelperService.mapper(resultEntity, new ElasticsearchUserEntity());
            });
    }
}
