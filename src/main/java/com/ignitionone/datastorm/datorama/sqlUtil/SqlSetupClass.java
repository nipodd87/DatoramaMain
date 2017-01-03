package com.ignitionone.datastorm.datorama.sqlUtil;
import com.ignitionone.datastorm.datorama.util.PropertyLoader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * Created by ravi.peddi on 12/28/2016.
 */
public class SqlSetupClass {
    protected String strSQL;
    protected String connectionURL;

    public void sqlSetup(String sqlFile, String sqlQueryName, String environment) {
        PropertyLoader propertySQLFile = new PropertyLoader();
        propertySQLFile.loadProperties(sqlFile);
        Properties sqlQueries = propertySQLFile. getProps();
        String dbEnvt = (String) sqlQueries.get("dbEnvt");
        strSQL = (String) sqlQueries.get(sqlQueryName);

        PropertyLoader propertiesFile = new PropertyLoader();
        propertiesFile.loadProperties("db.properties");
        Properties dbProperties = propertiesFile.getProps();
        connectionURL = (String) dbProperties.get(environment + "." + dbEnvt + ".connectionUrl");
    }

    public void sqlSetup(String environment, String dbEnvt) {
        PropertyLoader propertiesFile = new PropertyLoader();
        propertiesFile.loadProperties("db.properties");
        Properties dbProperties = propertiesFile.getProps();
        connectionURL = (String) dbProperties.get(environment + "." + dbEnvt + ".connectionUrl");
    }


    public static String getPropertyAsString(Properties prop) {
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();
    }
}
