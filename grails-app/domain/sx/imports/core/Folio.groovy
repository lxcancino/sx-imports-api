package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includes = "empresa, entidad, serie, folio", includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['id', 'empresa', 'entidad', 'serie', 'folio'])
class Folio implements  MultiTenant<Folio> {

    String id

    String empresa

    String entidad

    String serie

    String folio

    static constraints = {
        entidad maxSize:30
        serie size:1..20
        folio unique: ['empresa','entidad','serie']
    }

    static  mapping={
        id generator:'uuid'
        tenantId name: 'empresa'
    }

}
