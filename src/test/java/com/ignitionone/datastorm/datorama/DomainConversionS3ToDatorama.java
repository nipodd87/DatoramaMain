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
 * Created by nitin.poddar on 1/13/2017.
 */
public class DomainConversionS3ToDatorama extends BaseClass{

    private static final String REPORT_HEADER = "Compare Domain Conversion File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Domain Conversion file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Datorama API Response";
    String DESTINATION_TABLE = "Domain Conversion CSV S3 File";
    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File domainConversionFile;
    String domainConversionFilePath;
    String domainConversionDirectory = "Datorama/Archive/EventData/Summarized/Domain/Conversion";
    String sqlFile = "sql/sqlNan.sql";
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    String domainConversionFileName;
    ETLUtil etlUtil = new ETLUtil();

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt,REPORT_HEADER, REPORT_TITLE);
    }

    @Test
    public void domainConversionToDatorama() throws Exception {

        //Execute the Third Party File Info Query to get the Corresponding Information Report Start Date and Report End Date
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", FileTypeID.DOMAIN_CONVERSION);
        reportStartDate=DatoramaNanETL.reportStartDate;
        reportEndDate=DatoramaNanETL.reportEndDate;
        domainConversionFileName=DatoramaNanETL.fileName;

        //Download the file from Amazon S3
        domainConversionFilePath = s3Functions.getFilePathFromBucket(bucketName, s3, domainConversionFileName, domainConversionDirectory);
        domainConversionFile=s3Functions.DownloadCSVFromS3(bucketName,s3, domainConversionFilePath,"DomainConversionSummarizedData");

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);

        //Authenticate Datorama to fetch token for Requests
        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        //Get the API Response data from Datorama and convert into list of Strings
        String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getDomainConversion(reportStartDate, reportEndDate), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> domainConversionSrcList = parser.convertJsonToList(jsonObject);

        //Generate the source and destination Mapping using ETL util and excel reader
        List <String> domainConversionDestList = DatoramaCSVUtil.getDomainDeliveryCSVData("DomainConversionSummarizedData.csv", ',', "|@|");
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir")+"/"+"Datorama_Mapping.xlsx", "Domain_Conversion_Mapper");

        extentReportUtil.logInfo("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE+" Report Start Date: "+reportStartDate+" Report End Date: "+reportEndDate);
        recordLevel.verifySrcWithDestData(mapper,domainConversionSrcList,domainConversionDestList);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
