package sx.cfdi

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, folio")
@ToString(includeNames = true, includes = "empresa, folio, fecha ")
class CfdiCancelado implements  MultiTenant<CfdiCancelado> {

    String id

    String empresa

    Long folio

    Date fecha

    String comentario

    byte[] aka

    String statusSat

    String origen

    String tipo
    //byte[] message

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    static belongsTo = [cfdi: Cfdi]

    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
        comentario nullable:true
        aka maxSize:(1024 * 512)  // 50kb para almacenar el xml
        origen nullable: true
        tipo nullable: true
        statusSat nullable: true
    }
    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type: 'date', index: 'CFDI_CANCELADO_IDX_FECHA'
    }
}
