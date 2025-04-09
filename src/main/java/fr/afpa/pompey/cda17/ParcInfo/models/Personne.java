package fr.afpa.pompey.cda17.ParcInfo.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// The @Data annotation from Lombok generates boilerplate code such as getters, setters, equals, hashCode, and toString methods.
@Data
public class Personne {

    // Unique identifier for the person
    private long id;

    // Last name of the person
    private String nom;

    // First name of the person
    private String prenom;

    // Address of the person
    private String adresse;

    // Phone number of the person
    private String telephone;

    // Date of birth of the person
    private LocalDate dateNaissance;

    // List of devices (Appareil) associated with the person
    private List<Appareil> appareils = new ArrayList<>();
}
