-- 1. Inserción de Usuarios base
insert into Usuario(email, password, rol, activo) values('test@unlam.edu.ar', 'test', 'CLIENTE', true);
insert into Usuario(email, password, rol, activo, nombre, apellido, telefono) values('j@j', '123', 'CLIENTE', true, 'Juan', 'Bringa', '1122334455');
insert into Usuario(email, password, rol, activo) values('admin@mail.com', 'admin', 'ADMIN', true);

-- 2. Inserción de Conductores
insert into Conductor (nombre, apellido, email, telefono, documento, password, licencia, calificacion, ganancia, cuentaHabilitada, disponible, enViaje, suspendido)
values ('Eduardo', 'Zaens', 'ezaens@mail.com', '1123456789', '12345678', 'asd', 'D2', 3.9, 75000.0, true, true, true, false);

INSERT INTO Conductor (nombre, apellido, email, telefono, documento, licencia, calificacion, ganancia, cuentaHabilitada, disponible, enViaje, suspendido)
VALUES ('María', 'Sosa', 'msosa@mail.com', '1134567890', '23456789', 'D1', 4.7, 92000.0, true,true, true, false),
       ('Carlos', 'Ruiz', 'cruiz@mail.com', '1145678901', '34567890', 'D2', 4.3, 81000.0, true, true, false, false),
       ('Lucía', 'Fernández', 'lfernandez@mail.com', '1156789012', '45678901', 'D1', 4.9, 98000.0, true, false, false, true);

-- 3. Inserción de Combis
insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo)
values ('ESTANDAR', 15, 'Manual', 'AD-542-XQ', 'Mercedes-Benz', 'Sprinter 515');

INSERT INTO Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo) VALUES
                                                                                                   ('TURISTICA', 19, 'Automatica', 'AE-781-LM', 'Ford', 'Transit Minibus'),
                                                                                                   ('ESTANDAR', 12, 'Manual', 'AF-234-RT', 'Renault', 'Master L2H2'),
                                                                                                   ('TURISTICA', 20, 'Automatica', 'AG-908-KP', 'Iveco', 'Daily Minibus'),
                                                                                                   ('ESTANDAR', 15, 'Manual', 'AH-456-ND', 'Volkswagen', 'Crafter'),
                                                                                                   ('TURISTICA', 17, 'Automatica', 'AI-123-ZX', 'Peugeot', 'Boxer Premium'),
                                                                                                   ('TURISTICA', 21, 'Automatica', 'AJ-654-WQ', 'Mercedes-Benz', 'Sprinter 517 Turismo'),
                                                                                                   ('ESTANDAR', 14, 'Manual', 'AK-332-UV', 'Toyota', 'Hiace');

-- 4. Asignaciones y Reportes de fallas
insert into AsignacionCombiConductor (id_conductor, id_combi, combiActiva) values (1, 1, true);

INSERT INTO ReporteFalla (descripcion, resuelta, conductor_id, combi_id, fechaCreacionReporte, fechaRealizadoReporte) VALUES
                                                                                                                          ('Falla en el motor, pérdida de potencia en subida', false, 1, 1, '2026-06-01', NULL),
                                                                                                                          ('Luz de aceite encendida intermitente', false, 1, 1, '2026-06-02', NULL),
                                                                                                                          ('Ruido en tren delantero al girar', false, 1, 1, '2026-06-03', NULL);

-- 5. Inserción de Viajes (Con toda la info para que Thymeleaf no rompa la pantalla)
insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor)
values ('San Justo', 'Ramos Mejia', '2026-06-29', '10:30', 4500.0, 4, '45 min', 'Expreso', 'PENDIENTE', 1, 1),
       ('San Justo', 'Ramos Mejia', '2026-06-29', '14:30', 4500.0, 7, '45 min', 'Expreso', 'PENDIENTE', 1, 1);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor)
values ('Moron', 'Moreno', '2026-05-30', '14:00', 3800.0, 2, '35 min', 'Común', 'PENDIENTE', 1, 1);