-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 21 Gru 2020, 21:37
-- Wersja serwera: 10.4.14-MariaDB
-- Wersja PHP: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `gra_powazna`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `places`
--

CREATE TABLE `places` (
  `title_name` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `description` varchar(500) COLLATE utf8_polish_ci NOT NULL,
  `url` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `places`
--

INSERT INTO `places` (`title_name`, `description`, `url`, `latitude`, `longitude`) VALUES
('Budynek A', 'Specjalność Analiza dużych zbiorów danych została przygotowana dla studentów, którzy posiadając już wiedzę z zakresu programowania chcą swoją wiedzę wykorzystać w procesach przetwarzania i analizy danych oraz ogólniej w ramach dynamicznie rozwijającego się nurtu określanego jako Big Data i Data Science.', '', 50.2593, 19.0444),
('Budynek B', 'Przedmioty specjalności analiza dużych zbiorów danych obejmują główne zagadnienia gromadzenia, integracji, wizualizacji i analizy dużych danych i charakteryzują się dużą liczbą zajęć ćwiczeniowych. Na zajęciach wykorzystywane są środowiska i języki programowania, których znajomość jest szczególnie poszukiwana na rynku pracy (Python, C#, R, bazy danych NoSQL, narzędzia BI, platforma AZURE).', '', 50.2598, 19.0458),
('Budynek E', 'Duży nacisk położono na umiejętności praktyczne, dlatego zajęcia z przedmiotów informatycznych prowadzone są w 100% w pracowniach komputerowych wyposażonych w nowoczesne oprogramowanie. W trakcie studiów studenci specjalności Analiza dużych zbiorów danych obok umiejętności programowania nabywają również zaawansowane umiejętności w zakresie posługiwania się specjalistycznym oprogramowaniem służącym przetwarzaniu dużych danych oraz pozyskiwaniu i wizualizacji wiedzy ukrytej w danych.', '', 50.2588, 19.0459),
('Budynek F', 'Ukończenie studiów na tej specjalności jest ogromnym atutem na rynku pracy, ponieważ specjaliści z zakresu Data Science są poszukiwani i wysoko opłacani. Absolwent specjalności może z powodzeniem zostać zatrudniony w dowolnej firmie dostarczającej oprogramowanie związane z analityką danych, jak i w przedsiębiorstwie każdego typu na stanowiskach informatycznych i analitycznych.', '', 50.2601, 19.0425),
('Budynek N', 'Przedmioty specjalności obejmują główne zagadnienia gromadzenia, integracji, wizualizacji i analizy dużych danych i charakteryzują się dużą liczbą zajęć ćwiczeniowych. Na zajęciach wykorzystywane są środowiska i języki programowania, których znajomość jest szczególnie poszukiwana na rynku pracy (Python, C#, R, bazy danych NoSQL, narzędzia BI, platforma AZURE).', '', 50.2536, 19.0023),
('Centrum Informacji Naukowej i Biblioteka Akademick', 'Grywalizacja – jak wykorzystać projekty grywalizacyjne w różnych obszarach działalności człowieka;', '', 50.2608, 19.0298),
('Centrum Nowoczesnych Technologii Informatycznych', 'Marketing gier komputerowych – jak wykorzystać potencjał gier komputerowych w obszarze marketingu oraz jak tworzyć kampanie marketingowe dla gier i aplikacji mobilnych;', '', 50.2603, 19.0451),
('Dworzec Katowice', 'Sztuczna inteligencja w grach – jak metody i narzędzia sztucznej inteligencji wpływają na gry komputerowe. Jak spowodować by przeciwnicy w grach zachowywali się realistycznie i grali lepiej;', '', 50.257, 19.0163),
('Planeta kotleta', 'Aplikacje mobilne oraz rozszerzona i wirtualna rzeczywistość – jak rozbudować tradycyjne aplikacje mobilne o elementy rozszerzonej rzeczywistości oraz jak programować rzeczywistość wirtualną;', '', 50.2574, 19.0329),
('Rektorat UE', 'Gry poważne – jak wykorzystać potencjał gier i zainteresowanie grami do przekazywania wiedzy z różnych obszarów funkcjonowania człowieka;', '', 50.2585, 19.0447),
('Rynek', 'Silniki gier – jak zbudowane są i jak funkcjonują nowoczesne silniki gier. W jaki sposób, z wykorzystaniem silnika gier przekształcić pomysł na grę w finalny produkt działający na różnych platformach sprzętowo – programowych.', '', 50.2592, 19.0218);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `user_name` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `points` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`user_name`, `password`, `points`) VALUES
('1', 'c4ca4238a0b923820dcc509a6f75849b', 50),
('2', 'c4ca4238a0b923820dcc509a6f75849b', 120),
('3', 'c4ca4238a0b923820dcc509a6f75849b', 150);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `visited`
--

CREATE TABLE `visited` (
  `user_name` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `title_name` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `visited_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `visited`
--

INSERT INTO `visited` (`user_name`, `title_name`, `visited_time`) VALUES
('1', 'Budynek A', '2020-12-21 19:42:38');

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
