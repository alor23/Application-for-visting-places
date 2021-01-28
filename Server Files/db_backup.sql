-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 28 Sty 2021, 11:31
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
-- Baza danych: `grapowazna`
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
('Budynek A', 'Specjalność Bazy danych i inżynieria danych\r\n\r\nAbsolwenci specjalności będą potrafili wykorzystać posiadane umiejętności i wiedzę do realizacji zadań związanych z szeroko rozumianym przetwarzaniem danych oraz eksploracją danych i pozyskiwaniem wiedzy z różnorodnych danych pochodzących ze zróżnicowanych źródeł.', 'b', 50.2593, 19.0444),
('Budynek B', 'GRAFIKA I MULTIMEDIA\r\n\r\nCelem kursu jest zapoznanie studentów z podstawami wybranych aspektów grafiki komputerowej i multimediów m.in. historią grafiki komputerowej, podstawami postrzegania, teorią barw i elementami estetyki, podstawami zapisu i obróbki dźwięku oraz materiałów wideo.', '', 50.2598, 19.0458),
('Budynek E', 'Specjalność Programowanie gier i aplikacji mobilnych (PGAMO) jest kontynuowania na drugim stopniu studiów w formie specjalności Gry i aplikacje mobilne (GIAM).', '', 50.2588, 19.0459),
('Budynek F', 'Specjalność Bazy danych i inżynieria danych (BDID) jest kontynuowania na drugim stopniu studiów w formie specjalności Analiza dużych zbiorów danych (ADZD).', 'b', 50.2601, 19.0425),
('Budynek N', 'WPROWADZENIE DO PROGRAMOWANIA GIER\r\n\r\nCelem przedmiotu jest zapoznanie studentów z podstawowymi możliwościom programowania gier w środowisku UNITY 3D', '', 50.2536, 19.0023),
('Centrum Informacji Naukowej i Biblioteka Akademick', 'MODELOWANIE DANYCH\r\n\r\n\r\nPrzedstawienie studentom wiedzy dotyczącej logiki procesu tworzenia baz danych oraz zakresu prac realizowanych na poszczególnych etapach modelowania relacyjnych baz danych.\r\nNabycie przez studentów umiejętności tworzenia modeli i prostych projektów baz danych.\r\nWykształcenie kompetencji społecznych związanych z tworzeniem rozwiązań informatycznych.', 'b', 50.2608, 19.0298),
('Centrum Nowoczesnych Technologii Informatycznych', 'Absolwenci specjalności Programowanie gier i aplikacji mobilnych posiądą umiejętności w zakresie tworzenia i programowania gier komputerowych (ze szczególnym uwzględnieniem gier strategicznych i edukacyjnych) oraz projektowania i programowania aplikacji mobilnych. Podstawowe kompetencje studenta obejmować będą wykorzystanie silników gier, projektowanie interfejsów gier a w szczególności, programowanie na różne platformy programowo-sprzętowe w takich językach jak: C#, Java, Python czy JavaScript.', '', 50.2603, 19.0451),
('Dworzec Katowice', 'PROGRAMOWANIE W JĘZYKU JAVA\r\n\r\nStudenci powinni zdobyć umiejętność praktycznego programowania w języku JAVA\r\nStudenci powinni nabyć kompetencje w zakresie przygotowania, implementacji, oraz testowania systemów wykonywanych z wykorzystaniem języka JAVA', '', 50.257, 19.0163),
('Planeta kotleta', 'PROGRAMOWANIE W JĘZYKU PYTHON\r\n\r\nStudenci powinny zdobyć umiejętność praktycznego programowania w języku Python\r\nStudenci powinni nabyć wiedzę na temat bibliotek związanych z językiem Python', 'b', 50.2574, 19.0329),
('Rektorat UE', 'PRZETWARZANIE DANYCH W ŚRODOWISKACH LOKALNYCH specka bazy\r\n\r\nPoznanie funkcjonowania bazodanowych aplikacji lokalnych.\r\nZdobycie praktycznych umiejętności projektowania i implementowania prostej bazy danych, jej interfejsu i sposobu działania.\r\nZamodelowanie w oprogramowaniu MS Access prostej bazy danych (składającej się z kilku tabel).', 'b', 50.2585, 19.0447);

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
('alek', 'c4ca4238a0b923820dcc509a6f75849b', 200),
('user1', 'c4ca4238a0b923820dcc509a6f75849b', 150),
('user2', 'c4ca4238a0b923820dcc509a6f75849b', 200),
('user3', 'c4ca4238a0b923820dcc509a6f75849b', 150);

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
('user1', 'Budynek A', '2020-12-21 19:42:38'),
('user1', 'Centrum Nowoczesnych Technologii Informatycznych', '2021-01-18 11:30:12'),
('user1', 'Budynek B', '2021-01-18 11:30:23'),
('alek', 'Budynek B', '2021-01-25 11:01:32'),
('alek', 'Budynek A', '2021-01-25 11:02:35'),
('alek', 'Rektorat UE', '2021-01-25 11:10:38'),
('alek', 'Budynek E', '2021-01-25 11:11:11'),
('alek', 'Centrum Nowoczesnych Technologii Informatycznych', '2021-01-25 11:11:42'),
('alek', 'Budynek F', '2021-01-25 11:12:22'),
('1', 'Budynek B', '2021-01-26 12:22:25');

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
