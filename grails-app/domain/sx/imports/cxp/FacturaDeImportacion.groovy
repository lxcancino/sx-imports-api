package sx.imports.cxp

import sx.imports.embarques.Pedimento

class FacturaDeImportacion extends CuentaPorPagar {

    Pedimento pedimento

    Date fechaBL

    Integer provisionada

    def beforeUpdate() {
        if(pedimento)
            totalMN=total*pedimento.tipoDeCambio
        else
            totalMN=0

    }

    static mapping ={
        fechaBL formula:'(select max(E.fecha) from EMBARQUE_DET x join EMBARQUE E ON(E.ID=X.EMBARQUE_ID) where x.factura_id=id)'

    }
}
