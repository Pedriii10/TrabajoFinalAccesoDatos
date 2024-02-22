-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: trabajoeventos
-- ------------------------------------------------------
-- Server version	8.0.30

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
  `cantidad_disponible` int NOT NULL,
  `entrada_id` int NOT NULL AUTO_INCREMENT,
  `evento_id` int NOT NULL,
  `precio` double NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `tipo` varchar(255) NOT NULL,
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
  `es_exterior` bit(1) NOT NULL,
  `es_gratis` bit(1) NOT NULL,
  `evento_id` int NOT NULL AUTO_INCREMENT,
  `organizador_id` int NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `fecha_fin` datetime(6) NOT NULL,
  `fecha_inicio` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `descripcion` longtext NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `ubicacion` varchar(255) NOT NULL,
  PRIMARY KEY (`evento_id`)
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
  `evento_id` int NOT NULL,
  `foto_id` int NOT NULL AUTO_INCREMENT,
  `date_created` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `urlfoto` varchar(255) NOT NULL,
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
  `evento_id` int NOT NULL,
  `fecha_inscripcion` date NOT NULL,
  `inscripcion_id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `estado` varchar(255) NOT NULL,
  PRIMARY KEY (`inscripcion_id`),
  KEY `FK9of2xids7r0bnqgsrvyaqj1kk` (`evento_id`),
  CONSTRAINT `FK9of2xids7r0bnqgsrvyaqj1kk` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`evento_id`)
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
  `evento_id` int NOT NULL,
  `resena_id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `date_created` datetime(6) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `last_updated` datetime(6) NOT NULL,
  `texto` longtext NOT NULL,
  PRIMARY KEY (`resena_id`),
  KEY `FK22l7tra21och9xxihpupdoidq` (`evento_id`),
  CONSTRAINT `FK22l7tra21och9xxihpupdoidq` FOREIGN KEY (`evento_id`) REFERENCES `evento` (`evento_id`)
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Pedro','pedro@gmail.com','123','2024-02-15 09:22:20',NULL,NULL,'',NULL),(2,'Pedro2','Pedro2@gmail.com','$2a$10$WxUFumHXDOA.ICnX8M5O0uNMA/UF9efICFFdVkwgb1PTU.IptAOtm','2024-02-15 12:19:49',NULL,NULL,NULL,NULL),(3,'PEdro3','Pedro3@gmail.com','$2a$10$Y.AJEqfsUbROChPBgmsGkO7haYvJSWl54pcmZOR7h07GGPudLGeM6','2024-02-15 12:36:44',NULL,NULL,NULL,NULL),(4,'Mateo','Mateo@gmail.com','$2a$10$K9rKmJHFU8Y1ZWQoHG50rOOr9g169vW0/UoGhYkGdmZPSTRAksiZK','2024-02-21 08:49:25',NULL,NULL,NULL,NULL),(5,'pedro','pedro23@gmail.com','$2a$10$xplj1wBVASNPOEb6sBJ7je48EfhFzrIHo9f9DmJYuZ3CNodDfqKNi','2024-02-21 17:28:33',NULL,NULL,NULL,NULL),(6,'pedro2','asdd@dasdas','$2a$10$Hlb8Mr0r5v11NBwlCrOa0eqoKq/ZlUI8pmyC9tIFSVgP3pzs7NTAC','2024-02-21 17:29:38',NULL,NULL,NULL,NULL),(7,'sergio','pedro@dsa','$2a$10$e9fsC6BNzNgAAj4FnydkZOkWsGnskPQfSU5oKaoEhWx8zZJAanW6K','2024-02-21 17:30:46',NULL,NULL,NULL,NULL),(8,'javier','vgvuh@yyyt','$2a$10$spPytItGJuMwX0KpvO1...TBeFFBRODK6IxSh.TDoq6EpM3rnDTbi','2024-02-21 18:19:26',NULL,NULL,NULL,NULL);
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

-- Dump completed on 2024-02-22 18:31:05
