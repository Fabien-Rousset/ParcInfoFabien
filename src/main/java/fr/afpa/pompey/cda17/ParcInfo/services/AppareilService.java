package fr.afpa.pompey.cda17.ParcInfo.services;

import fr.afpa.pompey.cda17.ParcInfo.models.Appareil;
import fr.afpa.pompey.cda17.ParcInfo.repositories.AppareilRepository;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PersonneRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AppareilService {

    @Autowired
    private AppareilRepository appareilRepository;

    public Iterable<Appareil> getAppareils(){
        return appareilRepository.getAppareils();
    }
}
