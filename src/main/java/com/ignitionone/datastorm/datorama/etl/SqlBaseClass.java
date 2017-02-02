package com.ignitionone.datastorm.datorama.etl;

import com.ignitionone.datastorm.datorama.util.Jdbc;
import com.ignitionone.datastorm.datorama.util.PropertyLoader;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by nitin.poddar on 1/31/2017.
 */
public class SqlBaseClass {
    protected String strSQL;
    protected String connectionURL;
    Jdbc jdbc = new Jdbc();

    public void sqlSetup(String sqlFile, String sqlQueryName, String environment) {
        PropertyLoader propertySQLFile = new PropertyLoader();
        propertySQLFile.loadProperties(sqlFile);
        Properties SQLFileProps = propertySQLFile.getProps();
        String dbEnvt = (String) SQLFileProps.get("dbEnvt");

        strSQL = (String) SQLFileProps.get(sqlQueryName);

        PropertyLoader propertiesFile = new PropertyLoader();
        propertiesFile.loadProperties("db.properties");
        Properties dbProperties = propertiesFile.getProps();
        connectionURL = (String) dbProperties.get(environment + "." + dbEnvt + ".connectionUrl");
    }

    public  Map<String, String> verifyAddFunctionality(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws Exception {
        sqlSetup(sqlFile, sqlQueryName, environment);
        List<String>  DbKeyValue =  jdbc.executeSQL(strSQL.replace(oldString, newString), connectionURL);
        Map<String, String> DbKeyValue1 = Jdbc.getDbKeyValues(DbKeyValue, 1);
        return DbKeyValue1;
    }

    public  Map<String, String> getTwoValues(String sqlFile, String environment, String sqlQueryName, String oldString, String newString, String oldString2, String newString2) throws Exception {
        sqlSetup(sqlFile, sqlQueryName, environment);
        List<String>  DbKeyValue =  jdbc.executeSQL(strSQL.replace(oldString, newString).replace(oldString2, newString2), connectionURL);
        Map<String, String> DbKeyValue1 = Jdbc.getDbKeyValues(DbKeyValue, 1);
        return DbKeyValue1;
    }

    public Map<String, String> verifyEditFunctionality(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws Exception {
        sqlSetup(sqlFile, sqlQueryName, environment);
        List<String> userList =  jdbc.executeSQL(strSQL.replace(oldString, newString), connectionURL);
        Map<String, String> DbKeyValue1 = Jdbc.getDbKeyValues(userList, 1);
        return DbKeyValue1;
    }

    public List<String> getMultipleValues(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws Exception {
        sqlSetup(sqlFile, sqlQueryName, environment);
        List<String> userList = jdbc.executeSQL(strSQL.replace(oldString, newString), connectionURL);
        return userList;
    }

    public void executeSPWithNoResultSet(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws Exception {
        sqlSetup(sqlFile, sqlQueryName, environment);
        jdbc.executeProcedureWithNoResultSet(strSQL.replace(oldString, newString), connectionURL);
    }

    public void undeleteFunctionality(String sqlFile, String environment, String sqlQueryName, String oldString, String newString) throws Exception {
        sqlSetup(sqlFile, sqlQueryName, environment);
        jdbc.executeSQL(strSQL.replace(oldString, newString), connectionURL);
    }
}