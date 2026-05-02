package com.affordmed.evaluation.service;

import com.affordmed.evaluation.dto.DTOs;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverServiceTest {

    @Test
    public void testSolver() {
        SolverService solver = new SolverService();
        List<DTOs.Task> tasks = List.of(
            createTask("1", 1, 5),
            createTask("2", 6, 2),
            createTask("3", 1, 3),
            createTask("4", 5, 5)
        );
        
        List<String> result = solver.solve(tasks, 10);
        // Best combination for maxDuration 10: Task 1 (1,5), Task 3 (1,3), Task 4 (5,5) -> Total Impact 13, Total Duration 7
        // Task 2 is (6,2) - not worth it.
        
        assertEquals(3, result.size());
    }

    private DTOs.Task createTask(String id, int d, int i) {
        DTOs.Task task = new DTOs.Task();
        task.setTaskID(id);
        task.setDuration(d);
        task.setImpact(i);
        return task;
    }
}
