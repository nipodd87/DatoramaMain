package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import com.ignitionone.datastorm.datorama.SqlNaan.SQLReadDataValidation;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by karthik.inuganti on 12/13/2016.
 */
public class DelTest extends BaseClass {

    private static final String REPORT_HEADER = "ConversionsTestDev";
    private static final String REPORT_TITLE = "This test is to verify that the urls's redirect";
    S3Functions s3Functions = new S3Functions();

    String envt;

    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"environment"})
    public void setUp(String environment) throws Exception {
        envt = environment;

        init(envt, REPORT_HEADER, REPORT_TITLE);
    }

    public void setUpTest(String testName, String testDetails) {
        extentReportUtil.startTest(testName, REPORT_TITLE);
        extentReportUtil.logInfo(testDetails);
    }

    @Test
    public void CompanyStoreDataCountValidation() throws Exception{
        setUpTest("CompanyStoreDataCountValidation", "SQLReadBusinessUnityDataValidation results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.CompareBusinessUnitTableCount();
        extentReportUtil.endTest();

    }

    @Test
    public void CompanyStoreIntegrationStagingTableCleanedValidation() throws Exception{
        setUpTest("CompanyStoreIntegrationStagingTableCleanedValidation", "SQLBusinessUnitStagingTableCleaned results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.BusinessUnitStagingTableCleaned();
        extentReportUtil.endTest();

    }

    @Test
    public void CompanyStoreTableColumnsValidation() throws Exception{
        setUpTest("CompanyStoreTableColumnValidation", "SQLBusinessUnitTableValidation results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.CompareCompanyStoreTableColumns();
        extentReportUtil.endTest();

    }

    @Test
    public void IntegrationTableColumnsValidation() throws Exception{
        setUpTest("IntegrationTableColumnValidation", "SQLIntegrationTableValidation results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.CompareIntegrationTableColumns();
        extentReportUtil.endTest();

    }
    @Test
    public void CompareCompanyStoreDataValidation() throws Exception{
        setUpTest("CompareCompanyStoreDataValidation", "CompareCompanyStoreData results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.CompareCompanyStoreData();
        extentReportUtil.endTest();

    }
    @Test
    public void CompareIntegrationTableCount() throws Exception{
        setUpTest("CompareIntegrationTableCountValidation", "CompareIntegrationTableCount results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.CompareIntegrationTableCount();
        extentReportUtil.endTest();

    }


    @Test
    public void CompareIntegrationDataValidation() throws Exception{
        setUpTest("CompareIntegrationDataValidation", "CompareIntegrationStoreData results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
        _sqlRead.CompareIntegrationData();
        extentReportUtil.endTest();

    }

    @Test
    public void BUHierarchyTestValidation() throws Exception{
        setUpTest("BUHierarchyValidation", "SQLBUHierarchyTestValidation results");
        SQLReadDataValidation _sqlRead=new SQLReadDataValidation();
         _sqlRead.BUHierarchyTest();

         extentReportUtil.endTest();

    }


   /* @Test
    public void s3Test() throws Exception {
        setUpTest("s3Test", "s3Test results");

        String filepath = s3Functions.getFilePathFromBucket("thirdpartyreporting", s3, "CompanyStoreMetaData_","Datorama/Final/EventData/Summarized/Company/");
        commonUtil.containsText(filepath, "Get event File path for Company Store", "File path for company store");
        Long filesize = s3Functions.getFileSize("thirdpartyreporting", s3,filepath);
        // String size= "filesize:-"+filesize;

        Long sz1 = filesize;
        commonUtil.containsValue(sz1, "Get event File size for Company Store", "File size for company store");
        //commonUtil.containsText(size,"Get event File size for Company Store","File size for company store");
       // String metadata = s3Functions.getMetaDataOfFile("thirdpartyreporting", s3, filepath);
       // commonUtil.verifyHeader(metadata, "Get metadata for Company Store", "meta data validation");
//filepath = "C:\\Users\\karthik.inuganti\\Desktop\\CompanySToreMetaData_20161213_135144_.csv";
    //    String data= s3Functions.csvReaderMethod();


        extentReportUtil.endTest();
    }
*/
/*
    @Test
    public void s3TestSecond() throws Exception {
        setUpTest("s3Test","s3Test results");

        s3Functions.getAllFileContentsOfBucket("thirdpartyreporting",s3);
        extentReportUtil.endTest();
    }
*/


}
