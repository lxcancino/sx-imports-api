package sx.imports.cxp

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class ComprobanteFiscalController extends RestfulController<ComprobanteFiscal> {
    static responseFormats = ['json']
    ComprobanteFiscalController() {
        super(ComprobanteFiscal)
    }

    @Override
    protected List<ComprobanteFiscal> listAllResources(Map params) {
        return super.listAllResources(params)
    }
}
