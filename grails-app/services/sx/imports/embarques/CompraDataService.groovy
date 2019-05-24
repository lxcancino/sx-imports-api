package sx.imports.embarques

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.transactions.Transactional
import sx.utils.FolioLog
import sx.utils.LogUser
import sx.utils.MonedaUtils
import sx.utils.Periodo

import static grails.gorm.multitenancy.Tenants.*

import grails.gorm.services.Service

// @Service(Compra)
@CurrentTenant
class CompraDataService implements FolioLog, LogUser{

    Compra buildCompra() {
        Compra compra = new Compra()
        compra.folio = -1L
        logEntity(compra)
        return compra
    }

    @Transactional
    Compra save(Compra compra) {
        if(!compra.id) {
            compra.folio = nextFolio('COMPRA', 'COMPRAS')
        }
        actualizarImportes(compra)
        logEntity(compra)
        compra.save flailOnError: true
        return compra
    }

    private void actualizarImportes(Compra compra) {
        compra.partidas.each {
            BigDecimal imp = it.precio * (it.solicitado/ it.producto.factor)
            it.importe = MonedaUtils.round(imp)
        }
        compra.importe = compra.partidas.sum 0.0, {it.importe}
    }

    List<Compra> findByPeriodo() {
        // def query = Compra.where{fecha >= periodo.fechaInicial && fecha <= periodo.fechaFinal}
        return Compra.list([max: 20, sort: 'fecha', order: 'desc'])
    }

}