-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 12 fév. 2021 à 17:03
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
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `adresse`
--

INSERT INTO `adresse` (`idAdresse`, `adresseComplete`, `ville`, `codePostal`, `pays`) VALUES
(1, '20 rue de la paix', 'Paris', '75002', 'France'),
(15, '40, avenue du balek', 'PARIS', '57600', 'FRANCE'),
(16, 'sqd', 'a', '5760000', 'd'),
(17, 'sqd', 'a', '5760000', 'd'),
(34, '10 rue osef', 'PARIS', '75000', 'FRANCE'),
(36, 'Rue medecin', NULL, NULL, NULL),
(37, 'azeaz', NULL, NULL, NULL),
(38, 'azeza', NULL, NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `medecin`
--

INSERT INTO `medecin` (`idMedecin`, `email`, `telephone`, `motDePasse`, `idPersonne`) VALUES
(14, 'm@mail.com', '0101010101', 'm', 35),
(15, 'm2@mail.com', '0101010101', 'm', 36),
(16, 'm3@mail.com', '0121211210', 'm', 37);

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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`idPersonne`, `nom`, `prenom`, `date_naissance`, `idAdresse`) VALUES
(1, 'Ministrateur', 'Gad', '1966-08-25', 1),
(14, 'Schwitthal', 'Alexandre', '1991-04-01', 15),
(15, 'z', 'q', '1995-07-22', 16),
(16, 'z', 'q', '1995-07-22', 17),
(33, 'schwitthal', 'Alexandre', '1997-04-14', 34),
(35, 'medecin', 'medecin', NULL, 36),
(36, 'ma', 'mb', NULL, 37),
(37, 'm', 'm', NULL, 38);

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
  `idCentre` int(11) NOT NULL,
  `idMedecin` int(11) NOT NULL,
  `idRendezVous` int(11) DEFAULT NULL,
  `disponible` tinyint(1) NOT NULL,
  PRIMARY KEY (`idPlanning`),
  UNIQUE KEY `uc_Planning` (`date`,`heureDebut`,`idCentre`,`idMedecin`),
  KEY `fk_idmedecinplanning` (`idMedecin`),
  KEY `fk_idcentreplanning` (`idCentre`),
  KEY `fk_idrendezvousplanning` (`idRendezVous`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `planning`
--

INSERT INTO `planning` (`idPlanning`, `date`, `heureDebut`, `heureFin`, `idCentre`, `idMedecin`, `idRendezVous`, `disponible`) VALUES
(1, '2021-02-12', '08:00:00', '08:30:00', 1, 15, NULL, 1),
(2, '2021-02-12', '08:30:00', '09:00:00', 1, 15, NULL, 1),
(3, '2021-02-12', '09:00:00', '09:30:00', 1, 15, NULL, 1),
(4, '2021-02-12', '09:30:00', '10:00:00', 1, 15, NULL, 1),
(5, '2021-02-12', '10:00:00', '10:30:00', 1, 15, NULL, 1),
(6, '2021-02-12', '10:30:00', '11:00:00', 1, 15, NULL, 1),
(7, '2021-02-12', '11:00:00', '11:30:00', 1, 15, NULL, 1),
(8, '2021-02-12', '11:30:00', '12:00:00', 1, 15, NULL, 1),
(9, '2021-02-12', '12:00:00', '12:30:00', 1, 15, NULL, 1),
(10, '2021-02-12', '12:30:00', '13:00:00', 1, 15, NULL, 1),
(11, '2021-02-12', '13:00:00', '13:30:00', 1, 15, NULL, 0),
(12, '2021-02-12', '13:30:00', '14:00:00', 1, 15, NULL, 1),
(13, '2021-02-12', '14:00:00', '14:30:00', 1, 15, NULL, 1),
(14, '2021-02-12', '14:30:00', '15:00:00', 1, 15, NULL, 1),
(15, '2021-02-12', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(16, '2021-02-12', '15:30:00', '16:00:00', 1, 15, NULL, 1),
(17, '2021-02-12', '16:00:00', '16:30:00', 1, 15, NULL, 1),
(18, '2021-02-12', '16:30:00', '17:00:00', 1, 15, NULL, 1),
(19, '2021-02-12', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(20, '2021-02-12', '17:30:00', '18:00:00', 1, 15, NULL, 1),
(21, '2021-02-12', '18:00:00', '18:30:00', 1, 15, NULL, 1),
(22, '2021-02-12', '18:30:00', '19:00:00', 1, 15, NULL, 1),
(23, '2021-02-12', '19:00:00', '19:30:00', 1, 15, NULL, 1),
(24, '2021-02-12', '19:30:00', '20:00:00', 1, 15, NULL, 1),
(25, '2021-02-13', '08:00:00', '08:30:00', 1, 15, NULL, 1),
(26, '2021-02-13', '08:30:00', '09:00:00', 1, 15, NULL, 1),
(27, '2021-02-13', '09:00:00', '09:30:00', 1, 15, NULL, 1),
(28, '2021-02-13', '09:30:00', '10:00:00', 1, 15, NULL, 1),
(29, '2021-02-13', '10:00:00', '10:30:00', 1, 15, NULL, 1),
(30, '2021-02-13', '10:30:00', '11:00:00', 1, 15, NULL, 1),
(31, '2021-02-13', '11:00:00', '11:30:00', 1, 15, NULL, 1),
(32, '2021-02-13', '11:30:00', '12:00:00', 1, 15, NULL, 1),
(33, '2021-02-13', '12:00:00', '12:30:00', 1, 15, NULL, 1),
(34, '2021-02-13', '12:30:00', '13:00:00', 1, 15, NULL, 1),
(35, '2021-02-13', '13:00:00', '13:30:00', 1, 15, NULL, 1),
(36, '2021-02-13', '13:30:00', '14:00:00', 1, 15, NULL, 1),
(37, '2021-02-13', '14:00:00', '14:30:00', 1, 15, NULL, 1),
(38, '2021-02-13', '14:30:00', '15:00:00', 1, 15, NULL, 1),
(39, '2021-02-13', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(40, '2021-02-13', '15:30:00', '16:00:00', 1, 15, NULL, 1),
(41, '2021-02-13', '16:00:00', '16:30:00', 1, 15, NULL, 1),
(42, '2021-02-13', '16:30:00', '17:00:00', 1, 15, NULL, 1),
(43, '2021-02-13', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(44, '2021-02-13', '17:30:00', '18:00:00', 1, 15, NULL, 1),
(45, '2021-02-13', '18:00:00', '18:30:00', 1, 15, NULL, 1),
(46, '2021-02-13', '18:30:00', '19:00:00', 1, 15, NULL, 1),
(47, '2021-02-13', '19:00:00', '19:30:00', 1, 15, NULL, 1),
(48, '2021-02-13', '19:30:00', '20:00:00', 1, 15, NULL, 1),
(49, '2021-02-14', '08:00:00', '08:30:00', 1, 15, NULL, 0),
(50, '2021-02-14', '08:30:00', '09:00:00', 1, 15, NULL, 0),
(51, '2021-02-14', '09:00:00', '09:30:00', 1, 15, NULL, 0),
(52, '2021-02-14', '09:30:00', '10:00:00', 1, 15, NULL, 0),
(53, '2021-02-14', '10:00:00', '10:30:00', 1, 15, NULL, 0),
(54, '2021-02-14', '10:30:00', '11:00:00', 1, 15, NULL, 0),
(55, '2021-02-14', '11:00:00', '11:30:00', 1, 15, NULL, 0),
(56, '2021-02-14', '11:30:00', '12:00:00', 1, 15, NULL, 0),
(57, '2021-02-14', '12:00:00', '12:30:00', 1, 15, NULL, 0),
(58, '2021-02-14', '12:30:00', '13:00:00', 1, 15, NULL, 0),
(59, '2021-02-14', '13:00:00', '13:30:00', 1, 15, NULL, 0),
(60, '2021-02-14', '13:30:00', '14:00:00', 1, 15, NULL, 0),
(61, '2021-02-14', '14:00:00', '14:30:00', 1, 15, NULL, 0),
(62, '2021-02-14', '14:30:00', '15:00:00', 1, 15, NULL, 0),
(63, '2021-02-14', '15:00:00', '15:30:00', 1, 15, NULL, 0),
(64, '2021-02-14', '15:30:00', '16:00:00', 1, 15, NULL, 0),
(65, '2021-02-14', '16:00:00', '16:30:00', 1, 15, NULL, 0),
(66, '2021-02-14', '16:30:00', '17:00:00', 1, 15, NULL, 0),
(67, '2021-02-14', '17:00:00', '17:30:00', 1, 15, NULL, 0),
(68, '2021-02-14', '17:30:00', '18:00:00', 1, 15, NULL, 0),
(69, '2021-02-14', '18:00:00', '18:30:00', 1, 15, NULL, 0),
(70, '2021-02-14', '18:30:00', '19:00:00', 1, 15, NULL, 0),
(71, '2021-02-14', '19:00:00', '19:30:00', 1, 15, NULL, 0),
(72, '2021-02-14', '19:30:00', '20:00:00', 1, 15, NULL, 0),
(73, '2021-02-15', '08:00:00', '08:30:00', 1, 15, NULL, 1),
(74, '2021-02-15', '08:30:00', '09:00:00', 1, 15, NULL, 1),
(75, '2021-02-15', '09:00:00', '09:30:00', 1, 15, NULL, 1),
(76, '2021-02-15', '09:30:00', '10:00:00', 1, 15, NULL, 1),
(77, '2021-02-15', '10:00:00', '10:30:00', 1, 15, NULL, 1),
(78, '2021-02-15', '10:30:00', '11:00:00', 1, 15, NULL, 1),
(79, '2021-02-15', '11:00:00', '11:30:00', 1, 15, NULL, 1),
(80, '2021-02-15', '11:30:00', '12:00:00', 1, 15, NULL, 1),
(81, '2021-02-15', '12:00:00', '12:30:00', 1, 15, NULL, 1),
(82, '2021-02-15', '12:30:00', '13:00:00', 1, 15, NULL, 1),
(83, '2021-02-15', '13:00:00', '13:30:00', 1, 15, NULL, 1),
(84, '2021-02-15', '13:30:00', '14:00:00', 1, 15, NULL, 1),
(85, '2021-02-15', '14:00:00', '14:30:00', 1, 15, NULL, 1),
(86, '2021-02-15', '14:30:00', '15:00:00', 1, 15, NULL, 1),
(87, '2021-02-15', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(88, '2021-02-15', '15:30:00', '16:00:00', 1, 15, NULL, 1),
(89, '2021-02-15', '16:00:00', '16:30:00', 1, 15, NULL, 1),
(90, '2021-02-15', '16:30:00', '17:00:00', 1, 15, NULL, 1),
(91, '2021-02-15', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(92, '2021-02-15', '17:30:00', '18:00:00', 1, 15, NULL, 1),
(93, '2021-02-15', '18:00:00', '18:30:00', 1, 15, NULL, 1),
(94, '2021-02-15', '18:30:00', '19:00:00', 1, 15, NULL, 1),
(95, '2021-02-15', '19:00:00', '19:30:00', 1, 15, NULL, 1),
(96, '2021-02-15', '19:30:00', '20:00:00', 1, 15, NULL, 1),
(97, '2021-02-16', '08:00:00', '08:30:00', 1, 15, NULL, 1),
(98, '2021-02-16', '08:30:00', '09:00:00', 1, 15, NULL, 1),
(99, '2021-02-16', '09:00:00', '09:30:00', 1, 15, NULL, 1),
(100, '2021-02-16', '09:30:00', '10:00:00', 1, 15, NULL, 1),
(101, '2021-02-16', '10:00:00', '10:30:00', 1, 15, NULL, 1),
(102, '2021-02-16', '10:30:00', '11:00:00', 1, 15, NULL, 1),
(103, '2021-02-16', '11:00:00', '11:30:00', 1, 15, NULL, 1),
(104, '2021-02-16', '11:30:00', '12:00:00', 1, 15, NULL, 1),
(105, '2021-02-16', '12:00:00', '12:30:00', 1, 15, NULL, 1),
(106, '2021-02-16', '12:30:00', '13:00:00', 1, 15, NULL, 1),
(107, '2021-02-16', '13:00:00', '13:30:00', 1, 15, NULL, 1),
(108, '2021-02-16', '13:30:00', '14:00:00', 1, 15, NULL, 1),
(109, '2021-02-16', '14:00:00', '14:30:00', 1, 15, NULL, 1),
(110, '2021-02-16', '14:30:00', '15:00:00', 1, 15, NULL, 1),
(111, '2021-02-16', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(112, '2021-02-16', '15:30:00', '16:00:00', 1, 15, NULL, 1),
(113, '2021-02-16', '16:00:00', '16:30:00', 1, 15, NULL, 1),
(114, '2021-02-16', '16:30:00', '17:00:00', 1, 15, NULL, 1),
(115, '2021-02-16', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(116, '2021-02-16', '17:30:00', '18:00:00', 1, 15, NULL, 1),
(117, '2021-02-16', '18:00:00', '18:30:00', 1, 15, NULL, 1),
(118, '2021-02-16', '18:30:00', '19:00:00', 1, 15, NULL, 1),
(119, '2021-02-16', '19:00:00', '19:30:00', 1, 15, NULL, 1),
(120, '2021-02-16', '19:30:00', '20:00:00', 1, 15, NULL, 1),
(121, '2021-02-17', '08:00:00', '08:30:00', 1, 15, NULL, 1),
(122, '2021-02-17', '08:30:00', '09:00:00', 1, 15, NULL, 1),
(123, '2021-02-17', '09:00:00', '09:30:00', 1, 15, NULL, 1),
(124, '2021-02-17', '09:30:00', '10:00:00', 1, 15, NULL, 1),
(125, '2021-02-17', '10:00:00', '10:30:00', 1, 15, NULL, 1),
(126, '2021-02-17', '10:30:00', '11:00:00', 1, 15, NULL, 1),
(127, '2021-02-17', '11:00:00', '11:30:00', 1, 15, NULL, 1),
(128, '2021-02-17', '11:30:00', '12:00:00', 1, 15, NULL, 1),
(129, '2021-02-17', '12:00:00', '12:30:00', 1, 15, NULL, 1),
(130, '2021-02-17', '12:30:00', '13:00:00', 1, 15, NULL, 1),
(131, '2021-02-17', '13:00:00', '13:30:00', 1, 15, NULL, 1),
(132, '2021-02-17', '13:30:00', '14:00:00', 1, 15, NULL, 1),
(133, '2021-02-17', '14:00:00', '14:30:00', 1, 15, NULL, 1),
(134, '2021-02-17', '14:30:00', '15:00:00', 1, 15, NULL, 1),
(135, '2021-02-17', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(136, '2021-02-17', '15:30:00', '16:00:00', 1, 15, NULL, 1),
(137, '2021-02-17', '16:00:00', '16:30:00', 1, 15, NULL, 1),
(138, '2021-02-17', '16:30:00', '17:00:00', 1, 15, NULL, 1),
(139, '2021-02-17', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(140, '2021-02-17', '17:30:00', '18:00:00', 1, 15, NULL, 1),
(141, '2021-02-17', '18:00:00', '18:30:00', 1, 15, NULL, 1),
(142, '2021-02-17', '18:30:00', '19:00:00', 1, 15, NULL, 1),
(143, '2021-02-17', '19:00:00', '19:30:00', 1, 15, NULL, 1),
(144, '2021-02-17', '19:30:00', '20:00:00', 1, 15, NULL, 1),
(145, '2021-02-18', '08:00:00', '08:30:00', 1, 15, NULL, 1),
(146, '2021-02-18', '08:30:00', '09:00:00', 1, 15, NULL, 1),
(147, '2021-02-18', '09:00:00', '09:30:00', 1, 15, NULL, 1),
(148, '2021-02-18', '09:30:00', '10:00:00', 1, 15, NULL, 1),
(149, '2021-02-18', '10:00:00', '10:30:00', 1, 15, NULL, 1),
(150, '2021-02-18', '10:30:00', '11:00:00', 1, 15, NULL, 1),
(151, '2021-02-18', '11:00:00', '11:30:00', 1, 15, NULL, 1),
(152, '2021-02-18', '11:30:00', '12:00:00', 1, 15, NULL, 1),
(153, '2021-02-18', '12:00:00', '12:30:00', 1, 15, NULL, 1),
(154, '2021-02-18', '12:30:00', '13:00:00', 1, 15, NULL, 1),
(155, '2021-02-18', '13:00:00', '13:30:00', 1, 15, NULL, 1),
(156, '2021-02-18', '13:30:00', '14:00:00', 1, 15, NULL, 1),
(157, '2021-02-18', '14:00:00', '14:30:00', 1, 15, NULL, 1),
(158, '2021-02-18', '14:30:00', '15:00:00', 1, 15, NULL, 1),
(159, '2021-02-18', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(160, '2021-02-18', '15:30:00', '16:00:00', 1, 15, NULL, 1),
(161, '2021-02-18', '16:00:00', '16:30:00', 1, 15, NULL, 1),
(162, '2021-02-18', '16:30:00', '17:00:00', 1, 15, NULL, 1),
(163, '2021-02-18', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(164, '2021-02-18', '17:30:00', '18:00:00', 1, 15, NULL, 1),
(165, '2021-02-18', '18:00:00', '18:30:00', 1, 15, NULL, 1),
(166, '2021-02-18', '18:30:00', '19:00:00', 1, 15, NULL, 1),
(167, '2021-02-18', '19:00:00', '19:30:00', 1, 15, NULL, 1),
(168, '2021-02-18', '19:30:00', '20:00:00', 1, 15, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
CREATE TABLE IF NOT EXISTS `rendezvous` (
  `idRendezVous` int(11) NOT NULL AUTO_INCREMENT,
  `etat` varchar(20) NOT NULL,
  `idPatient` int(11) NOT NULL,
  `messageAnnulation` varchar(128) DEFAULT NULL,
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
  `isActivated` tinyint(1) NOT NULL,
  PRIMARY KEY (`idSpeMedecin`),
  KEY `fk_idspecialite` (`idSpecialite`),
  KEY `fk_idcentre` (`idCentre`),
  KEY `fk_idmedecin` (`idMedecin`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `spemedecin`
--

INSERT INTO `spemedecin` (`idSpeMedecin`, `idMedecin`, `idSpecialite`, `idCentre`, `isActivated`) VALUES
(12, 14, 1, 1, 1),
(13, 15, 1, 1, 1),
(14, 15, 1, 2, 0),
(15, 16, 1, 2, 1),
(16, 16, 1, 1, 1);

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
