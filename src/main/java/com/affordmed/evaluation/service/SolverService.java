package com.affordmed.evaluation.service;

import com.affordmed.evaluation.dto.DTOs;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolverService {

    public List<String> solve(List<DTOs.Task> tasks, int maxDuration) {
        int n = tasks.size();
        int[][] dp = new int[n + 1][maxDuration + 1];

        for (int i = 1; i <= n; i++) {
            DTOs.Task task = tasks.get(i - 1);
            for (int w = 1; w <= maxDuration; w++) {
                if (task.getDuration() <= w) {
                    dp[i][w] = Math.max(task.getImpact() + dp[i - 1][w - task.getDuration()], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        List<String> selectedTaskIDs = new ArrayList<>();
        int w = maxDuration;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedTaskIDs.add(tasks.get(i - 1).getTaskID());
                w -= tasks.get(i - 1).getDuration();
            }
        }

        return selectedTaskIDs;
    }
}
