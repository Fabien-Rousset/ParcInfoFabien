package fr.afpa.pompey.cda17.ParcInfo.repositories;

import config.CustomProperties;
import fr.afpa.pompey.cda17.ParcInfo.models.Appareil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class AppareilRepository {

    // Injects the CustomProperties bean to access application-specific properties
    @Autowired
    private CustomProperties props;

    /**
     * Fetches a list of Appareil objects from the external API.
     * 
     * @return An iterable collection of Appareil objects.
     */
    public Iterable<Appareil> getAppareils() {
        // Retrieve the base API URL from the application properties
        String baseApiUrl = props.getApiUrl();
        // Construct the full URL for fetching appareils
        String appareilsUrl = baseApiUrl + "/appareils";

        // Create a RestTemplate instance for making HTTP requests
        RestTemplate restTemplate = new RestTemplate();
        // Make a GET request to the API and parse the response into an Iterable of Appareil
        ResponseEntity<Iterable<Appareil>> response =
                restTemplate.exchange(
                        appareilsUrl, // API endpoint
                        HttpMethod.GET, // HTTP method
                        null, // No request body
                        new ParameterizedTypeReference<Iterable<Appareil>>() {} // Response type
                );

        // Return the body of the response, which contains the list of appareils
        return response.getBody();
    }
}
