package registration.controller;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import registration.constants.Constants;
import registration.model.GeolocationData;
import registration.model.User;
import registration.model.UserData;
import registration.service.GeolocationService;

@RestController
@RequestMapping("/")
public class DemoController {
    
    @Autowired
    private GeolocationService geolocationService;

    @PostMapping(
        value = "registration",   
        produces = MediaType.APPLICATION_JSON_VALUE  
        )
    public ResponseEntity<UserData> demo1(@RequestBody @Valid User user) throws Exception {

        UserData userData = new UserData();
        GeolocationData geolocation;

        try {
            geolocation = geolocationService.fetchGeo(user);
        } catch (Exception e) {
            throw new Exception ("Unable to retrieve user data. Geolocation service currently unavailable."); 
        }
        
        if (!geolocation.getCountry().equalsIgnoreCase(Constants.COUNTRY)) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "This user is not eligible. Required to be in Canada.");
        }
        userData.setUuid(UUID.randomUUID());
        userData.setMessage("Welcome, " + user.getUsername() + " from " + geolocation.getCity());
        return ResponseEntity.ok(userData);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user is not eligible. Required to be in Canada.");
    }

}
