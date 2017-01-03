package com.ignitionone.datastorm.datorama;
import com.ignitionone.datastorm.datorama.util.PropertyLoader;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.ignitionone.datastorm.datorama.util.Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author aaron.schwan
 * @version 2016/12/20
 */
public class DisplayAdvertisersTransferTest extends BaseClass {

    private static final String REPORT_HEADER = "Display Advertiser Data Transfer Test";
    private static final String REPORT_TITLE = "This test is to verify if display structure data is properly transferred to SQL Read";
    protected String sourceConnectionString = "jdbc:postgresql://ddsr7ulxnpcdhu.cxz2f6nkkeju.us-east-1.rds.amazonaws.com/display?user=qaauto&password=Qaaut0";
    protected String destinationConnectionString = "jdbc:sqlserver://ATL02SQLDEV02.searchignite.local\\ATL_SQLPROC_DEV;databaseName=DMS;integratedsecurity=true;";
    protected String environment;

    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        this.environment = environment;
        init(this.environment, REPORT_HEADER, REPORT_TITLE);

    }

    protected void setUpTest(String testName, String testDetails) {
        extentReportUtil.startTest(testName, REPORT_TITLE);
        extentReportUtil.logInfo(testDetails);
    }

    @Test
    public void AdvertiserCountTest() throws Exception {
        setUpTest("Advertiser Count test", "test results");

        String sourceStatement = "SELECT COUNT(1) FROM display.advertisers s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Advertisers] (nolock);";

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Advertiser Counts", "Advertiser Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    @Test
    public void CampaignCountTest() throws Exception {
        String sourceStatement = "SELECT COUNT(1) FROM display.advertisers s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Advertisers] (nolock);";

        setUpTest("Campaign Count test", "test results");

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Campaign Counts", "Campaign Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    @Test
    public void CampaignLineItemCountTest() throws Exception {
        String sourceStatement = "SELECT COUNT(1) FROM display.campaign_targets s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Campaign_Targets] (nolock);";

        setUpTest("Campaign Line Item Count test", "test results");

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Campaign Line Item Counts", "Campaign Line Item Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    @Test
    public void CampaignLineItemBudgetCapsCountTest() throws Exception {
        String sourceStatement = "SELECT COUNT(1) FROM display.campaign_target_budget_caps s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Campaign_Targets_Budget_Caps] (nolock);";

        setUpTest("Campaign Line Item Budget Caps Count test", "test results");

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Campaign Line Item Budget Caps Counts", "Campaign Line Item Budget Caps Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    @Test
    public void CreativeCountTest() throws Exception {
        String sourceStatement = "SELECT COUNT(1) FROM display.creatives s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Creatives] (nolock);";

        setUpTest("Creative Count test", "test results");

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Creative Counts", "Creative Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    @Test
    public void TraitCountTest() throws Exception {
        String sourceStatement = "SELECT COUNT(1) FROM display.traits s (nolock);";
        String destinationStatement = "SELECT count(1) FROM [dbo].[Display2_Traits] (nolock);";

        setUpTest("Trait Count test", "test results");

        ArrayList<Integer> results = getCounts(sourceStatement, destinationStatement);

        commonUtil.compareNumberEquals(results.get(0), results.get(1), "Trait Counts", "Trait Counts match between PostGreSQL and SQLRead");
        extentReportUtil.endTest();
    }

    protected ArrayList<Integer> getCounts(String sourceStatement, String destinationStatement) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList<Integer> results = new ArrayList<Integer>(2);

        List<String> sourceData = Jdbc.executeSQL(sourceStatement, this.sourceConnectionString);
        results.add(0, Integer.parseInt(sourceData.get(1)));
        List<String> destinationData = Jdbc.executeSQL(destinationStatement, this.destinationConnectionString);
        results.add(1,Integer.parseInt(destinationData.get(1)));

        return results;
    }

    protected ArrayList<Integer> getData(String sourceStatement, String destinationStatement) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList<Integer> results = new ArrayList<Integer>(2);

        List<String> sourceData = Jdbc.executeSQL(sourceStatement, this.sourceConnectionString);
        results.add(0, Integer.parseInt(sourceData.get(1)));
        List<String> destinationData = Jdbc.executeSQL(destinationStatement, this.destinationConnectionString);
        results.add(1,Integer.parseInt(destinationData.get(1)));

        return results;
    }
}
