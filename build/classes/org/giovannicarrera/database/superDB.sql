drop database superDB;
create database superDB;
use superDB;

create table Cargos(
	cargoId INT not null auto_increment,
    nombreCargo VARCHAR(30),
    descripcionCargo VARCHAR(100),
    primary key pk_cargoId(cargoId)
);
create table CategoriaProductos(
	categoriaProductosId INT NOT NULL AUTO_INCREMENT,
    nombreCategoria VARCHAR(30) NOT NULL,
    descripcionCategoria VARCHAR(100) NOT NULL,
    primary key PK_categoriaProductosId(categoriaProductosId)
);
create table Distribuidores(
	distribuidorId INT NOT NULL AUTO_INCREMENT,
	nombreDistribuidor VARCHAR(30) not null,
    disreccionDistribuidor VARCHAR(200) not null,
    nitDistribuidor VARCHAR(15) not null,
    telefonoDistribuidor VARCHAR(15) not null,
    web varchar(50),
    primary key pk_distribuidorId (distribuidorId)
);
create table compras(
	compraId INT NOT NULL AUTO_INCREMENT,
    fechaCompra DATE not null,
    totalCompra DECIMAL(10,2),
    primary key pk_compraId (compraId)
);
create table Clientes(
	clienteId INT NOT NULL AUTO_INCREMENT,
    NIT varchar(30),
    nombre varchar(30) not null,
    apellido varchar(30) not null,
	telefono varchar(15),
    direccion varchar(200) not null,
    primary key pk_clienteId (clienteId)	
);

create table Productos(
    productoId INT NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(50) not null,
    descripcionProducto VARCHAR(100),
    cantidadStack INT not null,
    precioVentaUnitario DECIMAL(10,2) not null,
    precioVentaMayor DECIMAL(10,2) not null,
    precioCompra DECIMAL(10,2)not null,
    imagenProducto BLOB,
    distribuidorId INT not null,
    categoriaProductosId INT,
    primary key PK_productoId(productoId),
    constraint FK_Productos_Distribuidores foreign key Productos(distribuidorId)
        references Distribuidores(distribuidorId),
	constraint FK_Productos_CategoriaProductos foreign key Productos(categoriaProductosId)
        references CategoriaProductos(categoriaProductosId)
);
create table Promociones(
	promocionId INT NOT NULL AUTO_INCREMENT,
    precioPromocio DECIMAL(10,2) NOT NULL,
    descripcionPromocion VARCHAR(100),
    fechaInicio DATE NOT NULL,
    fechaFinal DATE NOT NULL,
    productoId INT,
    primary key promocionId(promocionId),
    constraint FK_Promociones_Productos FOREIGN KEY Promociones(productoId)
		REFERENCES Productos(productoId)
); 
create table DetalleCompra(
	detalleCompraId INT NOT NULL AUTO_INCREMENT,
    cantidadCompra INT NOT NULL,
    productoId INT,
    compraId INT,
    primary key PK_detalleCompraId(detalleCompraId),
    CONSTRAINT FK_DetalleCompra_Producots FOREIGN KEY DetalleCompra(productoId)
		REFERENCES Productos(productoId),
	CONSTRAINT FK_DetalleCompra_Compras FOREIGN KEY DetalleCompra(compraId)
		REFERENCES Compras(compraId)
);
create table Empleados(
    empleadoId INT NOT NULL AUTO_INCREMENT,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar(30),
    sueldo DECIMAL(10,2),
    horaEntrada TIME,
    horaSalida TIME,
    cargoId INT,
    encargadoId int,
    primary key PK_empleadoId(empleadoId),
    constraint FK_Productos_Cargos foreign key Productos(cargoId)
        references Cargos(cargoId),
    constraint FK_encargadoId foreign key Empleados(encargadoId)
        references Empleados(empleadoId)
);
create table Facturas(
    facturaId INT NOT NULL AUTO_INCREMENT,
    fecha date not null,
    clienteId int not null,
    empleadoId int not null,
    total decimal(10,2),
    primary key PK_facturaId(facturaId),
    constraint FK_Factura_Clientes foreign key Facturas(clienteId)
        references Clientes(clienteId),
    constraint FK_Facturas_Empleados foreign key Facturas(empleadoId)
        references Empleados(empleadoId)
);

create table ticketSporte(
	ticketSoporteId INT NOT NULL AUTO_INCREMENT,
    descripcionTicket VARCHAR(250),
    estatus VARCHAR(30),
    clienteId INT,
    facturaId INT,
    primary key PK_ticketSoporteId(ticketSoporteId),
    constraint FK_ticketSporte_Clientes foreign key ticketSporte(clienteId)
        references Clientes(clienteId),
    constraint FK_ticketSporte_Facturas foreign key ticketSporte(facturaId)
        references Facturas(facturaId)
);
create table DetalleFactura(
    detalleFacturaId INT NOT NULL AUTO_INCREMENT,
    facturaId int not null,
    productoId int not null,
    primary key PK_detalleFacturaId(detalleFacturaId),
    constraint FK_DetalleFactura_Facturas foreign key DetalleFactura(facturaId)
        references Facturas(facturaId),
    constraint FK_DetalleFactura_Productos foreign key DetalleFactura(productoId)
        references Productos(productoId)
);

-- CRUD Clientes

-- Agregar
Delimiter $$
create procedure sp_AgregarClientes(in ni varchar(30),in nom varchar(30), in ape varchar (30), in tel varchar(15), in dir varchar (200))
	Begin
		insert into Clientes(NIT,nombre, apellido, telefono, direccion) values
			(ni,nom, ape, tel, dir);
	End$$
Delimiter ;

-- Listar
Delimiter $$
create procedure sp_ListarClientes()
	begin
		select
			Clientes.clienteId,
            Clientes.NIT,
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion
				from Clientes;
	end $$
Delimiter ;

-- Eliminar
Delimiter $$
create procedure sp_EliminarCliente(in cliId int)
	begin 
		delete from Clientes
			where clienteId = cliId;
	end $$
Delimiter ;

-- Buscar
Delimiter $$
create procedure sp_BuscarCliente(in cliId int)
	begin
		select
			Clientes.clienteId,
			Clientes.NIT,
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion
				from Clientes
					where clienteId = cliId;
	end $$
delimiter ;
             
-- Editar
Delimiter $$
create procedure sp_EditarCliente(in cliId int,in ni varchar(30), in nom varchar(30), in ape varchar (30), in tel varchar(15), in dir varchar (200))
	begin
		update Clientes
			set
				NIT = ni,
				nombre = nom,
                apellido = ape,
                telefono = tel,
                direccion = dir
					where clienteId = cliId;
	end $$
Delimiter ;

SET GLOBAL time_zone = '-6:00';




-- CRUD Facturas
delimiter $$
		create procedure sp_AgregarFactura(IN fec date,IN numCli int,IN empId int) 
	begin
		insert into Facturas(fecha,numCliente,empleadoId)
			values(fec,numCli,empId);
	end$$
delimiter ;

delimiter $$

create procedure SP_agregarDetFacturas(IN facId int,IN proId int)
        begin
		insert into DetalleFactura(facturaId,productoId)
			values(facId, proId);
		end$$
	
delimiter ;

Delimiter $$
create function FN_AsignarEncargado(encId int) returns boolean
deterministic
begin
	declare i int default 2;
    declare a int;
    select count(*) into a from Empleados;
		while i <= a do
			update Empleados set encargadoId = encId where empleadoId = i;
			set i = i +1;
		end while;
        return true;
	end$$
delimiter ;

