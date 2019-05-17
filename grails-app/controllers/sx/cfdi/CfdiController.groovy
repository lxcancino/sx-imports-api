package sx.cfdi

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured("permitAll")
@Slf4j
class CfdiController extends RestfulController<Cfdi> {
    static responseFormats = ['json']

    CfdiController() {
        super(Cfdi)
    }

    @Override
    protected List<Cfdi> listAllResources(Map params) {
        return super.listAllResources(params)
    }
}
