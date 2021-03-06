package sx.imports.cxp

class NotaDeCredito extends Abono {

    String documento

    String concepto

    static constraints = {
        concepto(inList:['DEVOLUCION',
                         'DESCUENTO_FINANCIERO',
                         'DESCUENTO',
                         'DESCUENTO_ANTICIPO',
                         'BONIFICACION',
                         'REBATE'])
        documento nullable:true, maxSize:20

    }
}
