SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Viaje;
TRUNCATE TABLE Usuario;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);
INSERT INTO Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoServicio) VALUES ('San Justo', 'Ramos Mejia', '2026-06-29', '10:30', 4500.0, 4, '25 min', 'Combi Comun');
INSERT INTO Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoServicio) VALUES ('Moron', 'Moreno', '2026-06-30', '14:00', 3800.0, 2, '1h 10 min', 'Semicama Ejecutivo');