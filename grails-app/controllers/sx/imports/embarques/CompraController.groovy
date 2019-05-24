package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j
import sx.utils.LogUser

@GrailsCompileStatic
@Secured("permitAll")
@Slf4j
class CompraController extends RestfulController<Compra> {
    CompraDataService compraDataService

    static responseFormats = ['json']
    CompraController() {
        super(Compra)
    }

    @Override
    protected List<Compra> listAllResources(Map params) {
        return compraDataService.findByPeriodo()
    }

    @Override
    protected Compra saveResource(Compra compra) {
        return compraDataService.save(compra)
    }

    @Override
    protected Compra createResource() {
        Compra compra = compraDataService.buildCompra()
        bindData(compra, getObjectToBind())
        compra.updateNombre()
        return compra
    }
}
