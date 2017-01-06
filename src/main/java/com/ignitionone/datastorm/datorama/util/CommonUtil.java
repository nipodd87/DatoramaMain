package com.ignitionone.datastorm.datorama.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import org.testng.Assert;


/**
 * TODO: Enter Javadoc
 */
public class CommonUtil {

    //~ Static fields/initializers -------------------------------------------------------------------------------------

    /**
     * this class name
     */
    private static final String PAGE_CONTAINER = "container";

    // private static final String POPUP_WINDOW_CLOSE = "Close";
    private static final String DB_PROPERTIY_FILE = "db.properties";
    private static final String PLATFORM_PROPERTIY_FILE = "platform.properties";

    static PropertyLoader propertiesFile = new PropertyLoader();

    private static ExtentReportUtil reportUtil;

    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new CommonUtil object.
     *
     * @param extentReportUtil in value
     */
    public CommonUtil(ExtentReportUtil extentReportUtil) {
        this.reportUtil = extentReportUtil;
    }

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Method to delete file if Exist where input parameter is fle name with Path as String
     *
     * @param fileNameWithPath in value
     */
    public static void deleteFileIfExist(String fileNameWithPath) {
        File fileName = new File(fileNameWithPath);
        deleteFileIfExist(fileName);
    }

    /**
     * Method to delete file if Exist where input parameter is fle name with Path as String
     *
     * @param fileNameWithPath in value
     */
    public static void deleteFileIfExist(File fileNameWithPath) {
        if (fileNameWithPath.exists()) {
            fileNameWithPath.delete();
        }
    }

    /**
     * Getter for property date str
     *
     * @return out value
     */
    public static String getDateStr() {
        return getDateStr(0);
    }

    /**
     * Getter for property date str
     *
     * @param dayOffset in value
     * @return out value
     */
    public static String getDateStr(int dayOffset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dayOffset);

        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");

        return sdf.format(date);
    }

    /**
     * Getter for property date time str
     *
     * @return out value
     */
    public static String getDateTimeStr() {
        return getDateTimeStr(0);
    }

    /**
     * Getter for property date time str
     *
     * @param dayOffset in value
     * @return out value
     */
    public static String getDateTimeStr(int dayOffset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dayOffset);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(date);
    }

    /**
     * Getter for property date time str
     *
     * @param dayOffset  in value
     * @param dateFormat in value
     * @return out value
     */
    public static String getDateTimeStr(int dayOffset, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dayOffset);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dateFormat");

        return sdf.format(date);
    }

    /**
     * Getter for property DBProperty value
     *
     * @param propertyName in value
     * @return out value
     */
    public static String getDBPropertyValue(String propertyName) {
        return getPropertyValue(DB_PROPERTIY_FILE, propertyName);
    }

    /**
     * Getter for property last word
     *
     * @param strText in value
     * @return out value
     */
    public static String getLastWord(String strText) {
        return strText.substring(strText.lastIndexOf(" ") + 1);
    }

    /**
     * Getter for property platform property value
     *
     * @param propertyName in value
     * @return out value
     */
    public static String getPlatformPropertyValue(String propertyName) {
        return getPropertyValue(PLATFORM_PROPERTIY_FILE, propertyName);
    }

    /**
     * Getter for property property value
     *
     * @param propertyFileName in value
     * @param propertyName     in value
     * @return out value
     */
    public static String getPropertyValue(String propertyFileName, String propertyName) {
        propertiesFile.loadProperties(propertyFileName);
        Properties propEnvironment = propertiesFile.getProps();
        return (String) propEnvironment.get(propertyName);
    }

    /**
     * Getter for property random number
     *
     * @return out value
     */
    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(1000) + rand.nextInt(1000);
    }

    /**
     * Getter for property random UUID
     *
     * @return out value
     */
    public static String getRandomUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 8);
        return uuid;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param processName in value
     * @throws IOException on error
     */
    public static void killTaskProcess(String processName) throws IOException {
        Runtime.getRuntime().exec("taskkill /IM " + processName + " /F");
    }

    public static void SQLDataReadFail(String message, String stackTrace, String steps) {
        reportUtil.logFail(steps, message, stackTrace);
    }

    public static void SQLDataReadSuccess(String message) {
        reportUtil.logPass(message);
    }

    public static void logError(String stepName, String description) {
        reportUtil.logFail(stepName, description);
        MultiTest.addFailure(new TestFailureException(description));
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver          in value
     * @param stackTraceError in value
     * @param fieldName       in value
     * @param fieldType       in value
     * @param expectedResult  in value
     * @param actualResult    in value
     * @throws Exception on error
     */
    public static void logError(WebDriver driver, String stackTraceError, String fieldName, String fieldType,
                                String expectedResult, String actualResult) throws Exception {
        String details = null;
        String stepName = null;
        PropertyLoader propertiesFile = new PropertyLoader();
        propertiesFile.loadProperties("platform.properties");
        Properties propEnvironment = propertiesFile.getProps();
        String envt = (String) propEnvironment.get("envt");
        String screenShotLocation = (String)
                propEnvironment.get("resultsLocation") +
                CommonUtil.getDateStr() + "/";
        String browserUsed = envt + ".browser";
        String browserRunning = (String) propEnvironment.get(browserUsed);

        if (fieldType == "TextField") {
            details =
                    "Verify " + fieldName + " Failed <BR>  Expected "
                            + fieldName + " : " + expectedResult + "<BR> Found "
                            + fieldName + " as : " + actualResult;
            stepName = "Verify " + fieldName;
        } else if (fieldType == "Navigation") {
            details =
                    "Verify Page navigation after performing " + fieldName
                            + " action -  Failed <BR>  Expected to navigate to  URL : "
                            + expectedResult
                            + "<BR> Found that the application navigated to URL : "
                            + actualResult;
            stepName = "Verify Page navigation after performing " + fieldName
                    + " action";
        } else if (fieldType == "Button") {
            details = "Cannot find Button " + fieldName;
            stepName = "Verify Button " + fieldName;
        } else if (fieldType == "PopUp") {
            details = "Cannot find PopUp " + fieldName;
            stepName = "Verify PopUp " + fieldName;
        } else if (fieldType == "Text") {
            details = "Cannot find Text " + fieldName;
            stepName = "Verify Text " + fieldName;
        } else if (fieldType == "Page") {
            details = "Cannot find page " + fieldName;
            stepName = "Verify page " + fieldName;
        } else if (fieldType == "Window") {
            details = "Cannot find New Window when clicking " + fieldName
                    + "<BR>With URL " + expectedResult;
            stepName = "Verify New Window " + fieldName;
        } else if (fieldType == "Link") {
            details = "Cannot find Link " + fieldName;
            stepName = "Verify Link " + fieldName;
        } else if (fieldType == "Object") {
            details = "Cannot find Object " + fieldName;
            stepName = "Verify Object " + fieldName;
        } else if (fieldType == "Box") {
            details = "Cannot find CheckBox/RadioButton " + fieldName;
            stepName = "Verify CheckBox/RadioButton " + fieldName;
        } else if (fieldType == "Functionality") {
            details = "Cannot find Functionality " + fieldName;
            stepName = "Verify Functionality " + fieldName;
        } else if (fieldType == "URL") {
            details = "Expected URL " + expectedResult + "<BR> Found URL" + actualResult;
            stepName = "Verify URL ";
        } else if (fieldType == "Exceptions") {
            details = fieldName + " at " + expectedResult + "<BR>" + stackTraceError;
            stepName = fieldName + " at " + expectedResult;
        } else if (fieldType == "Sorting") {
            details = fieldName + " where data is " + "<BR>" + expectedResult + "<BR>" + actualResult;
            stepName = fieldName;
        } else {
            details = "Failure Occured - " + stackTraceError;
            stepName = expectedResult;
        }

        if (browserRunning == "HTMLUNIT" || browserRunning == "JSDRIVER") {
            reportUtil.logFail(stepName, details + "<BR> <BR>" + stackTraceError);
        } else {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenShotNameLocation =
                    screenShotLocation
                            + "imgs" + scrFile.separator + "ScreenShots"

                            // + CommonUtil.getDateTimeStr().replace(":", "-")
                            + Calendar.getInstance().getTimeInMillis()
                            + ".png";
            File file = new File(screenShotNameLocation);
            System.out.println("Path :" + "imgs" + file.separator + file.getName());
            FileUtils.copyFile(scrFile, file);
            reportUtil.logFail(stepName, (details + "<BR> <BR>"
                    + stackTraceError).replace("/n", "<BR>"), "imgs" + file.separator + file.getName());
            // extent.log(logStatus, stepName, details, screenCapturePath);
        }

        MultiTest.addFailure(new TestFailureException(details));
        MultiTest.exitOnMaxErrors();
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param actualString       in value
     * @param startString        in value
     * @param includeStartString in value
     * @param endString          in value
     * @param includeEndString   in value
     * @return out value
     */
    public static String midString(String actualString, String startString, boolean includeStartString,
                                   String endString, boolean includeEndString) {
        // actualString.getChars(actualString,,a);
        if ((actualString.contains(startString))
                && (actualString.contains(endString))) {
            actualString = actualString.substring(actualString.indexOf(startString), actualString.indexOf(endString));
            if (includeStartString = true) {
                actualString = includeStartString + actualString;
            }
            if (includeEndString = true) {
                actualString = actualString + includeEndString;
            }
        } else {
            actualString = actualString;
        }
        return actualString;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @return out value
     */
    @SuppressWarnings("rawtypes")
    public static String readExternalApp(WebDriver driver) {
        String url = null;
        String mwh = driver.getWindowHandle();
        Set s = driver.getWindowHandles();
        Iterator ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mwh)) {
                driver.switchTo().window(popupHandle);
                url = driver.getCurrentUrl();

                // System.out.println("The Current Url is " + url);
                // driver.findElement(By.linkText(POPUP_WINDOW_CLOSE)).click();
                driver.close();

                // After finished your operation in pop-up just select the main
                // window again
                driver.switchTo().window(mwh);
            }
        }
        return url;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver   in value
     * @param popupURL in value
     * @return out value
     * @throws Exception on error
     */
    public static String readExternalApp(WebDriver driver, By popupURL) throws Exception {
        String url = null;
        String value = null;
        String mwh = driver.getWindowHandle();
        Set s = driver.getWindowHandles();
        Iterator ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mwh)) {
                driver.switchTo().window(popupHandle);
                url = driver.getCurrentUrl();

                // value = elementUtil.getText(driver, "URL", popupURL, "Popup URL");
                // System.out.println("The Current Url is " + url);
                // driver.findElement(By.linkText(POPUP_WINDOW_CLOSE)).click();
                driver.close();

                // After finished your operation in pop-up just select the main
                // window again
                driver.switchTo().window(mwh);
            }
        }
        return url;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @return out value
     * @throws InterruptedException on error
     */
    public static String readPopUpWindow(WebDriver driver) throws InterruptedException {
        String value = null;
        Thread.sleep(2000);
        String mwh = driver.getWindowHandle();
        Set s = driver.getWindowHandles();
        Iterator ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mwh)) {
                driver.switchTo().window(popupHandle);
                value = driver.findElement(org.openqa.selenium.By.id(PAGE_CONTAINER)).getText();

                // driver.findElement(By.linkText(POPUP_WINDOW_CLOSE)).click();
                driver.close();

                // After finished your operation in pop-up just select the main
                // window again
                driver.switchTo().window(mwh);
            }
        }
        return value;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver  in value
     * @param string1 in value
     * @param string2 in value
     * @throws Exception on error
     */
    public static void verifyTextExits(WebDriver driver, String string1, String string2) throws Exception {
        verifyTextExitsNotExist(driver, "Exist", string1, string2);
    }


   /* public static void SQLDataReadSuccessFull(String message)
    {
        reportUtil.logPass(message);
    }
    public static void SQLDataReadFail(String message,String stackTrace,String steps)
    {
        reportUtil.logFail(steps,message,stackTrace);
    }
*/

    /**
     * TODO: Enter Javadoc
     *
     * @param driver  in value
     * @param action  in value
     * @param string1 in value
     * @param string2 in value
     * @throws Exception on error
     */
    public static void verifyTextExitsNotExist(WebDriver driver, String action, String string1, String string2)
            throws Exception {
        if (action == "Exist") {
            if (string1.contains(string2)) {
                reportUtil.logPass("Verify text", "Sucessfully Verified text " + string2);
            } else {
                logError(driver, "", "Verify text", "Text", string2, "");
            }
        } else if (action == "NotExist") {
            if (string1.contains(string2) == false) {
                reportUtil.logPass("Verify text not present", "Sucessfully Verified text not present"
                        + string2);
            } else {
                logError(driver, "", "Verify text not present", "Text", string2, "");
            }
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver  in value
     * @param string1 in value
     * @param string2 in value
     * @throws Exception on error
     */
    public static void verifyTextNotExits(WebDriver driver, String string1, String string2) throws Exception {
        verifyTextExitsNotExist(driver, "NotExist", string1, string2);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver                 in value
     * @param Url                    in value
     * @param screenShotNameLocation in value
     * @throws Exception on error
     */
    @SuppressWarnings("rawtypes")
    public static void verifyURL(WebDriver driver, String Url, String screenShotNameLocation) throws Exception {
        if (driver.getCurrentUrl().contentEquals(Url)) {
            reportUtil.logPass("Verify URL", "Sucessfully verified URL");
        } else {
            CommonUtil.logError(driver, "", "Verify URL", "URL", Url, driver.getCurrentUrl().toString());
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param Url    in value
     * @throws Exception on error
     */
    public static void verifyURLContains(WebDriver driver, String Url) throws Exception {
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains(Url)) {
            reportUtil.logPass("Verify URL", "Sucessfully verified URL");
        } else {
            CommonUtil.logError(driver, "", "Verify URL", "URL", Url, currentURL.toString());
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param Url    in value
     * @throws Exception on error
     */
    public static void verifyURLDoesNotContains(WebDriver driver, String Url) throws Exception {
        String currentURL = driver.getCurrentUrl();
        if (currentURL.contains(Url)) {
            CommonUtil.logError(driver, "", "Verify URL", "URL", Url, currentURL.toString());
        } else {
            reportUtil.logPass("Verify URL", "Sucessfully verified URL");
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strTxt1  in value
     * @param strTxt2  in value
     * @param stepName in value
     * @param details  in value
     * @throws ParseException on error
     */
    public static void compareDateEquals(String strTxt1, String strTxt2, String stepName, String details)
            throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        compareDateEquals(strTxt1, "yyyy-MM-dd HH:mm", strTxt2, "yyyy-MM-dd HH:mm", stepName, details);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strTxt1     in value
     * @param dateFormat1 in value
     * @param strTxt2     in value
     * @param dateFormat2 in value
     * @param stepName    in value
     * @param details     in value
     * @throws ParseException on error
     */
    public static void compareDateEquals(String strTxt1, String dateFormat1, String strTxt2, String dateFormat2,
                                  String stepName, String details) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat1);
        Date date1 = simpleDateFormat.parse(strTxt1);
        simpleDateFormat = new SimpleDateFormat(dateFormat2);
        Date date2 = simpleDateFormat.parse(strTxt2);
        if (compareDates(date1, date2)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected Date:" + strTxt1 + "<BR>  Found Date:" + strTxt2);
        } else {
            reportUtil.logFail(stepName,
                    "Failed - " + details + "<BR>  Expected Date:" + strTxt1 + "<BR>  Found Date:" + strTxt2);
            MultiTest.addFailure(new TestFailureException(details));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param date1 in value
     * @param date2 in value
     * @return out value
     */
    public static boolean compareDates(Date date1, Date date2) {
        return compareDates(date1, date2, 60);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param date1          in value
     * @param date2          in value
     * @param allowTimeinSec in value
     * @return out value
     */
    public static boolean compareDates(Date date1, Date date2, int allowTimeinSec) {
        long allowTime = (allowTimeinSec + 1) * 1000;
        long minTime = date1.getTime() - allowTime;
        long maxTime = date1.getTime() + allowTime;
        long dbAppliedTime = date2.getTime();
        return dbAppliedTime > minTime && dbAppliedTime < maxTime;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strTxt1             in value
     * @param dateFormat1         in value
     * @param strTxt2             in value
     * @param dateFormat2         in value
     * @param intAllowedTimeInSec in value
     * @param stepName            in value
     * @param details             in value
     * @throws ParseException on error
     */
    public void compareDateWithinAllowedTime(String strTxt1, String dateFormat1, String strTxt2, String dateFormat2,
                                             Integer intAllowedTimeInSec, String stepName, String details) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat1);
        Date date1 = simpleDateFormat.parse(strTxt1);
        simpleDateFormat = new SimpleDateFormat(dateFormat2);
        Date date2 = simpleDateFormat.parse(strTxt2);
        if (compareDates(date1, date2, intAllowedTimeInSec)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected Date:" + strTxt1 + "<BR>  Found Date:" + strTxt2);
        } else {
            reportUtil.logFail(stepName,
                    "Failed - " + details + "<BR>  Expected Date:" + strTxt1 + "<BR>  Found Date:" + strTxt2);
            MultiTest.addFailure(new TestFailureException(details));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param intExpected in value
     * @param intActual   in value
     * @param stepName    in value
     * @param details     in value
     */
    public static void compareNumberEquals(int intExpected, int intActual, String stepName, String details) {
        if (intExpected == intActual) {
            reportUtil.logPass(stepName,
                    "Passed comparison to see if the numbers are equal " + details + "<BR>  Expected :" + intActual
                            + "<BR>  Found :" + intExpected);
        } else {
            String message =
                    "Failed comparison to see if the numbers are equal  " + details + "<BR>  Expected :" + intActual
                            + "<BR>  Found :" + intExpected;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param intExpected in value
     * @param intActual   in value
     * @param stepName    in value
     * @param details     in value
     */
    public static void compareNumberEquals(double intExpected, double intActual, String stepName, String details) {
        if (intExpected == intActual) {
            reportUtil.logPass(stepName,
                    "Passed comparison to see if the numbers are equal " + details + "<BR>  Expected :" + intActual
                            + "<BR>  Found :" + intExpected);
        } else {
            String message =
                    "Failed comparison to see if the numbers are equal  " + details + "<BR>  Expected :" + intActual
                            + "<BR>  Found :" + intExpected;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param intText1 in value
     * @param intText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public boolean compareNumberGreater(double intText1, double intText2, String stepName, String details) {
        if (intText1 > intText2) {
            reportUtil.logPass(stepName,
                    "Passed comparison to see if " + intText1 + "  is greater than " + intText2 + " <BR>" + details
                            + "<BR>  Expected greater than:" + intText2 + "<BR>  Found :" + intText1);
            return true;
        } else {
            reportUtil.logFail(stepName,
                    "Failed comparison to see if " + intText1 + " is greater than " + intText2 + " <BR>" + details
                            + "<BR>  Expected  greater than:" + intText2 + "<BR>  Found :" + intText1);
            return false;
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param intText1 in value
     * @param intText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public void compareNumberLess(double intText1, double intText2, String stepName, String details) {
        if (intText1 < intText2) {
            reportUtil.logPass(stepName,
                    "Passed comparison to see if " + intText1 + "is less than " + intText2 + " <BR>" + details
                            + "<BR>  Expected :" + intText2 + "<BR>  Found :" + intText1);
        } else {
            reportUtil.logFail(stepName,
                    "Failed comparison to see if " + intText1 + "is less than " + intText2 + " <BR>" + details
                            + "<BR>  Expected :" + intText2 + "<BR>  Found :" + intText1);
        }
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param intText1 in value
     * @param intText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public boolean compareNumberGreaterThanOrEqualTo(double intText1, double intText2, String stepName, String details) {
        if (intText1 >= intText2) {
            reportUtil.logPass(stepName,
                    "Passed comparison to see if " + intText1 + "  is greater than " + intText2 + " <BR>" + details
                            + "<BR>  Expected greater than:" + intText2 + "<BR>  Found :" + intText1);
            return true;
        } else {
            reportUtil.logFail(stepName,
                    "Failed comparison to see if " + intText1 + " is greater than " + intText2 + " <BR>" + details
                            + "<BR>  Expected  greater than:" + intText2 + "<BR>  Found :" + intText1);
            return false;
        }
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param intText1 in value
     * @param intText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public void compareNumberLessThanOrEqualTo(double intText1, double intText2, String stepName, String details) {
        if (intText1 <= intText2) {
            reportUtil.logPass(stepName,
                    "Passed comparison to see if " + intText1 + "is less than " + intText2 + " <BR>" + details
                            + "<BR>  Expected :" + intText2 + "<BR>  Found :" + intText1);
        } else {
            reportUtil.logFail(stepName,
                    "Failed comparison to see if " + intText1 + "is less than " + intText2 + " <BR>" + details
                            + "<BR>  Expected :" + intText2 + "<BR>  Found :" + intText1);
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strNumber1 in value
     * @param strNumber2 in value
     * @param stepName   in value
     * @param details    in value
     */
    public static void compareNumbers(double strNumber1, double strNumber2, String stepName, String details) {
        if (strNumber1 == strNumber2) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strNumber1 + "<BR>  Found :" + strNumber2);
        } else {
            String message = "Failed - " + details + "<BR>  Expected :" + strNumber1 + "<BR>  Found :" + strNumber2;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strText1 in value
     * @param strText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public static void compareText(String strText1, String strText2, String stepName, String details) {
        if (strText1.contentEquals(strText2)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :" + strText2);
        } else {
            String message = "Failed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :" + strText2;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param strText1 in value
     * @param strText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public static void verifyTextContains(String strText1, String strText2, String stepName, String details) {
        if ((strText1.contains(strText2)) || (strText2.contains(strText1))) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :" + strText2);
        } else {
            String message = "Failed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :" + strText2;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strText  in value
     * @param stepName in value
     * @param details  in value
     */
    public void containsText(String strText, String stepName, String details) {
        if (strText.length() > 0) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR> has text :" + strText + "<BR>");
        } else {
            String message = "Failed - " + details + "<BR>  text is blank ";
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param strSize  in value
     * @param stepName in value
     * @param details  in value
     */
    public void containsValue(Long strSize, String stepName, String details) {
        if (strSize != 0) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR> has text :" + strSize + "<BR>");
        } else {
            String message = "Failed - " + details + "<BR>  text is blank ";
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     * @param details  in value
     */
    public void verifyHeader(String input, String stepName, String details) throws IOException {
        // BufferedReader reader = new BufferedReader(new InputStreamReader(input));


        String line = input;
        if (line != null) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR> has text :" + input + "<BR>");
        } else {
            String message = "Failed - " + details + "<BR>  text is blank ";
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }


    }


    /**
     * TODO: Enter Javadoc
     *
     * @param strText1 in value
     * @param stepName in value
     * @param details  in value
     */
    public void compareTextContainsWithCSV(String strText1, String stepName, String details) {

        String str1 = "BU_ID";

        boolean isStringExists = strText1.contains(str1);// returns true if the array list contains string value as specified by user

        if (strText1.contains(str1)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + str1 + "<BR>  Found :" + strText1);
        } else {
            String message = "Failed - " + details + "<BR>  Expected :" + str1 + "<BR>  Found :" + strText1;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strText1 in value
     * @param strText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public void compareTextContains(String strText1, String strText2, String stepName, String details) {
        if ((strText1 == null) && (strText2 == null)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strText2 + "<BR>  Found :" + strText1);
        } else if ((strText1.contains(strText2)) || strText2.contains(strText1)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strText2 + "<BR>  Found :" + strText1);
        } else {
            String message = "Failed - " + details + "<BR>  Expected :" + strText2 + "<BR>  Found :" + strText1;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strText1 in value
     * @param strText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public void compareTextNotContains(String strText1, String strText2, String stepName, String details) {
        if (((strText1.length() == 0) && (strText2.length() > 0))
                || ((strText2.length() == 0) && (strText1.length() > 0))) {
            reportUtil.logPass(stepName,
                    "Test for Not Contains  Passed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :"
                            + strText2);
        } else if ((strText1.contains(strText2)) || strText2.contains(strText1)) {
            String message =
                    "Test for Not Contains Failed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :"
                            + strText2;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        } else {
            reportUtil.logPass(stepName,
                    "Test for Not Contains  Passed - " + details + "<BR>  Expected :" + strText1 + "<BR>  Found :"
                            + strText2);
        }
    }


    public static void checkIfListIsSorted(List<Double> listOfDoubles, String stepName) {
        Double previous = null;
        String message = stepName + " Failed";
        for (Double current : listOfDoubles) {
            if (!(previous == null) && previous > current) {
                reportUtil.logPass(stepName, "The value " + previous + " is succeeded by " + current);
            } else if (!(previous == null) && previous < current) {
                reportUtil.logFail(stepName, "The list is not sorted, the value " + previous + " is succeeded by " + current);
                MultiTest.addFailure(new TestFailureException(message));
            }
            previous = current;
        }

    }


    /**
     * TODO: Enter Javadoc
     *
     * @param strText1 in value
     * @param strText2 in value
     * @param stepName in value
     * @param details  in value
     */
    public void compareTextBeginsWith(String strText1, String strText2, String stepName, String details) {
        if ((strText1 == null) && (strText2 == null)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strText2 + "<BR>  Found :" + strText1);
        } else if ((strText1.startsWith(strText2)) || strText2.startsWith(strText1)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected :" + strText2 + "<BR>  Found :" + strText1);
        } else {
            String message = "Failed - " + details + "<BR>  Expected :" + strText2 + "<BR>  Found :" + strText1;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param strText in value
     * @return out value
     */
    @SuppressWarnings("rawtypes")
    public String convertNullToString(String strText) {
        if (strText == null || strText.isEmpty()) {
            strText = "";
        }
        return strText;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strTxt     in value
     * @param dateFormat in value
     * @return out value
     * @throws ParseException on error
     */
    public Date convertToDate(String strTxt, String dateFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.parse(strTxt);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param intNum   in value
     * @param YesNo    in value
     * @param stepName in value
     * @param details  in value
     */
    public void IsMinusOne(int intNum, String YesNo, String stepName, String details) {
        if ((intNum == -1) & (YesNo.compareToIgnoreCase("Yes") == 0)) {
            reportUtil.logPass(stepName, "Passed - " + details + "<BR>  Expected : -1 <BR>  Found : -1");
        } else if ((intNum != -1) & (YesNo.compareToIgnoreCase("No") == 0)) {
            reportUtil.logPass(stepName,
                    "Passed - " + details + "<BR>  Expected : " + intNum + " <BR>  Found : " + intNum);
        } else {
            String message =
                    "Failed - " + details + "<BR>  Expected :  and  Found  are Different <BR>  Found : " + intNum;
            reportUtil.logFail(stepName, message);
            MultiTest.addFailure(new TestFailureException(message));
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strDate    in value
     * @param dateFormat in value
     * @param offsetDays in value
     * @return out value
     * @throws ParseException on error
     */
    public String offsetDaysToDates(String strDate, String dateFormat, int offsetDays) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(strDate));
        calendar.add(Calendar.DAY_OF_MONTH, offsetDays);
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver       in value
     * @param action       in value
     * @param pageName     in value
     * @param section      in value
     * @param label        in value
     * @param inputValue   in value
     * @param compareValue in value
     * @return out value
     * @throws Exception on error
     */
    public String performActionOnText(WebDriver driver, String action, String pageName, String section, String label,
                                      String inputValue, String compareValue) throws Exception {
        String returnValue = null;
        if ((action == "Get Text")) {
            returnValue = inputValue;
        } else if ((action == "Verify Exist")) {
            if (!inputValue.isEmpty()) {
                reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + pageName + " for"
                                + section + "; " + label);
            } else {
                CommonUtil.logError(driver, "", "Verify " + pageName, "Link", section + "; " + label, "");
            }
        } else if ((action == "Verify Not Exist")) {
            if (inputValue.isEmpty()) {
                reportUtil.logPass("Verify not Exist - " + pageName + "; " + section,
                        "Sucessfully Verified Not Exist - " + pageName
                                + section + "; " + label);
            } else {
                CommonUtil.logError(driver, "", "Verify Not Exit -" + pageName, "Link", section + "; " + label, "");
            }
        } else if ((action == "Verify Text")) {
            if (inputValue.compareTo(compareValue) == 0) {
                reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + compareValue + " in Page " + pageName + " , "
                                + section + "; " + label);
            } else {
                CommonUtil.logError(driver, "",
                        "Verify " + compareValue + " in Page " + pageName + " , "
                                + section + "; " + label, "Link", compareValue + "," + section + "; " + label, "");
            }
        }
        return returnValue;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver      in value
     * @param textElement in value
     * @return out value
     * @throws Exception on error
     */
    public String readPopUpWindow(WebDriver driver, By textElement) throws Exception {
        ElementUtil elementUtil = new ElementUtil(reportUtil);
        String value = null;
        Thread.sleep(1000);
        String mwh = driver.getWindowHandle();
        Set s = driver.getWindowHandles();
        Iterator ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mwh)) {
                driver.switchTo().window(popupHandle);
                value = elementUtil.getText(driver, "Popup", textElement, "Popup Text");

                // value = driver.findElement(By.id(PAGE_CONTAINER)).getText();
                // driver.findElement(By.linkText(POPUP_WINDOW_CLOSE)).click();
                driver.close();

                // After finished your operation in pop-up just select the main
                // window again
                driver.switchTo().window(mwh);
            }
        }
        return value;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param strText    in value
     * @param replaceTxt in value
     * @return out value
     */
    public String replaceTextWhenLengthisZero(String strText, String replaceTxt) {
        if (strText == null) {
            strText = replaceTxt;
        } else if ((strText.length() == 0) || (strText == null) || (strText.isEmpty())) {
            strText = replaceTxt;
        }
        return strText;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName    in value
     * @param descritpion in value
     */
    public void reportPositive(String stepName, String descritpion) {
        reportUtil.logPass(stepName, descritpion);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param stepName in value
     */
    public void reportSingleTest(String stepName) {
        reportUtil.logSingleTest(stepName);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @throws AWTException on error
     */
    public void sendKeysENTER() throws AWTException {
        Robot robot = new Robot();
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @throws AWTException on error
     */
    public void sendKeysTab() throws AWTException {
        Robot robot = new Robot();
        robot.keyRelease(KeyEvent.VK_TAB);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver     in value
     * @param dateFormat in value
     * @param strTxt1    in value
     * @param strTxt2    in value
     * @param ascDesc    in value
     * @param columnName in value
     * @throws Exception on error
     */
    public void sortDate(WebDriver driver, String dateFormat, String strTxt1, String strTxt2, String ascDesc,
                         String columnName) throws Exception {
        DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Date date1 = simpleDateFormat.parse(strTxt1.replace(" ET", ""));
        Date date2 = simpleDateFormat.parse(strTxt2.replace(" ET", ""));
        if (ascDesc == "ASC") {
            if (date1.compareTo(date2) <= 0) {
                reportUtil.logPass("Verify Ascending order for " + columnName,
                        "Sucessfully Verified Ascending order for " + columnName + " for data " + strTxt1 + ", " + strTxt2);
            } else {
                CommonUtil.logError(driver, "", "Verify Ascending order for " + columnName, "Sorting", strTxt1,
                        strTxt2);
            }
        } else {
            if (date1.compareTo(date2) >= 0) {
                reportUtil.logPass("Verify Descending order for " + columnName,
                        "Sucessfully Verified Descending order for " + columnName + " for data " + strTxt1 + ", "
                                + strTxt2);
            } else {
                CommonUtil.logError(driver, "", "Verify Descending order for " + columnName, "Sorting", strTxt1,
                        strTxt2);
            }
        }
    }

    public boolean isColumnSorted(WebDriver driver, List<String> columnValues, String columnName) throws Exception {
        String previous = "";
        for (String current : columnValues) {
            sortText(driver, previous, current, "ASC", columnName);
        }
        return true;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver     in value
     * @param strTxt1    in value
     * @param strTxt2    in value
     * @param ascDesc    in value
     * @param columnName in value
     * @throws Exception on error
     */
    public void sortText(WebDriver driver, String strTxt1, String strTxt2, String ascDesc, String columnName)
            throws Exception {
        if (ascDesc == "ASC") {
            if (strTxt1.compareTo(strTxt2) <= 0) {
                reportUtil.logPass("Verify Ascending order for " + columnName,
                        "Sucessfully Verified Ascending order for " + columnName + " for data " + strTxt1 + ", " + strTxt2);
            } else {
                CommonUtil.logError(driver, "", "Verify Ascending order for " + columnName, "Sorting", strTxt1,
                        strTxt2);
            }
        } else {
            if (strTxt1.compareTo(strTxt2) >= 0) {
                reportUtil.logPass("Verify Descending order for " + columnName,
                        "Sucessfully Verified Descending order for " + columnName + " for data " + strTxt1 + ", "
                                + strTxt2);
            } else {
                CommonUtil.logError(driver, "", "Verify Descending order for " + columnName, "Sorting", strTxt1,
                        strTxt2);
            }
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @throws InterruptedException on error
     */
    public void waitForAjaxLoad(WebDriver driver) throws InterruptedException {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        if ((Boolean) executor.executeScript("return window.jQuery != undefined")) {
            while (!(Boolean) executor.executeScript("return jQuery.active == 0")) {
                Thread.sleep(100L);
            }
        }
        return;
    }
}
