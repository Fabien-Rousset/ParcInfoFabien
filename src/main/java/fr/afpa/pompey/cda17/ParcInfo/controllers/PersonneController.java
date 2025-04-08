package fr.afpa.pompey.cda17.ParcInfo.controllers;

import fr.afpa.pompey.cda17.ParcInfo.models.Personne;
import fr.afpa.pompey.cda17.ParcInfo.repositories.PersonneRepository;
import fr.afpa.pompey.cda17.ParcInfo.services.PersonneService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Controller
public class PersonneController {

    @Autowired
    private PersonneService service;

    @GetMapping("/personnes")
    public String index(Model model) {
        Iterable<Personne> listPersonnes = service.getPersonnes();
        model.addAttribute("personnes", listPersonnes);
        return "personnes/index";
    }
}
