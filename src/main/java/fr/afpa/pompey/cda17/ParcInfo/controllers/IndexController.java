package fr.afpa.pompey.cda17.ParcInfo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The IndexController class is a Spring MVC controller responsible for handling
 * HTTP requests to the root URL ("/"). It serves as the entry point for the web
 * application and returns the name of the view to be rendered.
 */
@Controller // Marks this class as a Spring MVC controller to handle web requests.
public class IndexController {

    /**
     * Handles HTTP GET requests for the root URL ("/").
     * 
     * @return The name of the view ("index") to be rendered by the view resolver.
     */
    @GetMapping("/") // Maps HTTP GET requests for the root URL ("/") to this method.
    public String index() {
        // Returns the name of the view (e.g., "index") to be rendered.
        return "index";
    }
}
