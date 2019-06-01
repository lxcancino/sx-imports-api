package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import groovy.util.logging.Slf4j
import sx.utils.FolioLog
import sx.utils.LogUser

@GrailsCompileStatic
@Secured(['ROLE_COMPRAS', 'ROLE_ADMIN'])
@Slf4j
class EmbarqueController extends RestfulController<Embarque> implements LogUser, FolioLog {

    static responseFormats = ['json']

    EmbarqueController() {
        super(Embarque)
    }

    @Override
    protected List<Embarque> listAllResources(Map params) {
        return super.listAllResources(params)
    }

    @Override
    protected Embarque createResource() {
        Embarque embarque = new Embarque()
        embarque.folio = -1L
        bindData(embarque, getObjectToBind())
        logEntity(embarque)
        return embarque
    }

    @Override
    protected Embarque saveResource(Embarque resource) {
        resource.folio = nextFolio('EMBARQUE', 'EMBARQUES')
        resource.save flush: true
    }
}
