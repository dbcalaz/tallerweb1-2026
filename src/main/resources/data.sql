INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Conductor(id, nombre, apellido, email, telefono, documento, password, licencia,calificacion,ganancia) VALUES (1, 'Eduardo', 'Zaens','ezaens@mail.com','1123456789','12345678','asd','D2',3.9, 75.000);

INSERT INTO Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
VALUES ('San Justo', 'Ramos Mejia', '2026-05-29', '10:30', 4500.0, 4);

INSERT INTO Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
VALUES ('Moron', 'Moreno', '2026-05-30', '14:00', 3800.0, 2);
