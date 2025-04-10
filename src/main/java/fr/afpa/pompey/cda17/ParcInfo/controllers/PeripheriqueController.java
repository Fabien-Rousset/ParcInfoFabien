package fr.afpa.pompey.cda17.ParcInfo.controllers;

import fr.afpa.pompey.cda17.ParcInfo.models.Peripherique;
import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.models.TypePeripherique;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PeripheriqueRepository;
import fr.afpa.pompey.cda17.ParcInfo.services.PeripheriqueService;
import fr.afpa.pompey.cda17.ParcInfo.services.PersonneService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.logging.Logger;

@Data
@Controller
public class PeripheriqueController {

    @Autowired
    private PeripheriqueService peripheriqueService;

    @Autowired
    private PersonneService personneService;

    /**
     * Displays the list of all peripherals.
     * @param model The model to pass data to the view.
     * @return The view name for the peripherals list.
     */
    @GetMapping("/peripheriques")
    public String index(Model model) {
        // Fetch all peripherals from the service
        Iterable<Peripherique> peripheriques = peripheriqueService.getPeripheriques();
        // Add the list of peripherals to the model for rendering in the view
        model.addAttribute("peripheriques", peripheriques);
        // Return the view name for the peripherals list
        return "peripheriques/index";
    }

    /**
     * Displays the form to create a new peripheral.
     * @param model The model to pass data to the view.
     * @return The view name for the create form.
     */
    @GetMapping("/peripheriques/create")
    public String create(Model model) {
        // Add a new empty peripheral object to the model for the form
        model.addAttribute("peripherique", new Peripherique());
        // Add the list of peripheral types to the model for the dropdown
        model.addAttribute("types", TypePeripherique.values());
        // Return the view name for the create form
        return "peripheriques/create";
    }

    /**
     * Handles the creation of a new peripheral.
     * @param peripherique The peripheral object to create.
     * @param redirectAttributes Attributes for flash messages.
     * @return A redirect to the peripherals list or the create form on error.
     */
    @PostMapping("/peripheriques/create")
    public RedirectView create(
            @ModelAttribute("peripherique") Peripherique peripherique,
            RedirectAttributes redirectAttributes) {
        try {
            // Save the new peripheral using the service
            peripheriqueService.save(peripherique);
            // Add a success message to the redirect attributes
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a bien été créé !");
            // Redirect to the peripherals list
            return new RedirectView("/peripheriques");
        } catch (Exception e) {
            // Add an error message to the redirect attributes in case of failure
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            // Redirect back to the create form
            return new RedirectView("/peripheriques/create");
        }
    }

    /**
     * Displays the form to update an existing peripheral.
     * @param model The model to pass data to the view.
     * @param id The ID of the peripheral to update.
     * @return The view name for the update form.
     */
    @GetMapping("/peripheriques/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        // Fetch the peripheral to update by its ID
        Peripherique peripherique = peripheriqueService.getPeripherique(id);
        // Add the peripheral to the model for pre-filling the form
        model.addAttribute("peripherique", peripherique);
        // Add the list of peripheral types to the model for the dropdown
        model.addAttribute("types", TypePeripherique.values());
        // Return the view name for the update form
        return "peripheriques/update";
    }

    /**
     * Handles the update of an existing peripheral.
     * @param peripherique The updated peripheral object.
     * @param id The ID of the peripheral to update.
     * @param redirectAttributes Attributes for flash messages.
     * @return A redirect to the peripherals list or the update form on error.
     */
    @PostMapping("/peripheriques/{id}/update")
    public RedirectView update(
            @ModelAttribute("peripherique") Peripherique peripherique,
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes) {
        // Fetch the current peripheral by its ID
        Peripherique current = peripheriqueService.getPeripherique(id);

        try {
            // Update the type of the peripheral
            current.setType(peripherique.getType());
            // Update the label of the associated device
            current.getAppareil().setLibelle(peripherique.getAppareil().getLibelle());
            // Save the updated peripheral using the service
            peripheriqueService.save(current);
            // Add a success message to the redirect attributes
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a bien été modifié !");
            // Redirect to the peripherals list
            return new RedirectView("/peripheriques");
        } catch (Exception e) {
            // Log the exception message
            Logger logger = Logger.getLogger(PeripheriqueController.class.getName());
            logger.info(e.getMessage());
            // Add an error message to the redirect attributes
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            // Redirect back to the update form
            return new RedirectView("/peripheriques/" + id + "/update");
        }
    }

    /**
     * Handles the deletion of a peripheral.
     * @param id The ID of the peripheral to delete.
     * @param redirectAttributes Attributes for flash messages.
     * @return A redirect to the peripherals list.
     */
    @PostMapping("/peripheriques/{id}/delete")
    public RedirectView delete(@PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        try {
            // Delete the peripheral by its ID using the service
            peripheriqueService.deletePeripherique(id);
            // Add a success message to the redirect attributes
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a bien été supprimé !");
        } catch (Exception e) {
            // Add an error message to the redirect attributes in case of failure
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
        }
        // Redirect to the peripherals list
        return new RedirectView("/peripheriques");
    }

    /**
     * Displays the form to assign a peripheral to one or more persons.
     * @param model The model to pass data to the view.
     * @param id The ID of the peripheral to assign.
     * @return The view name for the assign form.
     */
    @GetMapping("/peripheriques/{id}/affect")
    public String affect(Model model, @PathVariable("id") int id) {
        // Fetch the peripheral to assign by its ID
        Peripherique peripherique = peripheriqueService.getPeripherique(id);
        // Add the peripheral to the model for rendering in the view
        model.addAttribute("peripherique", peripherique);
        // Fetch all persons to whom the peripheral can be assigned
        Iterable<Personne> personnes = personneService.getPersonnes();
        // Add the list of persons to the model for the dropdown
        model.addAttribute("personnes", personnes);
        // Return the view name for the assign form
        return "peripheriques/affect";
    }

    /**
     * Handles the assignment of a peripheral to one or more persons.
     * @param personnes The list of person IDs to assign the peripheral to.
     * @param id The ID of the peripheral to assign.
     * @param redirectAttributes Attributes for flash messages.
     * @return A redirect to the peripherals list or the assign form on error.
     */
    @PostMapping("/peripheriques/{id}/affect")
    public RedirectView affect(@RequestParam("personnes") String[] personnes,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        // Fetch the current peripheral by its ID
        Peripherique current = peripheriqueService.getPeripherique(id);

        try {
            // Log the list of person IDs to whom the peripheral is being assigned
            Logger logger = Logger.getLogger(PeripheriqueController.class.getName());
            logger.info(Arrays.toString(personnes));
            // Assign the peripheral to the selected persons using the service
            peripheriqueService.affect(current, personnes);
            // Add a success message to the redirect attributes
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a bien été modifié !");
            // Redirect to the peripherals list
            return new RedirectView("/peripheriques");
        } catch (Exception e) {
            // Log the exception message
            Logger logger = Logger.getLogger(PeripheriqueController.class.getName());
            logger.info(e.getMessage());
            // Add an error message to the redirect attributes
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            // Redirect back to the assign form
            return new RedirectView("/peripheriques/" + id + "/affect");
        }
    }
}
