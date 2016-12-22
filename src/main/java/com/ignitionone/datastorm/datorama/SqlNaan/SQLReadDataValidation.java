package com.ignitionone.datastorm.datorama.SqlNaan;
import com.ignitionone.datastorm.datorama.util.CommonUtil;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
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
                    String [] splitVal= dataVal.split(",");
                    CommonUtil.compareNumberEquals(Double.parseDouble(splitVal[0]),Double.parseDouble(RegionID),"Compare RegionID","RegionID");
                    CommonUtil.compareNumberEquals(Double.parseDouble(splitVal[1]),Double.parseDouble(AgencyID),"Compare AgencyID","AgencyID");
                    CommonUtil.compareNumberEquals(Double.parseDouble(splitVal[2]),Double.parseDouble(DivisionID),"Compare DivisionID","DivisionID");
                    CommonUtil.compareNumberEquals(Double.parseDouble(splitVal[3]),Double.parseDouble(CompanyID),"Compare CompanyID","CompanyID");

                }
            }



        }
         catch(NullPointerException ex)
         {
             CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
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
                    CommonUtil.SQLDataReadSuccess("Source Column"+columnName+ "Matched Destination Column"+ prop.getProperty(columnName));
                else
                    CommonUtil.SQLDataReadFail("Source Column"+columnName+ "Does not Matched Destination Column"+ prop.getProperty(columnName),"Failed","Column Mismatch");
            }
            rsColumns = meta.getColumns(null, null, "CoreService_CompanyStoreBUHierarchy_Staging", null);

            while (rsColumns.next()) {
                columnName=rsColumns.getString("COLUMN_NAME");
                if (columnName.toLowerCase().contains(prop.getProperty(columnName).toLowerCase()))
                    CommonUtil.SQLDataReadSuccess("Source Column"+columnName+ "Matched Destination Column"+ prop.getProperty(columnName));
                else
                    CommonUtil.SQLDataReadFail("Source Column"+columnName+ "Does not Matched Destination Column"+ prop.getProperty(columnName),"Failed","Column Mismatch");
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

        try
        {
            destconn.close();
        }
        catch (SQLException ex)
        {
            CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
        }
    }
   public void BusinessUnitStagingTableCleaned()
   {
       try
       {
           String SQL =  prop.getProperty("CompanyStoreTableCleanup");
           stmt =  destconn.createStatement();
           rs = stmt.executeQuery(SQL);
           int sourcecount=0,destinationCount=0;
           while (rs.next()) {
               sourcecount = rs.getInt("companytotal");
           }
           SQL =  prop.getProperty("IntegrationTableCleanup");
           stmt =  destconn.createStatement();
           rs = stmt.executeQuery(SQL);
           while (rs.next()) {
               destinationCount = rs.getInt("integrationtotal");
           }
           CommonUtil.compareNumberEquals(sourcecount,0,"Company Store Staging table count","Company Store Staging table count");
           CommonUtil.compareNumberEquals(destinationCount,0,"Integration Table Staging table count","Company Store Staging table count");


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
                    CommonUtil.SQLDataReadSuccess("Source Column"+columnName+ "Matched Destination Column"+ prop.getProperty(columnName));
                else
                    CommonUtil.SQLDataReadFail("Source Column"+columnName+ "Does not Matched Destination Column"+ prop.getProperty(columnName),"Failed","Column Mismatch for "+columnName);

            }
            rsColumns = meta.getColumns(null, null, "CoreService_Integration_Staging", null);

            while (rsColumns.next()) {
                columnName=rsColumns.getString("COLUMN_NAME");
                if (columnName.toLowerCase().contains(prop.getProperty(columnName).toLowerCase()))
                    CommonUtil.SQLDataReadSuccess("Source Column"+columnName+ "Matched Destination Column"+ prop.getProperty(columnName));
                else
                    CommonUtil.SQLDataReadFail("Source Column"+columnName+ "Does not Matched Destination Column"+ prop.getProperty(columnName),"Failed","Column Mismatch for "+columnName);

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

        try
        {
            destconn.close();
        }
        catch (SQLException ex)
        {
            CommonUtil.SQLDataReadFail(ex.getMessage(),ex.getStackTrace().toString(),"BUHierarchyTest");
        }
    }
   public void CompareIntegrationData()
   {
       try
       {
           sourcecon = DriverManager.getConnection(sourceconnectionUrl);
           String SQL = prop.getProperty("SourceIntegrationStore");

           stmt = sourcecon.createStatement();
           rs = stmt.executeQuery(SQL);
           Map<Integer,String>  mSourceMap=new HashMap<Integer,String>();
           Map<Integer,String>  mDestMap=new HashMap<Integer,String>();
           int sourcecount=0;
           String value="",buIDValue="";
           while (rs.next()) {
               sourcecount = rs.getInt(prop.getProperty("Integration_ID"));
               buIDValue=buIDValue+rs.getString(prop.getProperty("Integration_ID"))+",";
               value=value+rs.getString(prop.getProperty("Integration_Name"))+"%^";
               value=value+rs.getString(prop.getProperty("Currency_Code"))+"%^";

               mSourceMap.put(sourcecount,value);

           }
           sourcecon.close();
           buIDValue = buIDValue.substring(0, buIDValue.length()-1);
           SQL =  prop.getProperty("DestinationIntegrationStore");
           SQL = SQL + " WHERE Integration_ID IN ("+buIDValue +") ORDER BY Integration_ID ASC";
           stmt =  destconn.createStatement();
           rs = stmt.executeQuery(SQL);
           while (rs.next()) {
               sourcecount = rs.getInt(prop.getProperty("Integration_ID"));
               buIDValue=buIDValue+rs.getString(prop.getProperty("Integration_ID"))+",";
               value=value+rs.getString(prop.getProperty("Integration_Name"))+"%^";
               value=value+rs.getString(prop.getProperty("Currency_Code"))+"%^";

               mDestMap.put(sourcecount,value);
           }
           for (Object key : mSourceMap.keySet()) {
               Object value2 = mDestMap.get(key);
               if (value2 != null) {
                   Object value1 = mSourceMap.get(key);
                   String[] firstObjectvalue=value2.toString().split("%^");
                   String[] secondObjectvalue= value1.toString().split("%^");
                   for (int i=0;i<firstObjectvalue.length;i++)
                   {
                       if (!firstObjectvalue[i].contains(secondObjectvalue[i]))
                           CommonUtil.SQLDataReadFail("Source Table and Destination Table data don't match for "+value1,"Source Table and Destination Table count don't match","CompareCompanyStoreData");

                   }


               }
           }


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
    public void CompareCompanyStoreData()
    {
        try
        {
            sourcecon = DriverManager.getConnection(sourceconnectionUrl);
            String SQL = prop.getProperty("SourceCompanyStore");
            SQL=SQL+prop.getProperty("SourceCompanyStoreCont");
            SQL=SQL+prop.getProperty("SourceCompanyStoreCont1");
            SQL=SQL+prop.getProperty("SourceCompanyStoreCont2");
            stmt = sourcecon.createStatement();
            rs = stmt.executeQuery(SQL);
            Map<Integer,String>  mSourceMap=new HashMap<Integer,String>();
            Map<Integer,String>  mDestMap=new HashMap<Integer,String>();
            int sourcecount=0;
            String value="",buIDValue="";
            while (rs.next()) {
                sourcecount = rs.getInt(prop.getProperty("BU_ID"));
                buIDValue=buIDValue+rs.getString(prop.getProperty("BU_ID"))+",";
                value=value+rs.getString(prop.getProperty("BU_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Company_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Company_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Agency_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Agency_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Division_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Division_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Region_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Region_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("TimeZone_Name"));
                mSourceMap.put(sourcecount,value);

            }
            sourcecon.close();
            buIDValue = buIDValue.substring(0, buIDValue.length()-1);
            SQL =  prop.getProperty("DestinationCompanyStore");
            SQL = SQL + " WHERE BU_ID IN ("+buIDValue +") ORDER BY BU_ID ASC";
            stmt =  destconn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                sourcecount = rs.getInt(prop.getProperty("BU_ID"));
                buIDValue=buIDValue+rs.getString(prop.getProperty("BU_ID"))+",";
                value=value+rs.getString(prop.getProperty("BU_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Company_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Company_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Agency_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Agency_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Division_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Division_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("Region_ID"))+"%^";
                value=value+rs.getString(prop.getProperty("Region_Name"))+"%^";
                value=value+rs.getString(prop.getProperty("TimeZone_Name"));
                mDestMap.put(sourcecount,value);
            }
            for (Object key : mSourceMap.keySet()) {
                Object value2 = mDestMap.get(key);
                if (value2 != null) {
                    Object value1 = mSourceMap.get(key);
                    String[] firstObjectvalue=value2.toString().split("%^");
                    String[] secondObjectvalue= value1.toString().split("%^");
                    for (int i=0;i<firstObjectvalue.length;i++)
                    {
                        if (!firstObjectvalue[i].contains(secondObjectvalue[i]))
                            CommonUtil.SQLDataReadFail("Source Table and Destination Table data don't match for "+value1,"Source Table and Destination Table count don't match","CompareCompanyStoreData");

                    }


                }
            }


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
    public void CompareIntegrationTableCount()
    {

        try
        {
            sourcecon = DriverManager.getConnection(sourceconnectionUrl);
            String SQL = prop.getProperty("SourceIntegration");
            stmt = sourcecon.createStatement();
            rs = stmt.executeQuery(SQL);
            int sourcecount=0,destinationCount=0;
            while (rs.next()) {
                sourcecount = rs.getInt("sourcetotal");
            }
            sourcecon.close();
            SQL =  prop.getProperty("DestinationIntegration");
            stmt =  destconn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                destinationCount = rs.getInt("destinationtotal");
            }
            CommonUtil.compareNumberEquals(sourcecount,destinationCount,"Compare source table and destination table","Compare Table Count");


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
        CommonUtil.compareNumberEquals(sourcecount,destinationCount,"Compare source table and destination table","Compare Table Count");


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
