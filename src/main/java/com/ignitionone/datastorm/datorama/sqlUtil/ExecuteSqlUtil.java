package com.ignitionone.datastorm.datorama.sqlUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.ignitionone.datastorm.datorama.util.Jdbc;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class ExecuteSqlUtil extends SqlSetupClass{
    Jdbc jdbc = new Jdbc();

    public String connectionURL(String environment, String dbEnvt) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        sqlSetup(environment, dbEnvt);
        return connectionURL;
    }

    public Integer countOfRows(String connectionURL, String sqlQuery) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<String> dbData = jdbc.executeSQL(sqlQuery, connectionURL);
        Map<String, String> DbKeyValue1 = Jdbc.getDbKeyValues(dbData, 1);
        return Integer.parseInt(DbKeyValue1.get("CNT"));
    }

    public Integer countOfRows(String sqlFile, String environment, String sqlQueryName) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        sqlSetup(sqlFile, sqlQueryName, environment);
        List<String> dbData = jdbc.executeSQL(strSQL, connectionURL);
        Map<String, String> DbKeyValue1 = Jdbc.getDbKeyValues(dbData, 1);
        return Integer.parseInt(DbKeyValue1.get("cnt"));
    }
    public Integer countOfRows(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        sqlSetup(sqlFile, sqlQueryName, environment);
        List<String> dbData = jdbc.executeSQL(strSQL.replace(oldString, newString), connectionURL);
        Map<String, String> DbKeyValue1 = Jdbc.getDbKeyValues(dbData, 1);
        return Integer.parseInt(DbKeyValue1.get("cnt"));
    }

    public List<String> getRows(String sqlFile, String environment, String sqlQueryName, String oldString1, String newString1, String oldString2, String newString2) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        sqlSetup(sqlFile, sqlQueryName, environment);
        return jdbc.executeSQL(strSQL.replace(oldString1, newString1).replace(oldString2,newString2), connectionURL);
    }
    public List<String> getRows(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        sqlSetup(sqlFile, sqlQueryName, environment);
        return jdbc.executeSQL(strSQL.replace(oldString, newString), connectionURL);
    }
}
