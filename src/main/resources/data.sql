insert into Usuario(id, email, password, rol, activo) values(null, 'test@unlam.edu.ar', 'test', 'CLIENTE', true);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
values ('San Justo', 'Ramos Mejia', '2026-05-29', '10:30', 4500.0, 4);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
values ('Moron', 'Moreno', '2026-05-30', '14:00', 3800.0, 2);

-- Inserts para vista del conductor
insert into Conductor (nombre, apellido, email, telefono, documento, password, licencia, calificacion, ganancia, cuentaHabilitada, disponible, enViaje, suspendido)
values ('Eduardo', 'Zaens', 'ezaens@mail.com', '1123456789', '12345678', 'asd', 'D2', 3.9, 75000.0, true, true, true, false);

insert into Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo)
values ('ESTANDAR', 15, 'Manual', 'AD-542-XQ', 'Mercedes-Benz', 'Sprinter 515');

insert into AsignacionCombiConductor (id_conductor, id_combi, combiActiva) values (1, 1, true);

insert into Viaje (origen, destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_combi, id_conductor)
values ('Morón', 'Ramos Mejía', '2026-06-01', '08:00', 2500.0, 1001, 2, 'PENDIENTE', 1, 1),
('San Justo', 'Liniers', '2026-06-02', '09:30', 3200.0, 1002, 3, 'PENDIENTE', 1, 1),
('Haedo', 'Moreno', '2026-05-20', '18:00', 2800.0, 1003, 0, 'FINALIZADO', 1, 1),
('Castelar', 'Ituzaingó', '2026-05-18', '14:15', 1900.0, 1004, 0, 'FINALIZADO', 1, 1);

-- Prueba de reservas

insert into Usuario
(id,email,password,rol,activo,nombre,apellido,telefono)
values
    (2,'j@j','123','CLIENTE',true,'Juan','Bringa','1122334455');

insert into Reserva (id_usuario, id_viaje, estadoReserva)
values (2, 3, 'FINALIZADA');

insert into Reserva (id_usuario, id_viaje, estadoReserva)
values (2, 4, 'FINALIZADA');

insert into Reserva (id_usuario, id_viaje, estadoReserva)
values (2, 6, 'FINALIZADA');

insert into Reserva (id_usuario, id_viaje, estadoReserva)
values (2, 5, 'CONFIRMADA');

-- inserts para la vista de administrador
insert into Usuario(email, password, rol, activo) values('admin@mail.com', 'admin', 'ADMIN', true);

INSERT INTO ReporteFalla (descripcion, resuelta, conductor_id, combi_id, fechaCreacionReporte, fechaRealizadoReporte) VALUES
('Falla en el motor, pérdida de potencia en subida', false, 1, 1, '2026-06-01', NULL),
('Luz de aceite encendida intermitente', false, 1, 1, '2026-06-02', NULL),
('Ruido en tren delantero al girar', false, 1, 1, '2026-06-03', NULL);

INSERT INTO Conductor (nombre, apellido, email, telefono, documento, licencia, calificacion, ganancia, cuentaHabilitada, disponible, enViaje, suspendido)
VALUES ('María', 'Sosa', 'msosa@mail.com', '1134567890', '23456789', 'D1', 4.7, 92000.0, true,true, true, false),
('Carlos', 'Ruiz', 'cruiz@mail.com', '1145678901', '34567890', 'D2', 4.3, 81000.0, true, true, false, false),
('Lucía', 'Fernández', 'lfernandez@mail.com', '1156789012', '45678901', 'D1', 4.9, 98000.0, true, false, false, true);

INSERT INTO Combi (tipoDeCombi, cantidadDeAsientos, tipoDeTransmision, patente, marca, modelo) VALUES
('TURISTICA', 19, 'Automatica', 'AE-781-LM', 'Ford', 'Transit Minibus'),
('ESTANDAR', 12, 'Manual', 'AF-234-RT', 'Renault', 'Master L2H2'),
('TURISTICA', 20, 'Automatica', 'AG-908-KP', 'Iveco', 'Daily Minibus'),
('ESTANDAR', 15, 'Manual', 'AH-456-ND', 'Volkswagen', 'Crafter'),
('TURISTICA', 17, 'Automatica', 'AI-123-ZX', 'Peugeot', 'Boxer Premium'),
('TURISTICA', 21, 'Automatica', 'AJ-654-WQ', 'Mercedes-Benz', 'Sprinter 517 Turismo'),
('ESTANDAR', 14, 'Manual', 'AK-332-UV', 'Toyota', 'Hiace');