package sx.imports.core

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includes = "clave, descripcion", includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['clave'])
class ProveedorProducto {

    Producto producto

    String clave

    String descripcion

    String codigo

    BigDecimal gramos = 0.0

    BigDecimal costoUnitario = 0.0

    static belongsTo = [proveedor:Proveedor]

    static constraints = {
        codigo nullable:true
    }
}
