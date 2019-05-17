package sx.imports.embarques

import grails.compiler.GrailsCompileStatic

import groovy.transform.EqualsAndHashCode

import sx.imports.core.Direccion


@GrailsCompileStatic
@EqualsAndHashCode(includes='nombre')
class Aduana {

    String nombre

    Direccion direccion

    /**
     * Id en la version anterior del sistema
     *
     */
    Long origen

    static embedded =['direccion']

    static constraints = {
        nombre unique:true
        origen nullable: true
    }

    String toString(){
        return nombre
    }
}
