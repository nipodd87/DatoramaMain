package com.ignitionone.datastorm.datorama;


import com.ignitionone.datastorm.datorama.apiUtil.APIRequestBodyGenerator;
import com.ignitionone.datastorm.datorama.apiUtil.APIUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.DatoramaCSVUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.FileTypeID;
import com.ignitionone.datastorm.datorama.datoramaUtil.JsonParser;
import com.ignitionone.datastorm.datorama.etl.DatoramaNanETL;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.model.DeliveryMetrics;
import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.path.json.JsonPath.from;

/**
 * Created by karthik.inuganti on 2/16/2017.
 */
public class CreativeConversionDataHierarchyETLTest extends ApiBaseClass {
    private static final String REPORT_HEADER = "Compare Creative Level Data Hierarchical counts between SQL NAN and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to compare at different hierarchical levels to test the counts between SQL NAN and Datorama APi End Point.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "SQL Nan tables";
    String DESTINATION_TABLE = "Datorama API Response";
    String sqlFile = "sql/sqlNan.sql";
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    String advertiser_id="advertiser_id";

    int fileStatusID;
    String domainDeliveryFileName;
    DeliveryMetrics metrics;
    public int total_click_based_conversion;
    public int total_view_based_conversion;
    public int total_impressions;
    public int total_clicks;
    public double total_cost;
    ETLUtil etlUtil = new ETLUtil();
    RecordLevel recordLevel = new RecordLevel();

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt, REPORT_HEADER, REPORT_TITLE);
    }

    @Test
    public void domainDeliveryToDatorama() throws Exception {

        //Execute the Third Party File Info Query to get the Corresponding Information Report Start Date and Report End Date
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", FileTypeID.CREATIVE_DELIVERY);
        reportStartDate = DatoramaNanETL.reportStartDate;
        reportEndDate = DatoramaNanETL.reportEndDate;
        domainDeliveryFileName = DatoramaNanETL.fileName;
        fileStatusID = DatoramaNanETL.fileStatusID;

        //Execute the SQL NAN Query with the Start and End Date to get the Total Counts.
        //Check Measurement Counts for Impressions, Cost and Clicks from the table

        executor.getMeasurementCount(sqlFile, envt, "getHierarchicalCountCreativeLevelForConversion", "$START_DATE$", reportStartDate, "$END_DATE$", reportEndDate,"$ColumnName$",advertiser_id);
        total_click_based_conversion=DatoramaNanETL.total_click_based_conversion;
        total_view_based_conversion=DatoramaNanETL.total_view_based_conversion;

        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        //Get the API Response data from Datorama and convert into list of Strings
        //String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getCreativeConversionDataHierarchy(reportStartDate, reportEndDate), token);
        //JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        //List<String> creativeConversionSrcList = parser.convertJsonToList(jsonObject);



        CommonUtil.compareNumberEquals(total_click_based_conversion, total_view_based_conversion, "Record Count Between Stored Procedure and Amazon S3", "Verify record count between Store Procedure and Amazon S3 csv file");

        extentReportUtil.endTest();


    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
