package sx.imports.integracion

import groovy.util.logging.Slf4j
import org.apache.commons.lang3.exception.ExceptionUtils
import sx.cfdi.Cfdi
import sx.cfdi.CfdiCancelado
import sx.imports.embarques.Distribucion
import sx.imports.embarques.DistribucionDet
import sx.imports.embarques.Embarque
import sx.imports.embarques.EmbarqueDet
import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.withId

@Slf4j
class ImportadorDeCfdis implements IntegracionDBSupport {


    def importar(String tenant, String fechaInicial, String fechaFinal) {
        Periodo periodo = new Periodo(fechaInicial, fechaFinal)
        importarEntidades(tenant, periodo)
    }


    def  importarEntidades(String origen, Periodo periodo) {
        String select = """
            select 
            d.*
            from cfdi d 
            where date(d.fecha) between ? and ?
            order by d.id 
        """
        if(origen == 'PAPER') {
            List<Map> rows = readFromPaper(select, periodo.fechaInicial, periodo.fechaFinal)
            importarCfdis(origen, rows)
        } else {
            List<Map> rows = readFromImpap(select, periodo.fechaInicial, periodo.fechaFinal)
            importarCfdis(origen, rows)
        }
    }

    def importarCfdis(String tenant, List<Map> rows) {
        rows.each { row ->
            withId(tenant) {
                Cfdi cfdi = Cfdi.where{uuid == row.uuid}.find()
                if(!cfdi) {
                    cfdi = new Cfdi()
                    cfdi.emisorRfc = row.rfc
                    cfdi.emisor = row.emisor
                    cfdi.tipo = row.tipo
                    cfdi.tipoDeCfdi = row.tipo_de_cfdi

                    cfdi.serie = row.serie
                    cfdi.folio = row.folio
                    cfdi.comentario = row.comentario
                    cfdi.fecha = row.fecha
                    cfdi.origen = row.origen.toString()
                    cfdi.receptor = row.receptor
                    cfdi.receptorRfc = row.receptor_rfc
                    cfdi.uuid = row.uuid
                    cfdi.xml = row.xml
                    cfdi.xmlName = row.xml_name
                    cfdi.swx = row.id
                    cfdi.total = row.total
                    cfdi.createUser = 'admin'
                    cfdi.updateUser = 'admin'
                    cfdi.save failOnError: true, flush: true
                }
            }
        }
    }

    def importarCancelaciones(String tenant) {
        String select  = "select * from cancelacion_de_cfdi"
        List<Map> rows = tenant == 'PAPER' ?
                readFromPaper(select) :
                readFromImpap(select)
        rows.each {
            Cfdi cfdi = Cfdi.where{empresa == tenant && swx == row.cfdi_id}.find()
            if(cfdi) {

                CfdiCancelado cfdiCancelado = CfdiCancelado
                        .where {empresa == tenant && folio == row.id}.find()
                if(cfdiCancelado == null) {
                    cfdiCancelado = new CfdiCancelado(folio: row.id)
                    cfdiCancelado.aka = row.aka
                    cfdiCancelado.origen = row.origen
                    cfdiCancelado.tipo = row.tipo
                    cfdi.cancelacion = cfdiCancelado
                    cfdi.save failOnError: true, flush: true
                }
            }
        }


    }

    /**
     * Actualia el origen de los CFDI con la nueva version del sistema
     *
     * @return
     */
    def actualiarOrigen() {
        List<Cfdi> rows = Cfdi.where{swx != null}.list()
        rows.each { cfdi ->
            switch (cfdi.tipo) {
                case 'VENTA':
                    return
                default:
                    throw new RuntimeException("No se puede evaluar el tipo: ${cfdi.tipo}" )
            }
        }
    }
}
