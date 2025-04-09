package fr.afpa.pompey.cda17.ParcInfo.controllers;

// Import necessary classes and services

import fr.afpa.pompey.cda17.ParcInfo.models.Appareil;
import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.models.TypePeripherique;
import fr.afpa.pompey.cda17.ParcInfo.services.AppareilService;
import fr.afpa.pompey.cda17.ParcInfo.services.PersonneService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    // Display the list of all Personne entities
    @GetMapping("/personnes")
    public String index(Model model) {
        // Fetch all Personne entities and add them to the model
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        return "personnes/index"; // Return the view for listing Personne entities
    }

    // Handle POST requests to display the list of Personne entities with additional attributes
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

    // Display the form to create a new Personne
    @GetMapping("/personnes/create")
    public String create(Model model) {
        model.addAttribute("personne", new Personne()); // Add an empty Personne object to the model
        return "personnes/create"; // Return the view for creating a Personne
    }

    // Handle the creation of a new Personne
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

    // Display the form to update an existing Personne
    @GetMapping("/personnes/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        Personne personne = service.getPersonne(id); // Fetch the Personne by ID
        model.addAttribute("personne", personne); // Add the Personne to the model
        return "personnes/update"; // Return the view for updating a Personne
    }

    // Handle the update of an existing Personne
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

    // Handle the deletion of a Personne
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

    // Display the form to assign Appareils to a Personne
    @GetMapping("/personnes/{id}/affect")
    public String affect(Model model, @PathVariable("id") int id) {
        Personne personne = service.getPersonne(id); // Fetch the Personne by ID
        Iterable<Appareil> appareils = appareilService.getAppareils(); // Fetch all Appareils

        model.addAttribute("personne", personne); // Add the Personne to the model
        model.addAttribute("listAppareils", appareils); // Add the list of Appareils to the model
        return "personnes/affect"; // Return the view for assigning Appareils
    }

    // Handle the assignment of Appareils to a Personne
    @PostMapping("/personnes/{id}/affect")
    public RedirectView affect(@RequestParam("appareils") String[] appareilIds,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        try {
            Personne personne = service.getPersonne(id); // Fetch the Personne by ID
            service.affectAppareils(personne, appareilIds); // Assign the selected Appareils to the Personne
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Appareils affectés!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
        }
        return new RedirectView("/personnes"); // Redirect to the list of Personne entities
    }
}
