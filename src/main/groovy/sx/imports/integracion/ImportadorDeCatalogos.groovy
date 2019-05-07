package sx.imports.integracion

import groovy.sql.Sql
import java.sql.SQLException

import groovy.util.logging.Slf4j

import sx.imports.core.Clase
import sx.imports.core.Linea
import sx.imports.core.Marca

@Slf4j
class ImportadorDeCatalogos implements IntegracionDBSupport {

    ImportadorDeCatalogos(String dbUrl) {}

    def importar() {
        importarLineas()
        .importarMarcas()
        .importarClases()
    }

    def importarLineas() {
        List<Map> rows = readFromImpap("select * from linea")
        rows.each { row ->
            new Linea(
                nombre: row.nombre,
                descripcion: row.nombre,
                activa: true,
                sw2: row.nombre
            ).save failOnError: true
        }
        return this
    }

    def importarMarcas() {
        List<Map> rows = readFromImpap("select * from marca")
        rows.each { row ->
            new Marca(
                    nombre: row.nombre,
                    descripcion: row.nombre,
                    activa: true,
                    sw2: row.nombre
            ).save failOnError: true
        }
        return this
    }

    def importarClases() {
        List<Map> rows = readFromImpap("select * from clase")
        rows.each { row ->
            new Clase(
                    nombre: row.nombre,
                    descripcion: row.nombre,
                    activa: true,
                    sw2: row.nombre
            ).save failOnError: true
        }
        return this
    }
}
