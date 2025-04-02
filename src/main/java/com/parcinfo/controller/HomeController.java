package com.parcinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur pour la page d'accueil de l'application.
 */
@Controller
public class HomeController {

    /**
     * Affiche la page d'accueil.
     *
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
} 