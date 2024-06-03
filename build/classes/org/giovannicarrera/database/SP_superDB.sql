USE superDB;

-- ============================================= CRUD Cargos ==============================================
-- ========================================================================================================

Delimiter $$
CREATE PROCEDURE sp_agregarCargo(IN nomCarg VARCHAR(30),IN descCarg VARCHAR(100))
	BEGIN
		INSERT INTO Cargos(nombreCargo,descripcionCargo) VALUES
			(nomCarg,descCarg);
	END$$
Delimiter ;

Delimiter $$
CREATE PROCEDURE sp_listarCargo()
	BEGIN
		SELECT
			Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
				FROM Cargos;
	END$$
Delimiter ;

Delimiter $$
CREATE PROCEDURE sp_eliminarCargo(IN carId INT)
	BEGIN 
		DELETE FROM Cargos
			WHERE cargoId = carId;
	END$$
Delimiter ;

Delimiter $$
CREATE PROCEDURE sp_buscarCargo(IN carId INT)
	BEGIN
		SELECT
            Cargos.cargoId,
            Cargos.nombreCargo,
            Cargos.descripcionCargo
				FROM Cargos
					WHERE cargoId = carId;
	END $$
delimiter ;
             
Delimiter $$
CREATE PROCEDURE sp_editarCargo(IN carId INT,IN nomCar VARCHAR(30), IN descCar VARCHAR(100))
	BEGIN
		UPDATE Cargos
			SET
				nombreCargo = nomCar,
				descripcionCargo = descCarg
					WHERE cargoId = carId;
	END $$
Delimiter ;
-- ======================================= CRUD CategoriaProductos ========================================
-- ========================================================================================================
Delimiter $$
CREATE PROCEDURE sp_agregarCategoriaProducto(IN nomCat VARCHAR(30),IN descCat VARCHAR(100))
	Begin
		insert into CategoriaProductos(nombreCategoria,descripcionCategoria) values
			(nomCat,descCat);
	END$$
Delimiter ;

Delimiter $$
create procedure sp_listarCategoriaProductos()
	begin
		select
			CategoriaProductos.categoriaProductosId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
				from CategoriaProductos;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_eliminarCategoriaProductos(in catProId int)
	begin 
		delete from CategoriaProductos
			where categoriaProductosId = catProId;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_buscarCategoriaProductos(in catProId int)
	begin
		select
            CategoriaProductos.categoriaProductosId,
            CategoriaProductos.nombreCategoria,
            CategoriaProductos.descripcionCategoria
				from CategoriaProductos
					where categoriaProductosId = catProId;
	end $$
delimiter ;
             
Delimiter $$
create procedure sp_editarCategoriaProductos(in catProId int,in nomCat varchar(30), in descCat varchar(100))
	begin
		update CategoriaProductos
			set
				nombreCategoria = nomCat,
				descripcionCategoria = descCat
					where categoriaProductosId = catProId;
	end $$
Delimiter ;
-- ========================================= CRUD Distribuidores ==========================================
-- ========================================================================================================
Delimiter $$
create procedure sp_AgregarDistribuidor(in nomDis varchar(30), in dirDis varchar (200), in nitDis varchar(15), in telDis varchar(15), in we varchar(50))
	Begin
		insert into Distribuidores(nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web) values
			(nomDis, dirDis, nitDis, telDis, we);
	End$$
Delimiter ;

Delimiter $$
create procedure sp_ListarDistribuidor()
	begin
		select
			Distribuidores.distribuidorId,
			Distribuidores.nombreDistribuidor,
			Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor, 
            Distribuidores.web
				from Distribuidores;
	End $$
Delimiter ;

Delimiter $$
create procedure sp_EliminarDistribuidores(in disId int)
	begin 
		delete from Distribuidores
			where distribuidorId = disId;
	End $$
Delimiter ;

Delimiter $$
create procedure sp_BuscarDistribuidores(in disId int)
	begin
		select
			Distribuidores.distribuidorId,
			Distribuidores.nombreDistribuidor,
            Distribuidores.direccionDistribuidor,
            Distribuidores.nitDistribuidor,
            Distribuidores.telefonoDistribuidor,
            Distribuidores.web
				from Distribuidores
					where distribuidorId = disId;
	End $$
delimiter ;
     
Delimiter $$
Create procedure sp_EditarDistribuidores(in disId int,in nomDis varchar(30), in dirDis varchar (200), in nitDis varchar(15), in telDis varchar(15), in we varchar(50))
	Begin
		Update Distribuidores
			set
                nombreDistribuidor = nomDis,
                direccionDistribuidor = dirDis,
                nitDistribuidor = nitDis,
                telefonoDistribuidor = telDis,
                web = we
					where distribuidorId = disId;
	End $$
Delimiter ;
-- ============================================ CRUD Compras ==============================================
-- ========================================================================================================
Delimiter $$
create procedure sp_agregarCompra(in fecCom DATE)
	Begin
		insert into Compras(fechaCompra) values
			(fecCom);
	End$$
Delimiter ;

-- Listar
Delimiter $$
create procedure sp_listarCompra()
	begin
		select
			Compras.compraId,
            Compras.fechaCompra,
            Compras.totalCompra
				from Compras;
	end $$
Delimiter ;

-- Eliminar
Delimiter $$
create procedure sp_eliminarCompra(in comId int)
	begin 
		delete from Compras
			where compraId = comId;
	end $$
Delimiter ;

-- Buscar
Delimiter $$
create procedure sp_buscarCompra(in comId int)
	begin
		select
			Compras.compraId,
            Compras.fechaCompra,
            Compras.totalCompra
				from Compras
					where compraId = comId;
	end $$
delimiter ;
             
-- Editar
Delimiter $$
create procedure sp_editarCompra(in comId int,in fecCom DATE)
	begin
		update Compras
			set
				fechaCompra = fecCom
					where compraId = comId;
	end $$
Delimiter ;
-- ============================================ CRUD Clientes =============================================
-- ========================================================================================================
Delimiter $$
create procedure sp_agregarClientes(in ni varchar(30),in nom varchar(30), in ape varchar (30), in tel varchar(15), in dir varchar (200))
	Begin
		insert into Clientes(NIT,nombre, apellido, telefono, direccion) values
			(ni,nom, ape, tel, dir);
	End$$
Delimiter ;

Delimiter $$
create procedure sp_listarClientes()
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



Delimiter $$
create procedure sp_eliminarCliente(in cliId int)
	begin 
		delete from Clientes
			where clienteId = cliId;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_buscarCliente(in cliId int)
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
             
Delimiter $$
create procedure sp_editarCliente(in cliId int,in ni varchar(30), in nom varchar(30), in ape varchar (30), in tel varchar(15), in dir varchar (200))
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
-- ============================================ CRUD Productos =============================================
-- =========================================================================================================
Delimiter $$
create procedure sp_agregarProducto(in nomProd varchar(50),in desProd varchar(100), in cantiSt INT, in preVenUnit DECIMAL(10,2), in preVenMay DECIMAL(10,2),precioCompra DECIMAL(10,2),IN disId INT,IN catProId INT)
	Begin
		insert into Productos(nombreProducto,descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor,precioCompra,distribuidorId,categoriaProductosId) values
			(nomProd,desProd,cantiSt, preVenUnit, preVenMay, precioCompra,disId,catProId);
	End$$
Delimiter ;

Delimiter $$
create procedure sp_listarProducto()
	begin
		select
			Productos.productoId,
            Productos.nombreProducto,
            Productos.descripcionProducto,
            Productos.cantidadStock,
            Productos.precioVentaUnitario,
            Productos.precioVentaMayor,
            Productos.precioCompra,
			Productos.imagen,
			Productos.distribuidorId,
			Productos.categoriaProductosId

				from Productos;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_ListarProductoComple()
	begin
		select P.productoId, P.nombreProducto, P.descripcionProducto,P.precioVentaUnitario,P.precioVentaMayor,P.precioCompra,P.imagen,
        CONCAT("Id: ",D.distribuidorId,"|",D.nombreDistribuidor,"|" ,D.direccionDistribuidor,"|",D.nitDistribuidor,"|",telefonoDistribuidor) As Distribuidor,
        CONCAT("Id: ",CA.categoriaProductosId,"|",CA.nombreCategoria,"|" , CA.descripcionCategoria) As Categoria, P.cantidadStock from Productos P
        join Distribuidores D on P.distribuidorId =  D.distribuidorId
		join CategoriaProductos CA on P.categoriaProductosId = CA.categoriaProductosId;	
	End $$
Delimiter;
Delimiter $$
create procedure sp_eliminarProducto(in prodId int)
	begin 
		delete from Productos
			where productoId = prodId;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_buscarProducto(in prodId int)
	begin
		select P.productoId, P.nombreProducto, P.descripcionProducto,P.precioVentaUnitario,P.precioVentaMayor,P.precioCompra,P.imagen,
        CONCAT("Id: ",D.distribuidorId,"|",D.nombreDistribuidor,"|" ,D.direccionDistribuidor,"|",D.nitDistribuidor,"|",telefonoDistribuidor) As Distribuidor,
        CONCAT("Id: ",CA.categoriaProductosId,"|",CA.nombreCategoria,"|" , CA.descripcionCategoria) As Categoria, P.cantidadStock from Productos P
        join Distribuidores D on P.distribuidorId =  D.distribuidorId
		join CategoriaProductos CA on P.categoriaProductosId = CA.categoriaProductosId
					where productoId = prodId;
	end $$
delimiter ;
             
Delimiter $$
create procedure sp_editarProducto(in prodId int,in nomProd varchar(50),in desProd varchar(100), in cantiSt INT, in preVenUnit DECIMAL(10,2), in preVenMay DECIMAL(10,2),precioCompra DECIMAL(10,2))
	begin
		update Productos
			set
				nombreProducto = nomProd,
				descripcionProducto = desProd,
                cantidadStock = cantiSt,
                precioVentaUnitario = preVenUnit,
                precioVentaMayor =preVenMay,
                precioCompra = precioCompra
					where productoId = prodId;
	end $$
Delimiter ;
-- =========================================== CRUD Promociones ============================================
-- =========================================================================================================
Delimiter $$
create procedure sp_agregarPromocion(in preProm DECIMAL(10,2),in desProm VARCHAR(100), in fecIn DATE, in fecFin DATE, in prodId INT)
	Begin
		insert into Promociones(precioPromocio,descripcionPromocion, fechaInicio, fechaFinal,productoId) values
			(preProm,desProm, fecIn, fecFin, prodId);
	End$$
Delimiter ;

Delimiter $$
create procedure sp_listarPromocion()
	begin
		select
			Promociones.promocionId,
            Promociones.precioPromocio,
            Promociones.descripcionPromocion,
            Promociones.fechaInicio,
            Promociones.fechaFinal,
            Promociones.productoId
				from Promociones;
	end $$
Delimiter ;
Delimiter $$
create procedure sp_ListarPromocionComple()
	begin
		select P.promocionId, P.precioPromocio, P.descripcionPromocion, P.fechaInicio , P.fechaFinal,
        CONCAT("Id: ",Pr.productoId, "|",Pr.nombreProducto, "|", Pr.cantidadStock, "|", Pr.precioVentaUnitario) As Productos from Promociones P
        join Productos Pr on P.productoId =  Pr.productoId;

	End $$
Delimiter ;
Delimiter $$
create procedure sp_eliminarPromocion(in promId int)
	begin 
		delete from Promociones
			where promocionId = promId;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_buscarPromocion(in promId int)
	begin
		select P.promocionId, P.precioPromocio, P.descripcionPromocion, P.fechaInicio , P.fechaFinal,
        CONCAT("Id: ",Pr.productoId, "|",Pr.nombreProducto, "|", Pr.cantidadStock, "|", Pr.precioVentaUnitario) As Productos from Promociones P
        join Productos Pr on P.productoId =  Pr.productoId
					where promocionId = promId;
	end $$
delimiter ;
             
Delimiter $$
create procedure sp_editarPromocion(in promId int,in preProm DECIMAL(10,2),in desProm VARCHAR(100), in fecIn DATE, in fecFin DATE, in prodId INT)
	begin
		update Promociones
			set
				precioPromocio = preProm,
				descripcionPromocion = desProm,
                fechaInicio = fecIn,
                fechaFinal = fecFin,
                productoId = prodId
					where promocionId = prodId;
	end $$
Delimiter ;

-- ========================================== CRUD DetalleCompras ==========================================
-- ========================================================================================================
Delimiter $$
create procedure sp_agregarDetalleCompra(in cantCom INT,in prodId INT, in comId INT)
	Begin
		insert into DetalleCompras(cantidadCompra,productoId, compraId) values
			(cantCom,prodId, comId);
	End$$
Delimiter ;

Delimiter $$
create procedure sp_listarDetalleCompra()
	begin
		select
			DetalleCompras.detalleCompraId,
            DetalleCompras.cantidadCompra,
            DetalleCompras.productoId,
            DetalleCompras.compraId
				from DetalleCompras;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_listarDetalleCompraComple()
	begin
		select DC.detalleCompraId, DC.cantidadCompra,
        CONCAT("Id: ",Pr.productoId, "|",Pr.nombreProducto, "|", Pr.cantidadStock, "|", Pr.precioVentaUnitario) As Productos,
        CONCAT("Id: ",c.CompraId, "|",c.fechaCompra) As Compras from DetalleCompras DC
        join Productos Pr on DC.productoId =  Pr.productoId
        join Compras C on DC.compraId =  C.compraId;
	end $$
Delimiter ;


Delimiter $$
create procedure sp_eliminarDetalleCompra(in detComId int)
	begin 
		delete from DetalleCompras
			where detalleCompraId = detComId;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_buscarDetalleCompra(in detComId int)
	begin
		select
			DetalleCompras.detalleCompraId,
            DetalleCompras.cantidadCompra,
            DetalleCompras.productoId,
            DetalleCompras.compraId
				from DetalleCompras
					where detalleCompraId = detComId;
	end $$
delimiter ;
             
Delimiter $$
create procedure sp_editarDetalleCompra(in detComId int,in cantCom INT,in prodId INT, in comId INT)
	begin
		update Clientes
			set
                cantidadCompra = cantCom,
                productoId = prodId,
                compraId = comId
					where detalleCompraId = detComId;
	end $$
Delimiter ;
-- ============================================ CRUD Empleados ============================================
-- ========================================================================================================
delimiter $$
		create procedure sp_agregarEmpleado(IN nomEmp varchar(30), in apeEmp varchar(30),IN suel DECIMAL(10,2),IN horEnt TIME, IN horSal TIME, IN carId INT) 
	begin
		insert into Empleados(nombreEmpleado, apellidoEmpleado,sueldo,horaEntrada,horaSalida,cargoId)
			values(nomEmp,apeEmp,suel,horEnt,horSal,carId);
	end$$
delimiter ;

Delimiter $$
create procedure sp_listarEmpleado()
	begin
		select
			Empleados.empleadoId,
            Empleados.nombreEmpleado,
            Empleados.apellidoEmpleado,
            Empleados.sueldo,
            Empleados.horaEntrada,
            Empleados.horaSalida,
            Empleados.cargoId,
            Empleados.encargadoId
				from Empleados;
	end $$
Delimiter ;

Delimiter $$
create procedure sp_listarEmpleadoComp()
	begin
		select E.empleadoId, E.nombreEmpleado, E.apellidoEmpleado, E.sueldo, E.horaEntrada, E.horaSalida,
        CONCAT("Id: ",C.cargoId, "|",C.nombreCargo, "|" , C.descripcionCargo) As Cargo, E.encargadoId from Empleados E
        join Cargos C on E.cargoId =  C.cargoId;
	end $$
Delimiter ;

-- Eliminar
Delimiter $$
create procedure sp_eliminarEmpleado(in empId int)
	begin 
		delete from Empleados
			where empleadoId = empId;
	end $$
Delimiter ;

-- Buscar
Delimiter $$
create procedure sp_buscarEmpleado(in empId int)
	begin
		select
			Empleados.empleadoId,
            Empleados.nombreEmpleado,
            Empleados.apellidoEmpleado,
            Empleados.sueldo,
            Empleados.horaEntrada,
            Empleados.horaSalida,
            Empleados.cargoId,
            Empleados.encargadoId
				from Empleados
                where empleadoId = empId;
	end $$
delimiter ;
             
-- Editar
Delimiter $$
create procedure sp_editarEmpleado(IN empId INT, IN nomEmp VARCHAR(30), IN apeEmp VARCHAR(30),IN suel DECIMAL(10,2),IN horEnt TIME, IN horSal TIME, IN carId INT)
	begin
		update Empleados
			set
				nombreEmpleado = nomEmp,
				apellidoEmpleado = apeEmp,
                sueldo = suel,
                horaEntrada = horEnt,
                horaSalida = horSal,
                cargoId = carId
					where empleadoId = empId;
	end $$
Delimiter ;

-- ============================================ CRUD Facturas =============================================
-- ========================================================================================================
delimiter $$
		create procedure sp_agregarFactura(IN fec date, in hor time,IN cliId int,IN empId int) 
	begin
		insert into Facturas(fecha,hora,clienteId,empleadoId)
			values(fec,hor,cliId,empId);
	end$$
delimiter ;

Delimiter $$
create procedure sp_ListarFactura()
	begin
		select
			Facturas.facturaId,
            Facturas.fecha,
            Facturas.hora,
            Facturas.clienteId,
            Facturas.empleadoId,
            Facturas.total
				from Facturas;
	End $$
Delimiter ;

Delimiter $$
create procedure sp_ListarFacturaComple()
	begin
		select F.facturaId, F.fecha, F.hora ,
        CONCAT("id: ",C.clienteId,"|", C.nombre," ", C.apellido) AS Cliente,
        CONCAT("Id: ",E.empleadoId, " | ",E.nombreEmpleado, " " , E.apellidoEmpleado) As empleado, F.total from Facturas F
        join Clientes C on F.clienteId =  C.clienteId
		join Empleados E on F.empleadoId = E.empleadoId;	
	End $$
Delimiter ;


-- Eliminar
Delimiter $$
create procedure sp_eliminarFactura(in facId int)
	begin 
		delete from Facturas
			where facturaId = facId;
	end $$
Delimiter ;

-- Buscar
Delimiter $$
create procedure sp_buscarFactura(in facId int)
	begin
		select F.facturaId, F.fecha, F.hora ,
        CONCAT("id: ",C.clienteId,"|", C.nombre," ", C.apellido) AS Cliente,
        CONCAT("Id: ",E.empleadoId, " | ",E.nombreEmpleado, " " , E.apellidoEmpleado) As empleado, F.total from Facturas F
        join Clientes C on F.clienteId =  C.clienteId
		join Empleados E on F.empleadoId = E.empleadoId
                where facturaId = facId;
	end $$
delimiter ;
             
-- Editar
Delimiter $$
create procedure sp_editarFactura(in facId int,in fec date, in hor time, in cliId INT, in empId INT)
	begin
		update Facturas
			set
				fecha = fec,
				hora = hor,
                clienteId = cliId,
                empleadoId = empId
					where facturaId = facId;
	end $$
Delimiter ;

-- ========================================== CRUD ticketSporte ===========================================
-- ========================================================================================================
delimiter $$
	create procedure sp_agregarTicketSoporte(IN des VARCHAR(250),IN cliId int,IN facId INT) 
		begin
			insert into TicketSoportes(descripcion,clienteId,facturaId)
				values(des,cliId,facId);
	end$$
delimiter ;

Delimiter $$
	create procedure sp_listarTicketSoporte()
		begin
			select * from TicketSoportes;
	end $$
Delimiter ;

Delimiter $$
	create procedure sp_listarTicketSoporteComplet()
		begin
			select TS.ticketSoporteId,TS.descripcion,TS.estatus,
            CONCAT("id: ",C.clienteId,"|", C.nombre," ", C.apellido) AS Cliente,
            CONCAT("id: ",F.facturaId,"|", F.fecha,"|", F.hora,"|", F.total) AS Factura, TS.clienteId from TicketSoportes TS
            join Clientes C on TS.clienteId = C.clienteId
            join Facturas F on TS.facturaId = F.facturaId;
	end $$
Delimiter ;



Delimiter $$
	create procedure sp_eliminarTicketSoporte(in ticSopId int)
		begin 
			delete from TicketSoportes
				where ticketSoporteId = ticSopId;
	end $$
Delimiter ;

Delimiter $$
	create procedure sp_buscarTicketSoporte(in ticSopId int)
			begin
			select TS.ticketSoporteId,TS.descripcion,TS.estatus,
            CONCAT("id: ",C.clienteId,"|", C.nombre," ", C.apellido) AS Cliente,
            CONCAT("id: ",F.facturaId,"|", F.fecha,"|", F.hora,"|", F.total) AS Factura, TS.clienteId from TicketSoportes TS
            join Clientes C on TS.clienteId = C.clienteId
            join Facturas F on TS.facturaId = F.facturaId
					where ticketSoporteId = ticSopId;
	end $$
delimiter ;

Delimiter $$
	create procedure sp_editarTicketSoporte(IN ticSopId INT,IN des VARCHAR(250), in est VARCHAR(30),IN cliId int,IN facId INT)
		begin
			update TicketSoportes
				set
					descripcion = des,
					estatus = est,
					clienteId = cliId,
					facturaId = facId
						where ticketSoporteId = ticSopId;
	end $$
Delimiter ;

-- ======================================== CRUD DetalleFacturas ==========================================
-- ========================================================================================================
delimiter $$
	create procedure SP_agregarDetFacturas(IN facId int(11),IN prodId int(11))
        begin
		insert into DetalleFactura(facturaId,productoId)
			values(facId, prodId);
	end$$
delimiter ;
    
delimiter $$
    create procedure sp_ListarDetFacturas()
	begin
		select
			DetalleFactur.detalleFacturaId,
			DetalleFactur.facturaId,
            DetalleFactur.productoId
				from DetalleFactur;
	end$$
delimiter ;

delimiter $$
	create procedure sp_EliminarDetFacturas(in detFacId int)
		begin
			delete from DetalleFactur
				where detalleFacturaId = detFacId;
	end $$
delimiter ;

delimiter $$
	create procedure sp_BuscarDetFactura(in detFacId int)
		begin
			select
				DetalleFactur.detalleFacturaId,
				DetalleFactur.facturaId,
				DetalleFactur.productoId
					from DetalleFactur
					where detalleFacturaId = detFacId;
	end$$
delimiter ;

delimiter $$
	create procedure sp_EditarDetFactura(IN detFacId int(11),IN facId int(11), IN  prodId int(11)) 
		begin
			update DetalleFactur
				set
				detalleFacturaId = detFacId,
				facturaId =fec,
				productoId = facId,
				empleadoId = prodId
						where detalleFacturaId = detFacId;
	end$$
delimiter ;

-- ========================================= Facturas Complement ==========================================
-- ========================================================================================================
delimiter $$
create procedure sp_asignarTotalFactura(in factId int, in totalFact decimal(10,2))
begin
	update facturas 
		set total = totalFact
			where facturaId = factId;
end $$
delimiter ;

delimiter $$
create trigger tg_totalFactura
after insert on detalleFactura
for each row
begin
	declare totalFact decimal(10,2);
     
	set totalFact = FN_totalFact(new.facturaId);
end$$
delimiter ;
-- =========================================== Stok Complement ============================================
-- ========================================================================================================
delimiter $$
create procedure sp_asignarDesStok(in proId int, in totalStok int)
begin
	update Productos 
		set cantidadStock = totalStok
			where productoId = proId;
end $$
delimiter ;
delimiter $$
create trigger tg_totalStok
after insert on detalleFactura
for each row
begin
	declare totalStok int;
     
	set totalStok = FN_desStok(new.productoId);
end$$
delimiter ;
-- ========================================= Empleado Complement ==========================================
-- ========================================================================================================
delimiter $$
	create procedure sp_AsignarEncargado(IN empId int,IN encId int) 
	
    begin
		update Empleados 
			set encargadoId = encId
					where empleadoId = empId;
	end$$
    delimiter ;

call sp_agregarCargo('a','b');
call sp_agregarCategoriaProducto('c','d');
call sp_AgregarDistribuidor('f','g','21','6534','reg');
call sp_agregarCompra('2000-10-02');
call sp_agregarClientes('NIT','nombre', 'apellido', 'telefono', 'direccion');
call sp_agregarProducto('h','i',13,23.45,32.45,21.35,1,1);
call sp_agregarPromocion(12.50,'descripcionPromocion','2052-11-02','2005-10-02',1);
call sp_agregarDetalleCompra(20,1,1);
call sp_agregarEmpleado('nombreEmpleado', 'apellidoEmpleado',23.5,12,5,1);
call sp_agregarFactura('2000-10-02',2,1,1);
call sp_agregarTicketSoporte('1',1,1);
call SP_agregarDetFacturas(1,1);
call sp_listarTicketSoporteComplet;
call sp_ListarProductoComple;
call sp_listarEmpleadoComp;
call sp_ListarPromocionComple;
call sp_ListarFacturaComple;
call sp_listarDetalleCompraComple;