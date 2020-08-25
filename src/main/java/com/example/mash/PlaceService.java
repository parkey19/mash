package com.example.mash;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class PlaceService {

    public static final ClassPathResource resource = new ClassPathResource("countrytravelinfo.json");

    @RestController
    @RequiredArgsConstructor
    public static class PlaceController {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        //  0.06
        @GetMapping("/place")
        public static Mono<List<Place>> getPlace() throws IOException {
            List<Place> list = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Place>>(){});
            return Mono.just(list);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Place {
        @JsonProperty("tag")
        private String tag;
        @JsonProperty("geopoliticalarea")
        private String geopoliticalarea;
        @JsonProperty("travel_transportation")
        private String travelTransportation;
        @JsonProperty("health")
        private String health;
        @JsonProperty("local_laws_and_special_circumstances")
        private String localLawsAndSpecialCircumstances;
        @JsonProperty("safety_and_security")
        private String safetyAndSecurity;
        @JsonProperty("entry_exit_requirements")
        private String entryExitRequirements;
        @JsonProperty("destination_description")
        private String destinationDescription;
        @JsonProperty("iso_code")
        private String isoCode;
        @JsonProperty("travel_embassyAndConsulate")
        private String travelEmbassyAndConsulate;
        @JsonProperty("last_update_date")
        private String lastUpdateDate;
        @JsonProperty("policy")
        private PolicyService.Policy policy;
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8082");
        SpringApplication.run(PlaceService.class, args);
    }
}
