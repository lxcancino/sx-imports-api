package sx.imports.core

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

import groovy.util.logging.Slf4j

@Slf4j
@GrailsCompileStatic
@Secured("permitAll")
class ProveedorProductoController extends RestfulController<ProveedorProducto> {
    static responseFormats = ['json']
    ProveedorProductoController() {
        super(ProveedorProducto)
    }

    @Override
    protected List<ProveedorProducto> listAllResources(Map params) {
        Proveedor p = Proveedor.get(this.params.getLong('proveedorId'))
        return ProveedorProducto.where{proveedor == p}.list()
    }
}
