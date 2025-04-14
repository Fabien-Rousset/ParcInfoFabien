-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 14 avr. 2025 à 07:01
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `parcinfo`
--

-- --------------------------------------------------------

--
-- Structure de la table `appareils`
--

DROP TABLE IF EXISTS `appareils`;
CREATE TABLE IF NOT EXISTS `appareils` (
  `id_appareil` bigint NOT NULL AUTO_INCREMENT,
  `libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`id_appareil`)
) ENGINE=InnoDB AUTO_INCREMENT=285 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `appareils`
--

INSERT INTO `appareils` (`id_appareil`, `libelle`) VALUES
(153, 'HyperX Cloud - 1'),
(154, 'HyperX Cloud - 2'),
(155, 'HyperX Cloud - 3'),
(156, 'HyperX Cloud - 4'),
(157, 'HyperX Cloud - 5'),
(158, 'Logitech C920 - 1'),
(159, 'Logitech C920 - 2'),
(160, 'Logitech C920 - 3'),
(161, 'Logitech C920 - 4'),
(162, 'Logitech C920 - 5'),
(163, 'Blue Yéti - 1'),
(164, 'Blue Yéti - 2'),
(165, 'Blue Yéti - 3'),
(166, 'Blue Yéti - 4'),
(167, 'Blue Yéti - 5'),
(168, 'Lexmark B125 - 1');

-- --------------------------------------------------------

--
-- Structure de la table `peripheriques`
--

DROP TABLE IF EXISTS `peripheriques`;
CREATE TABLE IF NOT EXISTS `peripheriques` (
  `id_appareil` bigint NOT NULL,
  `type` tinyint NOT NULL,
  PRIMARY KEY (`id_appareil`)
) ;

--
-- Déchargement des données de la table `peripheriques`
--

INSERT INTO `peripheriques` (`id_appareil`, `type`) VALUES
(153, 2),
(154, 2),
(155, 2),
(156, 2),
(157, 2),
(158, 0),
(159, 0),
(160, 0),
(161, 0),
(162, 0),
(163, 12),
(164, 12),
(165, 0),
(166, 12),
(167, 12),
(168, 10);

-- --------------------------------------------------------

--
-- Structure de la table `personnes`
--

DROP TABLE IF EXISTS `personnes`;
CREATE TABLE IF NOT EXISTS `personnes` (
  `id_personne` bigint NOT NULL AUTO_INCREMENT,
  `adresse` varchar(50) NOT NULL,
  `date_naissance` date NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(25) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  PRIMARY KEY (`id_personne`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `personnes`
--

INSERT INTO `personnes` (`id_personne`, `adresse`, `date_naissance`, `nom`, `prenom`, `telephone`) VALUES
(46, '67b rue de la Cheneau', '1992-12-21', 'Kuntz', 'Lucas', '0778810469'),
(47, '4 rue de Sarre', '1978-10-24', 'Pierson', 'Hervé', '0387215487'),
(48, '1 rue du Pré Longeau', '1960-05-28', 'Condé', 'Geneviève', '0341742742');

-- --------------------------------------------------------

--
-- Structure de la table `personnes_appareils`
--

DROP TABLE IF EXISTS `personnes_appareils`;
CREATE TABLE IF NOT EXISTS `personnes_appareils` (
  `proprietaires_id_personne` bigint NOT NULL,
  `appareils_id_appareil` bigint NOT NULL,
  KEY `FK57xevcbmietjb8f99rh2idnlq` (`appareils_id_appareil`),
  KEY `FKg6pd9enlhoxb1mshonybocacx` (`proprietaires_id_personne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `personnes_appareils`
--

INSERT INTO `personnes_appareils` (`proprietaires_id_personne`, `appareils_id_appareil`) VALUES
(46, 158),
(46, 163),
(47, 153),
(47, 163),
(48, 153),
(48, 163);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `peripheriques`
--
ALTER TABLE `peripheriques`
  ADD CONSTRAINT `FK2xcxhx1q3p639p8s1be3gje05` FOREIGN KEY (`id_appareil`) REFERENCES `appareils` (`id_appareil`);

--
-- Contraintes pour la table `personnes_appareils`
--
ALTER TABLE `personnes_appareils`
  ADD CONSTRAINT `FK57xevcbmietjb8f99rh2idnlq` FOREIGN KEY (`appareils_id_appareil`) REFERENCES `appareils` (`id_appareil`),
  ADD CONSTRAINT `FKg6pd9enlhoxb1mshonybocacx` FOREIGN KEY (`proprietaires_id_personne`) REFERENCES `personnes` (`id_personne`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
