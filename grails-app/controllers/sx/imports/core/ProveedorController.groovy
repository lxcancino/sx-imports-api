package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import groovy.transform.CompileDynamic
import groovy.util.logging.Slf4j

@Slf4j
@GrailsCompileStatic
@Secured("permitAll")
class ProveedorController extends RestfulController<Proveedor> {
    static responseFormats = ['json']

    ProveedorController() {
        super(Proveedor)
    }

    @CompileDynamic
    protected List<Proveedor> listAllResources(Map params) {


        params.max = 1000
        params.sort = 'nombre'
        params.order = 'asc'
        println params
        log.info('List: {}', params)

        def query = Proveedor.where{}
        if(params.tipo) {
            query = query.where{tipo == params.tipo}
        }
        if(params.term) {
            def search = "%${params.term}%"
            query = query.where{nombre =~ search}
        }
        return query.list(params)
    }
}
