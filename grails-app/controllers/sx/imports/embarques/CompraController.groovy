package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured("permitAll")
@Slf4j
class CompraController extends RestfulController {
    CompraDataService compraDataService

    static responseFormats = ['json']
    CompraController() {
        super(Compra)
    }

    @Override
    protected List listAllResources(Map params) {
        return compraDataService.findByPeriodo()
    }
}
