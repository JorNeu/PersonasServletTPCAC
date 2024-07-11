CREATE DATABASE IF NOT EXISTS personas;
USE personas;

CREATE TABLE IF NOT EXISTS persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    anios INT NOT NULL
);

INSERT INTO persona (nombre, anios) VALUES ('Juan Pérez', 33);
INSERT INTO persona (nombre, anios) VALUES ('Ana Gómez', 28);