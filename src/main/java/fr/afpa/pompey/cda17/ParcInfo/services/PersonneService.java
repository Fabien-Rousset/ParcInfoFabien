package fr.afpa.pompey.cda17.ParcInfo.services;

import fr.afpa.pompey.cda17.ParcInfo.models.Appareil;
import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PersonneRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository; // Injects the repository to interact with the data layer.

    /**
     * Retrieves a Personne object by its ID.
     * @param id The ID of the Personne to retrieve.
     * @return The Personne object with the specified ID.
     */
    public Personne getPersonne(int id) {
        return personneRepository.getPersonne(id);
    }

    /**
     * Retrieves all Personne objects.
     * @return An iterable collection of all Personne objects.
     */
    public Iterable<Personne> getPersonnes() {
        return personneRepository.getPersonnes();
    }

    /**
     * Deletes a Personne object by its ID.
     * @param id The ID of the Personne to delete.
     */
    public void deletePersonne(int id) {
        personneRepository.deletePersonne(id);
    }

    /**
     * Saves a Personne object. If the ID is 0, a new Personne is created.
     * Otherwise, the existing Personne is updated.
     * @param personne The Personne object to save.
     * @return The saved Personne object.
     */
    public Personne savePersonne(Personne personne) {
        Personne saved;

        if(personne.getId() == 0){
            // Create a new Personne if the ID is 0.
            saved = personneRepository.createPersonne(personne);
        }else{
            // Update the existing Personne if the ID is not 0.
            saved = personneRepository.updatePersonne(personne);
        }

        return saved;
    }

    /**
     * Assigns a list of Appareils to a Personne.
     * @param personne The Personne to whom the Appareils will be assigned.
     * @param appareils An array of Appareil identifiers to assign.
     */
    public void affectAppareils(Personne personne, String[] appareils) {
        personneRepository.affectAppareils(personne, appareils);
    }
}
