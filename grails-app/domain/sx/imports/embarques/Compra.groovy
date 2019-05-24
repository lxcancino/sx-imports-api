package sx.imports.embarques

import groovy.transform.EqualsAndHashCode

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

import sx.imports.core.Proveedor

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, folio")
class Compra implements  MultiTenant<Compra> {

    String id

    Long folio

    String empresa

    Proveedor proveedor

    String nombre

    Date fecha

    Date entrega

    Date depuracion

    Long papelFolio

    String papelOrigen

    String comentario

    String moneda

    BigDecimal tipoDeCambio

    BigDecimal importe = 0.0

    BigDecimal descuentos = 0.0

    BigDecimal subtotal = 0.0

    BigDecimal impuestos = 0.0

    BigDecimal total = 0.0

    List<CompraDet> partidas = []

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    Long swx

    static hasMany = [partidas:CompraDet]

    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
        entrega nullable: true
        depuracion nullable:true
        comentario nullable: true
        tipoDeCambio scale:4
        papelOrigen nullable:true
        papelFolio nullable: true
        swx nullable: true, maxSize: 10
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        partidas cascade: "all-delete-orphan"
        fecha type: 'date', index: 'COMPRA_IDX_FECHA'
        depuracion type: 'date'
        entrega type: 'date'
    }

    def beforeInsert() {
        updateNombre()
    }

    def beforeUpdate() {
        updateNombre()
    }

    def updateNombre() {
        nombre = proveedor.nombre
    }
}
