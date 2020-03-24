package com.peplink.ecommerce.workflow.reactive.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.peplink.ecommerce.workflow.reactive.api.controller.cellularmodule.CellularModuleEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface CellularModuleRepository extends ReactiveCrudRepository<CellularModuleEntity, String> {

    Mono<CellularModuleEntity> findById(String id);

    Flux<CellularModuleEntity> findAll();

}
