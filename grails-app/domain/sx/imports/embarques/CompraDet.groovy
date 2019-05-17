package sx.imports.embarques

import groovy.transform.EqualsAndHashCode

import grails.compiler.GrailsCompileStatic

import grails.gorm.MultiTenant

import sx.imports.core.Producto

@GrailsCompileStatic
@EqualsAndHashCode()
class CompraDet implements  MultiTenant<CompraDet>{

    String id

    String empresa

    Producto producto

    String clave

    String descripcion

    BigDecimal solicitado = 0.0

    // BigDecimal entregado = 0.0

    // BigDecimal pendiente = 0.0

    BigDecimal depurado = 0.0

    BigDecimal precio

    BigDecimal descuento = 0.0

    BigDecimal importeDescuento = 0.0

    BigDecimal importe = 0.0

    String comentario

    Long origen

    static belongsTo = [compra:Compra]

    static constraints = {
        empresa masSize: 20
        comentario nullable: true
        origen nullable: true
    }


    // static transients =['entregado', 'pendiente']

    static mapping ={
        id generator: 'uuid'
        tenantId name: 'empresa'
        //entregado formula:'(select ifnull(sum(x.cantidad),0) from embarque_det x where x.compra_det_id=id)'
    }

    String toString(){
        return "${clave}  ${descripcion}   Sol:${solicitado} "
    }

    def beforeInsert() {
        this.clave = producto.clave
        this.descripcion = producto.descripcion
    }


}
