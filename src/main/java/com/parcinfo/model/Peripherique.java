package com.parcinfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant un périphérique dans le système.
 */
@Entity
@Table(name = "peripheriques")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "idPeripherique")
@ToString
public class Peripherique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeripherique;

    @NotBlank(message = "Le nom est requis")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "La marque est requise")
    @Column(nullable = false)
    private String marque;

    @NotBlank(message = "Le modèle est requis")
    @Column(nullable = false)
    private String modele;

    @NotBlank(message = "Le numéro de série est requis")
    @Column(nullable = false, unique = true)
    private String numeroSerie;

    @NotNull(message = "Le type est requis")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePeripherique type;

    @Column(nullable = false)
    private LocalDate dateAchat;

    @Column(nullable = false)
    private LocalDate dateFinGarantie;

    @Column(nullable = false)
    private boolean enService = true;

    @ElementCollection
    @CollectionTable(name = "peripherique_commentaires", joinColumns = @JoinColumn(name = "peripherique_id"))
    @Column(name = "commentaire", columnDefinition = "TEXT")
    private Set<String> commentaires = new HashSet<>();

    /**
     * Vérifie si le périphérique est valide (en service et dans la période de garantie).
     *
     * @return true si le périphérique est valide, false sinon
     */
    public boolean estValide() {
        return enService && LocalDate.now().isBefore(dateFinGarantie);
    }

    /**
     * Ajoute un commentaire au périphérique.
     *
     * @param commentaire Le commentaire à ajouter
     */
    public void ajouterCommentaire(String commentaire) {
        if (commentaire != null && !commentaire.trim().isEmpty()) {
            commentaires.add(commentaire.trim());
        }
    }

    /**
     * Supprime un commentaire du périphérique.
     *
     * @param commentaire Le commentaire à supprimer
     */
    public void supprimerCommentaire(String commentaire) {
        commentaires.remove(commentaire);
    }
} 