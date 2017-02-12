package com.ignitionone.datastorm.datorama.util;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Properties;

/**
 * Created by rpeddi on 5/17/2015.
 */
public class SetupUtil {

    //~ Instance fields ------------------------------------------------------------------------------------------------

    private String baseUrl;

    private WebDriver driver;

    private File file;
    private String testingMode;
    private String dmsUserName;
    private String dmsPassword;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new SetupUtil object.
     *
     * @param propertiesFile in value
     */
    public SetupUtil(PropertyLoader propertiesFile, String browserName, String environment) {
        String resultsFileName = "tracking";
        propertiesFile.loadProperties("datastorm.properties");
        Properties propEnvironment = propertiesFile.getProps();

        if (environment.trim().length() == 0) {
            environment = (String) propEnvironment.get("envt");
        }
        String resultsLocation = (String) propEnvironment.get("resultsLocation") + CommonUtil.getDateStr() + "/";
        this.file = new File(resultsLocation + resultsFileName + ".html");

        String platformUrl = environment + ".url";
        String browserUsed = environment + ".browser";
        String testingMode = environment + ".testingMode";
        String dmsUserName = environment + ".dmsUserName";
        String dmsPassword = environment + ".dmsPassword";
        /*
        if (browserName.trim().length() == 0) {
            browserName = (String) propEnvironment.get(browserUsed);
        }
        Browser browserRun = new Browser();
        this.driver = browserRun.createDriver(browserName);
        this.baseUrl = (String) propEnvironment.get(platformUrl);
        */
        this.testingMode = (String) propEnvironment.get(testingMode);
        this.dmsUserName = (String) propEnvironment.get(dmsUserName);
        this.dmsPassword = (String) propEnvironment.get(dmsPassword);


    }
    /**
     * Creates a new SetupUtil object.
     *
     * @param propertiesFile in value
     */
    public SetupUtil(PropertyLoader propertiesFile, String environment) {
        String resultsFileName = "Datorama Report";
        propertiesFile.loadProperties("datastorm.properties");
        Properties propEnvironment = propertiesFile.getProps();

        if (environment.trim().length() == 0) {
            environment = (String) propEnvironment.get("envt");
        }
        String resultsLocation = (String) propEnvironment.get("resultsLocation") + CommonUtil.getDateStr() + "/";
        this.file = new File(resultsLocation + resultsFileName + ".html");

        String platformUrl = environment + ".url";
        String testingMode = environment + ".testingMode";
        String dmsUserName = environment + ".dmsUserName";
        String dmsPassword = environment + ".dmsPassword";


        this.baseUrl = (String) propEnvironment.get(platformUrl);
        this.testingMode = (String) propEnvironment.get(testingMode);
        this.dmsUserName = (String) propEnvironment.get(dmsUserName);
        this.dmsPassword = (String) propEnvironment.get(dmsPassword);


    }

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Getter for property base url
     *
     * @return out value
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Getter for property driver
     *
     * @return out value
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Getter for property file
     *
     * @return out value
     */
    public File getFile() {
        return file;
    }

    /**
     * Setter for property base url
     *
     * @param baseUrl in value
     */

    /**
     * Getter for property testing mode
     *
     * @return out value
     */
    public String getTestingMode() {
        return testingMode;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Getter for DMS UI User Name
     */
    public String getDmsUserName() {
        return dmsUserName;
    }

    /**
     *  Getter for DMS UI Password
     */
    public String getDmsPassword() {
        return dmsPassword;
    }


    /**
     * Setter for property driver
     *
     * @param driver in value
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Setter for property file
     *
     * @param file in value
     */
    public void setFile(File file) {
        this.file = file;
    }
}
