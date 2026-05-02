# AffordMed Evaluation Service

This project is set up for the AffordMed evaluation task. It includes registration, mandatory logging, and authentication integration.

## Getting Started

### 1. Environment Setup
Copy the `.env.example` to `.env`:
```bash
cp .env.example .env
```
Fill in your details in the `.env` file:
- `EMAIL`: Your college email
- `NAME`: Your full name
- `ROLL_NO`: Your roll number
- `PHONE`: Your mobile number
- `GITHUB_USERNAME`: Your GitHub username
- `ACCESS_CODE`: The access code sent to your email (not the one in the example image)

### 2. Registration
Run the registration script to get your `ClientID` and `ClientSecret`:
```bash
node register.js
```
The script will output your `ClientID` and `ClientSecret`. Add these to your `.env` file.

### 3. Running the Server
Start the development server:
```bash
npm start
```
(Note: You might need to add `"start": "node index.js"` to your `package.json` scripts)

## Project Structure
- `index.js`: Main Express server.
- `register.js`: Utility to register with the test server.
- `LoggingMiddleware.js`: Mandatory logging middleware that logs to `app.log`.
- `AuthMiddleware.js`: Middleware to handle token-based authentication with the test server.

## Mandatory Logging
The logging middleware is applied globally in `index.js`. It logs every request, its body, and the corresponding response to both the console and `app.log`. This fulfills the evaluation requirement.
