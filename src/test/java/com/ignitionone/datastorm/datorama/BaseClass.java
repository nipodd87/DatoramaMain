package com.ignitionone.datastorm.datorama;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ignitionone.datastorm.datorama.util.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

/**
 * Created by ravi.peddi on 10/27/2016.
 */
public class BaseClass {

    ExtentReportUtil extentReportUtil;
    protected CommonUtil commonUtil;
    protected WebDriver driver;
    protected ElementUtil elementUtil;
    protected SetupUtil setupUtil;
    protected String testingMode;
    protected AmazonS3 s3;
    protected String baseUrl;
    public static String bucketName;
    public static String brandId;
    public static long USER_PROFILE;
    public static RequestSpecBuilder requestBuilder;
    public static RequestSpecification requestSpec;
    public static ResponseSpecBuilder responseBuilder;
    public static ResponseSpecification responseSpec;

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
        getEnvironmentDetails(environment);
    }

    public void setUp(String environment,String reportHeader, String reportTitle) throws Exception {
        setupTestingMode(environment);
        setupReport(reportHeader, reportTitle);
        buildRequestSpec(environment);
        buildResponseSpec();
        getEnvironmentDetails(environment);
    }

    public void getEnvironmentDetails(String environment) {
        PropertyLoader propertiesFile = new PropertyLoader();
        propertiesFile.loadProperties("datastorm.properties");
        Properties dataStormProperties = propertiesFile.getProps();
        bucketName = (String) dataStormProperties.get(environment + "." + "bucketName");
        brandId = (String) dataStormProperties.get(environment + "." + "brandId");
        USER_PROFILE = Long.parseLong((String) dataStormProperties.get(environment + "."+"dcm_profileId"));
    }

    public void buildResponseSpec() {
        responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectContentType(ContentType.JSON);
        responseBuilder.expectStatusCode(200);
        responseSpec = responseBuilder.build();
    }

    public void buildRequestSpec(String environment) {
        PropertyLoader propertiesFile = new PropertyLoader();
        propertiesFile.loadProperties("datastorm.properties");
        Properties dataStormProperties = propertiesFile.getProps();
        RestAssured.baseURI = (String) dataStormProperties.get(environment + "." + "baseURL");
        requestBuilder = new RequestSpecBuilder();
        requestSpec = requestBuilder.build();
    }

    public void setupReport(String reportHeader, String reportTitle) {
        extentReportUtil = new ExtentReportUtil(setupUtil.getFile().getAbsolutePath());
        commonUtil = new CommonUtil(extentReportUtil);
        elementUtil = new ElementUtil(extentReportUtil);
        extentReportUtil.startTest(reportHeader, reportTitle);
    }

    public void setupTestingMode(String environment) {
        setupUtil = new SetupUtil(new PropertyLoader(), "CHROME", environment);
        testingMode = setupUtil.getTestingMode();
    }

    public void endTest() {
        extentReportUtil.endTest();
        extentReportUtil.writeReport();
    }
}
