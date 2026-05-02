const fs = require('fs');
const path = require('path');

const logFile = path.join(__dirname, 'app.log');

const loggingMiddleware = (req, res, next) => {
    const timestamp = new Date().toISOString();
    const { method, url, body, query } = req;
    
    const logMessage = `[${timestamp}] ${method} ${url}\n` +
                       `Query: ${JSON.stringify(query)}\n` +
                       `Body: ${JSON.stringify(body)}\n`;


    console.log(logMessage);
    fs.appendFileSync(logFile, logMessage + '-------------------\n');

    const originalSend = res.send;
    res.send = function (data) {
        const responseLog = `[${timestamp}] Response for ${method} ${url}: ${data}\n`;
        console.log(responseLog);
        fs.appendFileSync(logFile, responseLog + '===================\n');
        originalSend.apply(res, arguments);
    };

    next();
};

module.exports = loggingMiddleware;
