package fr.afpa.pompey.cda17.ParcInfo.controllers;

import fr.afpa.pompey.cda17.ParcInfo.models.Appareil;
import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PersonneRepository;
import fr.afpa.pompey.cda17.ParcInfo.services.AppareilService;
import fr.afpa.pompey.cda17.ParcInfo.services.PersonneService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Data
@Controller
public class PersonneController {

    // Injecting the PersonneService to handle business logic related to "Personne"
    @Autowired
    private PersonneService service;

    // Injecting the AppareilService to handle business logic related to "Appareil"
    @Autowired
    private AppareilService appareilService;

    // Handles GET requests to "/personnes" and displays a list of all "Personne" entities
    @GetMapping("/personnes")
    public String index(Model model) {
        // Fetch all "Personne" entities and add them to the model
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        return "personnes/index"; // Return the view for displaying the list
    }

    // Handles POST requests to "/personnes" and displays the list with additional alert messages
    @PostMapping("/personnes")
    public String index(Model model,
                        @ModelAttribute("alert") String alert,
                        @ModelAttribute("type") String type) {
        // Fetch all "Personne" entities and add them to the model
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        model.addAttribute("alert", alert); // Add alert message to the model
        model.addAttribute("type", type);   // Add alert type to the model
        return "personnes/index"; // Return the view for displaying the list
    }

    // Handles GET requests to "/personnes/create" and displays the form for creating a new "Personne"
    @GetMapping("/personnes/create")
    public String create(Model model) {
        model.addAttribute("personne", new Personne()); // Add a new "Personne" object to the model
        return "personnes/create"; // Return the view for the creation form
    }

    // Handles POST requests to "/personnes/create" and saves a new "Personne" entity
    @PostMapping("/personnes/create")
    public RedirectView create(@ModelAttribute("personne") Personne personne,
                               RedirectAttributes redirectAttributes) {
        try {
            service.savePersonne(personne); // Save the new "Personne" entity
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été créée !");
            return new RedirectView("/personnes"); // Redirect to the list view
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/personnes/create"); // Redirect back to the creation form
        }
    }

    // Handles GET requests to "/personnes/{id}/update" and displays the form for updating a "Personne"
    @GetMapping("/personnes/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        Personne personne = service.getPersonne(id); // Fetch the "Personne" entity by ID
        model.addAttribute("personne", personne); // Add the entity to the model
        return "personnes/update"; // Return the view for the update form
    }

    // Handles POST requests to "/personnes/{id}/update" and updates an existing "Personne" entity
    @PostMapping("/personnes/{id}/update")
    public RedirectView update(@ModelAttribute("personne") Personne personne,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        Personne current = service.getPersonne(id); // Fetch the current "Personne" entity

        try {
            // Update the fields of the current entity with the new values
            current.setNom(personne.getNom());
            current.setPrenom(personne.getPrenom());
            current.setAdresse(personne.getAdresse());
            current.setTelephone(personne.getTelephone());
            current.setDateNaissance(personne.getDateNaissance());
            service.savePersonne(current); // Save the updated entity
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été modifiée !");
            return new RedirectView("/personnes"); // Redirect to the list view
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/personnes/" + id + "/update"); // Redirect back to the update form
        }
    }

    // Handles POST requests to "/personnes/{id}/delete" and deletes a "Personne" entity
    @PostMapping("/personnes/{id}/delete")
    public RedirectView delete(@PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        try {
            service.deletePersonne(id); // Delete the "Personne" entity by ID
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été supprimée !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
        }
        return new RedirectView("/personnes"); // Redirect to the list view
    }

    // Handles GET requests to "/personnes/{id}/affect" and displays the form for assigning "Appareil" entities to a "Personne"
    @GetMapping("/personnes/{id}/affect")
    public String affect(Model model, @PathVariable("id") int id) {
        Personne personne = service.getPersonne(id); // Fetch the "Personne" entity by ID
        Iterable<Appareil> appareils = appareilService.getAppareils(); // Fetch all "Appareil" entities

        model.addAttribute("personne", personne); // Add the "Personne" entity to the model
        model.addAttribute("listAppareils", appareils); // Add the list of "Appareil" entities to the model
        return "personnes/affect"; // Return the view for the assignment form
    }

    // Handles POST requests to "/personnes/{id}/affect" and assigns selected "Appareil" entities to a "Personne"
    @PostMapping("/personnes/{id}/affect")
    public RedirectView affect(@RequestParam("appareils") String[] appareilIds,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        Logger logger = Logger.getLogger(PersonneController.class.getName());
        logger.info(appareilIds.length + " appareils"); // Log the number of selected "Appareil" entities

        Personne personne = service.getPersonne(id); // Fetch the "Personne" entity by ID
        service.affectAppareils(personne, appareilIds); // Assign the selected "Appareil" entities to the "Personne"

        redirectAttributes.addFlashAttribute("message", "Appareils affectés avec succès");
        return new RedirectView("/personnes"); // Redirect to the list view
    }
}
