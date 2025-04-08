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


@Slf4j
@Component
public class PersonneRepository {

    @Autowired
    private CustomProperties props;

    public Iterable<Personne> getPersonnes() {
        String baseApiUrl = props.getApiUrl();
        String getPersonnesUrl = baseApiUrl + "/personnes";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Personne>> response =
                restTemplate.exchange(
                        getPersonnesUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Iterable<Personne>>() {}
                );

        return response.getBody();
    }

    public Personne getPersonne(long id) {
        String baseApiUrl = props.getApiUrl();
        String getPersonnesUrl = baseApiUrl + "/personne/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Personne> response = restTemplate.exchange(
                getPersonnesUrl,
                HttpMethod.GET,
                null,
                Personne.class
        );

        return response.getBody();
    }

    public Personne createPersonne(Personne personne) {
        String baseApiUrl = props.getApiUrl();
        String createPersonneUrl = baseApiUrl + "/personne";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Personne> request = new HttpEntity<>(personne);
        ResponseEntity<Personne> response = restTemplate.exchange(
                createPersonneUrl,
                HttpMethod.POST,
                request,
                Personne.class
        );

        return response.getBody();
    }

    public void deletePersonne(long id) {
        String baseApiUrl = props.getApiUrl();
        String deletePersonneUrl = baseApiUrl + "/personne/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deletePersonneUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    public Personne updatePersonne(Personne personne) {
        String baseApiUrl = props.getApiUrl();
        String updatePersonneUrl = baseApiUrl + "/personne/"+ personne.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Personne> request = new HttpEntity<>(personne);
        ResponseEntity<Personne> response = restTemplate.exchange(
                updatePersonneUrl,
                HttpMethod.PUT,
                request,
                Personne.class
        );

        return response.getBody();
    }
}
