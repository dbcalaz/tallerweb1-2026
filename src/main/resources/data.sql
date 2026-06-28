-- 1. Inserción de Usuarios base
insert into Usuario(email, password, rol, activo) values('test@unlam.edu.ar', 'test', 'CLIENTE', true);
insert into Usuario(email, password, rol, activo, nombre, apellido, telefono) values('j@j', '123', 'CLIENTE', true, 'Juan', 'Bringa', '1122334455');
insert into Usuario(email, password, rol, activo) values('admin@mail.com', 'admin', 'ADMIN', true);

-- 2. Inserción de Conductores
insert into Conductor (nombre, apellido, email, telefono, documento, password, licencia, calificacion, ganancia, cuentaHabilitada, estadoConductor)
values ('Eduardo', 'Zaens', 'ezaens@mail.com', '1123456789', '12345678', 'asd', 'D2', 3.9, 75000.0, true, 'DISPONIBLE');

INSERT INTO Conductor (nombre, apellido, email, telefono, documento, licencia, calificacion, ganancia, cuentaHabilitada, estadoConductor)
VALUES ('María', 'Sosa', 'msosa@mail.com', '1134567890', '23456789', 'D1', 4.7, 92000.0, true, 'DISPONIBLE'),
       ('Carlos', 'Ruiz', 'cruiz@mail.com', '1145678901', '34567890', 'D2', 4.3, 81000.0, true, 'EN_VIAJE'),
       ('Lucía', 'Fernández', 'lfernandez@mail.com', '1156789012', '45678901', 'D1', 4.9, 98000.0, true, 'SUSPENDIDO');

-- 3. Inserción de Combis
insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo,EstadoDeCombi, kilometros)
values ('ESTANDAR', 15, 'Manual', 'AD-542-XQ', 'Mercedes-Benz', 'Sprinter 515','EN_VIAJE',1500);

insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo,EstadoDeCombi, kilometros) values
('TURISTICA', 19, 'Automatica', 'AE-781-LM', 'Ford', 'Transit Minibus','DISPONIBLE',1500),
('ESTANDAR', 12, 'Manual', 'AF-234-RT', 'Renault', 'Master L2H2','DISPONIBLE',30000),
('TURISTICA', 20, 'Automatica', 'AG-908-KP', 'Iveco', 'Daily Minibus','DISPONIBLE',2000),
('ESTANDAR', 15, 'Manual', 'AH-456-ND', 'Volkswagen', 'Crafter','EN_VIAJE',150),
('TURISTICA', 17, 'Automatica', 'AI-123-ZX', 'Peugeot', 'Boxer Premium','EN_MANTENIMIENTO',100),
('TURISTICA', 21, 'Automatica', 'AJ-654-WQ', 'Mercedes-Benz', 'Sprinter 517 Turismo','EN_MANTENIMIENTO',10000),
('ESTANDAR', 14, 'Manual', 'AK-332-UV', 'Toyota', 'Hiace','DISPONIBLE',21500);

-- 4. Asignaciones y Reportes de fallas
insert into AsignacionCombiConductor (id_conductor, id_combi, combiActiva) values (1, 1, true);

/*
insert into Viaje (origen, destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_combi, id_conductor)
values ('Morón', 'Ramos Mejía', '2026-06-01', '08:00', 2500.0, 1001, 2, 'PENDIENTE', 1, 1),
       ('San Justo', 'Liniers', '2026-06-02', '09:30', 3200.0, 1002, 3, 'PENDIENTE', 1, 1),
       ('Haedo', 'Moreno', '2026-05-20', '18:00', 2800.0, 1003, 0, 'FINALIZADO', 1, 1),
       ('Castelar', 'Ituzaingó', '2026-05-18', '14:15', 1900.0, 1004, 0, 'FINALIZADO', 1, 1);*/

INSERT INTO ReporteFalla (descripcion, resuelta, conductor_id, combi_id, fechaCreacionReporte, fechaRealizadoReporte) VALUES
                                                                                                                          ('Falla en el motor, pérdida de potencia en subida', false, 1, 1, '2026-06-01', NULL),
                                                                                                                          ('Luz de aceite encendida intermitente', false, 1, 1, '2026-06-02', NULL),
                                                                                                                          ('Ruido en tren delantero al girar', false, 1, 1, '2026-06-03', NULL);
/* 5. Inserción de Viajes extra
insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor)
values ('San Justo', 'Ramos Mejia', '2026-06-29', '10:30', 4500.0, 4, '45 min', 'Expreso', 'PENDIENTE', 1, 1),
       ('San Justo', 'Ramos Mejia', '2026-06-29', '14:30', 4500.0, 7, '45 min', 'Expreso', 'PENDIENTE', 1, 1),
       ('Moron', 'Moreno', '2026-05-30', '14:00', 3800.0, 2, '35 min', 'Común', 'PENDIENTE', 1, 1),
       ('2026-06-01', '08:00', 2500.0, 1001, 2, '45 min', 'COMUN', 'PENDIENTE', 1, 1),
('2026-06-02', '09:30', 3200.0, 1002, 3, '50 min', 'COMUN', 'PENDIENTE', 1, 1),;

insert into Reserva (id_usuario, id_viaje, estadoReserva) values
(2, 3, 'FINALIZADA'), (2, 4, 'FINALIZADA'), (2, 6, 'FINALIZADA'), (2, 5, 'CONFIRMADA');*/

insert into Parada (nombre) values
('Morón'), ('Ramos Mejía'), ('San Justo'), ('Liniers'), ('Haedo'), ('Moreno'),('Castelar'), ('Ituzaingó'), ('Once');

insert into Viaje (fecha, horario, precio, numeroViaje, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor) values
('2026-05-20', '18:00', 2800.0, 1003, 0, '40 min', 'COMUN', 'FINALIZADO', 1, 1),
('2026-05-18', '14:15', 1900.0, 1004, 0, '35 min', 'COMUN', 'FINALIZADO', 1, 1),
('2026-06-25', '08:00', 4200.0, 1005, 4, '40 min', 'COMUN', 'DISPONIBLE', null, null),
('2026-06-25', '09:30', 5100.0, 1006, 3, '55 min', 'EJECUTIVO', 'DISPONIBLE', null, null),
('2026-06-25', '11:00', 3900.0, 1007, 2, '35 min', 'COMUN', 'ASIGNADO', 1, 1),
('2026-06-20', '18:15', 4700.0, 1008, 1, '50 min', 'COMUN', 'FINALIZADO', 1, 1);

-- haedo - moreno
insert into ViajeParada (id_viaje, id_parada, orden) values
(1, 5, 1), (1, 6, 2);

-- castelar - ituzaingo
insert into ViajeParada (id_viaje, id_parada, orden) values
(2, 7, 1), (2, 8, 2);

insert into ViajeParada (id_viaje, id_parada, orden) values
(3, 1, 1), (3, 4, 2);

insert into ViajeParada (id_viaje, id_parada, orden) values
(4, 7, 1), (4, 9, 2);

insert into ViajeParada (id_viaje, id_parada, orden) values
(5, 1, 1), (5, 3, 2);

insert into ViajeParada (id_viaje, id_parada, orden) values
(6, 2, 1),(6, 6, 2);