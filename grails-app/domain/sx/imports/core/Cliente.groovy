package sx.imports.core

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includes = "nombre, rfc", includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['nombre', 'rfc'])
class Cliente {

    String nombre

    String rfc

    String email

    String formaDePago

    String cuentaDePago

    Direccion direccion

    String subCuentaOperativa

    Date dateCreated

    Date lastUpdated

    String createUser

    String updateUser


    static embedded = ['direccion']

    static constraints = {
        nombre unique:true
        email nullable:true
        formaDePago nullable:true
        cuentaDePago nullable:true, maxSize:4
        direccion nullable:true
        subCuentaOperativa nullable:true,maxSize:4
        createUser nullable: true
        updateUser nullable: true
    }
}
