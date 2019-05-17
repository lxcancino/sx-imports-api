package sx.utils

import grails.compiler.GrailsCompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@GrailsCompileStatic
class PeriodoInterceptor {

    public PeriodoInterceptor() {
        matchAll()
        match action: ~/(index)/
    }

    boolean before() {
        if(params.fechaInicial) {
            Periodo periodo = new Periodo()
            bindData(periodo, params)
            // log.debug('Periodo: {}',periodo)
            params.periodo = periodo
            params.remove('fechaInicial')
            params.remove('fechaFinal')
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
