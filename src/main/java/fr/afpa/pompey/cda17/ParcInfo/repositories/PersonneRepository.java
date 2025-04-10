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
     * @return Iterable of Personne objects.
     */
    public List<Personne> getPersonnes() {
        String baseApiUrl = props.getApiUrl(); // Base API URL from properties.
        String getPersonnesUrl = baseApiUrl + "/personnes"; // Endpoint for fetching all Personne objects.

        RestTemplate restTemplate = new RestTemplate(); // RestTemplate for making HTTP requests.
        ResponseEntity<List<Personne>> response =
                restTemplate.exchange(
                        getPersonnesUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        return response.getBody(); // Returns the response body containing the list of Personne.
    }

    /**
     * Fetches a specific Personne by ID from the API.
     * @param id The ID of the Personne to fetch.
     * @return The Personne object.
     */
    public Personne getPersonne(long id) {
        String baseApiUrl = props.getApiUrl(); // Base API URL from properties.
        String getPersonnesUrl = baseApiUrl + "/personne/" + id; // Endpoint for fetching a specific Personne by ID.

        RestTemplate restTemplate = new RestTemplate(); // RestTemplate for making HTTP requests.
        ResponseEntity<Personne> response = restTemplate.exchange(
                getPersonnesUrl,
                HttpMethod.GET,
                null,
                Personne.class // Response type for a single Personne.
        );

        return response.getBody(); // Returns the response body containing the Personne.
    }

    /**
     * Creates a new Personne in the API.
     * @param personne The Personne object to create.
     * @return The created Personne object.
     */
    public Personne createPersonne(Personne personne) {
        String baseApiUrl = props.getApiUrl(); // Base API URL from properties.
        String createPersonneUrl = baseApiUrl + "/personne"; // Endpoint for creating a new Personne.

        RestTemplate restTemplate = new RestTemplate(); // RestTemplate for making HTTP requests.
        HttpEntity<Personne> request = new HttpEntity<>(personne); // Wraps the Personne object in an HTTP request.
        ResponseEntity<Personne> response = restTemplate.exchange(
                createPersonneUrl,
                HttpMethod.POST,
                request,
                Personne.class // Response type for the created Personne.
        );

        return response.getBody(); // Returns the response body containing the created Personne.
    }

    /**
     * Deletes a specific Personne by ID in the API.
     * @param id The ID of the Personne to delete.
     */
    public void deletePersonne(long id) {
        String baseApiUrl = props.getApiUrl(); // Base API URL from properties.
        String deletePersonneUrl = baseApiUrl + "/personne/" + id; // Endpoint for deleting a specific Personne by ID.

        RestTemplate restTemplate = new RestTemplate(); // RestTemplate for making HTTP requests.
        restTemplate.exchange(
                deletePersonneUrl,
                HttpMethod.DELETE,
                null,
                Void.class // Response type for a delete operation (no content expected).
        );
    }

    /**
     * Updates an existing Personne in the API.
     * @param personne The Personne object with updated data.
     * @return The updated Personne object.
     */
    public Personne updatePersonne(Personne personne) {
        String baseApiUrl = props.getApiUrl(); // Base API URL from properties.
        String updatePersonneUrl = baseApiUrl + "/personne/" + personne.getId(); // Endpoint for updating a specific Personne.

        RestTemplate restTemplate = new RestTemplate(); // RestTemplate for making HTTP requests.
        HttpEntity<Personne> request = new HttpEntity<>(personne); // Wraps the updated Personne object in an HTTP request.
        ResponseEntity<Personne> response = restTemplate.exchange(
                updatePersonneUrl,
                HttpMethod.PUT,
                request,
                Personne.class // Response type for the updated Personne.
        );

        return response.getBody(); // Returns the response body containing the updated Personne.
    }
}
