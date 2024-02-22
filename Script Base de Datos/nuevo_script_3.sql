
DROP TABLE IF EXISTS `categorias`;

CREATE TABLE `categorias` (
  `CategoriaID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(255) NOT NULL,
  `Descripcion` text NOT NULL,
  PRIMARY KEY (`CategoriaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `categorias` WRITE;

UNLOCK TABLES;




DROP TABLE IF EXISTS `entradas`;

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


--
-- Dumping data for table `entradas`
--

LOCK TABLES `entradas` WRITE;

UNLOCK TABLES;


DROP TABLE IF EXISTS `eventos`;

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

LOCK TABLES `eventos` WRITE;

UNLOCK TABLES;


DROP TABLE IF EXISTS `fotos_evento`;

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

LOCK TABLES `fotos_evento` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `fotoseventos`;

CREATE TABLE `fotoseventos` (
  `FotoID` int NOT NULL AUTO_INCREMENT,
  `EventoID` int NOT NULL,
  `URLFoto` varchar(255) NOT NULL,
  PRIMARY KEY (`FotoID`),
  KEY `EventoID` (`EventoID`),
  CONSTRAINT `fotoseventos_ibfk_1` FOREIGN KEY (`EventoID`) REFERENCES `eventos` (`EventoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `fotoseventos` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `inscripcione`;

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

LOCK TABLES `inscripcione` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `inscripciones`;

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

LOCK TABLES `inscripciones` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `resena`;

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

LOCK TABLES `resena` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `resenas`;

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

LOCK TABLES `resenas` WRITE;

UNLOCK TABLES;

DROP TABLE IF EXISTS `usuarios`;
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

LOCK TABLES `usuarios` WRITE;

UNLOCK TABLES;
