package sx.imports.embarques


import grails.rest.*
import grails.converters.*

class EmbarqueDetController extends RestfulController {
    static responseFormats = ['json', 'xml']
    EmbarqueDetController() {
        super(EmbarqueDet)
    }
}
