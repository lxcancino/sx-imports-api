package sx.utils

import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import grails.plugins.jasper.JasperService

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier


/**
 * Enable Report execution
 */
@Slf4j
trait Reports {


    @Autowired
    @Qualifier('jasperService')
    JasperService jasperService

    ByteArrayOutputStream run(String reportName, Map params ) {
        log.debug("Ejecutando reporte ${reportName} con parametros: ${params}")
        def reportDef= new JasperReportDef(
                name: reportName,
                fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: params
        )
        ByteArrayOutputStream pdfStream = jasperService.generateReport(reportDef)
        return pdfStream
    }

    ByteArrayOutputStream run(String reportName, Map params, Collection data){
        log.debug("Ejecutando reporte ${reportName} con parametros: ${params} y data: ${data}")
        def reportDef=new JasperReportDef(
                name:reportName,
                fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters:params,
                reportData:data
        )
        ByteArrayOutputStream  stream=jasperService.generateReport(reportDef)
        return stream
    }

}