const axios = require('axios');
require('dotenv').config();

async function register() {
    const registrationData = {
        email: process.env.EMAIL,
        name: process.env.NAME,
        mobileNo: process.env.PHONE,
        githubUsername: process.env.GITHUB_USERNAME,
        rollNo: process.env.ROLL_NO,
        accessCode: process.env.ACCESS_CODE
    };

    try {
        console.log('Registering with AffordMed Test Server...');
        const response = await axios.post(`${process.env.TEST_SERVER_URL}/evaluation-service/register`, registrationData);
        
        console.log('Registration Successful!');
        console.log('Response:', JSON.stringify(response.data, null, 2));
        
        console.log('\nIMPORTANT: Add the clientID and clientSecret to your .env file:');
        console.log(`CLIENT_ID=${response.data.clientID}`);
        console.log(`CLIENT_SECRET=${response.data.clientSecret}`);
    } catch (error) {
        console.error('Registration Failed:', error.response ? error.data : error.message);
        if (error.response) {
            console.error('Status:', error.response.status);
            console.error('Data:', error.response.data);
        }
    }
}

register();
