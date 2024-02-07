create database trabajoeventos;

use trabajoeventos;

CREATE TABLE Usuarios (
    UsuarioID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    CorreoElectronico VARCHAR(255) NOT NULL UNIQUE,
    Contrasena VARCHAR(255) NOT NULL,
    FechaRegistro DATE NOT NULL,
    FotoPerfil VARCHAR(255),
    Descripcion TEXT
);

CREATE TABLE Eventos (
    EventoID INT AUTO_INCREMENT PRIMARY KEY,
    OrganizadorID INT NOT NULL,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion TEXT NOT NULL,
    FechaInicio DATETIME NOT NULL,
    FechaFin DATETIME NOT NULL,
    Ubicacion VARCHAR(255) NOT NULL,
    EsExterior BOOLEAN NOT NULL,
    EsGratis BOOLEAN NOT NULL,
    Logo VARCHAR(255),
    Banner VARCHAR(255),
    FOREIGN KEY (OrganizadorID) REFERENCES Usuarios(UsuarioID)
);

CREATE TABLE Inscripciones (
    InscripcionID INT AUTO_INCREMENT PRIMARY KEY,
    UsuarioID INT NOT NULL,
    EventoID INT NOT NULL,
    FechaInscripcion DATE NOT NULL,
    Estado VARCHAR(255) NOT NULL,
    FOREIGN KEY (UsuarioID) REFERENCES Usuarios(UsuarioID),
    FOREIGN KEY (EventoID) REFERENCES Eventos(EventoID)
);

CREATE TABLE FotosEventos (
    FotoID INT AUTO_INCREMENT PRIMARY KEY,
    EventoID INT NOT NULL,
    URLFoto VARCHAR(255) NOT NULL,
    FOREIGN KEY (EventoID) REFERENCES Eventos(EventoID)
);

CREATE TABLE Categorias (
    CategoriaID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion TEXT NOT NULL
);

CREATE TABLE Entradas (
    EntradaID INT AUTO_INCREMENT PRIMARY KEY,
    EventoID INT NOT NULL,
    Tipo VARCHAR(255) NOT NULL,
    Precio FLOAT NOT NULL,
    CantidadDisponible INT NOT NULL,
    FOREIGN KEY (EventoID) REFERENCES Eventos(EventoID)
);

CREATE TABLE Resenas (
    ResenaID INT AUTO_INCREMENT PRIMARY KEY,
    UsuarioID INT NOT NULL,
    EventoID INT NOT NULL,
    Texto TEXT NOT NULL,
    FechaHora DATETIME NOT NULL,
    FOREIGN KEY (UsuarioID) REFERENCES Usuarios(UsuarioID),
    FOREIGN KEY (EventoID) REFERENCES Eventos(EventoID)
);
