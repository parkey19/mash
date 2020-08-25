package com.example.mash;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootApplication
public class PolicyService {
    @RestController
    public static class PolicyController {
        @GetMapping("/policy")
        public static Mono<Policy> getPolicy() {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Mono.just(Policy.builder().id(UUID.randomUUID().getMostSignificantBits()).build());
        }
    }

    @Data
    @Builder
    public static class Policy {
        private Long id;
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        SpringApplication.run(PolicyService.class, args);
    }
}
