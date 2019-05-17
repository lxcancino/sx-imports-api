package sx.imports.integracion

import groovy.util.logging.Slf4j
import sx.imports.core.Producto
import sx.imports.core.Proveedor
import sx.imports.embarques.Pedimento
import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.withId

@Slf4j
class ImportadorDePedimentos implements IntegracionDBSupport {


    def importar(String tenant, String fechaInicial, String fechaFinal) {
        Periodo periodo = new Periodo(fechaInicial, fechaFinal)
        importar(tenant, periodo)
    }

    def importar(origen = 'PAPER', Periodo periodo) {
        importarCompras(origen, periodo)
    }

    def importarCompras(String origen, Periodo periodo) {
        String select = """
            select 
            e.*, p.nombre as paisDeOrigen
            from pedimento e 
            left join pais_de_origen p on (e.pais_de_origen_id = p.id)
            where date(e.fecha) between ? and ?
            order by e.id 
        """
        if (origen == 'PAPER') {
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
                Pedimento pedimento = Pedimento.where { folio == row.id && empresa == tenant }.find()
                if (pedimento == null) {
                    pedimento = new Pedimento(folio: row.id)
                }
                pedimento.proveedor = Proveedor.where { swx == row.proveedor_id }.find()
                pedimento.nombre = pedimento.proveedor.nombre
                pedimento.pedimento = row.pedimento
                pedimento.with {
                    fecha = row.fecha
                    dta = row.dta
                    comentario = row.comentario
                    tipoDeCambio = row.tipo_de_cambio
                    prevalidacion = row.prevalidacion
                    arancel = row.arancel
                    impuestoTasa = row.impuesto_tasa
                    impuesto = row.impuesto
                    incrementables = row.incrementables
                    referenciacg = row.referenciacg
                    agenteAduanal = row.agente_aduanal
                    contraPrestacion = row.contra_prestacion
                    paisDeOrigen = row.paisDeOrigen
                }
                pedimento.createUser = 'admin'
                pedimento.updateUser = 'admin'

                pedimento.save failOnError: true, flush: true

            }
        }
    }

}