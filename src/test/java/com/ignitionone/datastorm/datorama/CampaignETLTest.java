package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.BaseClass;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.etl.TableLevel;
import com.ignitionone.datastorm.datorama.etl.ValidationStyle;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ignitionone.datastorm.datorama.etl.DataType.INT;
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.SUBSTRING;

/**
 * Created by ravi.peddi on 1/3/2017.
 */
public class CampaignETLTest extends BaseClass {
    //sql postgres
    private String SOURCE_TABLE = "campaigns";
    //sql nan
    private String DESTINATION_TABLE = "Display2_Campaigns";

    private final String REPORT_HEADER = "File level tests <BR>  Verify Row Count <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    private final String REPORT_TITLE = "Verify Total Row count between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE;
    protected String environment;

    //String srcSqlFile = "sql/dmsReadCache.sql";
    String srcSqlFile = "sql/postgres.sql";

    //String destSqlFile = "sql/dmsReadCache.sql";
    String destSqlFile = "sql/sqlNan.sql";

    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        this.environment = environment;
        init(this.environment, REPORT_HEADER, REPORT_TITLE);

    }

    @Test
    public void etlValidation() throws Exception {
        RecordLevel recordLevel = new RecordLevel();
        TableLevel tableLevel = new TableLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);

        Map<String, DestinationTable> validate = new HashMap<>();

        //sql nan                            Column_name, Unique Key, Type of field, validation
        DestinationTable campaignID = new DestinationTable("Campaign_ID", true, INT, MATCH);
        DestinationTable advertiserID = new DestinationTable("Advertiser_ID", false, INT, MATCH);
        DestinationTable campaignStatus = new DestinationTable("Campaign_Status", false, VARCHAR, MATCH);
        DestinationTable accountManagerID = new DestinationTable("Account_Manager_ID", false, INT, MATCH);
        DestinationTable isCostPaced = new DestinationTable("Is_Cost_Paced", false, VARCHAR, MATCH);
        DestinationTable isImpressionPaced = new DestinationTable("Is_Impression_Paced", false, VARCHAR, MATCH);
        DestinationTable campaignName = new DestinationTable("Campaign_Name", false, VARCHAR, MATCH);
        DestinationTable campaignFlightdateStart = new DestinationTable("Campaign_Flightdate_Start", false, VARCHAR, MATCH);
        DestinationTable campaignFlightdateEnd = new DestinationTable("Campaign_Flightdate_End", false, VARCHAR, MATCH);


        // Postgress
        validate.put("campaign_id", campaignID);
        validate.put("advertiser_id", advertiserID);
        validate.put("campaign_status", campaignStatus);
        validate.put("account_manager_id", accountManagerID);
        validate.put("is_cost_paced", isCostPaced);
        validate.put("is_impression_paced", isImpressionPaced);
        validate.put("name", campaignName);
        validate.put("flightdate_start", campaignFlightdateStart);
        validate.put("flightdate_end", campaignFlightdateEnd);
        String srcColumnNames="campaign_id,advertiser_id,campaign_status,account_manager_id,is_cost_paced,is_impression_paced,name,flightdate_start,flightdate_end";
        String destColumnNames="Campaign_ID,Advertiser_ID,Campaign_Status,Account_Manager_ID,Is_Cost_Paced,Is_Impression_Paced,Campaign_Name,Campaign_Flightdate_Start,Campaign_Flightdate_End";


        tableLevel.verifyTableCount(environment, srcSqlFile, SOURCE_TABLE, destSqlFile, DESTINATION_TABLE);
        extentReportUtil.endTest();

        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        tableLevel.verifyTableDataTypes(environment, validate, srcSqlFile, SOURCE_TABLE, destSqlFile, DESTINATION_TABLE);

        extentReportUtil.endTest();

        extentReportUtil.startTest("Record level tests <BR> Verify Data <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        recordLevel.verifyTableData(environment, validate, srcSqlFile, SOURCE_TABLE, srcColumnNames, destSqlFile, DESTINATION_TABLE,destColumnNames);

    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
