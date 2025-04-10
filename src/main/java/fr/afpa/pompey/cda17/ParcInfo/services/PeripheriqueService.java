package fr.afpa.pompey.cda17.ParcInfo.services;

import fr.afpa.pompey.cda17.ParcInfo.models.Peripherique;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PeripheriqueRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PeripheriqueService {

    @Autowired
    private PeripheriqueRepository peripheriqueRepository;

    /**
     * Retrieves a Peripherique by its ID.
     * Calls the repository method to fetch a Peripherique object based on the provided ID.
     * @param id the ID of the Peripherique to retrieve.
     * @return the Peripherique object.
     */
    public Peripherique getPeripherique(int id) {
        return peripheriqueRepository.getPeripherique(id);
    }

    /**
     * Retrieves all Peripheriques.
     * Calls the repository method to fetch all Peripherique objects.
     * @return an iterable collection of Peripherique objects.
     */
    public Iterable<Peripherique> getPeripheriques() {
        return peripheriqueRepository.getPeripheriques();
    }

    /**
     * Creates a new Peripherique.
     * Calls the repository method to save a new Peripherique object in the database.
     * @param peripherique the Peripherique object to create.
     * @return the created Peripherique object.
     */
    public Peripherique createPeripherique(Peripherique peripherique) {
        return peripheriqueRepository.createPeripherique(peripherique);
    }

    /**
     * Deletes a Peripherique by its ID.
     * Calls the repository method to remove a Peripherique object from the database using its ID.
     * @param id the ID of the Peripherique to delete.
     */
    public void deletePeripherique(int id) {
        peripheriqueRepository.deletePeripherique(id);
    }

    /**
     * Saves a Peripherique.
     * If the ID of the Peripherique is 0, it creates a new Peripherique.
     * Otherwise, it updates the existing Peripherique in the database.
     * @param peripherique the Peripherique object to save.
     * @return the saved Peripherique object.
     */
    public Peripherique save(Peripherique peripherique) {
        Peripherique saved;

        if(peripherique.getIdAppareil() == 0){
            saved = peripheriqueRepository.createPeripherique(peripherique);
        }else{
            saved = peripheriqueRepository.updatePeripherique(peripherique);
        }

        return saved;
    }

    /**
     * Assigns a list of Personnes to a Peripherique.
     * Calls the repository method to associate the provided Personne identifiers with the given Peripherique.
     * @param peripherique the Peripherique to which the Personnes will be assigned.
     * @param personnes an array of Personne identifiers.
     */
    public void affect(Peripherique peripherique, String[] personnes) {
        peripheriqueRepository.affectPersonnes(peripherique, personnes);
    }
}
