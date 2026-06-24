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
insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo,EstadoDeCombi, kilometros)
values ('ESTANDAR', 15, 'Manual', 'AD-542-XQ', 'Mercedes-Benz', 'Sprinter 515','EN_VIAJE',1500);

INSERT INTO Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo,EstadoDeCombi, kilometros) VALUES
                                                                                                                             ('TURISTICA', 19, 'Automatica', 'AE-781-LM', 'Ford', 'Transit Minibus','DISPONIBLE',1500),
                                                                                                                             ('ESTANDAR', 12, 'Manual', 'AF-234-RT', 'Renault', 'Master L2H2','DISPONIBLE',30000),
                                                                                                                             ('TURISTICA', 20, 'Automatica', 'AG-908-KP', 'Iveco', 'Daily Minibus','DISPONIBLE',2000),
                                                                                                                             ('ESTANDAR', 15, 'Manual', 'AH-456-ND', 'Volkswagen', 'Crafter','EN_VIAJE',150),
                                                                                                                             ('TURISTICA', 17, 'Automatica', 'AI-123-ZX', 'Peugeot', 'Boxer Premium','EN_MANTENIMIENTO',100),
                                                                                                                             ('TURISTICA', 21, 'Automatica', 'AJ-654-WQ', 'Mercedes-Benz', 'Sprinter 517 Turismo','EN_MANTENIMIENTO',10000),
                                                                                                                             ('ESTANDAR', 14, 'Manual', 'AK-332-UV', 'Toyota', 'Hiace','DISPONIBLE',21500);

-- 3.5 Inserción de Paradas (Necesarias para los Viajes)
insert into Parada (nombre, latitud, longitud) values ('Estacion Moron (Av. Rivadavia)', -34.6475, -58.6200);         -- ID 1 (Morón)
insert into Parada (nombre, latitud, longitud) values ('Estacion Ramos Mejia (Av. de Mayo)', -34.6402, -58.5645);     -- ID 2 (Ramos Mejía)
insert into Parada (nombre, latitud, longitud) values ('UNLaM San Justo (Florencio Varela)', -34.6681, -58.5615);     -- ID 3 (San Justo)
insert into Parada (nombre, latitud, longitud) values ('Terminal Liniers (Av. Gral Paz)', -34.6395, -58.5283);        -- ID 4 (Liniers)
insert into Parada (nombre, latitud, longitud) values ('Estacion Haedo (Fasola y Rivadavia)', -34.6429, -58.5898);    -- ID 5 (Haedo)
insert into Parada (nombre, latitud, longitud) values ('Plaza San Martin Moreno (B. Mitre)', -34.6500, -58.7900);     -- ID 6 (Moreno)
insert into Parada (nombre, latitud, longitud) values ('Estacion Castelar (Buenos Aires 100)', -34.6508, -58.6415);   -- ID 7 (Castelar)
insert into Parada (nombre, latitud, longitud) values ('Plaza 20 de Febrero Ituzaingo', -34.6586, -58.6653);          -- ID 8 (Ituzaingó)
insert into Parada (nombre, latitud, longitud) values ('Plaza San Justo (Arieta y Villegas)', -34.6750, -58.5520);    -- ID 9 (San Justo alt)
insert into Parada (nombre, latitud, longitud) values ('Hospital Italiano San Justo', -34.6812, -58.5688);            -- ID 10 (San Justo alt)

-- 4. Asignaciones y Reportes de fallas
insert into AsignacionCombiConductor (id_conductor, id_combi, combiActiva) values (1, 1, true);

-- Inserción de Viajes Originales (Usando los IDs de las Paradas correspondientes)
insert into Viaje (id_origen, id_destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_combi, id_conductor)
values (1, 2, '2026-06-29', '08:00', 2500.0, 1001, 12, 'PENDIENTE', 1, 1),
       (3, 4, '2026-06-28', '09:30', 3200.0, 1002, 12, 'PENDIENTE', 1, 1),
       (5, 6, '2026-06-24', '18:00', 2800.0, 1003, 0, 'FINALIZADO', 1, 1),
       (7, 8, '2026-06-26', '14:15', 1900.0, 1004, 0, 'FINALIZADO', 1, 1);

INSERT INTO ReporteFalla (descripcion, resuelta, conductor_id, combi_id, fechaCreacionReporte, fechaRealizadoReporte) VALUES
                                                                                                                          ('Falla en el motor, pérdida de potencia en subida', false, 1, 1, '2026-06-01', NULL),
                                                                                                                          ('Luz de aceite encendida intermitente', false, 1, 1, '2026-06-02', NULL),
                                                                                                                          ('Ruido en tren delantero al girar', false, 1, 1, '2026-06-03', NULL);

-- 5. Inserción de Viajes extra (Mantenemos tus pruebas usando los IDs de San Justo, Ramos, Morón y Moreno)
insert into Viaje (id_origen, id_destino, fecha, horario, precio, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor)
values (3, 2, '2026-06-29', '10:30', 4500.0, 12, '45 min', 'Expreso', 'PENDIENTE', 1, 1),
       (3, 2, '2026-06-29', '14:30', 4500.0, 12, '45 min', 'Expreso', 'PENDIENTE', 1, 1);

insert into Viaje (id_origen, id_destino, fecha, horario, precio, asientosDisponibles, duracion, tipoDeViaje, estadoDeViaje, id_combi, id_conductor)
values (1, 6, '2026-06-30', '14:00', 3800.0, 12, '35 min', 'Comun', 'PENDIENTE', 1, 1);

-- 6. Prueba de reservas (Ahora están al final para que los viajes 5 y 6 ya existan)
insert into Reserva (id_usuario, id_viaje, estadoReserva) values (2, 3, 'FINALIZADA');
insert into Reserva (id_usuario, id_viaje, estadoReserva) values (2, 4, 'FINALIZADA');
insert into Reserva (id_usuario, id_viaje, estadoReserva) values (2, 6, 'FINALIZADA');
insert into Reserva (id_usuario, id_viaje, estadoReserva) values (2, 5, 'CONFIRMADA');