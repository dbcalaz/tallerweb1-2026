insert into Usuario(id, email, password, rol, activo) values(null, 'test@unlam.edu.ar', 'test', 'CLIENTE', true);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
values ('San Justo', 'Ramos Mejia', '2026-05-29', '10:30', 4500.0, 4);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
values ('Moron', 'Moreno', '2026-05-30', '14:00', 3800.0, 2);

-- Inserts para vista del conductor
insert into Conductor (nombre, apellido, email, telefono, documento, password, licencia, calificacion, ganancia)
values ('Eduardo', 'Zaens', 'ezaens@mail.com', '1123456789', '12345678', 'asd', 'D2', 3.9, 75000.0);

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
values (2, 2, 'CONFIRMADA');

-- inserts para la vista de administrador
insert into Usuario(email, password, rol, activo) values('admin@mail.com', 'admin', 'ADMIN', true);

INSERT INTO ReporteFalla (descripcion, resuelta, conductor_id, combi_id, fechaCreacionReporte, fechaRealizadoReporte) VALUES
('Falla en el motor, pérdida de potencia en subida', false, 1, 1, '2026-06-01 08:30:00', NULL),
('Luz de aceite encendida intermitente', false, 1, 1, '2026-06-02 10:15:00', NULL),
('Ruido en tren delantero al girar', false, 1, 1, '2026-06-03 18:45:00', NULL);