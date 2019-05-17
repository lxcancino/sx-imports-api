package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.MultiTenant
import groovy.transform.EqualsAndHashCode
import sx.imports.core.Proveedor

@GrailsCompileStatic
@EqualsAndHashCode(includes = "id, empresa, folio")
class Pedimento implements  MultiTenant<Pedimento> {

    String id

    Long folio

    String empresa

    Date fecha

    Proveedor proveedor

    String nombre

    String pedimento

    BigDecimal dta = 0.0

    BigDecimal prevalidacion = 0.0

    BigDecimal tipoDeCambio = 0.0

    BigDecimal arancel = 0.0

    BigDecimal impuestoTasa = 16.0

    BigDecimal impuesto = 0.0

    BigDecimal incrementables = 0.0

    String comentario

    // CuentaPorPagar incrementable1
    String referenciacg

    String agenteAduanal

    String paisDeOrigen

    BigDecimal contraPrestacion = 0.0

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser


    static constraints = {
        folio unique: 'empresa'
        empresa maxSize: 20
        fecha nullable: false
        pedimento nullable: false, unique: true, maxSize: 50
        dta nullable: false, scale: 2
        prevalidacion nullable: false, scale: 2
        arancel nullable:true
        tipoDeCambio scale: 4
        impuestoTasa sacle: 4
        comentario nullable:true
        proveedor nullable: true
        // incrementable1 nullable:true
        referenciacg nullable: true,maxSize:50
        agenteAduanal nullable: true
        paisDeOrigen nullable:true
    }

    static mapping = {
        id generator: 'uuid'
        tenantId name: 'empresa'
        fecha type: 'date', index: 'PEDIMENTO_IDX_FECHA'
        //embarques fetch:'join'
        //incrementables formula:'(select ifnull(sum(x.incrementables),0) from embarque_det x where x.pedimento_id=id)'
    }

     static PAISES = [
        'ALEMANIA',
        'BELGICA',
        'CHINA',
        'ESPAÑA',
        'FRANCIA',
        'HOLANDA',
        'INDONESIA',
        'ITALIA',
        'KOREA',
        'PORTUGAL',
        'TAIWAN',
        'USA']



}


