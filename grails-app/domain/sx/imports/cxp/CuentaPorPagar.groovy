package sx.imports.cxp

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import sx.imports.core.Proveedor

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, folio, documento")
@ToString(includeNames = true, includes = "empresa, folio, nombre, bl")
abstract class CuentaPorPagar implements  MultiTenant<CuentaPorPagar> {

    String id

    String empresa

    Long swx

    Proveedor proveedor

    String nombre

    String documento

    Date fecha

    Date vencimiento

    String moneda = 'MXN'

    BigDecimal tipoDeCambio = 1.0

    BigDecimal importe = 0.0

    BigDecimal descuentos = 0.0
    BigDecimal subTotal = 0.0
    BigDecimal impuestos = 0.0
    BigDecimal total = 0.0
    BigDecimal saldo = 0.0
    BigDecimal pagosAplicados = 0.0
    BigDecimal tasaDeImpuesto = 0.0
    BigDecimal totalMN = 0.0

    BigDecimal retTasa = 0.0
    BigDecimal retImp = 0.0
    BigDecimal retensionIsr = 0.0

    BigDecimal analisisCosto = 0.0

    String comentario

    BigDecimal requisitado = 0.0

    Boolean gastoPorComprobar = true

    /** Transient properties **/

    BigDecimal pendienteRequisitar

    BigDecimal saldoActual

    BigDecimal saldoAlCorte

    String uuid

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    ComprobanteFiscal comprobante

    static constraints = {
        empresa maxSize: 20
        moneda(nullable:false)
        tipoDeCambio scale:4
        tasaDeImpuesto scale:4
        comentario nullable: true
        uuid nullable: true
        swx nullable: true
        comentario nullable: true
        comprobante nullable:true
    }

    static transients = ['pendienteRequisitar','saldoActual','saldoAlCorte']

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type:'date'
        // requisitado formula:'(select ifnull(sum(x.total),0) from requisicion_det x where x.factura_id=id)'
        // pagosAplicados formula:'(select ifnull(sum(x.total),0) from aplicacion x where x.factura_id=id)'
    }
}
