package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class EmbarqueController extends RestfulController<Embarque> {
    static responseFormats = ['json']
    EmbarqueController() {
        super(Embarque)
    }

    @Override
    protected List<Embarque> listAllResources(Map params) {
        return super.listAllResources(params)
    }
}
