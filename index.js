const express = require('express');
const loggingMiddleware = require('./LoggingMiddleware');
const { authMiddleware } = require('./AuthMiddleware');
const { solveKnapsack } = require('./Solver');
const axios = require('axios');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

// Mandatory Logging Middleware - apply globally
app.use(loggingMiddleware);

// Sample Route to fetch data from the AffordMed test server
app.get('/vehicles', authMiddleware, async (req, res) => {
    try {
        // Example: Fetching vehicles from the test server
        // This is a placeholder URL based on the pattern in the images
        const response = await axios.get(`${process.env.TEST_SERVER_URL}/evaluation-service/vehicles`, {
            headers: {
                Authorization: `Bearer ${req.authToken}`
            }
        });
        
        // Return the data to the user
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching vehicles:', error.response ? error.response.data : error.message);
        res.status(error.response ? error.response.status : 500).json({
            error: 'Failed to fetch data from test server',
            details: error.response ? error.response.data : error.message
        });
    }
});

// Endpoint to solve the task optimization problem
app.post('/solve', authMiddleware, async (req, res) => {
    try {
        const { maxDuration = 10 } = req.body; // Default or provided max duration
        
        // 1. Fetch tasks from the test server
        // (Assuming the endpoint is /tasks based on the logic)
        const response = await axios.get(`${process.env.TEST_SERVER_URL}/evaluation-service/tasks`, {
            headers: {
                Authorization: `Bearer ${req.authToken}`
            }
        });

        const tasks = response.data.tasks || [];
        
        // 2. Solve the Knapsack problem
        const selectedIDs = solveKnapsack(tasks, maxDuration);
        
        // 3. Format the response as seen in Image 3
        const result = {
            vehicles: selectedIDs.map(id => ({ TaskID: id }))
        };

        res.json(result);
    } catch (error) {
        console.error('Error solving tasks:', error.response ? error.response.data : error.message);
        res.status(500).json({ error: 'Optimization failed' });
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
    console.log(`Don't forget to run 'node register.js' first to set up your credentials!`);
});
