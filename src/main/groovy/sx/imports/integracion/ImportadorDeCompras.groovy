package sx.imports.integracion

import groovy.util.logging.Slf4j

import sx.imports.core.Producto
import sx.imports.core.Proveedor
import sx.imports.embarques.Compra
import sx.imports.embarques.CompraDet

import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.*

@Slf4j
class ImportadorDeCompras implements IntegracionDBSupport {


    def importar(String tenant, String fechaInicial, String fechaFinal) {
        Periodo periodo = new Periodo(fechaInicial, fechaFinal)
        importar(tenant, periodo)
    }

    def importar(origen = 'PAPER', Periodo periodo) {
        importarCompras(origen, periodo)
    }

    def  importarCompras(String origen, Periodo periodo) {
        String select = """
            select 
            c.*
            from compra c 
            where date(c.fecha) between ? and ?
            order by c.id 
        """
        if(origen == 'PAPER') {
            List<Map> rows = readFromPaper(select, periodo.fechaInicial, periodo.fechaFinal)
            importar(origen, rows)
        } else {
            List<Map> rows = readFromImpap(select, periodo.fechaInicial, periodo.fechaFinal)
            importar(origen, rows)
        }
    }

    def importar(String tenant, List<Map> rows) {
        rows.each { row ->
            withId(tenant) {
                Compra compra = Compra.where {swx == row.id && empresa == tenant}.find()
                if(compra == null) {
                    compra = new Compra(folio: row.id, swx: row.id)
                }
                compra.proveedor = Proveedor.where{swx == row.proveedor_id}.find()
                compra.entrega = row.entrega
                compra.depuracion = row.depuracion
                compra.papelFolio = row.folio as Long
                compra.papelOrigen = row.origen
                compra.fecha = row.fecha
                compra.comentario = row.comentario
                compra.moneda = row.moneda
                compra.tipoDeCambio = row.tc
                compra.importe = row.importe
                compra.descuentos = row.descuentos
                compra.subtotal = row.subtotal
                compra.impuestos = row.impuestos
                compra.total = row.total
                compra.updateNombre()

                compra.createUser = 'admin'
                compra.updateUser = 'admin'
                importarPartidas(compra, tenant )
                compra.save failOnError: true, flush: true
            }

        }

    }

    def importarPartidas(Compra compra, String from) {
        println "Buscando partidas de compra: ${compra.swx} para ${from} "
        String sql = """
            select p.clave, p.descripcion, d.* 
            from compra_det d
            join producto p on (p.id = d.producto_id) 
            where d.compra_id = ? 
        """

        List<Map> rows = from == 'PAPER'?
                readFromPaper(sql, compra.swx) :
                readFromImpap(sql, compra.swx)

        //  println "Partidas para ${compra.swx}: ${rows.size()}"

        rows.each { row ->
            CompraDet det = new CompraDet()
            Producto producto = Producto.where{clave == row.clave}.find()
            if(producto == null)  {
                // throw new RuntimeException('No existe el producto: ' + row.producto_id)
                println 'No existe el producto para: ' + row
            } else {
                println producto
            }
            det.producto = producto
            det.origen = row.id
            det.clave = det.producto.clave
            det.descripcion = det.producto.descripcion
            det.descuento = row.descuento
            det.importe = row.importe
            det.descuento = row.descuento
            det.importeDescuento = row.importe_descuento
            det.precio = row.precio
            det.solicitado = row.solicitado
            compra.addToPartidas(det)
        }

    }
}
