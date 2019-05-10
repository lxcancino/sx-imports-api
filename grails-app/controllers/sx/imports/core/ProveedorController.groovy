package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

import groovy.util.logging.Slf4j

@Slf4j
@GrailsCompileStatic
@Secured("permitAll")
class ProveedorController extends RestfulController<Proveedor> {
    static responseFormats = ['json']

    ProveedorController() {
        super(Proveedor)
    }

    @Override
    protected List<Proveedor> listAllResources(Map params) {
        params.max = 1000
        return super.listAllResources(params)
    }
}
