package fr.afpa.pompey.cda17.ParcInfo.controllers;

// Import necessary classes and services

import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.services.AppareilService;
import fr.afpa.pompey.cda17.ParcInfo.services.PersonneService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Data
@Controller
public class PersonneController {

    // Inject PersonneService to handle business logic related to Personne
    @Autowired
    private PersonneService service;

    // Inject AppareilService to handle business logic related to Appareil
    @Autowired
    private AppareilService appareilService;

    /**
     * Display the list of all Personne entities.
     * 
     * @param model The model to pass data to the view.
     * @return The view name for listing Personne entities.
     */
    @GetMapping("/personnes")
    public String index(Model model) {
        // Fetch all Personne entities and add them to the model
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        return "personnes/index";
    }

    /**
     * Handle POST requests to display the list of Personne entities with additional attributes.
     * 
     * @param model The model to pass data to the view.
     * @param alert The alert message to display.
     * @param type  The type of alert (e.g., success, danger).
     * @return The view name for listing Personne entities.
     */
    @PostMapping("/personnes")
    public String index(Model model,
                        @ModelAttribute("alert") String alert,
                        @ModelAttribute("type") String type) {
        // Fetch all Personne entities and add them to the model
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        model.addAttribute("alert", alert); // Add alert message to the model
        model.addAttribute("type", type);   // Add alert type to the model
        return "personnes/index"; // Return the view for listing Personne entities
    }

    /**
     * Display the form to create a new Personne.
     * 
     * @param model The model to pass data to the view.
     * @return The view name for creating a Personne.
     */
    @GetMapping("/personnes/create")
    public String create(Model model) {
        model.addAttribute("personne", new Personne()); // Add an empty Personne object to the model
        return "personnes/create"; // Return the view for creating a Personne
    }

    /**
     * Handle the creation of a new Personne.
     * 
     * @param personne           The Personne object to create.
     * @param redirectAttributes Attributes for redirect scenarios.
     * @return A RedirectView to the appropriate page.
     */
    @PostMapping("/personnes/create")
    public RedirectView create(@ModelAttribute("personne") Personne personne,
                               RedirectAttributes redirectAttributes) {
        try {
            service.savePersonne(personne); // Save the new Personne
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été créée !");
            return new RedirectView("/personnes"); // Redirect to the list of Personne entities
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/personnes/create"); // Redirect back to the creation form
        }
    }

    /**
     * Display the form to update an existing Personne.
     * 
     * @param model The model to pass data to the view.
     * @param id    The ID of the Personne to update.
     * @return The view name for updating a Personne.
     */
    @GetMapping("/personnes/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        Personne personne = service.getPersonne(id); // Fetch the Personne by ID
        model.addAttribute("personne", personne); // Add the Personne to the model
        return "personnes/update"; // Return the view for updating a Personne
    }

    /**
     * Handle the update of an existing Personne.
     * 
     * @param personne           The updated Personne object.
     * @param id                 The ID of the Personne to update.
     * @param redirectAttributes Attributes for redirect scenarios.
     * @return A RedirectView to the appropriate page.
     */
    @PostMapping("/personnes/{id}/update")
    public RedirectView update(@ModelAttribute("personne") Personne personne,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        Personne current = service.getPersonne(id); // Fetch the current Personne by ID

        try {
            // Update the fields of the current Personne
            current.setNom(personne.getNom());
            current.setPrenom(personne.getPrenom());
            current.setAdresse(personne.getAdresse());
            current.setTelephone(personne.getTelephone());
            current.setDateNaissance(personne.getDateNaissance());
            service.savePersonne(current); // Save the updated Personne
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été modifiée !");
            return new RedirectView("/personnes"); // Redirect to the list of Personne entities
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/personnes/" + id + "/update"); // Redirect back to the update form
        }
    }

    /**
     * Handle the deletion of a Personne.
     * 
     * @param id                 The ID of the Personne to delete.
     * @param redirectAttributes Attributes for redirect scenarios.
     * @return A RedirectView to the list of Personne entities.
     */
    @PostMapping("/personnes/{id}/delete")
    public RedirectView delete(@PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        try {
            service.deletePersonne(id); // Delete the Personne by ID
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été supprimée !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
        }
        return new RedirectView("/personnes"); // Redirect to the list of Personne entities
    }
}
