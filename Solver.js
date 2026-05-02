/**
 * Solve the 0/1 Knapsack problem to maximize impact within duration.
 * @param {Array} tasks - Array of { TaskID, Duration, Impact }
 * @param {number} maxDuration - Maximum allowed total duration
 * @returns {Array} - Array of selected TaskIDs
 */
function solveKnapsack(tasks, maxDuration) {
    const n = tasks.length;
    const dp = Array.from({ length: n + 1 }, () => Array(maxDuration + 1).fill(0));

    // Fill DP table
    for (let i = 1; i <= n; i++) {
        const { Duration, Impact } = tasks[i - 1];
        for (let w = 1; w <= maxDuration; w++) {
            if (Duration <= w) {
                dp[i][w] = Math.max(Impact + dp[i - 1][w - Duration], dp[i - 1][w]);
            } else {
                dp[i][w] = dp[i - 1][w];
            }
        }
    }

    // Backtrack to find selected items
    const selectedTaskIDs = [];
    let w = maxDuration;
    for (let i = n; i > 0 && w > 0; i--) {
        if (dp[i][w] !== dp[i - 1][w]) {
            selectedTaskIDs.push(tasks[i - 1].TaskID);
            w -= tasks[i - 1].Duration;
        }
    }

    return selectedTaskIDs;
}

module.exports = { solveKnapsack };
