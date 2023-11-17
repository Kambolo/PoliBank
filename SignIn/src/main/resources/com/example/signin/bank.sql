-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Lis 17, 2023 at 03:45 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `currencies`
--

CREATE TABLE `currencies` (
  `status` text NOT NULL,
  `pln` float NOT NULL,
  `eur` float NOT NULL,
  `gbp` float NOT NULL,
  `usd` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Dumping data for table `currencies`
--

INSERT INTO `currencies` (`status`, `pln`, `eur`, `gbp`, `usd`) VALUES
('sold', 1, 4.41, 5.04, 4.05),
('buy', 1, 4.32, 4.94, 3.97);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `customers`
--

CREATE TABLE `customers` (
  `idCustomer` int(11) NOT NULL,
  `name` text NOT NULL,
  `lastname` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `deposis`
--

CREATE TABLE `deposis` (
  `idDeposis` int(11) NOT NULL,
  `idCustomer` int(11) NOT NULL,
  `sum` float NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `percent` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `loans`
--

CREATE TABLE `loans` (
  `idLoan` int(11) NOT NULL,
  `idCustomer` int(11) NOT NULL,
  `sum` float NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `percent` float NOT NULL,
  `status` text NOT NULL,
  `interest` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `registers`
--

CREATE TABLE `registers` (
  `idRegister` int(11) NOT NULL,
  `idCustomer` int(11) NOT NULL,
  `operation` text NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wallet`
--

CREATE TABLE `wallet` (
  `idCustomer` int(11) NOT NULL,
  `pln` float NOT NULL,
  `eur` float NOT NULL,
  `gbp` float NOT NULL,
  `usd` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`idCustomer`);

--
-- Indeksy dla tabeli `deposis`
--
ALTER TABLE `deposis`
  ADD PRIMARY KEY (`idDeposis`);

--
-- Indeksy dla tabeli `loans`
--
ALTER TABLE `loans`
  ADD PRIMARY KEY (`idLoan`);

--
-- Indeksy dla tabeli `registers`
--
ALTER TABLE `registers`
  ADD PRIMARY KEY (`idRegister`);

--
-- Indeksy dla tabeli `wallet`
--
ALTER TABLE `wallet`
  ADD PRIMARY KEY (`idCustomer`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `idCustomer` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `deposis`
--
ALTER TABLE `deposis`
  MODIFY `idDeposis` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `loans`
--
ALTER TABLE `loans`
  MODIFY `idLoan` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `registers`
--
ALTER TABLE `registers`
  MODIFY `idRegister` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wallet`
--
ALTER TABLE `wallet`
  MODIFY `idCustomer` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
