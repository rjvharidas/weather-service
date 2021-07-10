# weather-service

The API to search for a weather updates by city and lat-lon

## weather-service API
- GET /weather/update/lat-lon  : Find weather update by lat lon coordinates
- GET /weather/update/city : Find weather update by city

###Requirements
For building and running the application you need:

- JDK 14
- Redis
- Maven

## Sample Requests
- Lat-lon api `http://localhost:8080/weather/update/lat-lon?lat=13.0827&lon=80.2707`
- Lat-lon api `http://localhost:8080/weather/update/city?country=IN&city=chennai`
- Open API documentation `http://localhost:8080/swagger-ui.html`
