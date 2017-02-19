package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.apiUtil.APIRequestBodyGenerator;
import com.ignitionone.datastorm.datorama.apiUtil.APIUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.JsonParser;
import com.ignitionone.datastorm.datorama.etl.DatoramaNanETL;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.model.DeliveryMetrics;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;

import static io.restassured.path.json.JsonPath.from;

/**
 * Created by nitin.poddar on 2/17/2017.
 */
public class CreativeDeliveryCampaignTargetHierarchy extends ApiBaseClass {

    private static final String REPORT_HEADER = "Compare Creative Level Campaign Target Hierarchical counts between SQL NAN and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to compare at different hierarchical levels to test the counts between SQL NAN and Datorama API Endpoints";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "SQL Nan tables";
    String DESTINATION_TABLE = "Datorama API Response";
    String sqlFile = "sql/sqlNan.sql";
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    ETLUtil etlUtil = new ETLUtil();
    RecordLevel recordLevel = new RecordLevel();

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt, REPORT_HEADER, REPORT_TITLE);
    }

    @Test
    public void creativeLevelCampaignTest() throws Exception {

        //Execute the Third Party File Info Query to get the Corresponding Information Report Start Date and Report End Date
        executor = new DatoramaNanETL();

        //Get Report Start and End Date for Creative Delivery File based on File Type ID
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", "1");
        reportStartDate = DatoramaNanETL.reportStartDate;
        reportEndDate = DatoramaNanETL.reportEndDate;

        //Execute the SQL NAN Query with the Start and End Date to get the Total Counts.
        //Check Measurement Counts for Impressions, Cost and Clicks from the table
        List<String> creativeLevelSQLList = executor.executeQuery(sqlFile, envt, "getCountCreativeDeliveryLevel", "$ColumnName$", "campaign_id", "$START_DATE$", reportStartDate, "$END_DATE$", reportEndDate);
        System.out.println();
        //Authenticate Datorama to fetch Authentication Token
        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        //Execute the API query to fetch the Creative Delivery data
        String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getCreativeDeliveryLevelCampaignTarget(reportStartDate, reportEndDate), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> creativeLevelAPIList = parser.convertJsonToList(jsonObject);

        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);

        //Create Source and Destination data mapping using ETL util methods from excel sheets
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/"+"Datorama_Creative_Hierarchcial.xlsx", "CreativeDelivery_CampaignTarget");

        extentReportUtil.startTest("Creative Delivery <BR> Campaign Target ID: Get Measurement Counts  <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE+" Report Start Date:"+reportStartDate+" Report End Date: "+reportEndDate);
        recordLevel.verifySrcWithDestData(mapper,creativeLevelSQLList,creativeLevelAPIList);
    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}