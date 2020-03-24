package com.peplink.ecommerce.workflow.reactive.api.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

import com.peplink.ecommerce.workflow.reactive.api.controller.user.UserEntity;

//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

@Component
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

}
