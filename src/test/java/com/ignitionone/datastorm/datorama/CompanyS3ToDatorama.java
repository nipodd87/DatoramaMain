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
import static com.ignitionone.datastorm.datorama.etl.DataType.INT;
import static com.ignitionone.datastorm.datorama.etl.DataType.VARCHAR;
import static com.ignitionone.datastorm.datorama.etl.ValidationStyle.MATCH;
import static io.restassured.path.json.JsonPath.from;

/**
 * Created by nitin.poddar on 1/13/2017.
 */
public class CompanyS3ToDatorama extends ApiBaseClass{

    private static final String REPORT_HEADER = "Compare Company Store File between Amazon S3 and Datorama Stream Using API";
    private static final String REPORT_TITLE = "This test is to verify Company Store file is picked and processed properly by Datorama.";
    public JsonParser parser = new JsonParser();
    String envt;
    String SOURCE_TABLE = "Company Store CSV S3 File";
    String DESTINATION_TABLE = "Datorama API Response";
    S3Functions s3Functions = new S3Functions();
    AmazonS3 s3 = new AmazonS3Client();
    File testFile;
    String CompanyStoreFilePath;
    String Bucket_Name = "thirdpartyreporting";
    String Company_File_Name = "CompanyStoreMetaData_";
    String Company_Directory = "Datorama/Final/MetaData/CompanyStoreMetaData/";

    @BeforeClass
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;
        setUp(envt,REPORT_HEADER, REPORT_TITLE);
        CompanyStoreFilePath = s3Functions.getFilePathFromBucket(Bucket_Name, s3, Company_File_Name, Company_Directory);
        testFile=s3Functions.DownloadCSVFromS3(Bucket_Name,s3,CompanyStoreFilePath,"CompanyStoreMetaData");
    }

    @Test
    public void companyS3ToDatorama() throws Exception {

        RecordLevel recordLevel = new RecordLevel();
        extentReportUtil.logInfo("Reading Mapping between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        Map<String, DestinationTable> validate = new HashMap<>();

        String AuthResponse = APIUtil.getResponseAsString("/auth/authenticate", APIRequestBodyGenerator.getAuthRequestBody());
        String token=from(AuthResponse).get("token");

        String Resp = APIUtil.getResportAsString("/query/execBatchQuery",APIRequestBodyGenerator.getCompanyStoreRequestBody("2016-12-01", "2016-12-01"), token);
        JSONObject jsonObject = parser.convertStringtoJsonObj(Resp);
        List<String> companyList = parser.convertJsonToList(jsonObject);

        DestinationTable advertiserId = new DestinationTable("Advertiser ID", true, INT, MATCH);
        DestinationTable advertiser = new DestinationTable("Advertiser", false, VARCHAR, MATCH);
        DestinationTable companyId = new DestinationTable("Company ID", false, VARCHAR, MATCH);
        DestinationTable company = new DestinationTable("Company", false, VARCHAR, MATCH);
        DestinationTable agencyId = new DestinationTable("Agency ID", false, VARCHAR, MATCH);
        DestinationTable agency = new DestinationTable("Agency", false, VARCHAR, MATCH);
        DestinationTable divisionId = new DestinationTable("Division ID", false, VARCHAR, MATCH);
        DestinationTable division = new DestinationTable("Division", false, VARCHAR, MATCH);
        DestinationTable regionId = new DestinationTable("Region ID", false, VARCHAR, MATCH);
        DestinationTable region = new DestinationTable("Region", false, VARCHAR, MATCH);
        DestinationTable timeZone = new DestinationTable("Time Zone", false, VARCHAR, MATCH);

        validate.put("BU_ID", advertiserId);
        validate.put("BU_Name", advertiser);
        validate.put("Company_ID", companyId);
        validate.put("Company_Name", company);
        validate.put("Agency_ID", agencyId);
        validate.put("Agency_Name", agency);
        validate.put("Division_ID", divisionId);
        validate.put("Division_Name", division);
        validate.put("Region_ID", regionId);
        validate.put("Region_Name", region);
        validate.put("TimeZone_Name", timeZone);

        CSVandTextReader csvReader = new CSVandTextReader();
        System.out.println(testFile.getAbsolutePath());
        List <String> companyStoreData = csvReader.getCSVData(testFile.getAbsolutePath());

        extentReportUtil.startTest("File level tests <BR> Verify Data Types <BR> Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE, "Verify Data Types for each column between Source Table : " + SOURCE_TABLE + " and Destination Table : " + DESTINATION_TABLE);
        FileLevel fileLevel= new FileLevel();
        fileLevel.verifyTableCount(companyStoreData,"Company Store Data in CSV format", companyList, "Companystore data from Datorama");

        recordLevel.verifySrcWithDestData(validate, companyStoreData,companyList);
    }
    @AfterClass(alwaysRun = true)
    public void generateReport() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
