package com.parcinfo.controller;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Contrôleur pour la gestion des vues liées aux périphériques.
 * Gère les interactions utilisateur pour l'affichage et la modification des périphériques.
 *
 * @author Benjamin
 * @version 1.0
 */
@Controller
@RequestMapping("/peripheriques")
public class PeripheriqueViewController {

    @Autowired
    private PeripheriqueService peripheriqueService;

    /**
     * Affiche la liste de tous les périphériques.
     *
     * @param model Le modèle pour la vue
     * @return Le nom de la vue à afficher
     */
    @GetMapping
    public String listPeripheriques(Model model) {
        model.addAttribute("peripheriques", peripheriqueService.findAll());
        return "peripheriques/list";
    }

    /**
     * Affiche le formulaire de création d'un nouveau périphérique.
     *
     * @param model Le modèle pour la vue
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("peripherique", new Peripherique());
        model.addAttribute("types", TypePeripherique.values());
        return "peripheriques/form";
    }

    /**
     * Crée un nouveau périphérique.
     *
     * @param peripherique Le périphérique à créer
     * @param result Le résultat de la validation
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping
    public String createPeripherique(@Valid @ModelAttribute Peripherique peripherique,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/form";
        }
        try {
            peripheriqueService.create(peripherique);
            redirectAttributes.addFlashAttribute("message", "Périphérique créé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du périphérique : " + e.getMessage());
        }
        return "redirect:/peripheriques";
    }

    /**
     * Affiche le formulaire de modification d'un périphérique.
     *
     * @param id L'identifiant du périphérique
     * @param model Le modèle pour la vue
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Peripherique peripherique = peripheriqueService.findById(id);
            model.addAttribute("peripherique", peripherique);
            model.addAttribute("types", TypePeripherique.values());
            return "peripheriques/form";
        } catch (Exception e) {
            return "redirect:/peripheriques";
        }
    }

    /**
     * Met à jour un périphérique existant.
     *
     * @param id L'identifiant du périphérique
     * @param peripherique Les nouvelles données du périphérique
     * @param result Le résultat de la validation
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping("/{id}")
    public String updatePeripherique(@PathVariable Long id,
                                   @Valid @ModelAttribute Peripherique peripherique,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/form";
        }
        try {
            peripheriqueService.update(id, peripherique);
            redirectAttributes.addFlashAttribute("message", "Périphérique mis à jour avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du périphérique : " + e.getMessage());
        }
        return "redirect:/peripheriques";
    }

    /**
     * Supprime un périphérique.
     *
     * @param id L'identifiant du périphérique
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping("/{id}/delete")
    public String deletePeripherique(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            peripheriqueService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Périphérique supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du périphérique : " + e.getMessage());
        }
        return "redirect:/peripheriques";
    }
} 