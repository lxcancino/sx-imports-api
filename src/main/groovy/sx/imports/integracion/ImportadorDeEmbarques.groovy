package sx.imports.integracion

import groovy.util.logging.Slf4j
import org.apache.commons.lang3.exception.ExceptionUtils
import sx.imports.core.Producto
import sx.imports.core.Proveedor
import sx.imports.embarques.Aduana
import sx.imports.embarques.CompraDet
import sx.imports.embarques.Embarque
import sx.imports.embarques.EmbarqueDet
import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.withId

@Slf4j
class ImportadorDeEmbarques implements IntegracionDBSupport {


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
            e.*
            from embarque e 
            where date(e.fecha_embarque) between ? and ?
            order by e.id 
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
                Embarque embarque = Embarque.where {folio == row.id && empresa == tenant}.find()
                if(embarque == null) {
                    embarque = new Embarque(folio: row.id)
                }
                embarque.proveedor = Proveedor.where{swx == row.proveedor_id}.find()
                embarque.nombre = embarque.proveedor.nombre
                embarque.with {
                    bl = row.bl
                    fecha = row.fecha_embarque
                    aduana = Aduana.where{origen == row.aduana_id}.find()
                    ingresoAduana = row.ingreso_aduana
                    contenedores = row.contenedores
                    comentario = row.comentario
                    moneda = row.moneda
                    tipoDeCambio = row.tc
                    liberado = row.liberado
                }
                embarque.createUser = 'admin'
                embarque.updateUser = 'admin'
                // importarPartidas(compra, tenant )
                embarque.save failOnError: true, flush: true
                println 'Salvando: ' + embarque
            }
        }
    }

    def importarPartidasDeEmbarques(String tenant, String f1, String f2) {
        Periodo periodo = new Periodo(f1, f2)
        withId(tenant) {
            List<Embarque> rows = Embarque
                    .findAll("from Embarque e where date(e.fecha) between ? and ?",
                    [periodo.fechaInicial,periodo.fechaFinal])
            rows.each {e ->
                try {
                    importarPartidas(e, e.empresa)
                } catch (Exception ex) {
                    String m = ExceptionUtils.getRootCauseMessage(ex)
                    println m
                }


            }
        }
    }

    def importarPartidas(Embarque embarque, String from) {
        if(embarque.partidas.size() > 0) {
            embarque.partidas.clear()
        }
        println "Buscando partidas de embarque: ${embarque.folio} para ${from} "
        String sql = """
            select p.clave, p.descripcion, d.* 
            from embarque_det d
            join producto p on (p.id = d.producto_id) 
            where d.embarque_id = ? 
        """

        List<Map> rows = from == 'PAPER'?
                readFromPaper(sql, embarque.folio) :
                readFromImpap(sql, embarque.folio)
        println "Procesando partidas: ${rows.collect{it.compra_det_id}}"

        rows.each { row ->

            EmbarqueDet det = new EmbarqueDet()
            CompraDet compraDet = CompraDet.where{origen == row.compra_det_id }.find()
            if(compraDet == null) {
                throw new RuntimeException("No existe la CompraDet: ${row.compra_det_id} de ${from}")
            }
            det.origen = row.id
            det.compraDet = compraDet
            det.producto = compraDet.producto
            det.clave = compraDet.clave
            det.descripcion = compraDet.descripcion
            det.cantidad = row.cantidad
            det.contenedor = row.contenedor
            det.costoBruto = row.costo_bruto
            det.costoNeto = row.costo_neto
            det.costoUnitarioNeto = row.costo_unitario_neto
            det.gastosHonorarios = row.gastos_honorarios
            det.gastosPorPedimento = row.gastos_por_pedimento
            det.gramos = row.gramos
            det.importe = row.importe
            det.incrementables = row.incrementables
            det.kilosEstimados = row.kilos_estimados
            det.kilosNetos = row.kilos_netos
            det.pedimento = row.pedimento_id
            det.precio = row.precio
            det.precioDeVenta = row.precio_de_venta
            det.tarimas = row.tarimas
            det.tipoDeCambio = row.tc
            det.incrementablesUsd = row.incrementables_usd
            embarque.addToPartidas(det)

        }
        embarque.save failOnError: true, flush: true

    }
}
