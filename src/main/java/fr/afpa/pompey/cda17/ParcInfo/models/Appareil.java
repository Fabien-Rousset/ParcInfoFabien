package fr.afpa.pompey.cda17.ParcInfo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The Appareil class represents a device or equipment in the system.
 * It uses Lombok's @Data annotation to automatically generate
 * boilerplate code such as getters, setters, equals, hashCode, and toString methods.
 */
@Data
@NoArgsConstructor
public class Appareil {

    /**
     * The unique identifier for the Appareil.
     */
    private long id;

    /**
     * The name or label of the Appareil.
     */
    private String libelle;

    /**
     * The proprietaires of the Appareil.
     */
    private List<Personne> proprietaires = new ArrayList<>();
}
