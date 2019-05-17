package sx.imports.cxp

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, uuid")
@ToString(includeNames = true, includes = "empresa, folio, nombre, bl")
class ComprobanteFiscal implements MultiTenant<ComprobanteFiscal>{

    String id

    String empresa

    String versionCfdi

    byte[] cfdi

    String cfdiFileName

    String uuid

    String serie

    String folio

    Date fecha

    String emisorRfc

    String receptorRfc

    BigDecimal total

    byte[] acuse

    String acuseEstado

    String acuseCodigoEstatus

    String comentario

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    Long swx

    static constraints = {
        empresa maxSize: 20
        uuid unique:true
        serie nullable:true, maxSize:20
        folio nullable:true,maxSize:20
        cfdiFileName nullable:true, maxSize:200
        cfdi maxSize:(1024 * 512)  // 50kb para almacenar el xml
        acuse nullable:true, maxSize:(1024*256)
        acuseEstado nullable:true, maxSize:100
        acuseCodigoEstatus nullable:true, maxSize:100
        versionCfdi nullable:true, maxSize:10
        comentario nullable: true
        swx nullable: true
    }

    static mapping ={
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type:'date'
    }
}
