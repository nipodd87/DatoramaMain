package com.ignitionone.datastorm.datorama;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.util.*;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

/**
 * Created by ravi.peddi on 10/27/2016.
 */
public class BaseClass {
    ExtentReportUtil extentReportUtil;

    protected String baseUrl;
    protected String dmsUserName;
    protected String dmsEncryptedPassword;
    protected CommonUtil commonUtil;
    protected WebDriver driver;
    protected ElementUtil elementUtil;

    protected SetupUtil setupUtil;

    protected Properties sqlQueries;
    protected String testingMode;
    protected AmazonS3 s3;

    public void init(String environment, String reportHeader, String reportTitle) {
        setupUtil = new SetupUtil(new PropertyLoader(), environment);
        driver = setupUtil.getDriver();
        baseUrl = setupUtil.getBaseUrl();
        s3 = new AmazonS3Client();

        testingMode = setupUtil.getTestingMode();
        extentReportUtil = new ExtentReportUtil(setupUtil.getFile().getAbsolutePath());
        commonUtil = new CommonUtil(extentReportUtil);
        elementUtil = new ElementUtil(extentReportUtil);
        extentReportUtil.startTest(reportHeader, reportTitle);
    }


}
