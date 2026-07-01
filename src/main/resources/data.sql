-- 1. Inserción de Usuarios base
insert into Usuario(email, password, rol, activo) values('test@unlam.edu.ar', 'test', 'CLIENTE', true);
insert into Usuario(email, password, rol, activo, nombre, apellido, telefono) values('j@j', '123', 'CLIENTE', true, 'Juan', 'Bringa', '1122334455');
insert into Usuario(email, password, rol, activo) values('admin@mail.com', 'admin', 'ADMIN', true);
insert into Usuario(email, password, rol, activo) values('usuario1@gmail.com', '1234qwer', 'CLIENTE', true);
insert into Usuario(email, password, rol, activo) values('usuario2@gmail.com', 'qwerasdf', 'CLIENTE', true);

-- 2. Inserción de Conductores
insert into Conductor (nombre, apellido, email, telefono, documento, password, licencia, calificacion, ganancia, cuentaHabilitada, estadoConductor)
values ('Eduardo', 'Zaens', 'ezaens@mail.com', '1123456789', '12345678', 'asd', 'D2', 3.9, 75000.0, true, 'DISPONIBLE');

INSERT INTO Conductor (nombre, apellido, email, telefono, documento, licencia, calificacion, ganancia, cuentaHabilitada, estadoConductor)
VALUES ('María', 'Sosa', 'msosa@mail.com', '1134567890', '23456789', 'D1', 4.7, 92000.0, true, 'DISPONIBLE'),
       ('Carlos', 'Ruiz', 'cruiz@mail.com', '1145678901', '34567890', 'D2', 4.3, 81000.0, true, 'EN_VIAJE'),
       ('Lucía', 'Fernández', 'lfernandez@mail.com', '1156789012', '45678901', 'D1', 4.9, 98000.0, true, 'SUSPENDIDO');

-- 3. Inserción de Combis
insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo,EstadoDeCombi, kilometros)
values ('ESTANDAR', 15, 'Manual', 'AD-542-XQ', 'Mercedes-Benz', 'Sprinter 515','DISPONIBLE',1500);

insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo,EstadoDeCombi, kilometros) values
('TURISTICA', 19, 'Automatica', 'AE-781-LM', 'Ford', 'Transit Minibus','DISPONIBLE',1500),
('ESTANDAR', 12, 'Manual', 'AF-234-RT', 'Renault', 'Master L2H2','DISPONIBLE',30000),
('TURISTICA', 20, 'Automatica', 'AG-908-KP', 'Iveco', 'Daily Minibus','EN_MANTENIMIENTO',2000),
('ESTANDAR', 15, 'Manual', 'AH-456-ND', 'Volkswagen', 'Crafter','EN_VIAJE',150),
('TURISTICA', 17, 'Automatica', 'AI-123-ZX', 'Peugeot', 'Boxer Premium','EN_MANTENIMIENTO',100),
('TURISTICA', 21, 'Automatica', 'AJ-654-WQ', 'Mercedes-Benz', 'Sprinter 517 Turismo','DISPONIBLE',10000),
('ESTANDAR', 14, 'Manual', 'AK-332-UV', 'Toyota', 'Hiace','DISPONIBLE',21500);

-- 4. Asignaciones y Reportes de fallas
insert into AsignacionCombiConductor (id_conductor, id_combi, combiActiva) values (1, 1, true);

insert into ReporteFalla (descripcion, estadoReporte, conductor_id, combi_id, fechaCreacionReporte, fechaResueltoReporte) values
('Falla en el motor, pérdida de potencia en subida', 'PENDIENTE', 2, 6, '2026-06-01', NULL),
('Luz de aceite encendida intermitente', 'RESUELTO', 1, 1, '2026-06-02', '2026-06-27'),
('Ruido en tren delantero al girar', 'PENDIENTE', 4, 4, '2026-06-03', NULL);
/* 5. Inserción de Viajes extra
insert into Reserva (id_usuario, id_viaje, estadoReserva) values
(2, 3, 'FINALIZADA'), (2, 4, 'FINALIZADA'), (2, 6, 'FINALIZADA'), (2, 5, 'CONFIRMADA');*/

insert into Parada (nombre, direccion) values
('Morón - Plaza San Martín', 'Belgrano 100'),('Morón - Estación', 'Av. Rivadavia 17600'),('Haedo - Estación', 'Av. Rivadavia 16100'),
('Ramos Mejía - Plaza Mitre', 'Av. de Mayo 100'),('Ramos Mejía - Estación', 'Av. Rivadavia 13700'),('Liniers - Terminal', 'Av. General Paz 10600'),
('Liniers - Av. Rivadavia', 'Av. Rivadavia 11400'),('Ciudadela - Estación', 'Padre Elizalde 100'),('San Justo - Plaza San Justo', 'Arieta 3200'),
('San Justo - Universidad Nacional de La Matanza', 'Florencio Varela 1903'),('Castelar - Estación', 'Pompeya 2400'),('Castelar - Plaza Cumelén', 'Almafuerte 2600'),
('Ituzaingó - Estación', 'Juncal 100'),('Moreno - Estación', 'Bartolomé Mitre 200'),('Moreno - Plaza Mariano Moreno', 'Av. Libertador 100'),
('Villa Luro - Plaza Ejército de los Andes', 'Av. Rivadavia 10000'),('Flores - Plaza Flores', 'Av. Rivadavia 7000'),('Caballito - Parque Rivadavia', 'Av. Rivadavia 4900'),
('Once - Plaza Miserere', 'Av. Rivadavia 2800'),('Congreso - Plaza Congreso', 'Av. Entre Ríos 100'),('Tribunales', 'Talcahuano 550'),
('Obelisco', 'Av. 9 de Julio 1000'),('Puerto Madero', 'Av. Alicia Moreau de Justo 1100'),('Retiro - Terminal', 'Av. Ramos Mejía 1680');

insert into Viaje (fecha, horario, precio, numeroViaje, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor) values
('2026-05-20', '18:00', 2800.0, 1003, 0, '40 min', 'COMUN', 'FINALIZADO', 1, 1),
('2026-05-18', '14:15', 1900.0, 1004, 0, '35 min', 'COMUN', 'FINALIZADO', 1, 1),
('2026-06-25', '08:00', 4200.0, 1005, 4, '40 min', 'COMUN', 'DISPONIBLE', null, null),
('2026-06-25', '09:30', 5100.0, 1006, 3, '55 min', 'EJECUTIVO', 'DISPONIBLE', null, null),
('2026-06-25', '11:00', 3900.0, 1007, 2, '35 min', 'COMUN', 'ASIGNADO', 1, 1),
('2026-06-20', '18:15', 4700.0, 1008, 1, '50 min', 'COMUN', 'FINALIZADO', 1, 1);

-- Viaje 1: Haedo → Moreno
INSERT INTO ViajeParada (id_viaje, id_parada, orden) VALUES
(1, 5, 1),   -- Haedo - Estación
(1, 11, 2),  -- Castelar - Estación
(1, 13, 3),  -- Ituzaingó - Estación
(1, 14, 4),  -- Moreno - Estación
(1, 15, 5);  -- Moreno - Plaza Mariano Moreno


-- Viaje 2: Castelar → Ituzaingó
INSERT INTO ViajeParada (id_viaje, id_parada, orden) VALUES
(2, 12, 1),  -- Castelar - Plaza Cumelén
(2, 11, 2),  -- Castelar - Estación
(2, 13, 3);  -- Ituzaingó - Estación


-- Viaje 3: Morón → Ramos Mejía
INSERT INTO ViajeParada (id_viaje, id_parada, orden) VALUES
(3, 1, 1),   -- Morón - Plaza San Martín
(3, 2, 2),   -- Morón - Estación
(3, 3, 3),   -- Haedo - Estación
(3, 4, 4),   -- Ramos Mejía - Plaza Mitre
(3, 5, 5);   -- Ramos Mejía - Estación


-- Viaje 4: Castelar → Once
INSERT INTO ViajeParada (id_viaje, id_parada, orden) VALUES
(4, 11, 1),  -- Castelar - Estación
(4, 13, 2),  -- Ituzaingó - Estación
(4, 14, 3),  -- Moreno - Estación
(4, 15, 4),  -- Moreno - Plaza Mariano Moreno
(4, 19, 5);  -- Once - Plaza Miserere

-- Viaje 5: Morón → Once
INSERT INTO ViajeParada (id_viaje, id_parada, orden) VALUES
(5, 1, 1),   -- Morón - Plaza San Martín
(5, 2, 2),   -- Morón - Estación
(5, 3, 3),   -- Haedo - Estación
(5, 4, 4),   -- Ramos Mejía - Plaza Mitre
(5, 5, 5),   -- Ramos Mejía - Estación
(5, 6, 6),   -- Liniers - Terminal
(5, 17, 7),  -- Flores - Plaza Flores
(5, 18, 8),  -- Caballito - Parque Rivadavia
(5, 19, 9);  -- Once - Plaza Miserere

-- Viaje 6: Morón → Retiro
INSERT INTO ViajeParada (id_viaje, id_parada, orden) VALUES
(6, 2, 1),   -- Morón - Estación
(6, 3, 2),   -- Haedo - Estación
(6, 5, 3),   -- Ramos Mejía - Estación
(6, 6, 4),   -- Liniers - Terminal
(6, 17, 5),  -- Flores - Plaza Flores
(6, 18, 6),  -- Caballito - Parque Rivadavia
(6, 19, 7),  -- Once - Plaza Miserere
(6, 20, 8),  -- Congreso - Plaza Congreso
(6, 21, 9),  -- Tribunales
(6, 22, 10), -- Obelisco
(6, 23, 11), -- Puerto Madero
(6, 24, 12); -- Retiro - Terminal

-- Inserts para probar la ganancia del conductor $3900 x pasajero por eso $19500
INSERT INTO Reserva (id_usuario, id_viaje, estadoReserva, precioTotal) VALUES
(1, 5, 'CONFIRMADA', 19500), (2, 5, 'CONFIRMADA', 19500);

-- pasajeros para la reserva nro1
INSERT INTO Pasajero (nombre, apellido, dni, email, numeroAsiento, id_reserva) VALUES
('Belén', 'Calaz', '40111222', 'belen@mail.com', 1, 1), ('Martín', 'López', '39222111', 'martin@mail.com', 2, 1),
('Carla', 'Gómez', '41888333', 'carla@mail.com', 3, 1), ('Lucía', 'Fernández', '43111999', 'lucia@mail.com', 4, 1),
('Pedro', 'Ruiz', '38777666', 'pedro@mail.com', 5, 1);

-- pasajeros para la reserva nro2
INSERT INTO Pasajero (nombre, apellido, dni, email, numeroAsiento, id_reserva) VALUES
('Juan', 'Bringa', '35666777', 'juan@mail.com', 6, 2), ('María', 'Sosa', '33444555', 'maria@mail.com', 7, 2),
('Carlos', 'Pérez', '32111999', 'carlos@mail.com', 8, 2), ('Ana', 'Romero', '39999111', 'ana@mail.com', 9, 2),
('Sofía', 'Martínez', '41777111', 'sofia@mail.com', 10, 2);