package sx.imports.core

import grails.gorm.DetachedCriteria

import groovy.transform.CompileStatic

import org.grails.datastore.mapping.multitenancy.AllTenantsResolver
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletWebRequest

import javax.servlet.http.HttpServletRequest

@CompileStatic
class EmpresaTenantResolver implements  AllTenantsResolver {

    public static final String HEADER_NAME = 'gorm.tenantId'

    String headerName = HEADER_NAME


    @Override
    Iterable<Serializable> resolveTenantIds() {
        new DetachedCriteria(Empresa)
                .distinct('clave')
                .list()
    }

    @Override
    Serializable resolveTenantIdentifier() throws TenantNotFoundException {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes()
        if(requestAttributes instanceof ServletWebRequest) {
            HttpServletRequest servletRequest = ((ServletWebRequest) requestAttributes).getRequest()
            String value = servletRequest.getHeader("gorm.tenantId")
            if(value)
                return value
        }
        throw new TenantNotFoundException("Tenant could not be resolved from HTTP Header: ${headerName}")
        /*
        def value = System.getProperty('gorm.tenantId')

        if(value) {
            println 'SYSTEM Tenant : ' + value
            return value
        }
        return 'PAPER'
        */
    }
}
