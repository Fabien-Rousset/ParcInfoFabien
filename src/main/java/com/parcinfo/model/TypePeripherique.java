package com.parcinfo.model;

/**
 * Énumération représentant les différents types de périphériques disponibles.
 */
public enum TypePeripherique {
    SOURIS("Souris"),
    CLAVIER("Clavier"),
    ECRAN("Écran"),
    IMPRIMANTE("Imprimante"),
    CASQUE("Casque"),
    CABLE("Cable");

    private final String libelle;

    TypePeripherique(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
} 