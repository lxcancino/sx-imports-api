package sx.imports.core

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(includes = "clave, descripcion, unidad", includeNames = true, includePackage = false)
@EqualsAndHashCode(includes=['id', 'clave'])
class Producto {

    String clave

    String descripcion

    String unidad

    Linea linea

    Marca marca

    Clase clase

    BigDecimal kilos

    BigDecimal gramos

    BigDecimal largo

    BigDecimal ancho

    int calibre

    int caras

    String acabado

    String color

    BigDecimal m2

    BigDecimal precioCredito

    BigDecimal precioContado

    String claveProdSat

    String claveUnidadSat

    String unidadSat

    Date dateCreated
    Date lastUpdated

    String createUser
    String updateUser

    String swx

    static constraints = {
        clave unique:true
        acabado nullable:true
        color nullable:true
        swx nullable: true
    }
}
