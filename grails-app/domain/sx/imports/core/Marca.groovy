package sx.imports.core

import grails.compiler.GrailsCompileStatic

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includes = ["nombre"], includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['nombre'])
class Marca {

	String nombre

    String descripcion

    Boolean activa

    String sw2 

    Date dateCreated

    Date lastUpdated

    static constraints = {
        nombre unique:true
    }
}
