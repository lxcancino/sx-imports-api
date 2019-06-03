package sx.imports.core


import grails.buildtestdata.BuildDomainTest
import spock.lang.Specification

class ProductoSpec extends Specification implements BuildDomainTest<Producto> {

    void "build a valid producto"() {
        expect:
        domain.linea
        domain.marca
        println domain
    }
}
