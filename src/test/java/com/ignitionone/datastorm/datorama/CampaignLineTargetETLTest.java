package com.ignitionone.datastorm.datorama;

/**
 * Created by nitin.poddar on 1/5/2017.
 */

import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.etl.TableLevel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ignitionone.datastorm.datorama.etl.DataType.INT;
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;

public class CampaignLineTargetETLTest extends BaseClass {
    //sql postgres
    private String SOURCE_TABLE = "campaign_targets";
    //sql nan
    private String DESTINATION_TABLE = "Display2_Campaign_Targets";

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
        DestinationTable campaignTargetID = new DestinationTable("Campaign_Target_ID", true, INT, MATCH);
        DestinationTable campaignID = new DestinationTable("Campaign_ID", false, INT, MATCH);
        DestinationTable campaignTargetStatus = new DestinationTable("Campaign_Target_Status", false, VARCHAR, MATCH);
        DestinationTable campaignTargetIsCostPaced = new DestinationTable("Campaign_Target_Is_Cost_Paced", false, VARCHAR, MATCH);
        DestinationTable campaignTargetIsImpressionPaced = new DestinationTable("Campaign_Target_Is_Impression_Paced", false, VARCHAR, MATCH);
        DestinationTable campaignTargetFlightdateEnd = new DestinationTable("Campaign_Target_Flightdate_End", false, VARCHAR, MATCH);
        DestinationTable campaignFlightdateStart = new DestinationTable("Campaign_Target_Flightdate_Start", false, VARCHAR, MATCH);



        // Postgress
        validate.put("campaign_target_id", campaignTargetID);
        validate.put("campaign_id", campaignID);
        validate.put("target_status", campaignTargetStatus);
        validate.put("is_cost_paced", campaignTargetIsCostPaced);
        validate.put("is_impression_paced", campaignTargetIsImpressionPaced);
        validate.put("flightdate_end", campaignTargetFlightdateEnd);
        validate.put("flightdate_start", campaignFlightdateStart);

        String srcColumnNames="campaign_target_id,campaign_id,target_status,is_cost_paced,is_impression_paced,flightdate_end,flightdate_start";
        String destColumnNames="Campaign_Target_ID,Campaign_ID,Campaign_Target_Status,Campaign_Target_Is_Cost_Paced,Campaign_Target_Is_Impression_Paced,Campaign_Target_Flightdate_End,Campaign_Target_Flightdate_Start";


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


