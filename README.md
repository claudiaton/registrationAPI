
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


## Testing cases


## Testing values
