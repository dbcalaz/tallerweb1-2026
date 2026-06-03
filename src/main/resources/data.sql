SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Viaje;
TRUNCATE TABLE Usuario;
TRUNCATE TABLE Conductor;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Conductor(id, nombre, apellido, email, telefono, documento) VALUES(1, 'Carlos', 'Gomez', 'carlos@test.com', '1122334455', 12345678);

INSERT INTO Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoServicio, conductor_id) VALUES ('San Justo', 'Ramos Mejia', '2026-06-29', '10:30', 4500.0, 4, '25 min', 'Combi Comun', 1);
INSERT INTO Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoServicio, conductor_id) VALUES ('Moron', 'Moreno', '2026-06-30', '14:00', 3800.0, 2, '1h 10 min', 'Semicama Ejecutivo', 1);