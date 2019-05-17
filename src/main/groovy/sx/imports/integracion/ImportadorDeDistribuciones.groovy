package sx.imports.integracion

import groovy.util.logging.Slf4j
import org.apache.commons.lang3.exception.ExceptionUtils
import sx.imports.core.Proveedor
import sx.imports.embarques.Distribucion
import sx.imports.embarques.DistribucionDet
import sx.imports.embarques.Embarque
import sx.imports.embarques.EmbarqueDet
import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.withId

@Slf4j
class ImportadorDeDistribuciones implements IntegracionDBSupport {


    def importar(String tenant, String fechaInicial, String fechaFinal) {
        Periodo periodo = new Periodo(fechaInicial, fechaFinal)
        importar(tenant, periodo)
    }

    def importar(origen = 'PAPER', Periodo periodo) {
        importarEntidades(origen, periodo)
    }

    def  importarEntidades(String origen, Periodo periodo) {
        String select = """
            select 
            d.*
            from distribucion d 
            where date(d.fecha) between ? and ?
            order by d.id 
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
                Distribucion distribucion = Distribucion.where {folio == row.id}.find()
                if(distribucion == null) {
                    distribucion = new Distribucion(folio: row.id)
                }
                Embarque e = Embarque.where{folio == row.embarque_id}.find()
                distribucion.embarque = e
                distribucion.nombre = e.nombre
                distribucion.fecha = row.fecha
                distribucion.comentario = row.comentario
                distribucion.contenedores = row.contenedores

                distribucion.createUser = 'admin'
                distribucion.updateUser = 'admin'

                distribucion.save failOnError: true, flush: true
                println 'Salvando: ' + distribucion
            }
        }
    }

    def importarPartidas(String tenant, String f1, String f2) {
        Periodo periodo = new Periodo(f1, f2)
        withId(tenant) {
            List<Distribucion> rows = Distribucion
                    .findAll("from Distribucion d where date(d.fecha) between ? and ?",
                    [periodo.fechaInicial,periodo.fechaFinal])
            rows.each {d ->
                try {
                    importarPartidas(d, d.empresa)
                } catch (Exception ex) {
                    String m = ExceptionUtils.getRootCauseMessage(ex)
                    println m
                }
            }
        }
    }

    def importarPartidas(Distribucion distribucion, String from) {
        if(distribucion.partidas.size() > 0) {
            distribucion.partidas.clear()
        }
        println "Buscando partidas de distribucion: ${distribucion.folio} para ${from} "
        String sql = """
            select
            d.* 
            from distribucion_det d
            where d.distribucion_id = ? 
        """

        List<Map> rows = from == 'PAPER'?
                readFromPaper(sql, distribucion.folio) :
                readFromImpap(sql, distribucion.folio)

        rows.each { row ->
            DistribucionDet det = new DistribucionDet()
            EmbarqueDet ed = EmbarqueDet.where{ origen == row.embarque_det_id}.find()
            det.with {
                embarqueDet = ed
                producto = ed.producto
                clave = ed.clave
                descripcion = ed.descripcion
                sucursal = row.sucursal
                contenedor = row.contenedor
                tarimas = row.tarimas
                cantidad = row.cantidad
                cantidadPorTarima = row.cantidad_por_tarima
                kilosNetos = row.kilos_netos
                comentarios = row.comentarios
                instrucciones = row.instrucciones
                programacionDeEntrega = row.programacion_de_entrega
                fechaDeEntrada = row.fecha_de_entrada
            }
            distribucion.addToPartidas(det)
        }
        distribucion.save failOnError: true, flush: true

    }
}
