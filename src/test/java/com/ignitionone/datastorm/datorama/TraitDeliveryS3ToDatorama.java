
package com.ignitionone.datastorm.datorama;

import au.com.bytecode.opencsv.CSVReader;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import com.ignitionone.datastorm.datorama.datoramaUtil.DatoramaCSVUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.JsonParser;
import com.ignitionone.datastorm.datorama.apiUtil.APIRequestBodyGenerator;
import com.ignitionone.datastorm.datorama.apiUtil.APIUtil;
import com.ignitionone.datastorm.datorama.etl.DatoramaNanETL;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.FileLevel;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
import com.ignitionone.datastorm.datorama.util.CSVandTextReader;
import com.ignitionone.datastorm.datorama.util.ETLUtil;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileReader;
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

public class TraitDeliveryS3ToDatorama extends ApiBaseClass{

    private static final String REPORT_HEADER = "Compare Trait Delivery File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Trait Delivery file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Trait Delivery API Response";
    String DESTINATION_TABLE = "Trait Delivery CSV File";
    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File traitDeliveryFile;
    String traitDeliveryFilePath;
    String Bucket_Name = "thirdpartyreporting";
    String traitDeliveryFileName;
    String traitDeliveryDirectory = "Datorama/Archive/EventData/Summarized/Trait/Delivery";
    String sqlFile = "sql/sqlNan.sql";
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    ETLUtil etlUtil = new ETLUtil();

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt,REPORT_HEADER, REPORT_TITLE);
         //traitDeliveryFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, traitDeliveryFileName, traitDeliveryDirectory);
         //traitDeliveryFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, traitDeliveryFilePath,"TraitDeliverySummarizedData");
    }

    @Test
    public void traitDeliveryToDatorama() throws Exception {

        //Execute the Third Party File Info Query to get the Corresponding Information Report Start Date and Report End Date
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", "3");
        reportStartDate=DatoramaNanETL.reportStartDate;
        reportEndDate=DatoramaNanETL.reportEndDate;
        traitDeliveryFileName=DatoramaNanETL.fileName;

        //Download the file from Amazon S3
        traitDeliveryFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, traitDeliveryFileName, traitDeliveryDirectory);
        traitDeliveryFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, traitDeliveryFilePath,"TraitDeliverySummarizedData");

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);

        //Authenticate Datorama to fetch Authentication Token
        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        //Execute the API query to fetch the Trait Delivery data
        String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getTraitDelivery(reportStartDate, reportEndDate), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> traitDeliverySrcList = parser.convertJsonToList(jsonObject);

        //Read Data from CSV in a List of String delimited with |@|
        List <String> traitDeliveryDestList = DatoramaCSVUtil.getTraitDeliveryCSVData("TraitDeliverySummarizedData.csv", ',', "|@|");

        //Create Source and Destination data mapping using ETL util methods from excel sheets
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/"+"Datorama_Mapping.xlsx", "Trait_Delivery_Mapper");

        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE+" Report Start Date:"+reportStartDate+" Report End Date: "+reportEndDate);
        recordLevel.verifySrcWithDestData(mapper,traitDeliverySrcList,traitDeliveryDestList);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}

