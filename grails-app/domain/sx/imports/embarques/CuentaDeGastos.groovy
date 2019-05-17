package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import sx.imports.core.Proveedor

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, folio")
@ToString(includeFields = true, includes = ['empresa', 'folio', 'nombre'])
class CuentaDeGastos implements  MultiTenant<Pedimento> {

    String id

    Long folio

    String empresa

    Date fecha

    Proveedor proveedor

    String nombre

    Embarque embarque

    String comentario

    String referencia

    BigDecimal importe = 0.0

    BigDecimal impuestos = 0.0

    BigDecimal total = 0.0

    // static hasMany = [facturas:CuentaPorPagar]

    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
        comentario nullable:true
        referencia nullable:true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type: 'date', index: 'CUENTAGASTOS_IDX_FECHA'
    }


}
