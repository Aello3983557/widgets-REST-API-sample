# Summary
A web service to work with widgets via HTTP REST API. The service stores only widgets,
assuming that all clients work with the same board

## Howto's:

### Build the project:
* Checkout the project.
* Build the project with maven:

  `mvn clean install`

### Launching the server:
* After the build is done launch the server with following:

  `mvn spring-boot:run`

* Server is launched on 8080 port. Target on http://localhost:8080/widget
* To access the interactive API (openAPI) on: http://localhost:8080/api

## REST API: 
Following rest APIs documented by SWAGGER. The Swagger UI located at http://localhost:8080/swagger-ui/#/