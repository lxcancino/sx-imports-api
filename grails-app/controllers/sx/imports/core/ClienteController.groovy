package sx.imports.core

import groovy.util.logging.Slf4j

import grails.compiler.GrailsCompileStatic
import grails.rest.*

import grails.plugin.springsecurity.annotation.Secured

@Slf4j
@GrailsCompileStatic
@Secured("permitAll")
class ClienteController extends RestfulController<Cliente> {
    static responseFormats = ['json']
    ClienteController() {
        super(Cliente)
    }

    @Override
    protected List<Cliente> listAllResources(Map params) {
        params.max = 100
        return Cliente.list(params)
    }
}
