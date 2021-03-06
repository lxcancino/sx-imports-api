package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import groovy.util.logging.Slf4j

@Slf4j
@GrailsCompileStatic
@Secured("permitAll")
class AduanaController extends RestfulController<Aduana> {
    static responseFormats = ['json']
    AduanaController() {
        super(Aduana)
    }

    @Override
    protected List<Aduana> listAllResources(Map params) {
        params.max = 100
        return super.listAllResources(params)
    }
}
