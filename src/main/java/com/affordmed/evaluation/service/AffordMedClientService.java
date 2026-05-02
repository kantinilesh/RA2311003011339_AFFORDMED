package com.affordmed.evaluation.service;

import com.affordmed.evaluation.dto.DTOs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import java.util.List;

@Service
public class AffordMedClientService {

    @Value("${affordmed.test-server.url}")
    private String baseUrl;

    @Value("${affordmed.credentials.clientId}")
    private String clientId;

    @Value("${affordmed.credentials.clientSecret}")
    private String clientSecret;

    @Value("${affordmed.user.name}")
    private String ownerName;

    @Value("${affordmed.user.email}")
    private String ownerEmail;

    @Value("${affordmed.user.rollNo}")
    private String rollNo;

    private final RestTemplate restTemplate;
    private String cachedToken;
    private long tokenExpiry;

    public AffordMedClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DTOs.RegistrationResponse register(DTOs.RegistrationRequest request) {
        return restTemplate.postForObject(baseUrl + "/evaluation-service/register", request, DTOs.RegistrationResponse.class);
    }

    public String getAuthToken() {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiry) {
            return cachedToken;
        }

        DTOs.AuthRequest request = DTOs.AuthRequest.builder()
                .companyName("AffordMed")
                .clientID(clientId)
                .clientSecret(clientSecret)
                .ownerName(ownerName)
                .ownerEmail(ownerEmail)
                .rollNo(rollNo)
                .build();

        DTOs.AuthResponse response = restTemplate.postForObject(baseUrl + "/evaluation-service/auth", request, DTOs.AuthResponse.class);
        if (response != null) {
            cachedToken = response.getAccess_token();
            tokenExpiry = System.currentTimeMillis() + (response.getExpires_in() * 1000);
            return cachedToken;
        }
        return null;
    }

    public List<DTOs.Task> fetchTasks() {
        String token = getAuthToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        DTOs.TaskListResponse response = restTemplate.exchange(
            baseUrl + "/evaluation-service/tasks", 
            HttpMethod.GET, 
            entity, 
            DTOs.TaskListResponse.class
        ).getBody();
        
        return response != null ? response.getTasks() : List.of();
    }
}
