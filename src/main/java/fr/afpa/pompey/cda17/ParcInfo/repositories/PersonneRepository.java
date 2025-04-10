package fr.afpa.pompey.cda17.ParcInfo.repositories;

import config.CustomProperties;
import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class PersonneRepository {

    @Autowired
    private CustomProperties props; // Injects custom properties containing configuration like API URL.

    /**
     * Fetches all Personne objects from the API.
     * Makes a GET request to the API endpoint for retrieving all Personne objects.
     * @return A list of Personne objects retrieved from the API.
     */
    public List<Personne> getPersonnes() {
        // Retrieve the base API URL from the custom properties.
        String baseApiUrl = props.getApiUrl();
        // Construct the endpoint URL for fetching all Personne objects.
        String getPersonnesUrl = baseApiUrl + "/personnes";

        // Create a RestTemplate instance to make HTTP requests.
        RestTemplate restTemplate = new RestTemplate();
        // Make a GET request to the API and retrieve the response as a list of Personne objects.
        ResponseEntity<List<Personne>> response =
                restTemplate.exchange(
                        getPersonnesUrl,
                        HttpMethod.GET,
                        null, // No request body is needed for GET requests.
                        new ParameterizedTypeReference<>() {} // Specify the response type.
                );

        // Return the list of Personne objects from the response body.
        return response.getBody();
    }

    /**
     * Fetches a specific Personne by ID from the API.
     * Makes a GET request to the API endpoint for retrieving a Personne by its ID.
     * @param id The ID of the Personne to fetch.
     * @return The Personne object retrieved from the API.
     */
    public Personne getPersonne(long id) {
        // Retrieve the base API URL from the custom properties.
        String baseApiUrl = props.getApiUrl();
        // Construct the endpoint URL for fetching a specific Personne by ID.
        String getPersonnesUrl = baseApiUrl + "/personne/" + id;

        // Create a RestTemplate instance to make HTTP requests.
        RestTemplate restTemplate = new RestTemplate();
        // Make a GET request to the API and retrieve the response as a single Personne object.
        ResponseEntity<Personne> response = restTemplate.exchange(
                getPersonnesUrl,
                HttpMethod.GET,
                null, // No request body is needed for GET requests.
                Personne.class // Specify the response type.
        );

        // Return the Personne object from the response body.
        return response.getBody();
    }

    /**
     * Creates a new Personne in the API.
     * Makes a POST request to the API endpoint for creating a new Personne.
     * Handles HTTP status codes: returns the created Personne if status is 201, 
     * throws an exception if status is 204 or unexpected.
     * @param personne The Personne object to create.
     * @return The created Personne object.
     */
    public Personne createPersonne(Personne personne) {
        // Retrieve the base API URL from the custom properties.
        String baseApiUrl = props.getApiUrl();
        // Construct the endpoint URL for creating a new Personne.
        String createPersonneUrl = baseApiUrl + "/personne";

        // Create a RestTemplate instance to make HTTP requests.
        RestTemplate restTemplate = new RestTemplate();
        // Wrap the Personne object in an HTTP request entity.
        HttpEntity<Personne> request = new HttpEntity<>(personne);
        // Make a POST request to the API and retrieve the response as a Personne object.
        ResponseEntity<Personne> response = restTemplate.exchange(
                createPersonneUrl,
                HttpMethod.POST,
                request,
                Personne.class // Specify the response type.
        );

        // Check the HTTP status code and handle accordingly.
        if (response.getStatusCodeValue() == 201) {
            // Return the created Personne object if the status is 201 (Created).
            return response.getBody();
        } else if (response.getStatusCodeValue() == 204) {
            // Throw an exception if the status is 204 (No Content).
            throw new RuntimeException("Personne non créée");
        } else {
            // Throw an exception for any other unexpected status code.
            throw new RuntimeException("Unexpected response status: " + response.getStatusCodeValue());
        }
    }

    /**
     * Deletes a specific Personne by ID in the API.
     * Makes a DELETE request to the API endpoint for deleting a Personne by its ID.
     * No content is expected in the response.
     * @param id The ID of the Personne to delete.
     */
    public void deletePersonne(long id) {
        // Retrieve the base API URL from the custom properties.
        String baseApiUrl = props.getApiUrl();
        // Construct the endpoint URL for deleting a specific Personne by ID.
        String deletePersonneUrl = baseApiUrl + "/personne/" + id;

        // Create a RestTemplate instance to make HTTP requests.
        RestTemplate restTemplate = new RestTemplate();
        // Make a DELETE request to the API. No response body is expected.
        restTemplate.exchange(
                deletePersonneUrl,
                HttpMethod.DELETE,
                null, // No request body is needed for DELETE requests.
                Void.class // Specify that no response body is expected.
        );
    }

    /**
     * Updates an existing Personne in the API.
     * Makes a PUT request to the API endpoint for updating a Personne.
     * The Personne object must contain the updated data and its ID.
     * @param personne The Personne object with updated data.
     * @return The updated Personne object retrieved from the API.
     */
    public Personne updatePersonne(Personne personne) {
        // Retrieve the base API URL from the custom properties.
        String baseApiUrl = props.getApiUrl();
        // Construct the endpoint URL for updating a specific Personne by ID.
        String updatePersonneUrl = baseApiUrl + "/personne/" + personne.getId();

        // Create a RestTemplate instance to make HTTP requests.
        RestTemplate restTemplate = new RestTemplate();
        // Wrap the updated Personne object in an HTTP request entity.
        HttpEntity<Personne> request = new HttpEntity<>(personne);
        // Make a PUT request to the API and retrieve the response as a Personne object.
        ResponseEntity<Personne> response = restTemplate.exchange(
                updatePersonneUrl,
                HttpMethod.PUT,
                request,
                Personne.class // Specify the response type.
        );

        // Return the updated Personne object from the response body.
        return response.getBody();
    }
}
