package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.AmazonServices.S3Functions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.InputStream;

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
    public void s3Test() throws Exception {
        setUpTest("s3Test", "s3Test results");

        String filepath = s3Functions.getFilePathFromBucket("thirdpartyreporting", s3, "CompanySToreMetaData_");
        commonUtil.containsText(filepath, "Get event File path for Company Store", "File path for company store");
        Long filesize = s3Functions.getFileSize("thirdpartyreporting", s3, filepath);
        // String size= "filesize:-"+filesize;

        Long sz1 = filesize;
        commonUtil.containsValue(sz1, "Get event File size for Company Store", "File size for company store");
        //commonUtil.containsText(size,"Get event File size for Company Store","File size for company store");
        String metadata = s3Functions.getMetaDataOfFile("thirdpartyreporting", s3, filepath);
        commonUtil.verifyHeader(metadata, "Get metadata for Company Store", "meta data validation");


        extentReportUtil.endTest();
    }

/*
    @Test
    public void s3TestSecond() throws Exception {
        setUpTest("s3Test","s3Test results");

        s3Functions.getAllFileContentsOfBucket("thirdpartyreporting",s3);
        extentReportUtil.endTest();
    }
*/


}
