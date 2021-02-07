-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 07 fév. 2021 à 21:23
-- Version du serveur :  10.4.10-MariaDB
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `agenda_medical`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateur`
--

DROP TABLE IF EXISTS `administrateur`;
CREATE TABLE IF NOT EXISTS `administrateur` (
  `idAdmin` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `motDePasse` varchar(128) NOT NULL,
  `idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`idAdmin`),
  KEY `fk_idpersonneadmin` (`idPersonne`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `administrateur`
--

INSERT INTO `administrateur` (`idAdmin`, `email`, `telephone`, `motDePasse`, `idPersonne`) VALUES
(1, 'admin@admin.com', '0101010101', 'admin', 1);

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

DROP TABLE IF EXISTS `adresse`;
CREATE TABLE IF NOT EXISTS `adresse` (
  `idAdresse` int(11) NOT NULL AUTO_INCREMENT,
  `adresseComplete` varchar(128) NOT NULL,
  `ville` varchar(128) DEFAULT NULL,
  `codePostal` varchar(20) DEFAULT NULL,
  `pays` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`idAdresse`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `adresse`
--

INSERT INTO `adresse` (`idAdresse`, `adresseComplete`, `ville`, `codePostal`, `pays`) VALUES
(1, '20 rue de la paix', 'Paris', '75002', 'France'),
(15, '40, avenue du balek', 'PARIS', '57600', 'FRANCE'),
(16, 'sqd', 'a', '5760000', 'd'),
(17, 'sqd', 'a', '5760000', 'd'),
(34, '10 rue osef', 'PARIS', '75000', 'FRANCE'),
(36, 'Rue medecin', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `centremedical`
--

DROP TABLE IF EXISTS `centremedical`;
CREATE TABLE IF NOT EXISTS `centremedical` (
  `idCentre` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(64) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `idAdresse` int(11) NOT NULL,
  PRIMARY KEY (`idCentre`),
  KEY `fk_idadressecentre` (`idAdresse`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `centremedical`
--

INSERT INTO `centremedical` (`idCentre`, `nom`, `telephone`, `idAdresse`) VALUES
(1, 'Clinique Beausourire', '0635348769', 15),
(2, 'Centre Grandsoin', '0123456789', 16);

-- --------------------------------------------------------

--
-- Structure de la table `medecin`
--

DROP TABLE IF EXISTS `medecin`;
CREATE TABLE IF NOT EXISTS `medecin` (
  `idMedecin` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `motDePasse` varchar(128) NOT NULL,
  `idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`idMedecin`),
  KEY `fk_idpersonnemedecin` (`idPersonne`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `medecin`
--

INSERT INTO `medecin` (`idMedecin`, `email`, `telephone`, `motDePasse`, `idPersonne`) VALUES
(14, 'medecin@hotmail.com', '0101010101', 'medecin', 35);

-- --------------------------------------------------------

--
-- Structure de la table `patient`
--

DROP TABLE IF EXISTS `patient`;
CREATE TABLE IF NOT EXISTS `patient` (
  `idPatient` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `motDePasse` varchar(128) NOT NULL,
  `idPersonne` int(11) NOT NULL,
  `etat` varchar(50) NOT NULL DEFAULT 'Actif',
  PRIMARY KEY (`idPatient`),
  KEY `fk_idpersonnepatient` (`idPersonne`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `patient`
--

INSERT INTO `patient` (`idPatient`, `email`, `telephone`, `motDePasse`, `idPersonne`, `etat`) VALUES
(4, 'a.b@gmail.com', '0635348751', 'abc', 14, 'Supprimé'),
(5, 'd', '0635348769', 'a', 15, 'Actif'),
(6, 'd', '0635348769', 'azdqs', 16, 'Actif'),
(8, 'schwitthal.alexandre@gmail.com', '0635348769', 'da', 33, 'Actif');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `idPersonne` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `date_naissance` date DEFAULT NULL,
  `idAdresse` int(11) NOT NULL,
  PRIMARY KEY (`idPersonne`),
  KEY `fk_idadressepersonne` (`idAdresse`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`idPersonne`, `nom`, `prenom`, `date_naissance`, `idAdresse`) VALUES
(1, 'Ministrateur', 'Gad', '1966-08-25', 1),
(14, 'Schwitthal', 'Alexandre', '1991-04-01', 15),
(15, 'z', 'q', '1995-07-22', 16),
(16, 'z', 'q', '1995-07-22', 17),
(33, 'schwitthal', 'Alexandre', '1997-04-14', 34),
(35, 'medecin', 'medecin', NULL, 36);

-- --------------------------------------------------------

--
-- Structure de la table `planning`
--

DROP TABLE IF EXISTS `planning`;
CREATE TABLE IF NOT EXISTS `planning` (
  `idPlanning` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `heureDebut` time NOT NULL,
  `heureFin` time NOT NULL,
  `idCentre` int(11) DEFAULT NULL,
  `idMedecin` int(11) DEFAULT NULL,
  `idRendezVous` int(11) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idPlanning`),
  KEY `fk_idmedecinplanning` (`idMedecin`),
  KEY `fk_idcentreplanning` (`idCentre`),
  KEY `fk_idrendezvousplanning` (`idRendezVous`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `planning`
--

INSERT INTO `planning` (`idPlanning`, `date`, `heureDebut`, `heureFin`, `idCentre`, `idMedecin`, `idRendezVous`, `disponible`) VALUES
(1, '2021-02-07', '08:00:00', '08:30:00', NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `idRendezVous` int(11) NOT NULL AUTO_INCREMENT,
  `etat` varchar(20) NOT NULL,
  `idPatient` int(11) NOT NULL,
  PRIMARY KEY (`idRendezVous`),
  KEY `fk_idpatient` (`idPatient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `specialite`
--

DROP TABLE IF EXISTS `specialite`;
CREATE TABLE IF NOT EXISTS `specialite` (
  `idSpecialite` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(128) NOT NULL,
  PRIMARY KEY (`idSpecialite`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `specialite`
--

INSERT INTO `specialite` (`idSpecialite`, `libelle`) VALUES
(1, 'Généraliste');

-- --------------------------------------------------------

--
-- Structure de la table `spemedecin`
--

DROP TABLE IF EXISTS `spemedecin`;
CREATE TABLE IF NOT EXISTS `spemedecin` (
  `idSpeMedecin` int(11) NOT NULL AUTO_INCREMENT,
  `idMedecin` int(11) NOT NULL,
  `idSpecialite` int(11) NOT NULL,
  `idCentre` int(11) NOT NULL,
  PRIMARY KEY (`idSpeMedecin`),
  KEY `fk_idspecialite` (`idSpecialite`),
  KEY `fk_idcentre` (`idCentre`),
  KEY `fk_idmedecin` (`idMedecin`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `spemedecin`
--

INSERT INTO `spemedecin` (`idSpeMedecin`, `idMedecin`, `idSpecialite`, `idCentre`) VALUES
(12, 14, 1, 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `administrateur`
--
ALTER TABLE `administrateur`
  ADD CONSTRAINT `fk_idpersonneadmin` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `centremedical`
--
ALTER TABLE `centremedical`
  ADD CONSTRAINT `fk_idadressecentre` FOREIGN KEY (`idAdresse`) REFERENCES `adresse` (`idAdresse`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `medecin`
--
ALTER TABLE `medecin`
  ADD CONSTRAINT `fk_idpersonnemedecin` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `fk_idpersonnepatient` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `fk_idadressepersonne` FOREIGN KEY (`idAdresse`) REFERENCES `adresse` (`idAdresse`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `planning`
--
ALTER TABLE `planning`
  ADD CONSTRAINT `fk_idcentreplanning` FOREIGN KEY (`idCentre`) REFERENCES `centremedical` (`idCentre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idmedecinplanning` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idMedecin`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idrendezvousplanning` FOREIGN KEY (`idRendezVous`) REFERENCES `rendezvous` (`idRendezVous`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `fk_idpatient` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `spemedecin`
--
ALTER TABLE `spemedecin`
  ADD CONSTRAINT `fk_idcentre` FOREIGN KEY (`idCentre`) REFERENCES `centremedical` (`idCentre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idmedecin` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idMedecin`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idspecialite` FOREIGN KEY (`idSpecialite`) REFERENCES `specialite` (`idSpecialite`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
