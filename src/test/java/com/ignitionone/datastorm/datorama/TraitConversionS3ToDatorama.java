package com.ignitionone.datastorm.datorama;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import com.ignitionone.datastorm.datorama.datoramaUtil.DatoramaCSVUtil;
import com.ignitionone.datastorm.datorama.datoramaUtil.FileTypeID;
import com.ignitionone.datastorm.datorama.datoramaUtil.JsonParser;
import com.ignitionone.datastorm.datorama.apiUtil.APIRequestBodyGenerator;
import com.ignitionone.datastorm.datorama.apiUtil.APIUtil;
import com.ignitionone.datastorm.datorama.etl.DatoramaNanETL;
import com.ignitionone.datastorm.datorama.etl.DestinationTable;
import com.ignitionone.datastorm.datorama.etl.RecordLevel;
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
 * Created by karthik.inuganti on 1/31/2017.
 */
public class TraitConversionS3ToDatorama extends ApiBaseClass{

    private static final String REPORT_HEADER = "Compare Trait Conversion File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Trait Conversion file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Trait Conversion API Response";
    String DESTINATION_TABLE = "Trait Conversion CSV File";
    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    String sqlFile = "sql/sqlNan.sql";
    File traitConversionFile;
    String traitConversionFilePath;
    String Bucket_Name = "thirdpartyreporting";
    String traitConversionFileName;
    String traitConversionDirectory = "Datorama/Archive/EventData/Summarized/Trait/Conversion";
    ETLUtil etlUtil = new ETLUtil();

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt,REPORT_HEADER, REPORT_TITLE);
    }

    @Test
    public void traitConversionToDatorama() throws Exception {

        //Execute the Third Party File Info Query to get the Corresponding Information (Report Start, End date, File Status ID, Record Count, File Name
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", FileTypeID.TRAIT_CONVERSION);
        reportStartDate=DatoramaNanETL.reportStartDate;
        reportEndDate=DatoramaNanETL.reportEndDate;
        traitConversionFileName=DatoramaNanETL.fileName;

        //Download the file from Amazon S3
        traitConversionFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, traitConversionFileName, traitConversionDirectory);
        traitConversionFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, traitConversionFilePath,"TraitConversionSummarizedData");

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        
        //Authenticate Datorama to fetch token for Requests
        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        //Get the API Response data from Datorama and convert into list of Strings
        String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getTraitConversion(reportStartDate, reportEndDate), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> traitConversionSrcList = parser.convertJsonToList(jsonObject);


        //Generate the source and destination Mapping using ETL util and excel reader
        List <String> traitConversionDestList = DatoramaCSVUtil.getCreativeDeliveryCSVData("TraitConversionSummarizedData.csv", ',', "|@|");
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/"+"Datorama_Mapping.xlsx", "Trait_Conversion_Mapper");


        extentReportUtil.logInfo("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE+" Report Start Date: "+reportStartDate+" Report End Date: "+reportEndDate);
        recordLevel.verifySrcWithDestData(mapper,traitConversionSrcList,traitConversionDestList);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}

