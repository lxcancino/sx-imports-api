package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import sx.imports.core.Proveedor

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, folio, empresa")
@ToString(includeNames = true, includes = "empresa, folio, nombre, bl")
class Embarque implements  MultiTenant<Compra> {

    String id

    String empresa

    Long folio

    String bl

    Proveedor proveedor

    String nombre

    Date fecha

    Aduana aduana

    Date ingresoAduana

    Integer contenedores

    String comentario

    String moneda

    BigDecimal tipoDeCambio

    Date liberado

    List<EmbarqueDet> partidas

    static hasMany = [partidas:EmbarqueDet]

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
        bl blank:false, maxSie:100, unique:true
        ingresoAduana nullable:true
        comentario nullable:true
        moneda maxSize:3
        tipoDeCambio scale:4
        liberado nullable:true
    }

    String toString(){
        return "Folio: ${folio} BL: ${bl} $nombre (${proveedor.nombre})"
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        partidas cascade: "all-delete-orphan"
        proveedor fetch:'join'
        aduana fetch:'join'
        fecha type: 'date', index: 'EMBARQUE_IDX_FECHA'
        ingresoAduana type: 'date'
        liberado type: 'date'
        // cuentaDeGastos formula:"(SELECT max(g.id) FROM  cuenta_de_gastos g  where g.embarque_id=id)"
        // facturado formula:"(select ifnull(sum(d.importe),0) from Venta_det d join embarque_det x on(x.id=d.embarque_id) where x.embarque_id=id)"
        // valor formula:"(SELECT round(ifnull(sum(d.cantidad/u.factor*d.precio_de_venta),0),2) FROM  embarque_det d join producto p on(p.id=d.producto_id) join unidad u on(u.id=p.unidad_id) where d.embarque_id=id)"
    }

    /*
    BigDecimal porFacturar(){
        return valor - facturado
    }
    */

}
