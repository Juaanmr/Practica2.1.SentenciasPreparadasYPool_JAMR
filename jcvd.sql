-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-12-2023 a las 07:55:20
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `jcvd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuegos`
--

CREATE TABLE `videojuegos` (
  `id` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Genero` varchar(25) DEFAULT NULL,
  `FechaLanzamiento` date DEFAULT NULL,
  `Compañia` varchar(50) DEFAULT NULL,
  `Precio` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `videojuegos`
--

INSERT INTO `videojuegos` (`id`, `Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) VALUES
(1, 'Fortnite', 'Battle royale', '2017-11-15', 'Epic Games', 0.00),
(3, 'Rocket League', 'Coches', '2019-08-19', 'Epic Games', 0.00),
(5, 'Grand Theft Auto V', 'Mundo abierto', '2013-08-20', 'Rockstar Games', 50.00),
(10, 'C:S', 'Shooter', '2023-11-24', 'Steam', 0.00),
(11, 'asd', 'asd', '0202-05-21', 'asd', 0.00),
(12, 'Juan', 'Accion', '2005-12-21', 'SONY', 15.20),
(13, 'prueba', 'shooter', '2007-04-11', 'Steam', 15.00),
(14, 'Mario Bors', 'Accion', '1999-01-12', 'Nintendo', 52.12),
(15, 'prueba', 'shooter', '2007-04-11', 'Steam', 15.00),
(16, 'prueba', 'shooter', '2007-04-11', 'Steam', 15.00),
(17, 'mama', 'shooter', '2007-04-11', 'Steam', 15.00),
(18, 'pepe', 'accion', '2005-12-21', 'sony', 15.20);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `videojuegos`
--
ALTER TABLE `videojuegos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
