package sx.imports.embarques

import grails.buildtestdata.BuildDataTest

import grails.test.hibernate.HibernateSpec
import grails.testing.gorm.DataTest
import org.grails.datastore.mapping.multitenancy.resolvers.SystemPropertyTenantResolver
import org.grails.orm.hibernate.cfg.Settings


import static grails.gorm.multitenancy.Tenants.*

class CompraSpec extends HibernateSpec implements DataTest, BuildDataTest {

    def setup() {
    }

    def cleanup() {
    }

    @Override
    Class[] getDomainClassesToMock() {
        [Compra]
    }

    @Override
    Map getConfiguration() {
        [(Settings.SETTING_MULTI_TENANT_RESOLVER_CLASS)           : SystemPropertyTenantResolver,
         (Settings.SETTING_MULTI_TENANCY_MODE)                    : 'DISCRIMINATOR',
         (org.grails.orm.hibernate.cfg.Settings.SETTING_DB_CREATE): "create-drop"]
    }

    def cleanupSpec() {
        System.setProperty(SystemPropertyTenantResolver.PROPERTY_NAME, '')
    }


    void "build a Compra will build a Proveedor"() {
        when:
        def compra = build(Compra)

        then: compra.id

    }
}
