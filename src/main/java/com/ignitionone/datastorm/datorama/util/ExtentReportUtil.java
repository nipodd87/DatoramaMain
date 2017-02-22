package com.ignitionone.datastorm.datorama.util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;

/**
 * created By Ravi Peddi
 */
public class ExtentReportUtil {

    //~ Instance fields ------------------------------------------------------------------------------------------------

    private ExtentTest logger;

    private ExtentReports reporter;
    private static ExtentReports extent;
    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ExtentReportUtil object.
     *
     * @param filePath in value
     * @param headline in value
     * @param title in value
     */
    public ExtentReportUtil(String filePath, String headline, String title) {
        reporter = new ExtentReports(filePath, false);
        reporter.loadConfig(new File(System.getProperty("user.dir") +
                "\\src\\test\\resources\\Assets\\extent-config.xml"));
       /* System.getProperty("user.dir") +
                "\\src\\test\\x resources\\Assets\\extent-config.xml");*/

        // reporter.config().reportHeadline(headline).documentTitle(title);
    }
    public ExtentReportUtil(String filePath) {
        reporter = new ExtentReports(filePath, false);
        reporter.loadConfig(new File(System.getProperty("user.dir") +
                "\\src\\test\\resources\\Assets\\extent-config.xml"));
       /* System.getProperty("user.dir") +
                "\\src\\test\\x resources\\Assets\\extent-config.xml");*/

        //reporter.config().reportHeadline(headline).documentTitle(title);
    }
    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * TODO: Enter Javadoc
     *
     * @param imgPath in value
     *
     * @return out value
     */
    public String addScreenCapture(String imgPath) {
        return logger.addScreenCapture(imgPath);
    }

    /**
     * TODO: Enter Javadoc
     */
    public void endTest() {
        reporter.endTest(logger);
        reporter.flush();
     //  reporter.close();
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param logStatus in value
     * @param details in value
     */
    public void log(LogStatus logStatus, String details) {
        log(logStatus, details, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param logStatus in value
     * @param stepName in value
     * @param details in value
     */
    public void log(LogStatus logStatus, String stepName, String details) {
        logger.log(logStatus, stepName, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param details in value
     */
    public void logFail(String details) {
        logFail(details, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     * @param details in value
     */
    public void logFail(String stepName, String details) {
        log(LogStatus.FAIL, stepName, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     * @param details in value
     * @param imgPath in value
     */
    public void logFail(String stepName, String details, String imgPath) {
        log(LogStatus.FAIL, stepName, details + addScreenCapture(imgPath));
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param details in value
     */
    public void logInfo(String details) {
        logInfo(details, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     * @param details in value
     */
    public void logInfo(String stepName, String details) {
        log(LogStatus.INFO, stepName, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param details in value
     */
    public void logPass(String details) {
        logPass(details, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     * @param details in value
     */
    public void logPass(String stepName, String details) {
        log(LogStatus.PASS, stepName, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param details in value
     */
    public void logPassAndEndTest(String details) {
        logPassAndEndTest(details, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     * @param details in value
     */
    public void logPassAndEndTest(String stepName, String details) {
        logPass(stepName, details);
        endTest();
    }

    /**
     * TODO: Enter Javadoc
     */
    public void logPassOpenURL() {
        logPass("Open URL", "Sucessfully opened URL");
    }

    /**
     * TODO: Enter Javadoc
     */
    public void logPassUsernamePassword() {
        logPass("Retrived User Name and Password form DB");
        logInfo("Enter UserName and Password");
        endTest();
    }

    /**
     * TODO: Enter Javadoc
     */
    public void logSignOut() {
        logPass("Click Sign Out");
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param testName in value
     */
    public void logSingleTest(String testName) {
        logSingleTest(testName, testName);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param testName in value
     * @param description in value
     */
    public void logSingleTest(String testName, String description) {
        logger = reporter.startTest(testName, description);
        endTest();
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param testName in value
     */
    public void startTest(String testName) {
        startTest(testName, testName);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param testName in value
     * @param description in value
     */
    public void startTest(String testName, String description) {
        logger = reporter.startTest(testName, description);
    }

    /**
     * TODO: Enter Javadoc
     */
    public void writeReport() {
        if (reporter != null) {
             reporter.flush();
             reporter.close();
        }
    }
}
