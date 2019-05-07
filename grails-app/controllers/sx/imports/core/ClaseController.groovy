package sx.imports.core


import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

@GrailsCompileStatic
@Secured("permitAll")
class ClaseController extends RestfulController<Clase> {

    static responseFormats = ['json']

    ClaseController() {
        super(Clase)
    }

    @Override
    protected List<Clase> listAllResources(Map params) {
        params.max = 500
        return super.listAllResources(params)
    }
}
