package com.peplink.ecommerce.workflow.reactive.api.controller.user;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peplink.ecommerce.workflow.reactive.api.repository.UserRepository;

import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class UserService {

    @Autowired
    private DatabaseClient m_client;

    @Autowired
    private UserRepository m_userRepository;
    
    @Autowired
    private PostgresqlConnectionFactory m_connectionFactory;

    public Mono<UserEntity> getSingleRecord() {
        Mono<Map<String, Object>> first = m_client.execute("SELECT uuid, email FROM ops_api_user")
                .fetch().first();
        return first.map(m -> {
           return new UserEntity(UUID.fromString(m.get("uuid").toString()), m.get("email").toString()); 
        });
    }
    
    public Mono<UserEntity> getSingleRecord2(UUID uuid) {
        Mono<UserEntity> first = m_client
                .select()
                .from("ops_api_user")
                .matching(Criteria.where("uuid").is(uuid))
                .as(UserEntity.class)
                .fetch()
                .one();
        
        return first;
    }
    
    public Flux<UserEntity> getMultipleRecords() {
        Flux<UserEntity> all = m_client
                .execute("SELECT uuid, email FROM ops_api_user")
                .as(UserEntity.class)
                .fetch().all();
        return all;
    }
    
    public Flux<UserEntity> getMultipleRecords2() {
        Flux<UserEntity> all = m_client
                .select()
                .from("ops_api_user")
                .as(UserEntity.class)
                .fetch().all();
        return all;
    }
    
    public Flux<String> getAllEmails() {
        Flux<String> emails = m_client.execute("SELECT email FROM ops_api_user")
                .map((row, rowMetadata) -> row.get("email", String.class))
                .all();
        return emails;
    }
    
    @Transactional
    public Mono<UserEntity> insertPerson(UserEntity userEntity) {
        return m_userRepository.save(userEntity);
    }
    
    @Transactional
    public Mono<Void> insertPersonMultiple() {
      UUID uuid = UUID.randomUUID();
      return m_client.execute("INSERT INTO ops_api_user (id, email, password, status, creation_date) VALUES(:id, :email, :password, :status, :creation_date)")
        .bind("id", uuid)
        .bind("email", uuid + "@gmail.com")
        .bind("password", "$2a$10$T0YqNC3mOxkPnhubbuHqMupbz.P2NenZAm5i0Z0kO3ysIoKu2CE5G")
        .bind("status", "ACTIVE")
        .bind("creation_date", new Date())
        .fetch().rowsUpdated()
        .then(m_client.execute("INSERT INTO ops_api_user_role (users_uuid, roles_uuid) VALUES(:user_uuid, :roles_uuid)")
          .bind("user_uuid", "joe")
          .bind("roles_uuid", "c9b6c202-57cd-4585-b886-8a58f88eadb8")
          .fetch().rowsUpdated())
        .then();
    }
    
    @Transactional
    public Mono<UserEntity> createAccount() {
        UUID uuid = UUID.randomUUID();
        return Mono.from(m_connectionFactory.create())
          .flatMap(c -> Mono.from(c.beginTransaction())
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user (uuid, email, password, creation_date) VALUES($1,$2,$3,$4)")
                    .bind("$1", uuid)
                    .bind("$2", uuid + "@gmail.com")
                    .bind("$3", "$2a$10$T0YqNC3mOxkPnhubbuHqMupbz.P2NenZAm5i0Z0kO3ysIoKu2CE5G")
                    .bind("$4", new Date())
              .returnGeneratedValues("uuid")
              .execute()))
                        .map(result -> result.map((row, meta) -> 
                        new UserEntity(row.get("uuid", UUID.class))
                ))
            .flatMap(pub -> Mono.from(pub))
            .delayUntil(r -> c.commitTransaction())
            .doFinally((st) -> c.close())).publishOn(Schedulers.single());
    }
    
    @Transactional
    public Mono<UserEntity> createAccountMultipleTable() {
        UUID uuid = UUID.randomUUID();
        return Mono.from(m_connectionFactory.create())
          .flatMap(c -> Mono.from(c.beginTransaction())
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user (uuid, email, password, creation_date) VALUES($1,$2,$3,$4)")
                    .bind("$1", uuid)
                    .bind("$2", uuid + "@gmail.com")
                    .bind("$3", "$2a$10$T0YqNC3mOxkPnhubbuHqMupbz.P2NenZAm5i0Z0kO3ysIoKu2CE5G")
                    .bind("$4", new Date())
              .returnGeneratedValues("uuid")
              .execute()))
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user_role (users_uuid, roles_uuid) VALUES($1,$2)")
                    .bind("$1", uuid)
                    .bind("$2", UUID.fromString("c9b6c202-57cd-4585-b886-8a58f88eadb8"))
              .returnGeneratedValues("users_uuid")
              .execute()))
                        
            .map(result -> result.map((row, meta) -> new UserEntity(uuid)))
            .flatMap(pub -> Mono.from(pub))
            .delayUntil(r -> c.commitTransaction())
            .doFinally((st) -> c.close()));
    }
    
    @Transactional
    public Mono<UserEntity> createAccountMultipleTableWithExceptionRollback() {
        UUID uuid = UUID.randomUUID();
        return Mono.from(m_connectionFactory.create())
          .flatMap(c -> Mono.from(c.beginTransaction())
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user (uuid, email, password, creation_date) VALUES($1,$2,$3,$4)")
                    .bind("$1", uuid)
                    .bind("$2", uuid + "@gmail.com")
                    .bind("$3", "$2a$10$T0YqNC3mOxkPnhubbuHqMupbz.P2NenZAm5i0Z0kO3ysIoKu2CE5G")
                    .bind("$4", new Date())
              .returnGeneratedValues("uuid")
              .execute()))
            .concatWith(Mono.error(new RuntimeException("Exception ehere")))
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user_role (users_uuid, roles_uuid) VALUES($1,$2)")
                    .bind("$1", uuid)
                    .bind("$2", UUID.fromString("c9b6c202-57cd-4585-b886-8a58f88eadb8"))
              .returnGeneratedValues("users_uuid")
              .execute()))
                        .map(result -> result.map((row, meta) -> new UserEntity(uuid)))
            .flatMap(pub -> Mono.from(pub))
            .delayUntil(r -> c.commitTransaction())
            .doFinally((st) -> c.close()));   
    }
    
    @Transactional
    public Mono<UserEntity> createAccountMultipleTableAsync() {
        UUID uuid = UUID.randomUUID();
        return Mono.from(m_connectionFactory.create())
          .flatMap(c -> Mono.from(c.beginTransaction())
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user (uuid, email, password, creation_date) VALUES($1,$2,$3,$4)")
                    .bind("$1", uuid)
                    .bind("$2", uuid + "@gmail.com")
                    .bind("$3", "$2a$10$T0YqNC3mOxkPnhubbuHqMupbz.P2NenZAm5i0Z0kO3ysIoKu2CE5G")
                    .bind("$4", new Date())
              .returnGeneratedValues("uuid")
              .execute()))
            .delayUntil(r -> {
                while(true) {
                    System.err.println("x");
            }
            })
            .then(Mono.from(c.createStatement("INSERT INTO ops_api_user_role (users_uuid, roles_uuid) VALUES($1,$2)")
                    .bind("$1", uuid)
                    .bind("$2", UUID.fromString("c9b6c202-57cd-4585-b886-8a58f88eadb8"))
              .returnGeneratedValues("users_uuid")
              .execute()))
                        
            .map(result -> result.map((row, meta) -> new UserEntity(uuid)))
            .flatMap(pub -> Mono.from(pub))
            .delayUntil(r -> c.commitTransaction())
            .doFinally((st) -> c.close()));
    }
    
    @Transactional
    public Mono<UserEntity> updateAccount() {
        UUID uuid = UUID.randomUUID();
        
        return m_client.execute("UPDATE ops_api_user SET email = $1")
        .bind("$1", "testing")
        .bind("$2", uuid)
        .as(UserEntity.class)
        .fetch()
        .one();
//        return Mono.from(m_connectionFactory.create())
//          .flatMap(c -> Mono.from(c.beginTransaction())
//            .then(Mono.from(c.createStatement("UPDATE ops_api_user (uuid, email, password, creation_date) VALUES($1,$2,$3,$4)")
//                    .bind("$1", uuid)
//                    .bind("$2", uuid + "@gmail.com")
//                    .bind("$3", "$2a$10$T0YqNC3mOxkPnhubbuHqMupbz.P2NenZAm5i0Z0kO3ysIoKu2CE5G")
//                    .bind("$4", new Date())
//              .returnGeneratedValues("uuid")
//              .execute()))
//                        .map(result -> result.map((row, meta) -> 
//                        new UserEntity(row.get("uuid", UUID.class))
//                ))
//            .flatMap(pub -> Mono.from(pub))
//            .delayUntil(r -> c.commitTransaction())
//            .doFinally((st) -> c.close())).publishOn(Schedulers.single());
    }

}
