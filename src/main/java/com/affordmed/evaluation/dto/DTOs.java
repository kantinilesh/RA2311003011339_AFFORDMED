package com.affordmed.evaluation.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

public class DTOs {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegistrationRequest {
        private String email;
        private String name;
        private String mobileNo;
        private String githubUsername;
        private String rollNo;
        private String accessCode;
    }

    @Data
    public static class RegistrationResponse {
        private String clientID;
        private String clientSecret;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthRequest {
        private String companyName;
        private String clientID;
        private String clientSecret;
        private String ownerName;
        private String ownerEmail;
        private String rollNo;
    }

    @Data
    public static class AuthResponse {
        private String access_token;
        private long expires_in;
    }

    @Data
    public static class Task {
        private String TaskID;
        private int Duration;
        private int Impact;
    }

    @Data
    public static class TaskListResponse {
        private List<Task> tasks;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SolveResponse {
        private List<VehicleTask> vehicles;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VehicleTask {
        private String TaskID;
    }
}
