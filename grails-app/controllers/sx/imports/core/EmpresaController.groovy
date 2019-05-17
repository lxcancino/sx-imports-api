package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

import groovy.util.logging.Slf4j

@Slf4j
@GrailsCompileStatic
@Secured("permitAll")
class EmpresaController extends RestfulController<Empresa> {
    static responseFormats = ['json']
    EmpresaController() {
        super(Empresa)
    }

    @Override
    @Secured("ADMIN_ROLE")
    protected Empresa saveResource(Empresa resource) {
        return super.saveResource(resource)
    }

    @Override
    @Secured("ADMIN_ROLE")
    protected Empresa updateResource(Empresa resource) {
        return super.updateResource(resource)
    }
}
