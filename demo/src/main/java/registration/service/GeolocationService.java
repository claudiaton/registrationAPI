package registration.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import registration.constants.Constants;
import registration.model.GeolocationData;
import registration.model.User;

@Service
public class GeolocationService {

    @RequestMapping("/user")
    @ResponseBody
    public GeolocationData fetchGeo (User user) {
        String url = Constants.GEOLOCATION_API_URL + user.getIpAddress();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, GeolocationData.class);
        }    
}
