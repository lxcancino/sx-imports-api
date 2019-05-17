package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import sx.imports.core.Producto


@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, sucursal, contenedor")
@ToString(includeFields = true, includeNames = true, includes = "sucursal, contenedor")
class DistribucionDet implements  MultiTenant<Compra> {

    String id

    String empresa

    String sucursal

    EmbarqueDet embarqueDet

    Producto producto

    String clave

    String descripcion

    String contenedor

    int tarimas = 0 // Lo que trae embarque det por default editable

    BigDecimal cantidad = 0.0 // Lo q trae el embarque det

    BigDecimal cantidadPorTarima = 0.0 // cantidad/tarimas

    BigDecimal kilosNetos = 0.0 // Lo que tree el embaque por default en kilos netos/1000

    String comentarios

    String instrucciones

    Date programacionDeEntrega

    Date fechaDeEntrada

    static belongsTo = [distribucion:Distribucion]

    static constraints = {
        cantidadPorTarima scale:3
        comentarios nullable:true
        sucursal maxSize:30
        instrucciones nullable:true,maxSize:150
        programacionDeEntrega nullable:true
        fechaDeEntrada nullable:true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fechaDeEntrada type: 'date'
        programacionDeEntrega type: 'date'
    }
}
