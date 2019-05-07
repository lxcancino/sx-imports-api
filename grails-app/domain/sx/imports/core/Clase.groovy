package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includes = ["nombre"], includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['nombre'])
class Clase {

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
