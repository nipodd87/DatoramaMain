package com.ignitionone.datastorm.datorama;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import com.ignitionone.datastorm.datorama.datoramaUtil.JsonParser;
import com.ignitionone.datastorm.datorama.apiUtil.APIRequestBodyGenerator;
import com.ignitionone.datastorm.datorama.apiUtil.APIUtil;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.FileLevel;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.util.CSVandTextReader;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.IGNORE;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.SUBSTRING;
import static io.restassured.path.json.JsonPath.from;

/**
 * Created by nitin.poddar on 1/13/2017.
 */
public class CreativeConversionS3ToDatorama extends ApiBaseClass{

    private static final String REPORT_HEADER = "Compare Creative Conversion File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Creative Conversion file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Datorama API Response";
    String DESTINATION_TABLE = "Creative Conversion CSV S3 File";
    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File creativeConversionFile;
    String CreativeConversionFilePath;
    String Bucket_Name = "thirdpartyreporting";
    String CreativeConversionFileName = "SummarizedCreativeConversionEventData_";
    String CreativeConversionDirectory = "Datorama/Final/EventData/Summarized/Creative/Conversion";

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt,REPORT_HEADER, REPORT_TITLE);
       // creativeConversionFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, creativeConversionFileName, creativeConversionDirectory);
        //creativeConversionFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, creativeConversionFilePath,"CreativeConversionSummarizedData");
    }

    @Test
    public void creativeConversionToDatorama() throws Exception {

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        Map<String, DestinationTable> validate = new HashMap<>();

        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getCreativeConversion("2017-01-01", "2017-01-31"), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> creativeConversionSrcList = parser.convertJsonToList(jsonObject);

        DestinationTable day  = new DestinationTable("Date", true, VARCHAR, MATCH);
        DestinationTable advertiserId = new DestinationTable("BU_ID", false, VARCHAR, MATCH);
        DestinationTable campaignId = new DestinationTable("Campaign_ID", false, VARCHAR, MATCH);
        DestinationTable campaign = new DestinationTable("Campaign_Name", false, VARCHAR, MATCH);
        DestinationTable campaignFlightStartDate = new DestinationTable("Campaign_Flightdate_Start", false, VARCHAR, IGNORE);
        DestinationTable campaignFlightEndDate = new DestinationTable("Campaign_Flightdate_End", false, VARCHAR, IGNORE);
        DestinationTable accountManagerId = new DestinationTable("Account_Manager_ID", false, VARCHAR, MATCH);
        DestinationTable campaignStatus = new DestinationTable("Campaign_Status", false, VARCHAR, MATCH);
        DestinationTable advertiserSourceId = new DestinationTable("Advertiser_Source_ID", false, VARCHAR, MATCH);
        DestinationTable advertiserSource = new DestinationTable("Advertiser_Source_Name", false, VARCHAR, MATCH);
        DestinationTable campaignTargetId = new DestinationTable("Campaign_Target_ID", true, VARCHAR, MATCH);
        DestinationTable campaignTargetName = new DestinationTable("Campaign_Target_Name", false, VARCHAR, MATCH);
        DestinationTable campaignTargetStartDate = new DestinationTable("Campaign_Target_Flightdate_Start", false, VARCHAR, IGNORE);
        DestinationTable campaignTargetEndDate = new DestinationTable("Campaign_Target_Flightdate_End", false, VARCHAR, IGNORE);
        DestinationTable campaignTargetStatus = new DestinationTable("Campaign_Target_Status", false, VARCHAR, MATCH);
        DestinationTable creativeId = new DestinationTable("Creative_ID", true, VARCHAR, MATCH);
        DestinationTable creativeName = new DestinationTable("Creative_Name", false, VARCHAR, MATCH);
        DestinationTable creativeMessageId = new DestinationTable("Creative_Message_ID", false, VARCHAR, MATCH);
        DestinationTable creativeMessageName = new DestinationTable("Creative_Message_Name", false, VARCHAR, MATCH);
        DestinationTable adserverPlacementId = new DestinationTable("Adserver_Placement_ID", false, VARCHAR, MATCH);
        DestinationTable adserverPlacementName = new DestinationTable("Adserver_Placement_Name", false, VARCHAR, MATCH);
        DestinationTable integrationId = new DestinationTable("Integration_ID", true, VARCHAR, MATCH);
        DestinationTable integrationName = new DestinationTable("Integration_Name", false, VARCHAR, MATCH);
        DestinationTable currencyCode = new DestinationTable("Currency_Code", false, VARCHAR, SUBSTRING);
        DestinationTable clickBasedConversions = new DestinationTable("Click_Based_Conversions", false, VARCHAR, SUBSTRING);
        DestinationTable impressionBasedConversions = new DestinationTable("Impression_Based_Conversions", false, VARCHAR, SUBSTRING);

        validate.put("Day", day);
        validate.put("Advertiser ID", advertiserId);
        validate.put("Campaign ID", campaignId);
        validate.put("Campaign", campaign);
        validate.put("Campaign Flight Start Date", campaignFlightStartDate);
        validate.put("Campaign Flight End Date", campaignFlightEndDate);
        validate.put("Account Manager ID", accountManagerId);
        validate.put("Campaign Status", campaignStatus);
        validate.put("Advertiser Source ID", advertiserSourceId);
        validate.put("Advertiser Source", advertiserSource);
        validate.put("Line Item ID", campaignTargetId);
        validate.put("Line Item", campaignTargetName);
        validate.put("LineItem Flight Start Date", campaignTargetStartDate);
        validate.put("LineItem Flight End Date", campaignTargetEndDate);
        validate.put("LineItem Status", campaignTargetStatus);
        validate.put("Creative ID", creativeId);
        validate.put("Creative", creativeName);
        validate.put("Message Group ID", creativeMessageId);
        validate.put("Message Group", creativeMessageName);
        validate.put("Adserver Placement ID", adserverPlacementId);
        validate.put("Adserver Placement", adserverPlacementName);
        validate.put("Publisher ID", integrationId);
        validate.put("Publisher", integrationName);
        validate.put("Currency (Original)", currencyCode);
        validate.put("Click Based Conversions", clickBasedConversions);
        validate.put("View Based Conversions", impressionBasedConversions);


        CSVandTextReader csvReader = new CSVandTextReader();

        List <String> creativeConversionDestList = csvReader.getCSVData(System.getProperty("user.dir")+"/"+"CreativeConversionSummarizedData.csv");


        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        FileLevel fileLevel= new FileLevel();
        fileLevel.verifyTableCount(creativeConversionSrcList,"Creative Conversion API Response", creativeConversionDestList, "Creative Conversion CSV Data");

        recordLevel.verifySrcWithDestData(validate,creativeConversionSrcList,creativeConversionDestList);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
