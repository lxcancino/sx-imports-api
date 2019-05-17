package sx.imports.integracion

import groovy.sql.Sql
import sx.imports.core.Cliente
import sx.imports.core.Direccion
import sx.imports.core.Empresa
import sx.imports.core.Producto
import sx.imports.core.Proveedor
import sx.imports.core.ProveedorProducto
import sx.imports.embarques.Aduana
import sx.sat.BancoSat

import java.sql.SQLException

import groovy.util.logging.Slf4j

import sx.imports.core.Clase
import sx.imports.core.Linea
import sx.imports.core.Marca

@Slf4j
class ImportadorDeCatalogos implements IntegracionDBSupport {

    ImportadorDeCatalogos(String dbUrl) {}

    def importar() {
        importarLineas()
        .importarMarcas()
        .importarClases()
    }

    def importarLineas() {
        List<Map> rows = readFromImpap("select * from linea")
        rows.each { row ->
            new Linea(
                nombre: row.nombre,
                descripcion: row.nombre,
                activa: true,
                sw2: row.nombre
            ).save failOnError: true
        }
        return this
    }

    def importarMarcas() {
        List<Map> rows = readFromImpap("select * from marca")
        rows.each { row ->
            new Marca(
                    nombre: row.nombre,
                    descripcion: row.nombre,
                    activa: true,
                    sw2: row.nombre
            ).save failOnError: true
        }
        return this
    }

    def importarClases() {
        List<Map> rows = readFromImpap("select * from clase")
        rows.each { row ->
            new Clase(
                    nombre: row.nombre,
                    descripcion: row.nombre,
                    activa: true,
                    sw2: row.nombre
            ).save failOnError: true
        }
        return this
    }

    def importarProductos() {
        String sql = """
            select p.*, 
            p.precio_credito as precioCredito,
            p.precio_contado as precioContado,
            l.nombre as nlinea, 
            m.nombre as nmarca, 
            c.nombre as nclase ,
            u.clave as unidad,
            u.unidad_sat as unidadSat,
            u.clave_Unidad_Sat as claveUnidadSat,
            ps.clave_Prod_serv as claveProdSat
            from producto p
            left join linea l on(l.id = p.linea_id)
            left join marca m on(m.id = p.marca_id)
            left join clase c on(c.id = p.clase_id)
            left join unidad u on(u.id = p.unidad_id)
            left join producto_sat ps on(ps.id = p.producto_sat_id)
        """
        List<Map> rows = readFromPaper(sql)
        Linea l = Linea.where{nombre == 'SIN ASIGNAR'}.find()
        Marca m = Marca.where{nombre == 'SIN ASIGNAR'}.find()
        Clase c = Clase.where{nombre == 'SIN ASIGNAR'}.find()
        rows.each { row ->

            // Producto producto = Producto.findOrCreateWhere(swx: row.id.toString())
            Producto producto = Producto.where{clave  == row.clave}.find()
            if(producto == null) {
                producto = new Producto(swx: row.id.toString())
            }
            producto.clave = row.clave
            producto.properties = row
            Linea linea = Linea.where{nombre == row.nlinea}.find()
            Marca marca = Marca.where{nombre == row.nmarca}.find()
            Clase clase = Clase.where{nombre == row.nclase}.find()
            producto.linea = linea ?: l
            producto.clase = clase ?: c
            producto.marca = marca ?: m
            producto.createUser = 'admin'
            producto.updateUser = 'admin'
            producto.claveProdSat = producto.claveProdSat?: 'ND'
            producto.unidadSat = producto.unidadSat ?: 'ND'
            producto.claveUnidadSat = producto.claveUnidadSat ?: 'ND'

            producto.validate()
            if(producto.hasErrors()) {
                println("Tienen errores: ${producto.errors} ${row.clave}")
            } else {
                producto.save flush: true
            }

        }
        return this
    }

    def importarClientes() {
        String sql = """
            select *
            from cliente c
        """
        List<Map> rows = readFromPaper(sql)
        rows.each { row ->
            println 'Row: ' + row
            Cliente cliente = Cliente.findOrCreateWhere(rfc: row.rfc, nombre: row.nombre)
            cliente.formaDePago = row.forma_de_pago
            cliente.cuentaDePago = row.cuenta_de_pago
            cliente.subCuentaOperativa = row.sub_cuenta_operativa
            cliente.email = row.email1

            Direccion direccion = new Direccion()
            direccion.calle = row.direccion_calle
            direccion.numeroInterior = row.direccion_numero_interior
            direccion.numeroExterior = row.direccion_numero_exterior
            direccion.colonia = row.direccion_colonia
            direccion.municipio = row.direccion_municipio
            direccion.estado = row.direccion_estado
            direccion.pais = row.direccion_pais
            direccion.codigoPostal = row.direccion_codigo_postal
            cliente.direccion = direccion
            cliente.save failOnError: true, flush: true

        }
    }

    def impotarBancosSat() {
        String sql = "select * from banco_sat"
        List<Map> rows = readFromImpap(sql)
        rows.each { row ->
            BancoSat b = BancoSat.findOrCreateWhere(
                    clave: row.clave, nombreCorto: row.nombre_corto, razonSocial: row.razon_social)
            b.swx = row.id.toString()
            b.save flush: true
        }
    }

    def importarProveedores() {
        String sql = """
            select * from proveedor
        """
        List<Map> rows = readFromPaper(sql)
        rows.each { row ->
            Proveedor p = Proveedor.findOrCreateWhere(swx: row.id)
            p.rfc = row.rfc
            p.nombre = row.nombre
            p.subCuentaOperativa = row.sub_cuenta_operativa
            p.email = row.correo_electronico
            p.www = row.www
            p.tipo = row.tipo
            p.plazo = row.plazo
            p.lineaDeCredito = row.linea_de_credito
            p.agenciaAduanal = row.agencia_aduanal
            p.tipoDeCosteo = row.tipo_de_costeo
            p.paisDeOrigen = row.pais_de_origen
            p.cuentaBancaria = row.cuenta_bancaria
            if(row.banco_destino_id) {
                BancoSat bs = BancoSat.get(row.banco_destino_id.toLong())
                p.bancoDestino = bs
            }
            p.createUser = 'admin'
            p.updateUser = 'admin'

            Direccion direccion = new Direccion()
            direccion.calle = row.direccion_calle
            direccion.numeroInterior = row.direccion_numero_interior
            direccion.numeroExterior = row.direccion_numero_exterior
            direccion.colonia = row.direccion_colonia
            direccion.municipio = row.direccion_municipio
            direccion.estado = row.direccion_estado
            direccion.pais = row.direccion_pais
            direccion.codigoPostal = row.direccion_codigo_postal

            p.direccion = direccion

            // Buscar productos
            if(p.id) {
                p.productos.clear()
            }
            /*
            String s2 = "select * from proveedor_producto  where proveedor_id = ?"
            List<Map> prods = readFromPaper(s2, [row.id])
            prods.each {
                ProveedorProducto pp = ProveedorProducto(

                )
                p.addToProductos(pp)
            }
            */
            p.save failOnError: true, flush: true
        }

    }

    def importarAduanas() {
        String sql = "select * from aduana"
        List<Map> rows = readFromPaper(sql)
        rows.each { row ->
            Aduana a = Aduana.findOrCreateWhere(nombre: row.nombre)
            Direccion direccion = new Direccion()
            direccion.calle = row.direccion_calle
            direccion.numeroInterior = row.direccion_numero_interior
            direccion.numeroExterior = row.direccion_numero_exterior
            direccion.colonia = row.direccion_colonia
            direccion.municipio = row.direccion_municipio
            direccion.estado = row.direccion_estado
            direccion.pais = row.direccion_pais
            direccion.codigoPostal = row.direccion_codigo_postal
            a.direccion = direccion
            a.origen = row.id
            a.save failOnError: true, flush: true
        }
    }

    def importarEmpresas() {
        List<Map> rows = []
        rows.addAll(readFromPaper("select * from empresa"))
        rows.addAll(readFromImpap("select * from empresa"))
        rows.each { row ->
            String cve = row.nombre.startsWith('PAPER') ? 'PAPER': 'IMPAP'
            Empresa empresa = Empresa.findOrCreateWhere(clave: cve, nombre: row.nombre)
            empresa.rfc = row.rfc
            empresa.regimen = row.regimen
            empresa.certificadoDigital = row.certificado_digital
            empresa.certificadoDigitalPfx = row.certificado_digital_pfx
            empresa.llavePrivada = row.llave_privada
            empresa.numeroDeCertificado = row.numero_de_certificado
            empresa.passwordPfx = row.password_pfx
            empresa.regimenClaveSat = row.regimen_clave_sat
            empresa.versionDeCfdi = row.version_de_cfdi

            Direccion direccion = new Direccion()
            direccion.calle = row.direccion_calle
            direccion.numeroInterior = row.direccion_numero_interior
            direccion.numeroExterior = row.direccion_numero_exterior
            direccion.colonia = row.direccion_colonia
            direccion.municipio = row.direccion_municipio
            direccion.estado = row.direccion_estado
            direccion.pais = row.direccion_pais
            direccion.codigoPostal = row.direccion_codigo_postal
            empresa.direccion = direccion

            empresa.save failOnError: true, flush: true
        }


    }
}
