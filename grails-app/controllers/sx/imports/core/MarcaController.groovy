package sx.imports.core

import grails.rest.*
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured("permitAll")
class MarcaController extends RestfulController<Marca> {

    static responseFormats = ['json']

    MarcaController() {
        super(Marca)
    }

    @Override
    protected List<Marca> listAllResources(Map params) {
        params.max = 500
        return super.listAllResources(params)
    }
}
