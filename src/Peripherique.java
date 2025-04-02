import lombok.*;
import jakarta.persistence.*;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe représentant un périphérique dans le système de gestion du parc informatique.
 * Un périphérique peut être associé à un ordinateur ou à un objet nomade, mais pas aux deux simultanément.
 * Chaque périphérique a un type spécifique et un état d'activité.
 *
 * Cette classe gère :
 * - La validation des types de périphériques
 * - Les associations avec les ordinateurs et objets nomades
 * - L'état d'activité des périphériques
 * - La traçabilité des opérations via les logs
 *
 * Exemple d'utilisation :
 * <pre>
 * // Création d'un nouveau périphérique
 * Peripherique souris = new Peripherique(Peripherique.TYPE_SOURIS, appareil);
 * 
 * // Association à un ordinateur
 * souris.associerAOrdinateur(ordinateur);
 * 
 * // Vérification de l'état
 * if (souris.estDisponible()) {
 *     // Le périphérique peut être associé
 * }
 * </pre>
 *
 * @author Benjamin
 * @version 1.0
 * @see Ordinateur
 * @see ObjetNomade
 * @see Appareil
 */
@Entity
@Table(name = "peripherique")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peripherique {
    private static final Logger logger = LoggerFactory.getLogger(Peripherique.class);

    /**
     * Types de périphériques disponibles dans le système.
     * Ces constantes définissent les types valides pour un périphérique.
     * Les types disponibles sont :
     * <ul>
     *   <li>TYPE_SOURIS : Pour les souris informatiques</li>
     *   <li>TYPE_CLAVIER : Pour les claviers</li>
     *   <li>TYPE_ECRAN : Pour les écrans</li>
     *   <li>TYPE_IMPRIMANTE : Pour les imprimantes</li>
     *   <li>TYPE_CASQUE : Pour les casques audio</li>
     *   <li>TYPE_CABLE : Pour les câbles</li>
     * </ul>
     */
    public static final String TYPE_SOURIS = "Souris";
    public static final String TYPE_CLAVIER = "Clavier";
    public static final String TYPE_ECRAN = "Écran";
    public static final String TYPE_IMPRIMANTE = "Imprimante";
    public static final String TYPE_CASQUE = "Casque";
    public static final String TYPE_CABLE = "Cable";

    /**
     * Tableau contenant tous les types de périphériques valides.
     * Utilisé pour la validation des types.
     * Ce tableau est utilisé par la méthode {@link #isTypeValide(String)} pour vérifier
     * si un type donné est valide.
     */
    private static final String[] TYPES_VALIDES = {
            TYPE_SOURIS, TYPE_CLAVIER, TYPE_ECRAN,
            TYPE_IMPRIMANTE, TYPE_CASQUE, TYPE_CABLE
    };

    /**
     * Identifiant unique du périphérique.
     * Généré automatiquement par la base de données.
     * Cet identifiant est utilisé pour :
     * <ul>
     *   <li>Identifier de manière unique le périphérique dans la base de données</li>
     *   <li>Comparer les périphériques (voir {@link #equals(Object)})</li>
     *   <li>Générer le code de hachage (voir {@link #hashCode()})</li>
     * </ul>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeripherique;

    /**
     * Type du périphérique.
     * Doit correspondre à l'une des constantes définies dans la classe.
     * La validation du type est effectuée par la méthode {@link #setType(String)}.
     * Les types valides sont définis dans le tableau {@link #TYPES_VALIDES}.
     */
    @Column(nullable = false, length = 30)
    private String type;

    /**
     * Ordinateur auquel le périphérique est associé.
     * Un périphérique ne peut être associé qu'à un seul ordinateur à la fois.
     * Cette relation est gérée par les méthodes :
     * <ul>
     *   <li>{@link #associerAOrdinateur(Ordinateur)}</li>
     *   <li>{@link #desassocierAppareil()}</li>
     *   <li>{@link #estAssocieAOrdinateur()}</li>
     * </ul>
     */
    @ManyToOne
    @JoinColumn(name = "ordinateur_id")
    private Ordinateur ordinateur;

    /**
     * Objet nomade auquel le périphérique est associé.
     * Un périphérique ne peut être associé qu'à un seul objet nomade à la fois.
     * Cette relation est gérée par les méthodes :
     * <ul>
     *   <li>{@link #associerAObjetNomade(ObjetNomade)}</li>
     *   <li>{@link #desassocierAppareil()}</li>
     *   <li>{@link #estAssocieAObjetNomade()}</li>
     * </ul>
     */
    @ManyToOne
    @JoinColumn(name = "objet_nomade_id")
    private ObjetNomade objetNomade;

    /**
     * Appareil auquel le périphérique appartient.
     * Relation obligatoire avec l'appareil propriétaire.
     * Cette relation est établie à la création du périphérique et ne peut pas être modifiée.
     * L'appareil est utilisé pour :
     * <ul>
     *   <li>Identifier le propriétaire du périphérique</li>
     *   <li>Valider l'état du périphérique (voir {@link #estValide()})</li>
     * </ul>
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "appareil_id")
    private Appareil appareil;

    /**
     * État d'activité du périphérique.
     * Indique si le périphérique est en service ou non.
     * Cet état est utilisé par la méthode {@link #estDisponible()} pour déterminer
     * si le périphérique peut être associé à un appareil.
     */
    private boolean estActif;

    /**
     * Constructeur principal d'un périphérique.
     *
     * @param type Le type du périphérique
     * @param appareil L'appareil auquel le périphérique appartient
     * @throws IllegalArgumentException si le type est invalide ou si l'appareil est null
     */
    public Peripherique(String type, Appareil appareil) {
        logger.info("Création d'un nouveau périphérique de type {}", type);
        try {
            setType(type);
            if (appareil == null) {
                logger.error("Tentative de création d'un périphérique avec un appareil null");
                throw new IllegalArgumentException("L'appareil ne peut pas être null");
            }
            this.appareil = appareil;
            this.estActif = true;
            logger.debug("Périphérique créé avec succès - ID: {}, Type: {}", idPeripherique, type);
        } catch (IllegalArgumentException e) {
            logger.error("Erreur lors de la création du périphérique : {}", e.getMessage());
            throw new IllegalArgumentException("Erreur lors de la création du périphérique : " + e.getMessage());
        }
    }

    /**
     * Définit le type du périphérique avec validation.
     *
     * @param type Le type à définir
     * @throws IllegalArgumentException si le type est null, vide, trop long ou invalide
     */
    public void setType(String type) {
        logger.debug("Tentative de modification du type du périphérique {} vers {}", idPeripherique, type);
        try {
            if (type == null || type.trim().isEmpty()) {
                logger.warn("Tentative de définition d'un type vide pour le périphérique {}", idPeripherique);
                throw new IllegalArgumentException("Le type ne peut pas être vide");
            }
            if (type.length() > 30) {
                logger.warn("Tentative de définition d'un type trop long pour le périphérique {}", idPeripherique);
                throw new IllegalArgumentException("Le type ne peut pas dépasser 30 caractères");
            }
            if (!isTypeValide(type)) {
                logger.warn("Tentative de définition d'un type invalide '{}' pour le périphérique {}", type, idPeripherique);
                throw new IllegalArgumentException("Type de périphérique non valide");
            }
            this.type = type;
            logger.info("Type du périphérique {} modifié avec succès vers {}", idPeripherique, type);
        } catch (IllegalArgumentException e) {
            logger.error("Erreur lors de la modification du type du périphérique {} : {}", idPeripherique, e.getMessage());
            throw new IllegalArgumentException("Erreur lors de la définition du type : " + e.getMessage());
        }
    }

    /**
     * Vérifie si un type donné est valide.
     *
     * @param type Le type à vérifier
     * @return true si le type est valide, false sinon
     */
    private boolean isTypeValide(String type) {
        for (String typeValide : TYPES_VALIDES) {
            if (typeValide.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si le périphérique est dans un état valide.
     *
     * @return true si le périphérique est valide, false sinon
     */
    public boolean estValide() {
        return type != null && !type.trim().isEmpty() &&
                type.length() <= 30 &&
                appareil != null;
    }

    /**
     * Vérifie si le périphérique est associé à un ordinateur.
     *
     * @return true si le périphérique est associé à un ordinateur, false sinon
     */
    public boolean estAssocieAOrdinateur() {
        return ordinateur != null;
    }

    /**
     * Vérifie si le périphérique est associé à un objet nomade.
     *
     * @return true si le périphérique est associé à un objet nomade, false sinon
     */
    public boolean estAssocieAObjetNomade() {
        return objetNomade != null;
    }

    /**
     * Associe le périphérique à un ordinateur.
     *
     * @param ordinateur L'ordinateur à associer
     * @throws IllegalArgumentException si l'ordinateur est null
     * @throws IllegalStateException si le périphérique est déjà associé à un objet nomade
     */
    public void associerAOrdinateur(Ordinateur ordinateur) {
        logger.info("Tentative d'association du périphérique {} à l'ordinateur", idPeripherique);
        try {
            if (ordinateur == null) {
                logger.warn("Tentative d'association du périphérique {} à un ordinateur null", idPeripherique);
                throw new IllegalArgumentException("L'ordinateur ne peut pas être null");
            }
            if (estAssocieAObjetNomade()) {
                logger.warn("Tentative d'association du périphérique {} à un ordinateur alors qu'il est déjà associé à un objet nomade", idPeripherique);
                throw new IllegalStateException("Le périphérique est déjà associé à un objet nomade");
            }
            this.ordinateur = ordinateur;
            logger.info("Périphérique {} associé avec succès à l'ordinateur", idPeripherique);
        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.error("Erreur lors de l'association du périphérique {} à l'ordinateur : {}", idPeripherique, e.getMessage());
            throw new IllegalStateException("Erreur lors de l'association à l'ordinateur : " + e.getMessage());
        }
    }

    /**
     * Associe le périphérique à un objet nomade.
     *
     * @param objetNomade L'objet nomade à associer
     * @throws IllegalArgumentException si l'objet nomade est null
     * @throws IllegalStateException si le périphérique est déjà associé à un ordinateur
     */
    public void associerAObjetNomade(ObjetNomade objetNomade) {
        logger.info("Tentative d'association du périphérique {} à un objet nomade", idPeripherique);
        try {
            if (objetNomade == null) {
                logger.warn("Tentative d'association du périphérique {} à un objet nomade null", idPeripherique);
                throw new IllegalArgumentException("L'objet nomade ne peut pas être null");
            }
            if (estAssocieAOrdinateur()) {
                logger.warn("Tentative d'association du périphérique {} à un objet nomade alors qu'il est déjà associé à un ordinateur", idPeripherique);
                throw new IllegalStateException("Le périphérique est déjà associé à un ordinateur");
            }
            this.objetNomade = objetNomade;
            logger.info("Périphérique {} associé avec succès à l'objet nomade", idPeripherique);
        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.error("Erreur lors de l'association du périphérique {} à l'objet nomade : {}", idPeripherique, e.getMessage());
            throw new IllegalStateException("Erreur lors de l'association à l'objet nomade : " + e.getMessage());
        }
    }

    /**
     * Désassocie le périphérique de tout appareil.
     */
    public void desassocierAppareil() {
        logger.info("Tentative de désassociation du périphérique {}", idPeripherique);
        try {
            if (!estAssocieAOrdinateur() && !estAssocieAObjetNomade()) {
                logger.warn("Tentative de désassociation du périphérique {} qui n'est associé à aucun appareil", idPeripherique);
                return;
            }
            this.ordinateur = null;
            this.objetNomade = null;
            logger.info("Périphérique {} désassocié avec succès", idPeripherique);
        } catch (Exception e) {
            logger.error("Erreur lors de la désassociation du périphérique {} : {}", idPeripherique, e.getMessage());
            throw new IllegalStateException("Erreur lors de la désassociation de l'appareil : " + e.getMessage());
        }
    }

    /**
     * Vérifie si le périphérique est disponible pour être associé.
     *
     * @return true si le périphérique est actif et non associé, false sinon
     */
    public boolean estDisponible() {
        return estActif && !estAssocieAOrdinateur() && !estAssocieAObjetNomade();
    }

    /**
     * Modifie l'état d'activité du périphérique.
     *
     * @param actif Le nouvel état d'activité
     */
    public void setActif(boolean actif) {
        logger.info("Tentative de modification de l'état d'activité du périphérique {} vers {}", idPeripherique, actif);
        try {
            if (this.estActif == actif) {
                logger.debug("L'état d'activité du périphérique {} est déjà {}", idPeripherique, actif);
                return;
            }
            this.estActif = actif;
            logger.info("État d'activité du périphérique {} modifié avec succès vers {}", idPeripherique, actif);
        } catch (Exception e) {
            logger.error("Erreur lors de la modification de l'état d'activité du périphérique {} : {}", idPeripherique, e.getMessage());
            throw new IllegalStateException("Erreur lors de la modification de l'état d'activité : " + e.getMessage());
        }
    }

    /**
     * Retourne le type d'appareil auquel le périphérique est associé.
     *
     * @return Le type d'appareil associé ou "Aucun appareil associé"
     */
    public String getTypeAppareilAssocie() {
        if (estAssocieAOrdinateur()) {
            return "Ordinateur";
        } else if (estAssocieAObjetNomade()) {
            return "Objet Nomade";
        }
        return "Aucun appareil associé";
    }

    /**
     * Vérifie si le périphérique est d'un type spécifique.
     *
     * @param type Le type à vérifier
     * @return true si le périphérique est du type spécifié, false sinon
     * @throws IllegalArgumentException si le type est null
     */
    public boolean estDeType(String type) {
        logger.debug("Vérification du type du périphérique {} avec le type {}", idPeripherique, type);
        try {
            if (type == null) {
                logger.warn("Tentative de vérification du type avec une valeur null pour le périphérique {}", idPeripherique);
                throw new IllegalArgumentException("Le type ne peut pas être null");
            }
            boolean resultat = this.type.equals(type);
            logger.debug("Résultat de la vérification du type pour le périphérique {} : {}", idPeripherique, resultat);
            return resultat;
        } catch (IllegalArgumentException e) {
            logger.error("Erreur lors de la vérification du type du périphérique {} : {}", idPeripherique, e.getMessage());
            throw new IllegalArgumentException("Erreur lors de la vérification du type : " + e.getMessage());
        }
    }

    /**
     * Compare deux périphériques pour l'égalité.
     *
     * @param o L'objet à comparer
     * @return true si les périphériques sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peripherique that = (Peripherique) o;
        return Objects.equals(idPeripherique, that.idPeripherique);
    }

    /**
     * Calcule le code de hachage du périphérique.
     *
     * @return Le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPeripherique);
    }

    /**
     * Retourne une représentation textuelle du périphérique.
     *
     * @return La chaîne de caractères représentant le périphérique
     */
    @Override
    public String toString() {
        return "Peripherique{" +
                "idPeripherique=" + idPeripherique +
                ", type='" + type + '\'' +
                ", appareil=" + appareil +
                ", estActif=" + estActif +
                ", typeAppareilAssocie='" + getTypeAppareilAssocie() + '\'' +
                '}';
    }
}