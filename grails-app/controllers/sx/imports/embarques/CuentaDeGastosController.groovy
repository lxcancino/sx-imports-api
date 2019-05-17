package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class CuentaDeGastosController extends RestfulController<CuentaDeGastos> {
    static responseFormats = ['json']
    CuentaDeGastosController() {
        super(CuentaDeGastos)
    }

    @Override
    protected List<CuentaDeGastos> listAllResources(Map params) {
        params.max = 100
        return super.listAllResources(params)
    }
}
