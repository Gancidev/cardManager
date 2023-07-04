-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 24, 2023 alle 16:11
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cardmanager`
--
CREATE DATABASE IF NOT EXISTS `cardmanager` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cardmanager`;

-- --------------------------------------------------------

--
-- Struttura della tabella `card`
--

CREATE TABLE `card` (
  `card_id` bigint(20) NOT NULL,
  `number` varchar(16) NOT NULL,
  `expiration` varchar(10) NOT NULL,
  `cvv` varchar(3) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `credit` double DEFAULT NULL,
  `blocked` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `card`
--

INSERT INTO `card` (`card_id`, `number`, `expiration`, `cvv`, `user_id`, `credit`, `blocked`) VALUES
(21, '1111222233334444', '05/27', '123', 19, 582, 1),
(22, '5555666677778888', '05/27', '123', 19, 224.31, 0),
(23, '1212232334344545', '05/27', '123', 21, 895.02, 0),
(24, '0000111122223333', '05/27', '123', 16, 58, 0),
(25, '2222333344445555', '05/27', '123', 18, 2174, 0),
(27, '1919292939393321', '05/27', '123', 2, 1283, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `session`
--

CREATE TABLE `session` (
  `id_session` bigint(20) NOT NULL,
  `token` mediumtext NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `privileges` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struttura della tabella `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` bigint(20) NOT NULL,
  `card_id` bigint(20) NOT NULL,
  `user_shop_id` bigint(20) NOT NULL,
  `credit` double NOT NULL,
  `type` varchar(255) NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `card_id`, `user_shop_id`, `credit`, `type`, `date`) VALUES
(24, 23, 16, 3000, 'accredit', '2023-06-24 15:05:21'),
(25, 23, 16, -3104.98, 'payment', '2023-06-24 15:05:42'),
(26, 22, 16, 10, 'accredit', '2023-06-24 15:06:10'),
(29, 21, 18, 102, 'accredit', '2023-06-24 15:14:19'),
(30, 22, 18, -132, 'payment', '2023-06-24 15:14:37'),
(31, 23, 18, -1000, 'payment', '2023-06-24 15:14:50'),
(36, 25, 16, -12, 'payment', '2023-06-24 15:30:36'),
(37, 24, 18, -600, 'payment', '2023-06-24 15:31:23');

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `name`, `surname`, `email`, `password`, `role`, `disabled`) VALUES
(2, 'Giuseppe', 'Amministratore', 'giuseppe@admin.it', 'f4e5512a9e23811202e7cff65be43944', 'admin', 0),
(16, 'Giuseppe', 'Fioraio', 'giuseppe@venditore.it', 'f4e5512a9e23811202e7cff65be43944', 'venditore', 0),
(18, 'Giuseppe', 'Edicola', 'giuseppe@venditore2.it', 'f4e5512a9e23811202e7cff65be43944', 'venditore', 1),
(19, 'Giuseppe', 'Cliente', 'giuseppe@cliente.it', 'f4e5512a9e23811202e7cff65be43944', 'cliente', 0),
(21, 'Giovanni', 'Cliente', 'giovanni@cliente.it', 'f4e5512a9e23811202e7cff65be43944', 'cliente', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`card_id`),
  ADD UNIQUE KEY `UK_492xmgvvn7qbnwhcvpr3yor6h` (`number`),
  ADD KEY `FKl4gbym62l738id056y12rt6q6` (`user_id`);

--
-- Indici per le tabelle `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id_session`),
  ADD KEY `user_id` (`user_id`);

--
-- Indici per le tabelle `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `card_id` (`card_id`),
  ADD KEY `user_shop_id` (`user_shop_id`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `card`
--
ALTER TABLE `card`
  MODIFY `card_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT per la tabella `session`
--
ALTER TABLE `session`
  MODIFY `id_session` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transaction_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `card`
--
ALTER TABLE `card`
  ADD CONSTRAINT `FKl4gbym62l738id056y12rt6q6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limiti per la tabella `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`card_id`) REFERENCES `card` (`card_id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`user_shop_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
