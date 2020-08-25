package com.example.mash;

import com.example.mash.PlaceService.Place;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@SpringBootApplication
public class PdpService {

    @RestController
    @RequiredArgsConstructor
    public static class MeshController {

        private final MeshService meshService;

        @GetMapping("/pdp")
        public Mono pdpService() {
            // webclient.call policy, place and await 2 call service
            // and join 2 result and return\
            return meshService.getPdp();
        }
    }

    @Service
    public static class MeshService {
        private WebClient webClient;

        public Mono<PolicyService.Policy> getPolicy() {
            return webClient.get()
                    .uri("/policy")
                    .retrieve()
                    .bodyToMono(PolicyService.Policy.class);
        }

        public Mono<List<Place>> getPlace() {
            return webClient.get()
                    .uri("/place")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {});
        }

        public Mono<Pdp> getPdp() {
            Mono<List<Place>> place = getPlace().subscribeOn(Schedulers.elastic());
            Mono<PolicyService.Policy> policy = getPolicy().subscribeOn(Schedulers.elastic());

            return Mono.zip(place, policy, Pdp::new);
        }
    }

    @Data
    @NoArgsConstructor
    public static class Pdp {
        private List<Place> place;
        private PolicyService.Policy policy;

        public Pdp(List<Place> place, PolicyService.Policy policy) {
            this.place = place;
            this.policy = policy;
        }
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8083");
        SpringApplication.run(PdpService.class, args);
    }
}
