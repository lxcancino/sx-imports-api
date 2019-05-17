package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode

import sx.imports.core.Producto

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, clave, cantidad, contenedor, compraDet")
class EmbarqueDet implements  MultiTenant<Compra> {

    String id

    String empresa

    Embarque embarque

    Producto producto

    String clave

    String descripcion

    CompraDet compraDet

    String contenedor

    Integer tarimas = 0

    BigDecimal kilosNetos = 0.0

    BigDecimal kilosEstimados = 0.0

    BigDecimal cantidad
    BigDecimal precio = 0.0
    BigDecimal importe = 0.0  		//kilosNetos*costoUnitario
    BigDecimal tipoDeCambio = 0.0

    // Costos en MN
    BigDecimal costoBruto = 0.0
    BigDecimal gastosHonorarios = 0.0 //(kilosNetos*totalHonorarios)/embarque.kilosNetos
    BigDecimal gastosPorPedimento = 0.0  //(kilosNetos*valorPedimentoAsignado)/
    BigDecimal incrementablesUsd =0.0
    BigDecimal incrementables = 0.0
    BigDecimal costoNeto = 0.0  //costoBruto+gosto
    BigDecimal costoUnitarioNeto = 0.0
    BigDecimal precioDeVenta = 0.0
    BigDecimal gramos

    Long pedimento

    Long factura

    Long origen


    static belongsTo = [embarque:Embarque]

    static constraints = {
        producto nullable:true
        contenedor nullable:true, maxSize:30
        gastosHonorarios scale:4
        costoUnitarioNeto scale:4
        factura nullable:true
        pedimento nullable:true
        cantidad scale:3
        incrementables nullable:true,scale:2
        gramos nullable:true
        incrementablesUsd nullable:false
        origen nullable: true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
    }

}
