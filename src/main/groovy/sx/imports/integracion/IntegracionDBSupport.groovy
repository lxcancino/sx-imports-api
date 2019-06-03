package sx.imports.integracion

import groovy.sql.Sql
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.exception.ExceptionUtils

import java.sql.SQLException

@Slf4j
trait IntegracionDBSupport {

    static String PAPER = 'jdbc:mysql://localhost/paperx2'
    static String IMPAP = 'jdbc:mysql://localhost/impapx2'

    List<Map> readFromImpap(String sql, ...params) {
        return this.getRows(IMPAP, sql, params)
    }

    List<Map> readFromPaper(String sql, ...params) {
        return this.getRows(PAPER, sql, params)
    }


    def getRows(String dbUrl, String sql, ...params) {
        def db = getSql(dbUrl)
        try {
            return db.rows(sql,params)
        }catch (SQLException e){
            e.printStackTrace()
            def c = ExceptionUtils.getRootCause(e)
            def message = ExceptionUtils.getRootCauseMessage(e)
            throw new RuntimeException(message,c)
        }finally {
            db.close()
        }
    }


    def getSql(String dbUrl) {
        String user = 'root'
        String password = 'sys'
        String driver = 'com.mysql.jdbc.Driver'
        Sql db = Sql.newInstance(dbUrl, user, password, driver)
        return db
    }
}
