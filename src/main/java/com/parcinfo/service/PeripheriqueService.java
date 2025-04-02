package com.parcinfo.service;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import com.parcinfo.repository.PeripheriqueRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de gestion des périphériques.
 * Cette classe gère toutes les opérations métier liées aux périphériques,
 * notamment la création, la modification, la suppression et les associations
 * avec les ordinateurs et les objets nomades.
 *
 * Le service implémente les règles métier suivantes :
 * - Un périphérique doit être valide avant d'être créé ou modifié
 * - Un périphérique ne peut être associé qu'à un seul appareil à la fois
 * - Les opérations de modification sont transactionnelles
 *
 * @author Benjamin
 * @version 1.0
 * @see Peripherique
 * @see PeripheriqueRepository
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeripheriqueService {

    private final PeripheriqueRepository peripheriqueRepository;

    /**
     * Récupère tous les périphériques.
     *
     * @return La liste de tous les périphériques
     */
    public List<Peripherique> findAll() {
        return peripheriqueRepository.findAll();
    }

    /**
     * Récupère un périphérique par son ID.
     *
     * @param id L'ID du périphérique
     * @return Le périphérique trouvé
     * @throws EntityNotFoundException si le périphérique n'est pas trouvé
     */
    public Peripherique findById(Long id) {
        return peripheriqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Périphérique non trouvé avec l'ID : " + id));
    }

    /**
     * Récupère tous les périphériques d'un type spécifique.
     *
     * @param type Le type de périphérique
     * @return La liste des périphériques du type spécifié
     */
    public List<Peripherique> findByType(TypePeripherique type) {
        return peripheriqueRepository.findByType(type);
    }

    /**
     * Récupère tous les périphériques en service.
     *
     * @return La liste des périphériques en service
     */
    public List<Peripherique> findEnService() {
        return peripheriqueRepository.findByEnServiceTrue();
    }

    /**
     * Crée un nouveau périphérique.
     *
     * @param peripherique Le périphérique à créer
     * @return Le périphérique créé
     */
    @Transactional
    public Peripherique create(@Valid Peripherique peripherique) {
        return peripheriqueRepository.save(peripherique);
    }

    /**
     * Met à jour un périphérique existant.
     *
     * @param id L'ID du périphérique à mettre à jour
     * @param peripherique Les nouvelles données du périphérique
     * @return Le périphérique mis à jour
     * @throws EntityNotFoundException si le périphérique n'est pas trouvé
     */
    @Transactional
    public Peripherique update(Long id, @Valid Peripherique peripherique) {
        if (!peripheriqueRepository.existsById(id)) {
            throw new EntityNotFoundException("Périphérique non trouvé avec l'ID : " + id);
        }
        peripherique.setIdPeripherique(id);
        return peripheriqueRepository.save(peripherique);
    }

    /**
     * Supprime un périphérique.
     *
     * @param id L'ID du périphérique à supprimer
     * @throws EntityNotFoundException si le périphérique n'est pas trouvé
     */
    @Transactional
    public void delete(Long id) {
        if (!peripheriqueRepository.existsById(id)) {
            throw new EntityNotFoundException("Périphérique non trouvé avec l'ID : " + id);
        }
        peripheriqueRepository.deleteById(id);
    }

    /**
     * Ajoute un commentaire à un périphérique.
     *
     * @param id L'ID du périphérique
     * @param commentaire Le commentaire à ajouter
     * @return Le périphérique mis à jour
     * @throws EntityNotFoundException si le périphérique n'est pas trouvé
     */
    @Transactional
    public Peripherique ajouterCommentaire(Long id, String commentaire) {
        Peripherique peripherique = findById(id);
        peripherique.ajouterCommentaire(commentaire);
        return peripheriqueRepository.save(peripherique);
    }

    /**
     * Supprime un commentaire d'un périphérique.
     *
     * @param id L'ID du périphérique
     * @param commentaire Le commentaire à supprimer
     * @return Le périphérique mis à jour
     * @throws EntityNotFoundException si le périphérique n'est pas trouvé
     */
    @Transactional
    public Peripherique supprimerCommentaire(Long id, String commentaire) {
        Peripherique peripherique = findById(id);
        peripherique.supprimerCommentaire(commentaire);
        return peripheriqueRepository.save(peripherique);
    }
} 