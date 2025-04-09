package fr.afpa.pompey.cda17.ParcInfo.controllers;

import fr.afpa.pompey.cda17.ParcInfo.models.Peripherique;
import fr.afpa.pompey.cda17.ParcInfo.models.TypePeripherique;
import fr.afpa.pompey.cda17.ParcInfo.services.PeripheriqueService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Data
@Controller
public class PeripheriqueController {

    @Autowired
    private PeripheriqueService peripheriqueService;

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

    @PostMapping("/peripheriques/{id}/update")
    public String update(Model model, @PathVariable("id") int id) {
        Peripherique peripherique = peripheriqueService.getPeripherique(id);
        model.addAttribute("peripherique", peripherique);
        model.addAttribute("types", TypePeripherique.values());
        return "peripheriques/update";
    }
}
