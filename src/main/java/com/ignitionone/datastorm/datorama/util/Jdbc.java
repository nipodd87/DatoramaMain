package com.ignitionone.datastorm.datorama.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Enter Javadoc
 */
public class Jdbc {

    //~ Static fields/initializers -------------------------------------------------------------------------------------

    public static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String DB_HOST_URL = "hostUrl";
    public static String DB_USERNAME = "username";
    public static String DB_PASSWORD = "password";

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * TODO: Enter Javadoc
     *
     * @param query in value
     * @param connectionUrl    in value
     * @return out value
     * @throws SQLException on error
     */


    public static List<String> executeSQL(String query, String connectionUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        List<String> dbValues = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER).newInstance();
            //conn = DriverManager.getConnection(connectionUrl,"siasp", "siasp1871");
            conn = DriverManager.getConnection(connectionUrl);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            StringBuffer coloumn = new StringBuffer();
            ResultSetMetaData md = rs.getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                coloumn.append(md.getColumnLabel(i));
                if (i < md.getColumnCount()) {
                    coloumn.append(",");
                }
            }

            dbValues.add(coloumn.toString());

            // Loop through the result set
            while (rs.next()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    sb.append(rs.getString(i));
                    if (i < md.getColumnCount()) {
                        sb.append(",");
                    }
                }
                dbValues.add(sb.toString());
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return dbValues;

    }


    /**
     * Getter for property db key values
     *
     * @param dbValues  in value
     * @param rowNumber in value
     * @return out value
     */
    public static Map<String, String> getDbKeyValues(List<String> dbValues, int rowNumber) {
        Map<String, String> DbKeyValue = new HashMap<String, String>();

        String[] columnArray = dbValues.get(0).split(",");
        String[] dbColumnArray = dbValues.get(rowNumber).split(",");

        for (int i = 0; i < columnArray.length; i++) {
            DbKeyValue.put(columnArray[i], dbColumnArray[i]);
        }

        return DbKeyValue;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param dbEnvt in value
     * @param sql    in value
     * @throws SQLException on error
     */
    public static void UpdateQuery(String dbEnvt, String sql) throws SQLException {
        List<String> dbValues = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(DRIVER);
            String env = CommonUtil.getPlatformPropertyValue("envt").replaceAll("[0-9]", "");

            String hostURL = CommonUtil.getDBPropertyValue(env + "."
                    + dbEnvt + "." + DB_HOST_URL);
            String dbusername = CommonUtil.getDBPropertyValue(env + "."
                    + dbEnvt + "." + DB_USERNAME);
            String dbpassword = CommonUtil.getDBPropertyValue(env + "."
                    + dbEnvt + "." + DB_PASSWORD);

            // System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(hostURL, dbusername, dbpassword);

            // System.out.println("Creating statement...");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
    }
}
