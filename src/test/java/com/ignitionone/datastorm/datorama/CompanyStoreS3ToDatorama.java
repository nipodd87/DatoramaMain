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
import com.ignitionone.datastorm.datorama.etl.FileLevel;
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


public class CompanyStoreS3ToDatorama extends BaseClass {

    private static final String REPORT_HEADER = "Compare Company Store File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Company Store file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Datorama API Response";
    String DESTINATION_TABLE = "Company Store CSV S3 File";
    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File companyStoreFile;
    String companyStoreFilePath;
    String companyStoreDirectory = "Datorama/Archive/MetaData/CompanyStoreMetaData";
    String sqlFile = "sql/sqlNan.sql";
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    int recordCount;
    int fileStatusID;
    String companyStoreFileName;
    ETLUtil etlUtil = new ETLUtil();
    FileLevel fileLevel = new FileLevel();


    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt, REPORT_HEADER, REPORT_TITLE);
        //companyStoreFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, companyStoreFileName, companyStoreDirectory);
        //companyStoreFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3, companyStoreFilePath,"CreativeDeliverySummarizedData");
    }

    @Test
    public void companyStoreToDatorama() throws Exception {
        //Execute the Third Party File Info Query to get the Corresponding Information Report Start Date and Report End Date
        executor = new DatoramaNanETL();
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", FileTypeID.COMPANY_STORE);
        reportStartDate = DatoramaNanETL.reportStartDate;
        reportEndDate = DatoramaNanETL.reportEndDate;
        companyStoreFileName = DatoramaNanETL.fileName;
        recordCount = DatoramaNanETL.recordCount;
        fileStatusID = DatoramaNanETL.fileStatusID;

        //Download the file from Amazon S3
        companyStoreFilePath = s3Functions.getFilePathFromBucket(bucketName, s3, companyStoreFileName, companyStoreDirectory);
        companyStoreFile = s3Functions.DownloadCSVFromS3(bucketName, s3, companyStoreFilePath, "CompanyStoreSummarizedData");

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);

        //Authenticate Datorama to fetch Authentication Token
        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token = from(AuthResponse).get("token");

        //Execute the API query to fetch the Company Store data
        String Resp = APIUtil.getResportAsString("/query/execBatchQuery", APIRequestBodyGenerator.getCompanyStore(reportStartDate, reportEndDate, brandId), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> companyStoreSrcList = parser.convertJsonToList(jsonObject);


        //Read Data from CSV in a List of String delimited with |@|
        List<String> companyStoreDestList = DatoramaCSVUtil.getCompanyStoreCSVData("CompanyStoreSummarizedData.csv", ',', "|@|");

        //Create Source and Destination data mapping using ETL util methods from excel sheets
        Map<String, DestinationTable> mapper = etlUtil.getMapSet(System.getProperty("user.dir") + "/" + "Datorama_Mapping.xlsx", "Company_Store_Mapper");

        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE + " Report Start Date:" + reportStartDate + " Report End Date: " + reportEndDate);
        fileLevel.verifyTableCount(companyStoreSrcList, "Company Store API Response", companyStoreDestList, "Company Store CSV Data");

        extentReportUtil.startTest("Record level tests <BR> Verify Data  <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE + " Report Start Date:" + reportStartDate + " Report End Date: " + reportEndDate);
        recordLevel.verifySrcWithDestData(mapper, companyStoreSrcList, companyStoreDestList);
    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}