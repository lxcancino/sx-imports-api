package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, folio, empresa")
@ToString(includeNames = true, includes = "folio, embarque")
class Distribucion implements  MultiTenant<Compra> {

    String id

    String empresa

    Long folio

    Embarque embarque

    String nombre

    Date fecha

    int contenedores

    String comentario

    List<DistribucionDet> partidas = []

    static hasMany = [partidas:DistribucionDet]

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
        comentario nullable:true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        partidas cascade: "all-delete-orphan"
        fecha type: 'date', index: 'DISTRIBUCION_IDX_FECHA'
    }

}
