package com.ignitionone.datastorm.datorama;

import com.ignitionone.datastorm.datorama.datorama.S3Functions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
    public void setUpTest(String testName,String testDetails){
        extentReportUtil.startTest(testName, REPORT_TITLE);
        extentReportUtil.logInfo(testDetails);
    }


    @Test
    public void s3Test() throws Exception {
        setUpTest("s3Test","s3Test results");

        s3Functions.getFolderStructureOfBucket("thirdpartyreporting",s3);
        extentReportUtil.endTest();
    }


    @Test
    public void s3TestSecond() throws Exception {
        setUpTest("s3Test","s3Test results");

        s3Functions.getAllFileContentsOfBucket("thirdpartyreporting",s3);
        extentReportUtil.endTest();
    }

    @Test
    public void s3TestThird() throws Exception {
        setUpTest("s3Test","s3Test results");

        s3Functions.getAllFileContentsOfBucket("thirdpartyreporting",s3);
        extentReportUtil.endTest();
    }

}
