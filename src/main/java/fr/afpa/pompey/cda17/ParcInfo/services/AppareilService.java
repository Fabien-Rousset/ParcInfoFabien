package fr.afpa.pompey.cda17.ParcInfo.services;

import fr.afpa.pompey.cda17.ParcInfo.models.Appareil;
import fr.afpa.pompey.cda17.ParcInfo.repositories.AppareilRepository;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PersonneRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data // Lombok annotation to generate boilerplate code like getters, setters, equals, hashCode, and toString methods.
@Service // Marks this class as a Spring service, making it a candidate for component scanning and dependency injection.
public class AppareilService {

    @Autowired // Automatically injects an instance of AppareilRepository into this service.
    private AppareilRepository appareilRepository;

    /**
     * Retrieves all Appareil entities from the repository.
     * 
     * @return an Iterable containing all Appareil objects.
     */
    public Iterable<Appareil> getAppareils(){
        return appareilRepository.getAppareils(); // Delegates the call to the repository to fetch all Appareil entities.
    }
}
