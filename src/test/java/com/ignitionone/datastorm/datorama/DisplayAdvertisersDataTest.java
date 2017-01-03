package com.ignitionone.datastorm.datorama;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by aaron.schwan on 12/21/2016.
 */
public class DisplayAdvertisersDataTest extends DisplayAdvertisersTransferTest {
    private static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    @Test
    public void AdvertiserCountTest() throws Exception {
        setUpTest("Advertiser Count test", "test results");

        String sourceStatement = "SELECT COUNT(1) FROM display.advertisers s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Advertisers] (nolock);";

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Advertiser Counts", "Advertiser Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    private Boolean compareDataSets(String sourceStatementSQL, String destinationStatementSQL) throws Exception {
        Class.forName(DRIVER).newInstance();
        Connection sourceConnection = DriverManager.getConnection(super.sourceConnectionString);
        Connection destinationConnection = DriverManager.getConnection(super.destinationConnectionString);

        Statement sourceStatement = sourceConnection.createStatement();
        Statement destinationStatement = destinationConnection.createStatement();

        ResultSet sourceResultSet = sourceStatement.executeQuery(sourceStatementSQL);
        ResultSet destinationResultSet = destinationStatement.executeQuery(destinationStatementSQL);

        while(sourceResultSet.next() && destinationResultSet.next()) {
        //    sourceResultSet.getObject
        }
        return true;
    }


}
