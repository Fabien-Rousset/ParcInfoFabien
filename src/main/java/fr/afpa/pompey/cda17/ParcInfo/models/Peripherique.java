package fr.afpa.pompey.cda17.ParcInfo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a peripheral device in the system.
 * A Peripherique is associated with an Appareil and has a specific type.
 */
@Data
@NoArgsConstructor
public class Peripherique {

    /**
     * Unique identifier for the peripheral device.
     * This ID is used to distinguish one Peripherique from another.
     */
    private long idAppareil;

    /**
     * The type of the peripheral device.
     * This is represented by an enumeration {@link TypePeripherique}.
     */
    private TypePeripherique type;

    /**
     * The associated Appareil for this Peripherique.
     * This represents the device to which the Peripherique is linked.
     */
    private Appareil appareil;
}
