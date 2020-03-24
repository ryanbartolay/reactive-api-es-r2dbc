package com.peplink.ecommerce.workflow.reactive.api.controller.user;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peplink.ecommerce.workflow.reactive.api.exception.ObjectNotFoundException;
import com.peplink.ecommerce.workflow.reactive.api.repository.UserRepository;
import com.peplink.ecommerce.workflow.reactive.api.util.ControllerHelperService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping(value = "workflow/api/v1/user")
public class UserController {

    @Autowired
    @Qualifier("messageSource")
    private ResourceBundleMessageSource m_messageSource;

    @Autowired
    private ElasticsearchUserService m_elasticsearchUserService;

    @Autowired
    private UserService m_userService;

    @Autowired
    private UserRepository m_userRepository;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<UserApi> readAll() {
        throw new ObjectNotFoundException("This is sample error");
//        return m_userRepository.findAll()
////                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
////                .delayElements(Duration.ofSeconds(1))
//                .map(resultEntity -> {
//                    return ControllerHelperService.mapper(resultEntity, new UserApi());
//                });
    }

    @GetMapping(path = "/readSingle", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<UserApi> readSingle() {
        return m_userService.getSingleRecord()
                .map(resultEntity -> {
                    return ControllerHelperService.mapper(resultEntity, new UserApi());
                });
    }

    @GetMapping(path = "/readSingle2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<UserApi> readSingle2() {
        return m_userService.getSingleRecord2(UUID.fromString("e5e7f0f1-0260-4cea-a095-7d828cde0820"))
                .map(resultEntity -> {
                    return ControllerHelperService.mapper(resultEntity, new UserApi());
                });
    }

    @GetMapping(path = "/readAll2", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<UserApi> readAll2() {
        return m_userService.getMultipleRecords()
                .map(resultEntity -> {
                    return ControllerHelperService.mapper(resultEntity, new UserApi());
                });
    }

    @GetMapping(path = "/readAll3", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<UserApi> readAll3() {
        return m_userService.getMultipleRecords2()
                .map(resultEntity -> {
                    return ControllerHelperService.mapper(resultEntity, new UserApi());
                });
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserApi>> readAll(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return m_userRepository.findById(UUID.fromString(id)).map(resultEntity -> {
            return ResponseEntity.ok().body(ControllerHelperService.mapper(resultEntity, new UserApi()));
        });
    }

    @GetMapping(path = "/saveUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserApi>> save() {
        Mono<UserEntity> userEntity = m_userRepository
                .findById(UUID.fromString("e5e7f0f1-0260-4cea-a095-7d828cde0820"));
        return userEntity.map((resultEntity) -> {
            ElasticsearchUserEntity esUser = ControllerHelperService.mapper(resultEntity, new ElasticsearchUserEntity());
            return ResponseEntity.ok().body(ControllerHelperService.mapper(esUser, new UserApi()));
        });
    }
    
    @GetMapping(path = "/saveUserMultiple", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserApi>> saveMultiple() {
        return m_userService.createAccountMultipleTable().map(resultEntity -> {
            return ResponseEntity.ok().body(ControllerHelperService.mapper(resultEntity, new UserApi()));
        });
    }
    
    @GetMapping(path = "/saveUserMultipleWithExceptionRollback", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserApi>> saveMultipleWithExceptionRollback() {
        return m_userService.createAccountMultipleTableWithExceptionRollback().map(resultEntity -> {
            return ResponseEntity.ok().body(ControllerHelperService.mapper(resultEntity, new UserApi()));
        });
    }
    
    @GetMapping(path = "/saveUserMultipleAsync", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserApi>> saveUserMultipleAsync() {
        m_userService.createAccountMultipleTableAsync().publishOn(Schedulers.elastic());
        return m_userRepository
                .findById(UUID.fromString("e5e7f0f1-0260-4cea-a095-7d828cde0820")).map(resultEntity -> {
                    return ResponseEntity.ok().body(ControllerHelperService.mapper(resultEntity, new UserApi()));
                });
        
    }
    
    @GetMapping(path = "/testDelay", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> testDelay() {
        return Mono.just("Ryan").delaySubscription(Duration.ofSeconds(3));
    }
    
    @GetMapping(path = "/testMono", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserApi>> testMono() {
        return Mono.just(ResponseEntity.ok().body(new UserApi()));
    }

}
