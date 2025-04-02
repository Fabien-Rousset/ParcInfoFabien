package com.parcinfo.repository;

import com.parcinfo.model.Peripherique;
import com.parcinfo.model.TypePeripherique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de repository pour la gestion des périphériques.
 * Cette interface étend JpaRepository pour fournir les opérations CRUD de base
 * et ajoute des méthodes de recherche personnalisées.
 *
 * Les méthodes personnalisées permettent de :
 * - Rechercher des périphériques par type
 * - Rechercher des périphériques en service
 * - Rechercher des périphériques par type et état de service
 *
 * @author Benjamin
 * @version 1.0
 * @see Peripherique
 * @see JpaRepository
 */
@Repository
public interface PeripheriqueRepository extends JpaRepository<Peripherique, Long> {
    // Les méthodes CRUD sont automatiquement fournies par JpaRepository

    /**
     * Recherche des périphériques par type.
     *
     * @param type Le type de périphérique à rechercher
     * @return Liste des périphériques du type spécifié
     */
    List<Peripherique> findByType(TypePeripherique type);

    /**
     * Recherche des périphériques en service.
     *
     * @return Liste des périphériques en service
     */
    List<Peripherique> findByEnServiceTrue();

    /**
     * Recherche des périphériques par type et état de service.
     *
     * @param type Le type de périphérique à rechercher
     * @param enService L'état de service à rechercher
     * @return Liste des périphériques correspondant aux critères
     */
    List<Peripherique> findByTypeAndEnService(TypePeripherique type, boolean enService);
} 