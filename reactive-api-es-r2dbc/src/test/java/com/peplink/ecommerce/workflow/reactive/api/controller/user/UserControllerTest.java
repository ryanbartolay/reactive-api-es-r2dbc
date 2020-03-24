package com.peplink.ecommerce.workflow.reactive.api.controller.user;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {
//        UserController.class,
//        UserService.class,
//      })
//@ActiveProfiles("dev")
public class UserControllerTest {

//    private WebClient testClient = WebClient.create();

//    @Test
//    public void testByWebTestClient() {
//        WebTestClient.bindToServer().baseUrl("http://localhost:58002")
//        .build().get()
//        .uri("/workflow/api/v1/user/testMono")
//        .exchange()
//        .expectStatus().isOk()
//        .expectBody(UserApi.class)
//        .consumeWith(response -> {
//            System.err.println(response.getResponseBody());
//            System.err.println(response.getResponseHeaders());
//        });
//    }
//
//    @Test
//    public void testByWebClient() {
//        WebClient webClient = WebClient.builder().baseUrl("http://localhost:58002").build();
//        Mono<UserApi> userApi = webClient.get()
//        .uri("/workflow/api/v1/user/testMono")
//        .retrieve()
//        .bodyToMono(UserApi.class);
//        
//        System.err.println(userApi.block());
//    }
    

//    
//    @Test
//    public void fluxTest() {
//
//        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                /*.concatWith(Flux.error(new RuntimeException("Exception Occurred")))*/
//                .concatWith(Flux.just("After Error"))
//                .log();
//
//        stringFlux
//                .subscribe(System.out::println,
//                        (e) -> System.err.println("Exception is " + e)
//                        , () -> System.out.println("Completed"));
//    }
//
//    @Test
//    public void fluxTestElements_WithoutError() {
//
//        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .log();
//
//        StepVerifier.create(stringFlux)
//                .expectNext("Spring")
//                .expectNext("Spring Boot")
//                .expectNext("Reactive Spring")
//                .verifyComplete();
//
//
//    }
//
//    @Test
//    public void fluxTestElements_WithError() {
//
//        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
//                .log();
//
//        StepVerifier.create(stringFlux)
//                .expectNext("Spring")
//                .expectNext("Spring Boot")
//                .expectNext("Reactive Spring")
//                //.expectError(RuntimeException.class)
//                .expectErrorMessage("Exception Occurred")
//                .verify();
//
//
//    }
//
//    @Test
//    public void fluxTestElements_WithError1() {
//
//        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
//                .log();
//
//        StepVerifier.create(stringFlux)
//                .expectNext("Spring","Spring Boot","Reactive Spring")
//                .expectErrorMessage("Exception Occurred")
//                .verify();
//
//
//    }
//
//    @Test
//    public void fluxTestElementsCount_WithError() {
//
//        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
//                .log();
//
//        StepVerifier.create(stringFlux)
//                .expectNextCount(3)
//                .expectErrorMessage("Exception Occurred")
//                .verify();
//
//
//    }
//
//    @Test
//    public void monoTest(){
//
//        Mono<String>  stringMono = Mono.just("Spring");
//
//        StepVerifier.create(stringMono.log())
//                .expectNext("Spring")
//                .verifyComplete();
//
//    }
//
//    @Test
//    public void monoTest_Error(){
//
//
//        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
//                .expectError(RuntimeException.class)
//                .verify();
//
//    }


}
