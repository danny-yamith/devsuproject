# devsuproject
To deploy the application, you must:
1. Download the code.
2. Install MySQL and import the SQL file.
3. In the project, Docker must be installed on the PC.
4. To run it, you need to execute it in order.
   a. `service-registry`: This service handles client discovery.
   b. `ConfigServer`: This service manages access to Eureka and uses a file in the repository called https://github.com/danny-yamith/spring-app-config.
   c. `CloudGateway`: This is the API Gateway.
   d. `CustomerPersonService`.
   e. `AccountMoveService`.
5. Once all these services are up and running, you can use the Postman files to make queries.
