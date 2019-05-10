package sx.imports.embarques


import grails.rest.*
import grails.converters.*

class AduanaController extends RestfulController {
    static responseFormats = ['json', 'xml']
    AduanaController() {
        super(Aduana)
    }
}
