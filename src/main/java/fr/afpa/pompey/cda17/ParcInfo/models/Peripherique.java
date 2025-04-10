package fr.afpa.pompey.cda17.ParcInfo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Peripherique {

    // Unique identifier for the peripherique
    private long idAppareil;

    // Type of the peripherique
    private TypePeripherique type;

    // Appareil inherent to Peripherique.
    private Appareil appareil;
}
