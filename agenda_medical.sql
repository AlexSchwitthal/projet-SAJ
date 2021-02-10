-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 10, 2021 at 09:23 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `agenda_medical`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrateur`
--

CREATE TABLE `administrateur` (
  `idAdmin` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `motDePasse` varchar(128) NOT NULL,
  `idPersonne` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `administrateur`
--

INSERT INTO `administrateur` (`idAdmin`, `email`, `telephone`, `motDePasse`, `idPersonne`) VALUES
(1, 'admin@admin.com', '0101010101', 'admin', 1);

-- --------------------------------------------------------

--
-- Table structure for table `adresse`
--

CREATE TABLE `adresse` (
  `idAdresse` int(11) NOT NULL,
  `adresseComplete` varchar(128) NOT NULL,
  `ville` varchar(128) DEFAULT NULL,
  `codePostal` varchar(20) DEFAULT NULL,
  `pays` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `adresse`
--

INSERT INTO `adresse` (`idAdresse`, `adresseComplete`, `ville`, `codePostal`, `pays`) VALUES
(1, '20 rue de la paix', 'Paris', '75002', 'France'),
(15, '40, avenue du balek', 'PARIS', '57600', 'FRANCE'),
(16, 'sqd', 'a', '5760000', 'd'),
(17, 'sqd', 'a', '5760000', 'd'),
(34, '10 rue osef', 'PARIS', '75000', 'FRANCE'),
(36, 'Rue medecin', NULL, NULL, NULL),
(37, '65 rue de la Paix', NULL, NULL, NULL),
(38, '75 rue Paris', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `centremedical`
--

CREATE TABLE `centremedical` (
  `idCentre` int(11) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `idAdresse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `centremedical`
--

INSERT INTO `centremedical` (`idCentre`, `nom`, `telephone`, `idAdresse`) VALUES
(1, 'Clinique Beausourire', '0635348769', 15),
(2, 'Centre Grandsoin', '0123456789', 16);

-- --------------------------------------------------------

--
-- Table structure for table `medecin`
--

CREATE TABLE `medecin` (
  `idMedecin` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `motDePasse` varchar(128) NOT NULL,
  `idPersonne` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `medecin`
--

INSERT INTO `medecin` (`idMedecin`, `email`, `telephone`, `motDePasse`, `idPersonne`) VALUES
(14, 'medecin@hotmail.com', '0101010101', 'medecin', 35),
(15, 'medun@mail.fr', '0555555555', 'medun', 36),
(16, 'meddeux@mail.fr', '0955555555', 'meddeux', 37);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `idPatient` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `motDePasse` varchar(128) NOT NULL,
  `idPersonne` int(11) NOT NULL,
  `etat` varchar(50) NOT NULL DEFAULT 'Actif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`idPatient`, `email`, `telephone`, `motDePasse`, `idPersonne`, `etat`) VALUES
(4, 'a.b@gmail.com', '0635348751', 'abc', 14, 'Supprimé'),
(5, 'd', '0635348769', 'a', 15, 'Actif'),
(6, 'd', '0635348769', 'azdqs', 16, 'Actif'),
(8, 'schwitthal.alexandre@gmail.com', '0635348769', 'da', 33, 'Actif');

-- --------------------------------------------------------

--
-- Table structure for table `personne`
--

CREATE TABLE `personne` (
  `idPersonne` int(11) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `date_naissance` date DEFAULT NULL,
  `idAdresse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `personne`
--

INSERT INTO `personne` (`idPersonne`, `nom`, `prenom`, `date_naissance`, `idAdresse`) VALUES
(1, 'Ministrateur', 'Gad', '1966-08-25', 1),
(14, 'Schwitthal', 'Alexandre', '1991-04-01', 15),
(15, 'z', 'q', '1995-07-22', 16),
(16, 'z', 'q', '1995-07-22', 17),
(33, 'schwitthal', 'Alexandre', '1997-04-14', 34),
(35, 'medecin', 'medecin', NULL, 36),
(36, 'medun', 'medun', NULL, 37),
(37, 'meddeux', 'meddeux', NULL, 38);

-- --------------------------------------------------------

--
-- Table structure for table `planning`
--

CREATE TABLE `planning` (
  `idPlanning` int(11) NOT NULL,
  `date` date NOT NULL,
  `heureDebut` time NOT NULL,
  `heureFin` time NOT NULL,
  `idCentre` int(11) NOT NULL,
  `idMedecin` int(11) NOT NULL,
  `idRendezVous` int(11) DEFAULT NULL,
  `disponible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `planning`
--

INSERT INTO `planning` (`idPlanning`, `date`, `heureDebut`, `heureFin`, `idCentre`, `idMedecin`, `idRendezVous`, `disponible`) VALUES
(2, '2021-02-15', '15:00:00', '15:30:00', 1, 15, NULL, 1),
(3, '2021-02-15', '17:00:00', '17:30:00', 1, 15, NULL, 1),
(4, '2021-02-16', '10:00:00', '10:30:00', 2, 16, NULL, 1),
(5, '2021-02-16', '11:30:00', '12:00:00', 1, 16, NULL, 1),
(6, '2021-02-16', '14:30:00', '15:00:00', 1, 16, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rendezvous`
--

CREATE TABLE `rendezvous` (
  `idRendezVous` int(11) NOT NULL,
  `etat` varchar(20) NOT NULL,
  `idPatient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `specialite`
--

CREATE TABLE `specialite` (
  `idSpecialite` int(11) NOT NULL,
  `libelle` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `specialite`
--

INSERT INTO `specialite` (`idSpecialite`, `libelle`) VALUES
(1, 'Généraliste');

-- --------------------------------------------------------

--
-- Table structure for table `spemedecin`
--

CREATE TABLE `spemedecin` (
  `idSpeMedecin` int(11) NOT NULL,
  `idMedecin` int(11) NOT NULL,
  `idSpecialite` int(11) NOT NULL,
  `idCentre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `spemedecin`
--

INSERT INTO `spemedecin` (`idSpeMedecin`, `idMedecin`, `idSpecialite`, `idCentre`) VALUES
(12, 14, 1, 1),
(13, 15, 1, 1),
(14, 16, 1, 2),
(15, 16, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrateur`
--
ALTER TABLE `administrateur`
  ADD PRIMARY KEY (`idAdmin`),
  ADD KEY `fk_idpersonneadmin` (`idPersonne`);

--
-- Indexes for table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`idAdresse`);

--
-- Indexes for table `centremedical`
--
ALTER TABLE `centremedical`
  ADD PRIMARY KEY (`idCentre`),
  ADD KEY `fk_idadressecentre` (`idAdresse`);

--
-- Indexes for table `medecin`
--
ALTER TABLE `medecin`
  ADD PRIMARY KEY (`idMedecin`),
  ADD KEY `fk_idpersonnemedecin` (`idPersonne`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`idPatient`),
  ADD KEY `fk_idpersonnepatient` (`idPersonne`);

--
-- Indexes for table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`idPersonne`),
  ADD KEY `fk_idadressepersonne` (`idAdresse`);

--
-- Indexes for table `planning`
--
ALTER TABLE `planning`
  ADD PRIMARY KEY (`idPlanning`),
  ADD KEY `fk_idmedecinplanning` (`idMedecin`),
  ADD KEY `fk_idcentreplanning` (`idCentre`),
  ADD KEY `fk_idrendezvousplanning` (`idRendezVous`);

--
-- Indexes for table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD PRIMARY KEY (`idRendezVous`),
  ADD KEY `fk_idpatient` (`idPatient`);

--
-- Indexes for table `specialite`
--
ALTER TABLE `specialite`
  ADD PRIMARY KEY (`idSpecialite`);

--
-- Indexes for table `spemedecin`
--
ALTER TABLE `spemedecin`
  ADD PRIMARY KEY (`idSpeMedecin`),
  ADD KEY `fk_idspecialite` (`idSpecialite`),
  ADD KEY `fk_idcentre` (`idCentre`),
  ADD KEY `fk_idmedecin` (`idMedecin`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrateur`
--
ALTER TABLE `administrateur`
  MODIFY `idAdmin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `idAdresse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `centremedical`
--
ALTER TABLE `centremedical`
  MODIFY `idCentre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `medecin`
--
ALTER TABLE `medecin`
  MODIFY `idMedecin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `idPatient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `personne`
--
ALTER TABLE `personne`
  MODIFY `idPersonne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `planning`
--
ALTER TABLE `planning`
  MODIFY `idPlanning` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `rendezvous`
--
ALTER TABLE `rendezvous`
  MODIFY `idRendezVous` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `specialite`
--
ALTER TABLE `specialite`
  MODIFY `idSpecialite` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `spemedecin`
--
ALTER TABLE `spemedecin`
  MODIFY `idSpeMedecin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `administrateur`
--
ALTER TABLE `administrateur`
  ADD CONSTRAINT `fk_idpersonneadmin` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `centremedical`
--
ALTER TABLE `centremedical`
  ADD CONSTRAINT `fk_idadressecentre` FOREIGN KEY (`idAdresse`) REFERENCES `adresse` (`idAdresse`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `medecin`
--
ALTER TABLE `medecin`
  ADD CONSTRAINT `fk_idpersonnemedecin` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `fk_idpersonnepatient` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `fk_idadressepersonne` FOREIGN KEY (`idAdresse`) REFERENCES `adresse` (`idAdresse`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `planning`
--
ALTER TABLE `planning`
  ADD CONSTRAINT `fk_idcentreplanning` FOREIGN KEY (`idCentre`) REFERENCES `centremedical` (`idCentre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idmedecinplanning` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idMedecin`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idrendezvousplanning` FOREIGN KEY (`idRendezVous`) REFERENCES `rendezvous` (`idRendezVous`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `fk_idpatient` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`idPatient`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `spemedecin`
--
ALTER TABLE `spemedecin`
  ADD CONSTRAINT `fk_idcentre` FOREIGN KEY (`idCentre`) REFERENCES `centremedical` (`idCentre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idmedecin` FOREIGN KEY (`idMedecin`) REFERENCES `medecin` (`idMedecin`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idspecialite` FOREIGN KEY (`idSpecialite`) REFERENCES `specialite` (`idSpecialite`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
