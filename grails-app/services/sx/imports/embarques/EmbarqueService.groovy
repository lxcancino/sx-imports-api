package sx.imports.embarques

import grails.compiler.GrailsCompileStatic
import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j

import static grails.gorm.multitenancy.Tenants.*

import sx.utils.LogUser
import sx.utils.FolioLog


@Slf4j
@Transactional
@CurrentTenant
@GrailsCompileStatic
class EmbarqueService implements LogUser, FolioLog{


    Embarque save( Embarque embarque) {
        logEntity(embarque)
        if(embarque.id == null) {

            embarque.folio = nextFolio('Embarque', 'EMBARQUES')
        }
        embarque.save failOnError: true, flush: true
        return embarque
    }


}
