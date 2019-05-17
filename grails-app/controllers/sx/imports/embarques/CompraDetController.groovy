package sx.imports.embarques


import grails.rest.*
import grails.converters.*

class CompraDetController extends RestfulController {
    static responseFormats = ['json', 'xml']
    CompraDetController() {
        super(CompraDet)
    }
}
