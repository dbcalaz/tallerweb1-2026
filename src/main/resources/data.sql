insert into Usuario(id, email, password, rol, activo) values(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
values ('San Justo', 'Ramos Mejia', '2026-05-29', '10:30', 4500.0, 4);

insert into Viaje (origen, destino, fecha, horario, precio, asientosDisponibles)
values ('Moron', 'Moreno', '2026-05-30', '14:00', 3800.0, 2);

-- Inserts para vista del conductor
insert into Conductor(nombre, apellido, email, telefono, documento, password, licencia,calificacion,ganancia) values ('Eduardo', 'Zaens','ezaens@mail.com','1123456789','12345678','asd','D2',3.9, 75.000);

insert into Viaje
(origen, destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_conductor)
values
('Móron', 'Ramos Mejia', '2026-06-01', '08:00', 2500.0, 1001, 2, 'PENDIENTE', 1);

insert into Viaje
(origen, destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_conductor)
values
('San Justo', 'Liniers', '2026-06-02', '09:30', 3200.0, 1002, 3, 'PENDIENTE', 1);

insert into Viaje
(origen, destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_conductor)
values
('Haedo', 'Moreno', '2026-05-20', '18:00', 2800.0, 1003, 0, 'FINALIZADO', 1);

insert into Viaje
(origen, destino, fecha, horario, precio, numeroViaje, asientosDisponibles, estadoDeViaje, id_conductor)
values
('Castelar', 'Ituzaingo', '2026-05-18', '14:15', 1900.0, 1004, 0, 'FINALIZADO', 1);


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