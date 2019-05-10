package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import sx.imports.core.Direccion

@Resource(readOnly = false, formats = ['json'], uri = "/api/aduanas")
@GrailsCompileStatic
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@EqualsAndHashCode(includes='nombre')
class Aduana {

    String nombre

    Direccion direccion

    static embedded =['direccion']

    static constraints = {
        nombre(blank:false,unique:true,maxSize:50)
    }

    String toString(){
        return nombre;
    }
}
