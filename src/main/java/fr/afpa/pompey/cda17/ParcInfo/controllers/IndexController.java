package fr.afpa.pompey.cda17.ParcInfo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Marks this class as a Spring MVC controller to handle web requests.
public class IndexController {

    @GetMapping("/") // Maps HTTP GET requests for the root URL ("/") to this method.
    public String index() {
        // Returns the name of the view (e.g., "index") to be rendered.
        return "index";
    }
}
