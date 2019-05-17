package sx.imports.cxp

import grails.gorm.MultiTenant

class AplicacionDeAbono implements MultiTenant<AplicacionDeAbono> {

    String id

    String empresa

    Long swx

    Date fecha

    BigDecimal importe

    BigDecimal impuesto

    BigDecimal total

    BigDecimal impuestoTasa

    String comentario

    Date dateCreated
    Date lastUpdated

    static belongsTo = [abono:Abono,factura:CuentaPorPagar]

    static constraints = {
        comentario nullable:true
        swx nullable: true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type: 'date', index: 'ABONO_APLIC_IDX_FECHA'
    }

}
