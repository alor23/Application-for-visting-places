-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 29 Maj 2019, 15:04
-- Wersja serwera: 10.1.38-MariaDB
-- Wersja PHP: 7.1.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `licencjat`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `places`
--

CREATE TABLE `places` (
  `title_name` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `description` varchar(500) COLLATE utf8_polish_ci NOT NULL,
  `url` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `places`
--

INSERT INTO `places` (`title_name`, `description`, `url`, `latitude`, `longitude`) VALUES
('Archikatedra Chrystusa Krola', 'Archikatedra Chrystusa Krola w Katowicach zbudowana w latach 1927-1955 w stylu klasycyzujacym wedlug projektu architektow Z. Gawlika i F. Maczynskiego.', 'https://pl.wikipedia.org/wiki/Archikatedra_Chrystusa_Kr%C3%B3la_w_Katowicach', 50.2515, 19.019),
('CNTI', 'CNTI jest to nowoczesny budynek naszego Uniwersytetu, powstaly na dzialce o wielkości blisko 0,9 ha w obrebie kampusu. Znajduje sie on miedzy ulicami Bogucicka i Murckowska, tuz przy rzece Rawie.', 'https://www.ue.katowice.pl/jednostki/cnti.html', 50.2606, 19.0456),
('Spodek', 'Spodek-hala widowiskowo-sportowa, zlokalizowana w Katowicach przy Alei Wojciecha Korfantego 35.', 'https://pl.wikipedia.org/wiki/Spodek_(hala_widowiskowa)', 50.2661, 19.0253),
('Teatr Slaski', 'Teatr Slaski im. Stanilslawa Wyspianskiego jest najwieksza scena dramatyczna Gornego Slaska. Na czterech scenach prezentuje dorobek polskiej i swiatowej literatury, zarowno klasycznej, jak i wspolczesnej.', 'https://pl.wikipedia.org/wiki/Teatr_%C5%9Al%C4%85ski_im._Stanis%C5%82awa_Wyspia%C5%84skiego_w_Katowicach', 50.2595, 19.0224),
('Wieza spadochronowa', 'Wieza spadochronowa w Katowicach 35 metrowa stalowa wieza spadochronowa w Katowicach w Parku im. Tadeusza Kosciuszki.', 'https://pl.wikipedia.org/wiki/Wie%C5%BCa_spadochronowa_w_Katowicach', 50.2462, 19.0076);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `user_name` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`user_name`, `password`, `points`) VALUES
('1', 'c4ca4238a0b923820dcc509a6f75849b', 50);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `visited`
--

CREATE TABLE `visited` (
  `user_name` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `title_name` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `visited_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `places`
--
ALTER TABLE `places`
  ADD PRIMARY KEY (`title_name`),
  ADD UNIQUE KEY `	uc_title_name` (`title_name`) USING BTREE;

--
-- Indeksy dla tabeli `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_name`),
  ADD UNIQUE KEY `uc_user_name` (`user_name`);

--
-- Indeksy dla tabeli `visited`
--
ALTER TABLE `visited`
  ADD KEY `user_name` (`user_name`),
  ADD KEY `title_name` (`title_name`);

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `visited`
--
ALTER TABLE `visited`
  ADD CONSTRAINT `visited_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `visited_ibfk_2` FOREIGN KEY (`title_name`) REFERENCES `places` (`title_name`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
