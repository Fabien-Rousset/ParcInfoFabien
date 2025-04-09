package fr.afpa.pompey.cda17.ParcInfo.repositories;

import config.CustomProperties;
import fr.afpa.pompey.cda17.ParcInfo.models.Peripherique;
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
public class PeripheriqueRepository {

    @Autowired
    private CustomProperties props;

    public List<Peripherique> getPeripheriques() {
        String baseApiUrl = props.getApiUrl();
        String getPeripheriquesUrl = baseApiUrl + "/peripheriques";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Peripherique>> response =
                restTemplate.exchange(
                        getPeripheriquesUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        return response.getBody();
    }

    public Peripherique getPeripherique(int id) {
        String baseApiUrl = props.getApiUrl();
        String getPeripheriquesUrl = baseApiUrl + "/peripherique/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Peripherique> response = restTemplate.exchange(
                getPeripheriquesUrl,
                HttpMethod.GET,
                null,
                Peripherique.class
        );

        return response.getBody();
    }

    public Peripherique createPeripherique(Peripherique peripherique) {
        String baseApiUrl = props.getApiUrl();
        String createPeripheriqueUrl = baseApiUrl + "/peripherique";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Peripherique> request = new HttpEntity<>(peripherique);
        ResponseEntity<Peripherique> response = restTemplate.exchange(
                createPeripheriqueUrl,
                HttpMethod.POST,
                request,
                Peripherique.class
        );

        return response.getBody();
    }

    public void deletePeripherique(int id) {
        String baseApiUrl = props.getApiUrl();
        String deletePeripheriqueUrl = baseApiUrl + "/peripherique/" + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                deletePeripheriqueUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }

    public Peripherique updatePeripherique(Peripherique peripherique) {
        String baseApiUrl = props.getApiUrl();
        String updatePeripheriqueUrl = baseApiUrl + "/peripherique";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Peripherique> request = new HttpEntity<>(peripherique);
        ResponseEntity<Peripherique> response = restTemplate.exchange(
                updatePeripheriqueUrl,
                HttpMethod.PUT,
                request,
                Peripherique.class
        );

        return  response.getBody();
    }
}
