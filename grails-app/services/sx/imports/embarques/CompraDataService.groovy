package sx.imports.embarques

import grails.gorm.multitenancy.CurrentTenant
import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.*

import grails.gorm.services.Service

// @Service(Compra)
@CurrentTenant
class CompraDataService {

    List<Compra> findByPeriodo() {
        // def query = Compra.where{fecha >= periodo.fechaInicial && fecha <= periodo.fechaFinal}
        return Compra.list([max: 20])
    }

}