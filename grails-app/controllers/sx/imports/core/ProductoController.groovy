package sx.imports.core

import groovy.util.logging.Slf4j

import grails.compiler.GrailsCompileStatic
import grails.rest.RestfulController

import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured("permitAll")
@Slf4j
class ProductoController extends RestfulController {
    static responseFormats = ['json']
    ProductoController() {
        super(Producto)
    }

    @Override
    protected List listAllResources(Map params) {
        params.max = 5000
        return Producto.list(params)
    }
}
