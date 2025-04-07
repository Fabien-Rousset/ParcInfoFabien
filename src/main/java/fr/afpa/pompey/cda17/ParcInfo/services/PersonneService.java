package fr.afpa.pompey.cda17.ParcInfo.services;

import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PersonneRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public Personne getPersonne(int id) {
        return personneRepository.getPersonne(id);
    }

    public Iterable<Personne> getPersonnes() {
        return personneRepository.getPersonnes();
    }

    public void deletePersonne(int id) {
        personneRepository.deletePersonne(id);
    }

    public Personne savePersonne(Personne personne) {
        Personne saved;

        if(personne.getId() == 0){
            saved = personneRepository.createPersonne(personne);
        }else{
            saved = personneRepository.updatePersonne(personne);
        }

        return saved;
    }
}
