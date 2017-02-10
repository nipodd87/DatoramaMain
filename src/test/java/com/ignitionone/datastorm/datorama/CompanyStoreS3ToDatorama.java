package com.ignitionone.datastorm.datorama;

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


public class CompanyStoreS3ToDatorama extends ApiBaseClass {

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
    String Bucket_Name = "thirdpartyreporting";
    String companyStoreDirectory = "Datorama/Archive/MetaData/CompanyStoreMetaData";
    String sqlFile = "sql/sqlNan.sql";
    String storeProcFile = "sql/datorama_stored_procedure.sql";
    public static final int FILE_UPLOAD_SUCCESS = 3;
    DatoramaNanETL executor;
    String reportStartDate;
    String reportEndDate;
    int recordCount;
    int fileStatusID;
    String companyStoreFileName;

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
        executor.executeThirdPartyFileInfo(sqlFile, envt, "getThirdPartyFileInfo", "$fileTypeID$", "4");
        reportStartDate = DatoramaNanETL.reportStartDate;
        reportEndDate = DatoramaNanETL.reportEndDate;
        companyStoreFileName = DatoramaNanETL.fileName;
        //Download the file from Amazon S3
        companyStoreFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, companyStoreFileName, companyStoreDirectory);
        companyStoreFile = s3Functions.DownloadCSVFromS3(Bucket_Name, s3, companyStoreFilePath, "CompanyStoreSummarizedData");

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        Map<String, DestinationTable> validate = new HashMap<>();

        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token = from(AuthResponse).get("token");

        String Resp = APIUtil.getResportAsString("/query/execBatchQuery", APIRequestBodyGenerator.getCompanyStore(reportStartDate, reportEndDate), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> companyStoreSrcList = parser.convertJsonToList(jsonObject);

        DestinationTable advertiserId = new DestinationTable("BU_ID", true, VARCHAR, MATCH);
        DestinationTable advertiser = new DestinationTable("BU_Name", false, VARCHAR, MATCH);
        DestinationTable companyId = new DestinationTable("Company_ID", false, VARCHAR, MATCH);
        DestinationTable company = new DestinationTable("Company_Name", false, VARCHAR, IGNORE);
        DestinationTable agencyId = new DestinationTable("Agency_ID", false, VARCHAR, MATCH);
        DestinationTable agency = new DestinationTable("Agency_Name", false, VARCHAR, MATCH);
        DestinationTable divisionId = new DestinationTable("Division_ID", false, VARCHAR, MATCH);
        DestinationTable division = new DestinationTable("Division_Name", false, VARCHAR, MATCH);
        DestinationTable regionId = new DestinationTable("Region_ID", false, VARCHAR, MATCH);
        DestinationTable region = new DestinationTable("Region_Name", false, VARCHAR, MATCH);
        DestinationTable timeZone = new DestinationTable("TimeZone_Name", false, VARCHAR, MATCH);


        validate.put("Advertiser ID", advertiserId);
        validate.put("Advertiser", advertiser);
        validate.put("Company ID", companyId);
        validate.put("Company", company);
        validate.put("Agency ID", agencyId);
        validate.put("Agency", agency);
        validate.put("Division ID", divisionId);
        validate.put("Division", division);
        validate.put("Region ID", regionId);
        validate.put("Region", region);
        validate.put("Time Zone", timeZone);


        //Read Data from CSV in a List of String delimited with |@|
        List<String> companyStoreDestList = DatoramaCSVUtil.getCompanyStoreCSVData("CompanyStoreSummarizedData.csv", ',', "|@|");


        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        FileLevel fileLevel = new FileLevel();
        fileLevel.verifyTableCount(companyStoreSrcList, "Company Store API Response", companyStoreDestList, "Creative Delivery CSV Data");

        recordLevel.verifySrcWithDestData(validate, companyStoreSrcList, companyStoreDestList);
    }

    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}