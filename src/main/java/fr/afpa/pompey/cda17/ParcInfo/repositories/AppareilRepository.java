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

    @Autowired
    private CustomProperties props;

    public Iterable<Appareil> getAppareils() {
        String baseApiUrl = props.getApiUrl();
        String appareilsUrl = baseApiUrl + "/appareils";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Appareil>> response =
                restTemplate.exchange(
                        appareilsUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Iterable<Appareil>>() {}
                );

        return response.getBody();
    }
}
