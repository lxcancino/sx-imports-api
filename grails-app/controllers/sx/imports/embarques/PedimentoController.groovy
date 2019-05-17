package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class PedimentoController extends RestfulController<Pedimento> {
    static responseFormats = ['json']

    PedimentoController() {
        super(Pedimento)
    }

    @Override
    protected List<Pedimento> listAllResources(Map params) {
        return super.listAllResources(params)
    }
}
