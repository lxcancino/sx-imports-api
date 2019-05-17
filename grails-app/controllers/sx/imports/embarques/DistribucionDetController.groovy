package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*

import groovy.util.logging.Slf4j

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class DistribucionDetController extends RestfulController<DistribucionDet> {
    static responseFormats = ['json']
    DistribucionDetController() {
        super(DistribucionDet)
    }
}
