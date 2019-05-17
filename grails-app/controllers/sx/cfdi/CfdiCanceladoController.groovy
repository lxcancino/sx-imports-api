package sx.cfdi

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured("permitAll")
@Slf4j
class CfdiCanceladoController extends RestfulController<CfdiCancelado> {
    static responseFormats = ['json']
    CfdiCanceladoController() {
        super(CfdiCancelado)
    }

    @Override
    protected List<CfdiCancelado> listAllResources(Map params) {
        return super.listAllResources(params)
    }
}
