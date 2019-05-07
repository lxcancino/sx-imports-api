package sx.imports.core


import grails.rest.*
import grails.converters.*

class ProductoController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ProductoController() {
        super(Producto)
    }
}
