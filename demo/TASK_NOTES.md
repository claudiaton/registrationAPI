
# Registration API

## Specifications
Write an API microservice using spring boot to simulate user registration:
- Expose REST API to accept a payload of username, password, and IP address.
- All parameters must not be blank (!= empty and null). Return error messages if not valid
- Password need to be greater than 8 characters
- Call this end point to get geolocation for the provided IP:IP-API.com - Geolocation API - Documentation - JSON. If the IP is not in Canada, return error message that user is not eligible to register
- When all validation is passed, return a random uuid and a welcome message with his username and his City Name (resolved using ip-geolocation api)
- Need to have JUnit Tests
- Code must be shared via bitbucket or github

## Assumptions
1. The project/package name is temporary and irrelevant for the purpose of this exercise.
2. The validation to be provided for password is related to its lenght. Authentication (and possible connection to a database) will not be provided in this exercise.
3. The Username and city shall be included in the message text and not provided individually.
4. The password lenght 8 is not acceptable.
5. Each error must return an specific message, which may be included and created by the dev.

## Design
1. Input {
    Str username 
    Str password
    Str IP
    }

2. Output {
        Str uuid
        Str message {
            Str username
            Str city
            }
    }

## Testing cases
Valid input data
Invalid input data
ipgeo success
    Canada
    Outside
ipgeo fail

## Useful links
https://www.codejava.net/frameworks/spring-boot/rest-api-request-validation-examples

https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/

## TODO
Implement Service Test
Review and reorganize Exception handlers/advices

