CREATE DATABASE PlanPaciente;
USE PlanPaciente;

create table Usuario (
                         id varchar(10) unique not null,
                         clave varchar(100) not null,
                         rol varchar(10) not null,
                         Primary key(id)
);

create table Medicamento (
                             id varchar(20) not null,
                             nombre varchar(30) not null,
                             plan integer,
                             Primary key(id)
);

create table Farmacia (
                          id varchar(20) not null,
                          nombre varchar(30) not null,
                          usuario varchar(20) not null,
                          Primary key(id)
);

create table Paciente (
                          id varchar(20) not null,
                          nombre varchar(30) not null,
                          Primary key(id)
);

create table PacienteMedicamento (
                                     id integer auto_increment not null,
                                     paciente varchar(20) not null,
                                     medicamento varchar(20) not null,
                                     dosisafavor integer,
                                     Primary key(id)
);

alter table Farmacia add foreign key (usuario) references Usuario(id);
alter table PacienteMedicamento add foreign key (paciente) references Paciente(id);
alter table PacienteMedicamento add foreign key (medicamento) references Medicamento(id);
insert into Usuario (id,clave,rol) values ('Farma10','$2a$12$UafqQkd9TVJxw9W4HoQ1eePssHEW6IPlX9VeKHWvMfiEeHGgsdOGO','FARM'); /* clave 111 */
insert into Usuario (id,clave,rol) values ('Bomba07','$2a$12$z0dbkRqX1JSXOjAN9YX8tOWp.Es70TJl08ebtGgrwdOvgKe9dC67e','FARM'); /* clave 222 */

insert into Medicamento (id,nombre,plan) values ('LIP20','LipoRaven 20mg',2);
insert into Medicamento (id,nombre,plan) values ('EZE10','Ezetemin 10mg',3);
insert into Medicamento (id,nombre,plan) values ('TAL05','Taladaf 5mg',1);

insert into Farmacia (id,nombre,usuario) values ('Farma10','FarmaValue Sabanilla','Farma10');
insert into Farmacia (id,nombre,usuario) values ('Bomba07','La Bomba San Pedro','Bomba07');

insert into Paciente (id,nombre) values ('111','Juan Perez');
insert into Paciente (id,nombre) values ('222','Maria Mata');

insert into PacienteMedicamento (paciente,medicamento,dosisafavor) values ('111','EZE10',1);
insert into PacienteMedicamento (paciente,medicamento,dosisafavor) values ('111','TAL05',0);
insert into PacienteMedicamento (paciente,medicamento,dosisafavor) values ('222','LIP20',1);