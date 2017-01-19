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
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;
import static io.restassured.path.json.JsonPath.from;

/**
 * Created by nitin.poddar on 1/13/2017.
 */
public class CreativeConversionS3ToDatorama extends ApiBaseClass{

    private static final String REPORT_HEADER = "Compare Creative Conversion File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Creative Conversion file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Creative Conversion CSV S3 File";
    String DESTINATION_TABLE = "Datorama API Response";
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
        CreativeConversionFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, CreativeConversionFileName, CreativeConversionDirectory);
        creativeConversionFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, CreativeConversionFilePath,"CreativeConversionSummarizedData");
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
        List<String> creativeConversionList = parser.convertJsonToList(jsonObject);

        DestinationTable day  = new DestinationTable("Day", true, VARCHAR, MATCH);
        DestinationTable advertiserId = new DestinationTable("Advertiser ID", false, VARCHAR, MATCH);
        DestinationTable campaignId = new DestinationTable("Campaign ID", false, VARCHAR, MATCH);
        DestinationTable campaign = new DestinationTable("Campaign", false, VARCHAR, MATCH);
        DestinationTable accountManagerId = new DestinationTable("Account Manager ID", false, VARCHAR, MATCH);
        DestinationTable campaignStatus = new DestinationTable("Campaign Status", false, VARCHAR, MATCH);
        DestinationTable campaignTargetId = new DestinationTable("Campaign Target ID", true, VARCHAR, MATCH);
        DestinationTable campaignTargetName = new DestinationTable("Campaign Target Name", false, VARCHAR, MATCH);
        DestinationTable creativeId = new DestinationTable("Creative ID", true, VARCHAR, MATCH);
        DestinationTable creativeName = new DestinationTable("Creative", false, VARCHAR, MATCH);
        DestinationTable integrationId = new DestinationTable("Publisher ID", true, VARCHAR, MATCH);
        DestinationTable integrationName = new DestinationTable("Publisher", false, VARCHAR, MATCH);
        DestinationTable currencyCode = new DestinationTable("Currency (Original)", false, VARCHAR, MATCH);
        DestinationTable clickBasedConversions = new DestinationTable("Click Based Conversions", false, VARCHAR, MATCH);
        DestinationTable impressionBasedConversions = new DestinationTable("View Based Conversions", false, VARCHAR, MATCH);

        validate.put("Date", day);
        validate.put("BU_ID", advertiserId);
        validate.put("Campaign_ID", campaignId);
        validate.put("Campaign_Name", campaign);
        validate.put("Account_Manager_ID", accountManagerId);
        validate.put("Campaign_Status", campaignStatus);
        validate.put("Campaign_Target_ID", campaignTargetId);
        validate.put("Campaign_Target_Name", campaignTargetName);
        validate.put("Creative_ID", creativeId);
        validate.put("Creative_Name", creativeName);
        validate.put("Integration_ID", integrationId);
        validate.put("Integration_Name", integrationName);
        validate.put("Currency_Code", currencyCode);
        validate.put("Click_Based_Conversions", clickBasedConversions);
        validate.put("Impression_Based_Conversions", impressionBasedConversions);

        CSVandTextReader csvReader = new CSVandTextReader();

        List <String> creativeConversionData = csvReader.getCSVData(System.getProperty("user.dir")+"/"+"CreativeConversionSummarizedData.csv");


        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        FileLevel fileLevel= new FileLevel();
        fileLevel.verifyTableCount(creativeConversionData,"Company Store Data in CSV format", creativeConversionList, "Companystore data from Datorama");

        recordLevel.verifySrcWithDestData(validate, creativeConversionData,creativeConversionList);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
