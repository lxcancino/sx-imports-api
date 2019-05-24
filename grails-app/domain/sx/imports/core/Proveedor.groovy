package sx.imports.core

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import sx.sat.BancoSat

@GrailsCompileStatic
@ToString(includes = "nombre, rfc", includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['id', 'nombre', 'rfc'])
class Proveedor {

    String nombre

    Direccion direccion

    String email

    String www

    BigDecimal factorDeUtilidad

    String tipoDeCosteo = 'NORMAL'

    String rfc

    boolean nacional = true

    BigDecimal lineaDeCredito = 0.0

    int plazo

    boolean vencimentoBl = false

    String subCuentaOperativa

    String cuentaBancaria

    BancoSat bancoDestino

    String paisDeOrigen

    String nacionalidad

    Boolean agenciaAduanal = false

    String tipo = 'COMPRAS'

    Set<ProveedorProducto> productos

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    Long swx

    static embedded = ['direccion']

    static hasMany = [productos:ProveedorProducto, agentes:String]

    static constraints = {
        nombre unique: true, minSize:5
        factorDeUtilidad nullable:true,scale:4
        tipoDeCosteo nullable:true,inList:['NORMAL','ESPECIAL']
        email nullable:true,email:true
        www nullable:true,url:true
        direccion nullable:true
        rfc nullable:true, maxSize:13
        subCuentaOperativa nullable:true,maxSize:4
        paisDeOrigen nullable:true
        nacionalidad nullable:true
        cuentaBancaria nullable:true, maxSize:30
        bancoDestino nullable:true
        tipo nullable: true, maxSize:40
    }

    String toString(){
        return nombre;
    }

    static mapping = {
        productos cascade: "all-delete-orphan"
    }
}
