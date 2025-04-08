package fr.afpa.pompey.cda17.ParcInfo.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Personne {

    private long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private LocalDate dateNaissance;
    private List<Appareil> appareils = new ArrayList<>();
}
