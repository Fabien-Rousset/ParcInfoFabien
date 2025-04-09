package fr.afpa.pompey.cda17.ParcInfo.models;

import lombok.Data;

/**
 * The Appareil class represents a device or equipment in the system.
 * It uses Lombok's @Data annotation to automatically generate
 * boilerplate code such as getters, setters, equals, hashCode, and toString methods.
 */
@Data
public class Appareil {

    /**
     * The unique identifier for the Appareil.
     */
    private long id;

    /**
     * The name or label of the Appareil.
     */
    private String libelle;
}
