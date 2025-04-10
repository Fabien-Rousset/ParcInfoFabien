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

    @GetMapping("/peripheriques")
    public String index(Model model) {
        Iterable<Peripherique> peripheriques = peripheriqueService.getPeripheriques();
        model.addAttribute("peripheriques", peripheriques);
        return "peripheriques/index";
    }

    @GetMapping("/peripheriques/create")
    public String create(Model model) {
        model.addAttribute("peripherique", new Peripherique());
        model.addAttribute("types", TypePeripherique.values());
        return "peripheriques/create";
    }

    @PostMapping("/peripheriques/create")
    public RedirectView create(
            @ModelAttribute("peripherique") Peripherique peripherique,
            RedirectAttributes redirectAttributes) {
        try {
            peripheriqueService.save(peripherique); // Save the new Personne
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a " +
                    "bien été créé !");
            return new RedirectView("/peripheriques"); // Redirect to the list of Personne entities
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/peripheriques/create"); // Redirect back to the creation form
        }
    }

    @GetMapping("/peripheriques/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        Peripherique peripherique = peripheriqueService.getPeripherique(id);
        model.addAttribute("peripherique", peripherique);
        model.addAttribute("types", TypePeripherique.values());
        return "peripheriques/update";
    }

    @PostMapping("/peripheriques/{id}/update")
    public RedirectView update(
            @ModelAttribute("peripherique") Peripherique peripherique,
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes) {
        Peripherique current = peripheriqueService.getPeripherique(id);

        try {
            // Update the fields of the current Personne
            current.setType(peripherique.getType());
            current.getAppareil().setLibelle(peripherique.getAppareil().getLibelle());
            peripheriqueService.save(current); // Save the updated Personne
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a " +
                    "bien été modifié !");
            return new RedirectView("/peripheriques"); // Redirect to the list of Personne entities
        } catch (Exception e) {
            Logger logger = Logger.getLogger(PeripheriqueController.class.getName());
            logger.info(e.getMessage());
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/peripheriques/" + id + "/update"); // Redirect back to the update form
        }
    }

    @PostMapping("/peripheriques/{id}/delete")
    public RedirectView delete(@PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        try {
            peripheriqueService.deletePeripherique(id); // Delete the Personne by ID
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a " +
                    "bien été supprimé !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
        }
        return new RedirectView("/peripheriques"); // Redirect to the list of Personne entities
    }

    @GetMapping("/peripheriques/{id}/affect")
    public String affect(Model model, @PathVariable("id") int id){
        Peripherique peripherique = peripheriqueService.getPeripherique(id);
        model.addAttribute("peripherique", peripherique);
        Iterable<Personne> personnes = personneService.getPersonnes();
        model.addAttribute("personnes", personnes);
        return "peripheriques/affect";
    }

    @PostMapping("/peripheriques/{id}/affect")
    public RedirectView affect(@RequestParam("personnes") String[] personnes,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes){
        Peripherique current = peripheriqueService.getPeripherique(id);

        try {
            Logger logger = Logger.getLogger(PeripheriqueController.class.getName());
            logger.info(Arrays.toString(personnes));
            peripheriqueService.affect(current, personnes);

            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "Le périphérique a " +
                    "bien été modifié !");
            return new RedirectView("/peripheriques"); // Redirect to the list of Personne entities
        } catch (Exception e) {
            Logger logger = Logger.getLogger(PeripheriqueController.class.getName());
            logger.info(e.getMessage());

            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été rencontré!");
            return new RedirectView("/peripheriques/" + id + "/affect"); //
            // Redirect back to the update form
        }
    }
}
