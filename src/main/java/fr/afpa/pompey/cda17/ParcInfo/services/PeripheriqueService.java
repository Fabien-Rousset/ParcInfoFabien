package fr.afpa.pompey.cda17.ParcInfo.services;

import fr.afpa.pompey.cda17.ParcInfo.models.Peripherique;
import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PeripheriqueRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Data
@Service
public class PeripheriqueService {

    @Autowired
    private PeripheriqueRepository peripheriqueRepository;

    public Peripherique getPeripherique(int id) {
        return peripheriqueRepository.getPeripherique(id);
    }

    public Iterable<Peripherique> getPeripheriques() {
        return peripheriqueRepository.getPeripheriques();
    }

    public Peripherique createPeripherique(Peripherique peripherique) {
        return peripheriqueRepository.createPeripherique(peripherique);
    }

    public void deletePeripherique(int id) {
        peripheriqueRepository.deletePeripherique(id);
    }

    public Peripherique save(Peripherique peripherique) {
        Peripherique saved;

        if(peripherique.getIdAppareil() == 0){
            saved = peripheriqueRepository.createPeripherique(peripherique);
        }else{
            saved = peripheriqueRepository.updatePeripherique(peripherique);
        }

        return saved;
    }

    public void affect(Peripherique peripherique, String[] personnes) {
        peripheriqueRepository.affectPersonnes(peripherique, personnes);
    }
}
