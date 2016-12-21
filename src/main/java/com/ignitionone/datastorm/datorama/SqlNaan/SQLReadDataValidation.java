package com.ignitionone.datastorm.datorama.SqlNaan;
import com.ignitionone.datastorm.datorama.util.CommonUtil;

import java.sql.*;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by filgi.mathew on 12/19/2016.
 */
public class SQLReadDataValidation {

    Connection sourcecon = null;
    Connection destconn=null;
    String sourceconnectionUrl="";
    String destinationconnectionUrl="";
    Statement stmt = null;
    ResultSet rs = null;
    Properties prop = new Properties();

    ///Constructor to Initialize Required Fields.
    public SQLReadDataValidation()
    {
       try
        {
         InputStream input = null;
          input = new FileInputStream("configuration.properties");
          prop.load(input);
          sourceconnectionUrl=prop.getProperty("SQLRead");
          destinationconnectionUrl=prop.getProperty("SQLNaan");
          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

          destconn= DriverManager.getConnection(destinationconnectionUrl);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
       catch (ClassNotFoundException e) {
           System.out.println(e);
       }

    }

    public void BUHierarchyTest()
    {
        try
        {

            String SQL = prop.getProperty("BUHierarchyTestScript");
            SQL = SQL+prop.getProperty("BUID")+")";
            stmt = destconn.createStatement();
            rs = stmt.executeQuery(SQL);
            int BU_ID;
            String dataVal="",RegionID="",AgencyID="",DivisionID="",CompanyID="";
            while (rs.next()) {
                BU_ID = rs.getInt("BU_ID");
                dataVal=prop.getProperty(Integer.toString(BU_ID));
                RegionID= rs.getString("Region_ID");
                AgencyID= rs.getString("Agency_ID");
                DivisionID= rs.getString("Division_ID");
                CompanyID=rs.getString("Company_ID");
                if (dataVal!=null) {
                    if (!(dataVal.contains(RegionID)) && !(dataVal.contains(AgencyID)) && !(dataVal.contains(DivisionID)) && !(dataVal.contains(CompanyID)))
                        CommonUtil.SQLDataReadSuccessFull("BUHierarchyTest passed successfully");
                }
            }



        }
         catch(NullPointerException ex)
         {
             CommonUtil.SQLDataReadFail("NullPointerException",ex.getStackTrace().toString(),"BUHierarchyTest");
         }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
            CommonUtil.SQLDataReadFail(e.getMessage(),e.getStackTrace().toString(),"BUHierarchyTest");
        }

        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (sourcecon != null) try { sourcecon.close(); } catch(Exception e) {}
        }
         try
         {
             destconn.close();
         }
         catch (SQLException ex)
         {
             CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
         }

        CommonUtil.SQLDataReadSuccessFull("BUHierarchyTest passed successfully");

    }
   /// Confirm that the intermediary database table [DMS].[dbo].[CoreService_CompanyStoreBUHierarchy_Staging] in (ATL_SQLPROC_Dev) contains the following Company Store specific required columns:
   ////BU_ID
   /// BU_Name
   /// Company_ID
   /// Company_Name
   /// Agency_ID
   /// Agency_Name
   /// Division_ID
   /// Division_Name
   /// Region_ID
   /// Region_Name
   /// TimeZone_Name
    public void CompareCompanyStoreTableColumns() {
       boolean result=false;
        try {
            ResultSet rsColumns = null;
            DatabaseMetaData meta = destconn.getMetaData();
            rsColumns = meta.getColumns(null, null, "CoreService_CompanyStoreBUHierarchy", null);
            String columnName="";
            while (rsColumns.next()) {
                columnName=rsColumns.getString("COLUMN_NAME");
                if (columnName.toLowerCase().contains(prop.getProperty(columnName).toLowerCase()))
                    result=true;
                else
                    result=false;
            }
            rsColumns = meta.getColumns(null, null, "CoreService_CompanyStoreBUHierarchy_Staging", null);

            while (rsColumns.next()) {
                columnName=rsColumns.getString("COLUMN_NAME");
                if (columnName.toLowerCase().contains(prop.getProperty(columnName).toLowerCase()))
                    result=true;
                else
                   result=false;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
           result=false;
        }

        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (sourcecon != null) try { destconn.close(); } catch(Exception e) {}
        }
        if (result)
            CommonUtil.SQLDataReadSuccessFull("CompareCompanyStoreTableColumns passed successfully");
        else
            CommonUtil.SQLDataReadFail("Columns are not matching","Failed","CompareCompanyStoreTableColumns");

        try
        {
            destconn.close();
        }
        catch (SQLException ex)
        {
            CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
        }
    }

    ///Confirm that the intermediary database table [DMS].[dbo].[CoreService_Integration_Staging] in (ATL_SQLPROC_Dev) contains the following Integration specific required columns:
   ///- Integration_ID
   ///- Integration_Name
   ///- Currency_Code
    public void CompareIntegrationTableColumns() {
        boolean result=false;
        try {
            ResultSet rsColumns = null;
            DatabaseMetaData meta = destconn.getMetaData();
            rsColumns = meta.getColumns(null, null, "CoreService_Integration", null);
            String columnName="";
            while (rsColumns.next()) {
                columnName=rsColumns.getString("COLUMN_NAME");
                if (columnName.toLowerCase().contains(prop.getProperty(columnName).toLowerCase()))
                    result=true;
                else
                    result=false;
            }
            rsColumns = meta.getColumns(null, null, "CoreService_Integration_Staging", null);

            while (rsColumns.next()) {
                columnName=rsColumns.getString("COLUMN_NAME");
                if (columnName.toLowerCase().contains(prop.getProperty(columnName).toLowerCase()))
                    result=true;
                else
                   result=false;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            CommonUtil.SQLDataReadFail("Columns are not matching","Failed","CompareIntegrationTableColumns");
        }

        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (sourcecon != null) try { destconn.close(); } catch(Exception e) {}
        }
        if (result)
            CommonUtil.SQLDataReadSuccessFull("CompareIntegrationTableColumns passed successfully");
        else
            CommonUtil.SQLDataReadFail("Columns are not matching","Failed","CompareIntegrationTableColumns");
        try
        {
            destconn.close();
        }
        catch (SQLException ex)
        {
            CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
        }
    }

  /// Confirm that the record count available in the Source database [DMS_Core].[dbo].[BusinessUnit](ATL_SQLPROC_Dev) matches with the record count
  // available in the [DMS].[dbo].[CoreService_CompanyStoreBUHierarchy] destination table.
    public void CompareBusinessUnitTableCount()
    {

      try
      {
          sourcecon = DriverManager.getConnection(sourceconnectionUrl);
          String SQL = prop.getProperty("SourceBusinessUnit");
          stmt = sourcecon.createStatement();
          rs = stmt.executeQuery(SQL);
          int sourcecount=0,destinationCount=0;
          while (rs.next()) {
              sourcecount = rs.getInt("sourcetotal");
          }
          sourcecon.close();
          SQL =  prop.getProperty("DestinationBusinessUnit");
          stmt =  destconn.createStatement();
          rs = stmt.executeQuery(SQL);
          while (rs.next()) {
              destinationCount = rs.getInt("destinationtotal");
          }
          if (sourcecount==destinationCount)
              CommonUtil.SQLDataReadSuccessFull("CompareBusinessUnitTableCount passed successfully");

       }

      // Handle any errors that may have occurred.
      catch (Exception e) {
          e.printStackTrace();
          CommonUtil.SQLDataReadFail("Source and Destination count test failed"+e.getMessage(),"Failed","CompareBusinessUnitTableCount");
      }

      finally {
          if (rs != null) try { rs.close(); } catch(Exception e) {}
          if (stmt != null) try { stmt.close(); } catch(Exception e) {}
          if (sourcecon != null) try { sourcecon.close(); } catch(Exception e) {}
      }

        try
        {
            sourcecon.close();
            destconn.close();
        }
        catch (SQLException ex)
        {
            CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
        }
     }

}
