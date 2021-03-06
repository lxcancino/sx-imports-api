package sx.imports.cxp

class FacturaDeGastos extends CuentaPorPagar{

    BigDecimal descuento = 0.0

    BigDecimal rembolso = 0.0

    BigDecimal otros = 0.0

    String concepto = 'GASTOS'

    // CuentaDeGastosGenerica cuentaGenerica

    // static hasMany =[conceptos:ConceptoDeGasto]

    //static belongsTo=[cuentaGenerica:CuentaDeGastosGenerica]

    static constraints = {
        //retensionIsr(nullable:true)
        // cuentaGenerica(nullable:true)


    }

    static mapping = {
        // conceptos cascade: "all-delete-orphan"
    }

    /*
    public BigDecimal getSaldoActual(){
        def pag = pagosAplicados?:0.0
        return total- (descuento?:0) - (rembolso?:0) - (otros?:0) - pag
    }
    */

    static String[] CONCEPTOS = [
            'GASTOS',
            'HONORARIOS_CON_RETENCION',
            'HONORARIOS_SIN_RETENCION',
            'HONORARIOS_ASIMILADOS',
            'SERVICIOS_PROFESIONALES',
            'RETENCION_PAGOS',
            'COMISIONES_BANCARIAS'
    ]

}
