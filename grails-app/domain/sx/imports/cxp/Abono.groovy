package sx.imports.cxp

import grails.gorm.MultiTenant
import sx.imports.core.Proveedor

abstract class Abono implements MultiTenant<Abono> {

    String id

    Long folio

    String empresa

    Date fecha

    Proveedor proveedor

    String nombre

    String moneda = 'MXN'

    BigDecimal tipoDeCambio = 1.0

    BigDecimal importe = 0.0

    BigDecimal impuestos = 0.0

    BigDecimal total = 0.0

    BigDecimal disponible=0

    BigDecimal impuestoTasa=0

    String comentario

    List<AplicacionDeAbono> aplicaciones

    BigDecimal aplicado

    static hasMany = [aplicaciones:AplicacionDeAbono]

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type: 'date', index: 'ABONO_IDX_FECHA'
        // requisitado formula:'(select ifnull(sum(x.total),0) from requisicion_det x where x.factura_id=id)'
        pagos formula:'(select ifnull(sum(x.total),0) from aplicacion_de_abono x where x.factura_id = id)'
    }
}
