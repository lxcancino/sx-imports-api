package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

@GrailsCompileStatic
@Secured("permitAll")
class LineaController extends RestfulController<Linea> {

    static responseFormats = ['json']

    LineaController() {
        super(Linea)
    }

    @Override
    protected List<Linea> listAllResources(Map params) {
        params.max = 500
        return super.listAllResources(params)
    }
}
