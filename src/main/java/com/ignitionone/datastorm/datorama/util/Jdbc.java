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
    public static final String DELIMITER = "\\|@\\|";

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
                    coloumn.append("|@|");
                }
            }

            dbValues.add(coloumn.toString());

            // Loop through the result set
            while (rs.next()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    sb.append(rs.getString(i));
                    if (i < md.getColumnCount()) {
                        sb.append("|@|");
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

        String[] columnArray = dbValues.get(0).split(DELIMITER);
        String[] dbColumnArray = dbValues.get(rowNumber).split(DELIMITER);

        for (int i = 0; i < columnArray.length; i++) {
            DbKeyValue.put(columnArray[i], dbColumnArray[i]);
        }

        return DbKeyValue;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param query in value
     * @param connectionUrl    in value
     * @throws SQLException on error
     */
    public static void updateQuery(String query, String connectionUrl) throws SQLException {

        ResultSet rs = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(DRIVER);
            //Class.forName(DRIVER).newInstance();

            // System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(connectionUrl);

            // System.out.println("Creating statement...");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

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

    public ResultSet executeQuery(String query, String connectionUrl) throws SQLException {

        ResultSet rs = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(DRIVER);
            //Class.forName(DRIVER).newInstance();

            // System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(connectionUrl);

            // System.out.println("Creating statement...");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            stmt.close();
            conn.close();
        }
        return rs;
    }

    public void executeProcedureWithNoResultSet(String query, String connectionUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        List<String> dbValues = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(connectionUrl);
            // String SQL = String.format("call [dbo].[SI_SP_Entity_HardDeleteForAutomationTesting](107661,5,0)");
            stmt = conn.prepareCall(query);
            stmt.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            stmt.close();
            conn.close();
        }
    }

    public int getStoreProcedureRecordCount(String query, String connectionUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        List<String> dbValues = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        CallableStatement stmt = null;
        int counter=0;
        try {

            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(connectionUrl);
            // String SQL = String.format("call [dbo].[SI_SP_Entity_HardDeleteForAutomationTesting](107661,5,0)");
            stmt = conn.prepareCall(query);
            stmt.execute();
            rs = stmt.getResultSet();
            while(rs.next()){
                ++counter;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            conn.close();
        }
        return counter;
    }

    public static List<String> executeStoredProcedure(String query, String connectionUrl) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        List<String> dbValues = new ArrayList<String>();
        ResultSet rs = null;
        Connection conn = null;
        CallableStatement stmt = null;
        int counter = 0;
        try {

            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(connectionUrl);
            stmt = conn.prepareCall(query);
            stmt.execute();
            rs = stmt.getResultSet();
            dbValues = dbDataConvertToList(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            conn.close();
        }
        return dbValues;
    }

    private static List<String> dbDataConvertToList(ResultSet rs) throws SQLException {

        List<String> dbValues = new ArrayList<String>();
        StringBuffer coloumn = new StringBuffer();
        ResultSetMetaData md = rs.getMetaData();
        for (int i = 1; i <= md.getColumnCount(); i++) {
            coloumn.append(md.getColumnLabel(i));
            if (i < md.getColumnCount()) {
                coloumn.append("|@|");
            }
        }
        dbValues.add(coloumn.toString());

        // Loop through the result set
        while (rs.next()) {
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                sb.append(rs.getString(i));
                if (i < md.getColumnCount()) {
                    sb.append("|@|");
                }
            }
            dbValues.add(sb.toString());
        }
        return dbValues;
    }
}
