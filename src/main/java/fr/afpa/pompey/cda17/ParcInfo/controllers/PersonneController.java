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

    @Autowired
    private PersonneService service;

    @Autowired
    private AppareilService appareilService;

    @GetMapping("/personnes")
    public String index(Model model) {
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        return "personnes/index";
    }

    @PostMapping("/personnes")
    public String index(Model model,
                        @ModelAttribute("alert") String alert,
                        @ModelAttribute("type") String type) {
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        model.addAttribute("alert", alert);
        model.addAttribute("type", type);
        return "personnes/index";
    }

    @GetMapping("/personnes/create")
    public String create(Model model) {
        model.addAttribute("personne", new Personne());
        return "personnes/create";
    }

    @PostMapping("/personnes/create")
    public RedirectView create(@ModelAttribute("personne") Personne personne,
                               RedirectAttributes redirectAttributes) {
        try{
            service.savePersonne(personne);
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été" +
                    " créée !");

            return new RedirectView("/personnes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été " +
                    "rencontré!");
            return new RedirectView("/personnes/create");
        }
    }

    @GetMapping("/personnes/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        Personne personne = service.getPersonne(id);
        model.addAttribute("personne", personne);
        return "personnes/update";
    }

    @PostMapping("/personnes/{id}/update")
    public RedirectView update(@ModelAttribute("personne") Personne personne,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {
        Personne current = service.getPersonne(id);

        try{
            current.setNom(personne.getNom());
            current.setPrenom(personne.getPrenom());
            current.setAdresse(personne.getAdresse());
            current.setTelephone(personne.getTelephone());
            current.setDateNaissance(personne.getDateNaissance());
            service.savePersonne(current);
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été" +
                    " modifiée !");

            return new RedirectView("/personnes");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été " +
                    "rencontré!");
            return new RedirectView("/personnes/"+id+"/update");
        }
    }

    @PostMapping("/personnes/{id}/delete")
    public RedirectView delete(
            @PathVariable("id") int id,
            RedirectAttributes redirectAttributes
    ) {
        try{
            service.deletePersonne(id);
            redirectAttributes.addFlashAttribute("type", "success");
            redirectAttributes.addFlashAttribute("alert", "La personne a bien été" +
                    " supprimée !");

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("type", "danger");
            redirectAttributes.addFlashAttribute("alert", "Un problème a été " +
                    "rencontré!");
        }

        return new RedirectView("/personnes");
    }

    @GetMapping("/personnes/{id}/affect")
    public String affect(Model model, @PathVariable("id") int id){
        Personne personne = service.getPersonne(id);
        Iterable<Appareil> appareils = appareilService.getAppareils();

        model.addAttribute("personne", personne);
        model.addAttribute("listAppareils", appareils);
        return "personnes/affect";
    }

    @PostMapping("/personnes/{id}/affect")
    public RedirectView affect(@RequestParam("appareils") String[] appareilIds,
                               @PathVariable("id") int id,
                               RedirectAttributes redirectAttributes) {

        Logger logger = Logger.getLogger(PersonneController.class.getName());
        logger.info(appareilIds.length + " appareils");

        Personne personne = service.getPersonne(id);
        service.affectAppareils(personne, appareilIds);

        redirectAttributes.addFlashAttribute("message", "Appareils affectés avec succès");
        return new RedirectView("/personnes");
    }
}
