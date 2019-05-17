package sx.cfdi

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, serie, folio")
@ToString(includeNames = true, includes = "empresa, serie, folio, tipo ")
class Cfdi implements  MultiTenant<Cfdi> {

    String id

    String empresa

    String serie

    String tipo

    String tipoDeCfdi

    Date fecha

    String folio

    String uuid

    String emisor

    String emisorRfc

    String receptor

    String receptorRfc

    BigDecimal total

    String comentario

    String origen

    String xmlName

    byte[] xml

    String url

    String versionCfdi = '3.3'


    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    CfdiCancelado cancelacion

    static hasOne = [cancelacion: CfdiCancelado]

    Long swx

    static constraints = {
        empresa maxSize: 20
        serie maxSize:15
        tipo inList:['FACTURA','NOTA_CREDITO','NOTA_CARGO','PAGO','FAC','CRE','CAR']
        fecha nullable:false
        folio maxSize:20
        uuid nullable:true
        emisorRfc maxSize:13
        receptorRfc maxSize:13
        xmlName nullable:true, maxSize:200
        xml maxSize:(1024 * 512)  // 50kb para almacenar el xml
        tipoDeCfdi inList:['I','E','N', 'P']
        comentario nullable:true
        url nullable:true, url:true
        swx nullable: true
        versionCfdi inList: ['3.2','3.3']
        cancelacion nullable: true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type: 'date', index: 'CFDI_IDX_FECHA'
    }
}
