-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: trabajoeventos
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `categoria_id` int NOT NULL AUTO_INCREMENT,
  `date_created` datetime(6) NOT NULL,
  `descripcion` longtext NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`categoria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `CategoriaID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(255) NOT NULL,
  `Descripcion` text NOT NULL,
  PRIMARY KEY (`CategoriaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrada`
--

DROP TABLE IF EXISTS `entrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrada` (
  `entrada_id` int NOT NULL AUTO_INCREMENT,
  `cantidad_disponible` int NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `precio` double NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `evento_id` int NOT NULL,
  PRIMARY KEY (`entrada_id`),
  KEY `FKgs59heaqdm0q0kumlwv2yl0u6` (`evento_id`),
  CONSTRAINT `FKgs59heaqdm0q0kumlwv2yl0u6` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`evento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrada`
--

LOCK TABLES `entrada` WRITE;
/*!40000 ALTER TABLE `entrada` DISABLE KEYS */;
/*!40000 ALTER TABLE `entrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradas`
--

DROP TABLE IF EXISTS `entradas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entradas` (
  `EntradaID` int NOT NULL AUTO_INCREMENT,
  `EventoID` int NOT NULL,
  `Tipo` varchar(255) NOT NULL,
  `Precio` float NOT NULL,
  `CantidadDisponible` int NOT NULL,
  PRIMARY KEY (`EntradaID`),
  KEY `EventoID` (`EventoID`),
  CONSTRAINT `entradas_ibfk_1` FOREIGN KEY (`EventoID`) REFERENCES `eventos` (`EventoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradas`
--

LOCK TABLES `entradas` WRITE;
/*!40000 ALTER TABLE `entradas` DISABLE KEYS */;
/*!40000 ALTER TABLE `entradas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `evento_id` int NOT NULL AUTO_INCREMENT,
  `banner` varchar(255) DEFAULT NULL,
  `date_created` datetime(6) NOT NULL,
  `descripcion` longtext NOT NULL,
  `es_exterior` bit(1) NOT NULL,
  `es_gratis` bit(1) NOT NULL,
  `fecha_fin` datetime(6) NOT NULL,
  `fecha_inicio` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `ubicacion` varchar(255) NOT NULL,
  `organizador_id` int NOT NULL,
  PRIMARY KEY (`evento_id`),
  KEY `FKomx3ifvc4t0nyfwu67tnnpq7y` (`organizador_id`),
  CONSTRAINT `FKomx3ifvc4t0nyfwu67tnnpq7y` FOREIGN KEY (`organizador_id`) REFERENCES `usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventos`
--

DROP TABLE IF EXISTS `eventos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventos` (
  `EventoID` int NOT NULL AUTO_INCREMENT,
  `OrganizadorID` int NOT NULL,
  `Nombre` varchar(255) NOT NULL,
  `Descripcion` text NOT NULL,
  `FechaInicio` datetime NOT NULL,
  `FechaFin` datetime NOT NULL,
  `Ubicacion` varchar(255) NOT NULL,
  `EsExterior` tinyint(1) NOT NULL,
  `EsGratis` tinyint(1) NOT NULL,
  `Logo` varchar(255) DEFAULT NULL,
  `Banner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EventoID`),
  KEY `OrganizadorID` (`OrganizadorID`),
  CONSTRAINT `eventos_ibfk_1` FOREIGN KEY (`OrganizadorID`) REFERENCES `usuarios` (`UsuarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventos`
--

LOCK TABLES `eventos` WRITE;
/*!40000 ALTER TABLE `eventos` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotos_evento`
--

DROP TABLE IF EXISTS `fotos_evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fotos_evento` (
  `foto_id` int NOT NULL AUTO_INCREMENT,
  `date_created` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `urlfoto` varchar(255) NOT NULL,
  `evento_id` int NOT NULL,
  PRIMARY KEY (`foto_id`),
  KEY `FKmabvuh605d3qyu534llq9hai6` (`evento_id`),
  CONSTRAINT `FKmabvuh605d3qyu534llq9hai6` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`evento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotos_evento`
--

LOCK TABLES `fotos_evento` WRITE;
/*!40000 ALTER TABLE `fotos_evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `fotos_evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotoseventos`
--

DROP TABLE IF EXISTS `fotoseventos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fotoseventos` (
  `FotoID` int NOT NULL AUTO_INCREMENT,
  `EventoID` int NOT NULL,
  `URLFoto` varchar(255) NOT NULL,
  PRIMARY KEY (`FotoID`),
  KEY `EventoID` (`EventoID`),
  CONSTRAINT `fotoseventos_ibfk_1` FOREIGN KEY (`EventoID`) REFERENCES `eventos` (`EventoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotoseventos`
--

LOCK TABLES `fotoseventos` WRITE;
/*!40000 ALTER TABLE `fotoseventos` DISABLE KEYS */;
/*!40000 ALTER TABLE `fotoseventos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscripcione`
--

DROP TABLE IF EXISTS `inscripcione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscripcione` (
  `inscripcion_id` int NOT NULL AUTO_INCREMENT,
  `date_created` datetime(6) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `fecha_inscripcion` date NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `evento_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`inscripcion_id`),
  KEY `FK9of2xids7r0bnqgsrvyaqj1kk` (`evento_id`),
  KEY `FKn62oq9i3uckinfx23hyhl90s8` (`usuario_id`),
  CONSTRAINT `FK9of2xids7r0bnqgsrvyaqj1kk` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`evento_id`),
  CONSTRAINT `FKn62oq9i3uckinfx23hyhl90s8` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscripcione`
--

LOCK TABLES `inscripcione` WRITE;
/*!40000 ALTER TABLE `inscripcione` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscripcione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscripciones`
--

DROP TABLE IF EXISTS `inscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscripciones` (
  `InscripcionID` int NOT NULL AUTO_INCREMENT,
  `UsuarioID` int NOT NULL,
  `EventoID` int NOT NULL,
  `FechaInscripcion` date NOT NULL,
  `Estado` varchar(255) NOT NULL,
  PRIMARY KEY (`InscripcionID`),
  KEY `UsuarioID` (`UsuarioID`),
  KEY `EventoID` (`EventoID`),
  CONSTRAINT `inscripciones_ibfk_1` FOREIGN KEY (`UsuarioID`) REFERENCES `usuarios` (`UsuarioID`),
  CONSTRAINT `inscripciones_ibfk_2` FOREIGN KEY (`EventoID`) REFERENCES `eventos` (`EventoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscripciones`
--

LOCK TABLES `inscripciones` WRITE;
/*!40000 ALTER TABLE `inscripciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resena`
--

DROP TABLE IF EXISTS `resena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resena` (
  `resena_id` int NOT NULL AUTO_INCREMENT,
  `date_created` datetime(6) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `texto` longtext NOT NULL,
  `evento_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`resena_id`),
  KEY `FK22l7tra21och9xxihpupdoidq` (`evento_id`),
  KEY `FK49q4gyungok45bbok1d7rpuxa` (`usuario_id`),
  CONSTRAINT `FK22l7tra21och9xxihpupdoidq` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`evento_id`),
  CONSTRAINT `FK49q4gyungok45bbok1d7rpuxa` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resena`
--

LOCK TABLES `resena` WRITE;
/*!40000 ALTER TABLE `resena` DISABLE KEYS */;
/*!40000 ALTER TABLE `resena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resenas`
--

DROP TABLE IF EXISTS `resenas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resenas` (
  `ResenaID` int NOT NULL AUTO_INCREMENT,
  `UsuarioID` int NOT NULL,
  `EventoID` int NOT NULL,
  `Texto` text NOT NULL,
  `FechaHora` datetime NOT NULL,
  PRIMARY KEY (`ResenaID`),
  KEY `UsuarioID` (`UsuarioID`),
  KEY `EventoID` (`EventoID`),
  CONSTRAINT `resenas_ibfk_1` FOREIGN KEY (`UsuarioID`) REFERENCES `usuarios` (`UsuarioID`),
  CONSTRAINT `resenas_ibfk_2` FOREIGN KEY (`EventoID`) REFERENCES `eventos` (`EventoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resenas`
--

LOCK TABLES `resenas` WRITE;
/*!40000 ALTER TABLE `resenas` DISABLE KEYS */;
/*!40000 ALTER TABLE `resenas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usuario_id` int NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) NOT NULL,
  `correo_electronico` varchar(255) NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `descripcion` longtext,
  `fecha_registro` date NOT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `last_updated` datetime(6) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `UsuarioID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(255) NOT NULL,
  `CorreoElectronico` varchar(255) DEFAULT NULL,
  `Contrasena` varchar(255) NOT NULL,
  `FechaRegistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `FotoPerfil` varchar(255) DEFAULT NULL,
  `Descripcion` text,
  `correo_electronico` varchar(255) DEFAULT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UsuarioID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Pedro','pedro@gmail.com','123','2024-02-15 09:22:20',NULL,NULL,'',NULL),(2,'Pedro2','Pedro2@gmail.com','$2a$10$WxUFumHXDOA.ICnX8M5O0uNMA/UF9efICFFdVkwgb1PTU.IptAOtm','2024-02-15 12:19:49',NULL,NULL,NULL,NULL),(3,'PEdro3','Pedro3@gmail.com','$2a$10$Y.AJEqfsUbROChPBgmsGkO7haYvJSWl54pcmZOR7h07GGPudLGeM6','2024-02-15 12:36:44',NULL,NULL,NULL,NULL),(4,'Mateo','Mateo@gmail.com','$2a$10$K9rKmJHFU8Y1ZWQoHG50rOOr9g169vW0/UoGhYkGdmZPSTRAksiZK','2024-02-21 08:49:25',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-21 11:36:39
