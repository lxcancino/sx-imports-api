package sx.sat

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import groovy.transform.EqualsAndHashCode


@Resource(readOnly = false, formats = ['json'], uri = "/api/sat/bancos")
@GrailsCompileStatic
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@EqualsAndHashCode(includes='clave')
class BancoSat {

    String clave

    String nombreCorto

    String razonSocial

    String rfc

    String swx

    static constraints = {
        clave nullable:false, unique:true
        razonSocial nullable: true
        rfc nullable: true
        swx nullable: true
    }

    String toString(){
        return "$clave $nombreCorto"
    }

}
