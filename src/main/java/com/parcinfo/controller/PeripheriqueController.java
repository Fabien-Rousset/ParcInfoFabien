package com.parcinfo.controller;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.service.PeripheriqueService;
import com.parcinfo.exception.PeripheriqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des périphériques.
 * Ce contrôleur expose les endpoints REST pour toutes les opérations liées aux périphériques.
 *
 * Les endpoints disponibles sont :
 * <ul>
 *   <li>GET /api/peripheriques - Liste tous les périphériques</li>
 *   <li>GET /api/peripheriques/actifs - Liste les périphériques actifs</li>
 *   <li>GET /api/peripheriques/disponibles - Liste les périphériques disponibles</li>
 *   <li>GET /api/peripheriques/type/{type} - Liste les périphériques par type</li>
 *   <li>GET /api/peripheriques/{id} - Récupère un périphérique par ID</li>
 *   <li>POST /api/peripheriques - Crée un nouveau périphérique</li>
 *   <li>PUT /api/peripheriques/{id} - Met à jour un périphérique</li>
 *   <li>DELETE /api/peripheriques/{id} - Supprime un périphérique</li>
 *   <li>POST /api/peripheriques/{id}/associer-ordinateur/{ordinateurId} - Associe à un ordinateur</li>
 *   <li>POST /api/peripheriques/{id}/associer-objet-nomade/{objetNomadeId} - Associe à un objet nomade</li>
 *   <li>POST /api/peripheriques/{id}/desassocier - Désassocie de l'appareil actuel</li>
 * </ul>
 *
 * @author Benjamin
 * @version 1.0
 * @see Peripherique
 * @see PeripheriqueService
 * @see PeripheriqueException
 */
@Controller
@RequestMapping("/peripheriques")
public class PeripheriqueController {
    private static final Logger logger = LoggerFactory.getLogger(PeripheriqueController.class);

    @Autowired
    private PeripheriqueService peripheriqueService;

    /**
     * Affiche la liste des périphériques.
     *
     * @param model Le modèle pour la vue
     * @return Le nom de la vue à afficher
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("peripheriques", peripheriqueService.findAll());
        return "peripheriques/list";
    }

    /**
     * Affiche le formulaire de création d'un périphérique.
     *
     * @param model Le modèle pour la vue
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/create")
    public String createForm(Model model) {
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
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Peripherique peripherique,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/form";
        }
        try {
            peripheriqueService.create(peripherique);
            redirectAttributes.addFlashAttribute("message", "Périphérique créé avec succès");
            return "redirect:/peripheriques";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création du périphérique");
            return "redirect:/peripheriques";
        }
    }

    /**
     * Affiche le formulaire de modification d'un périphérique.
     *
     * @param id L'ID du périphérique à modifier
     * @param model Le modèle pour la vue
     * @return Le nom de la vue à afficher
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("peripherique", peripheriqueService.findById(id));
        model.addAttribute("types", TypePeripherique.values());
        return "peripheriques/form";
    }

    /**
     * Met à jour un périphérique existant.
     *
     * @param id L'ID du périphérique à mettre à jour
     * @param peripherique Les nouvelles données du périphérique
     * @param result Le résultat de la validation
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                        @Valid @ModelAttribute Peripherique peripherique,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "peripheriques/form";
        }
        try {
            peripheriqueService.update(id, peripherique);
            redirectAttributes.addFlashAttribute("message", "Périphérique mis à jour avec succès");
            return "redirect:/peripheriques";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du périphérique");
            return "redirect:/peripheriques";
        }
    }

    /**
     * Supprime un périphérique.
     *
     * @param id L'ID du périphérique à supprimer
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            peripheriqueService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Périphérique supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du périphérique");
        }
        return "redirect:/peripheriques";
    }

    /**
     * Ajoute un commentaire à un périphérique.
     *
     * @param id L'ID du périphérique
     * @param commentaire Le commentaire à ajouter
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping("/{id}/commentaire")
    public String ajouterCommentaire(@PathVariable Long id,
                                   @RequestParam String commentaire,
                                   RedirectAttributes redirectAttributes) {
        try {
            peripheriqueService.ajouterCommentaire(id, commentaire);
            redirectAttributes.addFlashAttribute("message", "Commentaire ajouté avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout du commentaire");
        }
        return "redirect:/peripheriques";
    }

    /**
     * Supprime un commentaire d'un périphérique.
     *
     * @param id L'ID du périphérique
     * @param commentaire Le commentaire à supprimer
     * @param redirectAttributes Les attributs de redirection
     * @return La redirection vers la liste des périphériques
     */
    @PostMapping("/{id}/commentaire/delete")
    public String supprimerCommentaire(@PathVariable Long id,
                                     @RequestParam String commentaire,
                                     RedirectAttributes redirectAttributes) {
        try {
            peripheriqueService.supprimerCommentaire(id, commentaire);
            redirectAttributes.addFlashAttribute("message", "Commentaire supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du commentaire");
        }
        return "redirect:/peripheriques";
    }
} 