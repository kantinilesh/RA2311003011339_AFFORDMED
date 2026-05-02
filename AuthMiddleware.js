const axios = require('axios');
require('dotenv').config();

let cachedToken = null;
let tokenExpiry = null;

async function getAuthToken() {

    if (cachedToken && tokenExpiry && Date.now() < tokenExpiry) {
        return cachedToken;
    }

    try {
        const authData = {
            companyName: "AffordMed",
            clientID: process.env.CLIENT_ID,
            clientSecret: process.env.CLIENT_SECRET,
            ownerName: process.env.NAME,
            ownerEmail: process.env.EMAIL,
            rollNo: process.env.ROLL_NO
        };

        const response = await axios.post(`${process.env.TEST_SERVER_URL}/evaluation-service/auth`, authData);
        
        cachedToken = response.data.access_token;
        // Assume 5 minute expiry if not provided, or parse from response
        tokenExpiry = Date.now() + (response.data.expires_in || 300) * 1000;
        
        return cachedToken;
    } catch (error) {
        console.error('Failed to get Auth Token:', error.response ? error.response.data : error.message);
        throw error;
    }
}

const authMiddleware = async (req, res, next) => {
    try {
        const token = await getAuthToken();
        req.authToken = token;
        next();
    } catch (error) {
        res.status(500).json({ error: 'Authentication with AffordMed failed' });
    }
};

module.exports = { authMiddleware, getAuthToken };
