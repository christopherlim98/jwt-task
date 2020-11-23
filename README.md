# jwt-task
Please run both the frontend SPA and backend API to test.

To test, you can use the following credentials.
```
<username>:<password>
jrocket:jrocket
tcrews:tcrews
suspended:suspended
```

## Frontend SPA
The frontend is a simple Single Page Application built on react. 

To run the front-end:
```
npm install
npm run start
```
JWTs are parsed using the `auth0.jwt-decode` package.

## Backend API
The backend is a rest-controller API built using Spring-Boot.

To run the backend:
```
./mvnw spring-boot:run
```

To test the backend, a rest client is provided. The rest client can be found in:
```
backend/src/main/resources/static/RestClient.http
```

## Useful Directories

#### "backend/src/main/java/auth/security/" 
	
	=> Contains security configurations and JWT encoder 

#### "backend/src/main/java/auth/user/UserController/"

 	=> Handles the rest controller API end-point

#### "frontend/src/App.js"

	=> The SPA containing the front-end logic

#### "frontend/src/utils/api.js

	=> To decode JWT and fetch data from the backend API

