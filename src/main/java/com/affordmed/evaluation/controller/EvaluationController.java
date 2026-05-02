package com.affordmed.evaluation.controller;

import com.affordmed.evaluation.dto.DTOs;
import com.affordmed.evaluation.service.AffordMedClientService;
import com.affordmed.evaluation.service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EvaluationController {

    @Autowired
    private AffordMedClientService clientService;

    @Autowired
    private SolverService solverService;

    @PostMapping("/register")
    public DTOs.RegistrationResponse register(@RequestBody DTOs.RegistrationRequest request) {
        return clientService.register(request);
    }

    @PostMapping("/solve")
    public DTOs.SolveResponse solve(@RequestBody SolveRequest request) {
        List<DTOs.Task> tasks = clientService.fetchTasks();
        int maxDuration = request.getMaxDuration() != 0 ? request.getMaxDuration() : 10;
        
        List<String> selectedIDs = solverService.solve(tasks, maxDuration);
        
        List<DTOs.VehicleTask> vehicleTasks = selectedIDs.stream()
                .map(DTOs.VehicleTask::new)
                .collect(Collectors.toList());
        
        return new DTOs.SolveResponse(vehicleTasks);
    }

    public static class SolveRequest {
        private int maxDuration;
        public int getMaxDuration() { return maxDuration; }
        public void setMaxDuration(int maxDuration) { this.maxDuration = maxDuration; }
    }
}
