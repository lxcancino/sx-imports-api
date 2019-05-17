package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class DistribucionController extends RestfulController<Distribucion> {
    static responseFormats = ['json']
    DistribucionController() {
        super(Distribucion)
    }
}
