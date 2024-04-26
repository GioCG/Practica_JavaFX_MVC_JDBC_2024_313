drop database IF EXISTS superDB;
CREATE DATABASE IF NOT EXISTS superDB;
USE superDB;
SET GLOBAL time_zone = '-6:00';

create table Cargos(
	cargoId INT NOT NULL AUTO_INCREMENT,
    nombreCargo VARCHAR(30),
    descripcionCargo VARCHAR(100),
    PRIMARY KEY pk_cargoId(cargoId)
);
create table CategoriaProductos(
	categoriaProductosId INT NOT NULL AUTO_INCREMENT,
    nombreCategoria VARCHAR(30) NOT NULL,
    descripcionCategoria VARCHAR(100) NOT NULL,
     PRIMARY KEY PK_categoriaProductosId(categoriaProductosId)
);
create table Distribuidores(
	distribuidorId INT NOT NULL AUTO_INCREMENT,
	nombreDistribuidor VARCHAR(30) NOt NULL,
    disreccionDistribuidor VARCHAR(200) NOt NULL,
    nitDistribuidor VARCHAR(15) NOt NULL,
    telefonoDistribuidor VARCHAR(15) NOt NULL,
    web varchar(50),
     PRIMARY KEY pk_distribuidorId (distribuidorId)
);
create table Compras(
	compraId INT NOT NULL AUTO_INCREMENT,
    fechaCompra DATE NOT NULL,
    totalCompra DECIMAL(10,2),
     PRIMARY KEY pk_compraId (compraId)
);
create table Clientes(
	clienteId INT NOT NULL AUTO_INCREMENT,
    NIT varchar(30),
    nombre varchar(30) NOT NULL,
    apellido varchar(30) NOT NULL,
	telefono varchar(15),
    direccion varchar(200) NOT NULL,
    PRIMARY KEY pk_clienteId (clienteId)	
);

create table Productos(
    productoId INT NOT NULL AUTO_INCREMENT,
    nombreProducto VARCHAR(50) NOT NULL,
    descripcionProducto VARCHAR(100),
    cantidadStock INT NOT NULL,
    precioVentaUnitario DECIMAL(10,2) NOT NULL,
    precioVentaMayor DECIMAL(10,2) NOT NULL,
    precioCompra DECIMAL(10,2)NOT NULL,
    imagenProducto BLOB,
    distribuidorId INT NOT NULL,
    categoriaProductosId INT,
	PRIMARY KEY PK_productoId(productoId),
    CONSTRAINT FK_Productos_Distribuidores FOREIGN KEY Productos(distribuidorId)
        REFERENCES Distribuidores(distribuidorId),
	CONSTRAINT FK_Productos_CategoriaProductos FOREIGN KEY Productos(categoriaProductosId)
        REFERENCES CategoriaProductos(categoriaProductosId)
);
create table Promociones(
	promocionId INT NOT NULL AUTO_INCREMENT,
    precioPromocio DECIMAL(10,2) NOT NULL,
    descripcionPromocion VARCHAR(100),
    fechaInicio DATE NOT NULL,
    fechaFinal DATE NOT NULL,
    productoId INT,
    PRIMARY KEY promocionId(promocionId),
    CONSTRAINT FK_Promociones_Productos FOREIGN KEY Promociones(productoId)
		REFERENCES Productos(productoId)
); 
create table DetalleCompras(
	detalleCompraId INT NOT NULL AUTO_INCREMENT,
    cantidadCompra INT NOT NULL,
    productoId INT,
    compraId INT,
    PRIMARY KEY PK_detalleCompraId(detalleCompraId),
    CONSTRAINT FK_DetalleCompras_Producots FOREIGN KEY DetalleCompra(productoId)
		REFERENCES Productos(productoId),
	CONSTRAINT FK_DetalleCompras_Compras FOREIGN KEY DetalleCompra(compraId)
		REFERENCES Compras(compraId)
);
create table Empleados(
    empleadoId INT NOT NULL AUTO_INCREMENT,
    nombreEmpleado VARCHAR(30) NOT NULL,
    apellidoEmpleado VARCHAR(30) NOT NULL,
    sueldo DECIMAL(10,2) NOT NULL,
    horaEntrada TIME NOT NULL,
    horaSalida TIME NOT NULL,
    cargoId INT,
    encargadoId INT,
    PRIMARY KEY PK_empleadoId(empleadoId),
    CONSTRAINT FK_Productos_Cargos FOREIGN KEY Productos(cargoId)
        REFERENCES Cargos(cargoId),
    CONSTRAINT FK_encargadoId FOREIGN KEY Empleados(encargadoId)
        REFERENCES Empleados(empleadoId)
);
create table Facturas(
    facturaId INT NOT NULL AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    clienteId INT NOT NULL,
    empleadoId INT NOT NULL,
    total decimal(10,2),
    PRIMARY KEY PK_facturaId(facturaId),
    CONSTRAINT FK_Factura_Clientes FOREIGN KEY Facturas(clienteId)
        REFERENCES Clientes(clienteId),
    CONSTRAINT FK_Facturas_Empleados FOREIGN KEY Facturas(empleadoId)
        REFERENCES Empleados(empleadoId)
);

create table TicketSoporte(
	ticketSoporteId INT NOT NULL AUTO_INCREMENT,
    descripcionTicketSoporte VARCHAR(250) NOT NULL,
    estatus VARCHAR(30) DEFAULT 'Recién creado' NOT NULL,
    clienteId INT,
    facturaId INT,
    PRIMARY KEY PK_ticketSoporteId(ticketSoporteId),
    CONSTRAINT FK_ticketSporte_Clientes FOREIGN KEY ticketSporte(clienteId)
        REFERENCES Clientes(clienteId),
    CONSTRAINT FK_ticketSporte_Facturas FOREIGN KEY ticketSporte(facturaId)
        REFERENCES Facturas(facturaId)
);
create table DetalleFactura(
    detalleFacturaId INT NOT NULL AUTO_INCREMENT,
    facturaId INT,
    productoId INT,
    PRIMARY KEY PK_detalleFacturaId(detalleFacturaId),
    CONSTRAINT FK_DetalleFactura_Facturas FOREIGN KEY DetalleFactura(facturaId)
        REFERENCES Facturas(facturaId),
    CONSTRAINT FK_DetalleFactura_Productos FOREIGN KEY DetalleFactura(productoId)
        REFERENCES Productos(productoId)
);