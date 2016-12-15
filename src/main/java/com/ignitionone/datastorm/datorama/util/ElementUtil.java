package com.ignitionone.datastorm.datorama.util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;


/**
 * TODO: Enter Javadoc
 */
public class ElementUtil {

    //~ Instance fields ------------------------------------------------------------------------------------------------

    private ExtentReportUtil reportUtil;

    Encryption encryption = new Encryption();

    //~ Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ElementUtil object.
     *
     * @param reportUtil in value
     */
    public ElementUtil(ExtentReportUtil reportUtil) {
        this.reportUtil = reportUtil;
    }

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void click(WebDriver driver, String pageName, By uniquePageElement, String elementNameForReporting)
    throws Exception {
        performAction(driver, "Click", pageName, elementNameForReporting, "", "", uniquePageElement);
    }



    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     */
    /*public void clickEnter(WebDriver driver) {
        driver.findElement(org.openqa.selenium.By.id("*")).sendKeys(Keys.ENTER);
    }*/

    public void clickEnter(WebDriver driver,By uniquePageElement) throws Exception {
        Thread.sleep(3000);
        driver.findElement(uniquePageElement).sendKeys(Keys.ENTER);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void clickMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Click", pageName, elementNameForReporting, "", "", uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param uniquePageElement in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public Integer countOfObjects(WebDriver driver, By uniquePageElement) throws Exception {
        ElementAction elementAction = new ElementAction();
        if (elementAction.isElementPresent(driver,uniquePageElement)) {
            List<WebElement> resultsDiv = ElementAction.lperform(driver,uniquePageElement);
            return resultsDiv.size();
        } else {
            return -1;
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param strText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void deselect(WebDriver driver, String pageName, By uniquePageElement, String strText,
                         String elementNameForReporting) throws Exception {
        performAction(driver, "DeSelect", pageName, elementNameForReporting, "", strText, uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param uniquePageElement in value
     * @param rowIdentifier in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public int findIndexInTable(WebDriver driver, By uniquePageElement, String rowIdentifier)
    throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        int returnValue = -1;
        for (int i = 0; i < resultsDiv.size(); i++) {
            if (resultsDiv.get(i).getText().compareTo(rowIdentifier) == 0) {
                returnValue = i;

                // System.out.println(i + 1 + ". " +resultsDiv.get(i).getText());
                i = resultsDiv.size() + 1;
            }
        }

        return returnValue;
    }


    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param uniquePageElement in value
     * @param rowIdentifier in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public int findIndexInTableContains(WebDriver driver, By uniquePageElement, String rowIdentifier)
    throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        int returnValue = -1;
        for (int i = 0; i < resultsDiv.size(); i++) {
            if (resultsDiv.get(i).getText().contains(rowIdentifier)) {
                returnValue = i;

                // System.out.println(i + 1 + ". " +resultsDiv.get(i).getText());
                i = resultsDiv.size() + 1;
            }
        }

        return returnValue;
    }

    /**
     * Getter for property data from child
     *
     * @param driver in value
     * @param uniquePageElement in value
     * @param label in value
     * @param stringCounter in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getDataFromChild(WebDriver driver, By uniquePageElement, String label, int stringCounter)
    throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        String returnValue = "Not Exist";
        for (int i = 0; i < resultsDiv.size(); i++) {
            if (resultsDiv.get(i).getText().contains(label)) {
                returnValue = resultsDiv.get(i + stringCounter).getText();
                i = resultsDiv.size() + 1;
            }
        }
        return returnValue;
    }

    /**
     * Getter for property drop down size
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public int getDropDownSize(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        return Integer.parseInt(performAction(driver, "DropDownSize", pageName, elementNameForReporting, "", "",
                    uniquePageElement));
    }

    /**
     * Getter for property element from parent
     *
     * @param driver in value
     * @param uniquePageElement in value
     * @param cellNumber in value
     * @param attribute in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getElementFromParent(WebDriver driver, By uniquePageElement, int cellNumber,
                                       String attribute) throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        return resultsDiv.get(cellNumber).getAttribute(attribute);
    }

    /**
     * Getter for property object source multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getObjectSourceMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        return performActionMultiObjects(driver, "Get Object Source", pageName, elementNameForReporting, "", "",
                uniquePageElement, objNumber);
    }

    /**
     * Getter for property object value
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getObjectValue(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        return performAction(driver, "Get Object Value", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * Getter for property object value multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getObjectValueMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        return performActionMultiObjects(driver, "Get Object Value", pageName, elementNameForReporting, "", "",
                uniquePageElement, objNumber);
    }

    /**
     * Getter for property source
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getSource(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        return performAction(driver, "Get Source", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * Getter for property source multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getSourceMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        return performActionMultiObjects(driver, "Get Source", pageName, elementNameForReporting, "", "", uniquePageElement,
                objNumber);
    }

    /**
     * Getter for property text
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getText(WebDriver driver, String pageName, By uniquePageElement, String elementNameForReporting)
    throws Exception {
        return performAction(driver, "Get Text", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * Getter for property text from objects
     *
     * @param driver in value
     * @param uniquePageElement in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getTextFromObjects(WebDriver driver, By uniquePageElement, int objNumber) throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        return resultsDiv.get(objNumber).getText();
    }

    /**
     * Getter for property text from table
     *
     * @param driver in value
     * @param uniquePageElement in value
     * @param cellNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getTextFromTable(WebDriver driver, By uniquePageElement, int cellNumber) throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        return resultsDiv.get(cellNumber).getText();
    }


    /**
     * Get list of all the objects from Multi Object
     *
     * @param driver in value
     * @param uniquePageElement in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public List<WebElement> getListMultiObject(WebDriver driver, By uniquePageElement) throws Exception {
        List<WebElement> resultsDiv = ElementAction.lperform(driver, uniquePageElement);
        return resultsDiv;
    }

    /**
     * Getter for property text multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String getTextMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        return performActionMultiObjects(driver, "Get Text", pageName, elementNameForReporting, "", "", uniquePageElement,
                objNumber);
    }

    /**
     * Getter for property checked
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String isChecked(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        return performAction(driver, "Is Checked", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * Getter for property checked multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String isCheckedMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        return performActionMultiObjects(driver, "Is Checked", pageName, elementNameForReporting, "", "", uniquePageElement,
                objNumber);
    }

    /**
     * Getter for property image present multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void isImagePresentMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Is Image Present", pageName, elementNameForReporting, "", "", uniquePageElement,
            objNumber);
    }

    /**
     * Getter for property present multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void isPresentMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Is Present", pageName, elementNameForReporting, "", "", uniquePageElement,
            objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param action in value
     * @param pageName in value
     * @param section in value
     * @param label in value
     * @param uniquePageElement in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String performAction(WebDriver driver, String action, String pageName, String section, String label,
            By uniquePageElement) throws Exception {
        return performAction(driver, action, pageName, section, label, "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param action in value
     * @param pageName in value
     * @param section in value
     * @param label in value
     * @param strText in value
     * @param uniquePageElement in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String performAction(WebDriver driver, String action, String pageName, String section, String label,
            String strText, By uniquePageElement) throws Exception {
        WebDriverWait wait;
        wait = new WebDriverWait(driver, 20);
        String returnValue = null;

        pageName = pageName + " ";
        try {
            // Add Hover over logic
            if ((action == "Click")) {
                driver.findElement(uniquePageElement).click();
                reportUtil.logPass("Click " + section + "; " + label, "Sucessfully clicked " + section + "; " + label);
            } else if ((action == "Submit")) {
                driver.findElement(uniquePageElement).submit();
                reportUtil.logPass("Submit " + section + "; " + label,
                    "Sucessfully Submitted " + section + "; " + label);
            } else if ((action == "Wait")) {
                wait.until(ExpectedConditions.presenceOfElementLocated(uniquePageElement));
                reportUtil.logPass("Wait for presence of element " + section + "; " + label,
                    "Sucessfully wait until presence of element " + section + "; " + label);
            } else if ((action == "Wait Visibility of Element")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(uniquePageElement));
                reportUtil.logPass("Wait for visibility of element " + section + "; " + label,
                    "Sucessfully wait until visibility of element  " + section + "; " + label);
            } else if ((action == "Wait Element Selected")) {
                wait.until(ExpectedConditions.elementToBeSelected(uniquePageElement));
                reportUtil.logPass("Wait for element to be selected is available " + section + "; " + label,
                    "Sucessfully wait until element to be selected is available  " + section + "; " + label);
            } else if ((action == "Wait until element Not Visible")) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(uniquePageElement));
                reportUtil.logPass("Wait for element to be invisible " + section + "; " + label,
                    "Sucessfully wait until element to be invisible  " + section + "; " + label);
            } else if ((action == "Wait Element Clickable")) {
                (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(uniquePageElement));
                reportUtil.logPass("Wait for presence of element " + section + "; " + label,
                    "Sucessfully on wait until presence of element " + section + "; " + label);
            } else if ((action == "Get Text")) {
                returnValue = driver.findElement(uniquePageElement).getText();
                reportUtil.logPass("Read " + section + "; " + label,
                    "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Select")) {
                new Select(driver.findElement(uniquePageElement)).selectByVisibleText(strText);
                reportUtil.logPass("Select " + section + "; " + label,
                    "Sucessfully Selected " + section + "; " + label + "; Value = \"" + strText + "\"");
            } else if ((action == "SelectByIndex")) {
                new Select(driver.findElement(uniquePageElement)).selectByIndex(Integer.parseInt(strText));
                reportUtil.logPass("Select " + section + "; " + label,
                    "Sucessfully Selected index " + section + "; " + label + "; Value = \"" + strText + "\"");
            } else if ((action == "DropDownSize")) {
                returnValue =
                    Integer.toString(driver.findElement(uniquePageElement).getText().split("\n").length);
                reportUtil.logPass("DropDown size " + section + "; " + label,
                    "Drop down size of " + section + "; " + label + "; size = \"" + returnValue + "\"");
            } else if ((action == "DeSelect")) {
                new Select(driver.findElement(uniquePageElement)).deselectByVisibleText(strText);
                driver.findElement(uniquePageElement).click();
                reportUtil.logPass("De-select " + section + "; " + label,
                    "Sucessfully De-selected " + section + "; " + label + "; Value = \"" + strText + "\"");
            } else if ((action == "Set Text")) {
                driver.findElement(uniquePageElement).clear();
                driver.findElement(uniquePageElement).sendKeys(strText);
                reportUtil.logPass("Enter Text - " + section + "; " + label,
                    "Sucessfully entered text \"" + strText + "\" in " + section + "; " + label);
            } else if ((action == "Get Object Value")) {
                returnValue = driver.findElement(uniquePageElement).getAttribute("value");
                reportUtil.logPass("Read " + section + "; " + label,
                    "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Get Source")) {
                returnValue = driver.findElement(uniquePageElement).getAttribute("src");
                reportUtil.logPass("Read " + section + "; " + label,
                    "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Get Object Class")) {
                returnValue = driver.findElement(uniquePageElement).getAttribute("class");
                reportUtil.logPass("Read " + section + "; " + label,
                        "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            }
            else if ((action == "Verify Object Value")) {
                if (driver.findElement(uniquePageElement).getAttribute("value").contentEquals(strText)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Object", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Exist")) {
                // if (driver.findElement(uniquePageElement).isDisplayed() ) {
                if (ElementAction.isElementPresent(driver,uniquePageElement)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify " + pageName + "- " + section + "; " + label, "Object",
                        section + "; " + label, "");
                }
            } else if ((action == "Verify Not Exist")) {
                // if (ElementAction.lperform(driver, uniquePageElement).size() == 0) {
                if (!ElementAction.isElementPresent(driver,uniquePageElement)) {
                    reportUtil.logPass("Verify not Exist - " + pageName + "; " + section,
                        "Sucessfully Verified Not Exist - " + pageName
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Not Exit -" + pageName + "- " + section + "; " + label,
                        "Object", section + "; " + label, "");
                }
                } else if ((action == "Verify Text")) {
                wait.until(ExpectedConditions.presenceOfElementLocated(uniquePageElement));
                if (driver.findElement(uniquePageElement).getText().contentEquals(strText)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Text Using Regex")) {
                wait.until(ExpectedConditions.presenceOfElementLocated(uniquePageElement));
                if ((driver.findElement(uniquePageElement).getText().matches(strText))
                        || (driver.findElement(uniquePageElement).getText().contentEquals(strText))) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Text Contains")) {
                if (driver.findElement(uniquePageElement).getText().contains(strText)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Text Does not Contains")) {
                if (driver.findElement(uniquePageElement).getText().contains(strText) == false) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Checked")) {
                if (driver.findElement(uniquePageElement).isSelected()) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify " + pageName + "- " + section + "; " + label, "Box",
                        section + "; " + label, "");
                }
            } else if ((action == "Verify Not Checked")) {
                if (!driver.findElement(uniquePageElement).isSelected()) {
                    reportUtil.logPass("Verify Not Checked- " + pageName + "; " + section,
                        "Sucessfully Verified Not Checked" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Not Checked" + pageName + "- " + section + "; " + label,
                        "Box", section + "; " + label, "");
                }
            } else if ((action == "Verify Not Enabled")) {
                if (driver.findElement(uniquePageElement).isEnabled() == false) {
                    reportUtil.logPass("Verify Not Enabled- " + pageName + "; " + section,
                        "Sucessfully Verified Not Enabled" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Not Enabled" + pageName + "- " + section + "; " + label,
                        "Object", section + "; " + label, "");
                }
            } else if ((action == "Verify Enabled")) {
                if (driver.findElement(uniquePageElement).isEnabled()) {
                    reportUtil.logPass("Verify Checked- " + pageName + "; " + section,
                        "Sucessfully Verified  Enabled" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Enabled" + pageName + "- " + section + "; " + label,
                        "Object", section + "; " + label, "");
                }
            } else if ((action == "Is Checked")) {
                if (driver.findElement(uniquePageElement).isSelected()) {
                    returnValue = "Yes";
                    reportUtil.logPass("Verify is Checked- " + pageName + "; " + section,
                        "Sucessfully found as Checked -" + pageName + " for"
                            + section + "; " + label);
                } else {
                    returnValue = "No";
                    reportUtil.logPass("Verify is Checked- " + pageName + "; " + section,
                        "Sucessfully found as Not Checked -" + pageName + " for"
                            + section + "; " + label);
                }
            } else if ((action == "Get Dropdown")) {
                Select options = new Select(driver.findElement(uniquePageElement));
                options.selectByIndex(Integer.parseInt(strText));
                returnValue = driver.findElement(uniquePageElement).getText();
                reportUtil.logPass("Verify is Checked- " + pageName + "; " + section,
                    "Sucessfully found as Checked -" + pageName + " for"
                        + section + "; " + label);
            } else if ((action == "Select Dropdown")) {
                Select options = new Select(driver.findElement(uniquePageElement));
                options.selectByIndex(Integer.parseInt(strText));

                reportUtil.logPass("Dropdown Selected " + pageName + "; " + section,
                    "Sucessfully Dropdown selected -" + pageName + " for"
                        + section + "; " + label);
            } else {
                returnValue = "No";
                reportUtil.logPass("Dropdown Not Selected" + pageName + "; " + section,
                    "Not Selected -" + pageName + " for"
                        + section + "; " + label);
            }
        } catch (NoSuchElementException nse) {
            CommonUtil.logError(driver, nse.getMessage().toString().replace("\n", "<BR>"),
                "Cannot Find Object on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
            // System.exit(0);
        } catch (IOException io) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + io.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "IO Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (TimeoutException tout) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + tout.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "Timeout Exception  on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (StaleElementReferenceException sre) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + sre.getStackTrace().toString().replace("\n", "<BR>") + "</font>",
                "Stale Element Reference Exception  on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (IndexOutOfBoundsException ioob) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + ioob.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "Index Out Of Bounds Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch(NullPointerException npe){
            CommonUtil.logError(driver,
                    "<font color=\"red\">java.lang.NullPointerException"
                            + npe.getStackTrace().toString().replace("\n", "<BR>") + "</font>",
                    "Exception on " + pageName + " , "
                            + section + "; " + label, "Exceptions", section + "; " + label, "");
        }catch (Exception e) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + e.getMessage().toString().replace("\n", "<BR>")
                    + e.getStackTrace().toString().replace("\n", "<BR>") + "</font>",
                "Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        }
        return returnValue;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param action in value
     * @param pageName in value
     * @param section in value
     * @param label in value
     * @param strText in value
     * @param uniquePageElement in value
     * @param objNumber in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public String performActionMultiObjects(WebDriver driver, String action, String pageName, String section,
                                            String label, String strText, By uniquePageElement, int objNumber) throws Exception {
        String returnValue = null;
        WebDriverWait wait;
        wait = new WebDriverWait(driver, 20);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(uniquePageElement));
            List<WebElement> resultsDiv = ElementAction.lperform(driver,uniquePageElement);
            if ((action == "Click")) {
                resultsDiv.get(objNumber).click();
                reportUtil.logPass("Click " + section + "; " + label, "Sucessfully clicked " + section + "; " + label);
            } else if ((action == "Get Object Count")) {
                returnValue = String.valueOf(resultsDiv.size());
                reportUtil.logPass("Get Object Count " + section + "; " + label,
                    "Sucessfully got Object Count " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Get Text")) {
                returnValue = resultsDiv.get(objNumber).getText();
                reportUtil.logPass("Read " + section + "; " + label,
                    "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Select")) {
                new Select(resultsDiv.get(objNumber)).selectByVisibleText(strText);
                resultsDiv.get(objNumber).click();
                reportUtil.logPass("Select " + section + "; " + label,
                    "Sucessfully Selected " + section + "; " + label + "; Value = \"" + strText + "\"");
            } else if ((action == "Get Source")) {
                returnValue = resultsDiv.get(objNumber).getAttribute("src");
                reportUtil.logPass("Read Source of Object" + section + "; " + label,
                    "Sucessfully read Source of Object" + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Set Text")) {
                resultsDiv.get(objNumber).clear();
                resultsDiv.get(objNumber).sendKeys(strText);
                reportUtil.logPass("Enter Text - " + section + "; " + label,
                    "Sucessfully entered text \"" + strText + "\" in " + section + "; " + label);
            } else if ((action == "Verify Text with Object Number")) {
                if (resultsDiv.get(objNumber).getText().contentEquals(strText)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Text Contains with Object Number")) {
                if (resultsDiv.get(objNumber).getText().contains(strText)) {
                    reportUtil.logPass("Verify text contains - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Text withOut Object Number")) {
                if (verifyTextFromObjects(resultsDiv, strText) > 0) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Object", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Text does not Exist withOut Object Number")) {
                if (verifyTextFromObjects(resultsDiv, strText) == 0) {
                    reportUtil.logPass("Verify Text does not Exist - " + pageName + "; " + section,
                        "Sucessfully Verified Text does not Exit" + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify Text does not Exist " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Text", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Get Object Source")) {
                returnValue = resultsDiv.get(objNumber).getAttribute("src");
                reportUtil.logPass("Read " + section + "; " + label,
                    "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Verify Object Source")) {
                if (resultsDiv.get(objNumber).getAttribute("src").contentEquals(strText)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Source value", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Get Object Value")) {
                returnValue = resultsDiv.get(objNumber).getAttribute("value");
                reportUtil.logPass("Read " + section + "; " + label,
                    "Sucessfully read " + section + "; " + label + "; Value = \"" + returnValue + "\"");
            } else if ((action == "Verify Object Value")) {
                if (resultsDiv.get(objNumber).getAttribute("value").contentEquals(strText)) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + strText + " in Page " + pageName + " , "
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "",
                        "Verify " + strText + " in Page " + pageName + " , "
                            + section + "; " + label, "Object Value", strText + "," + section + "; " + label, "");
                }
            } else if ((action == "Verify Exist")) {
                if (resultsDiv.get(objNumber).isDisplayed()) {
                    reportUtil.logPass("Verify - " + pageName + "; " + section,
                        "Sucessfully Verified " + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify " + pageName, "Object", section + "; " + label, "");
                }
            } else if ((action == "Verify Checked")) {
                if (resultsDiv.get(objNumber).isSelected()) {
                    reportUtil.logPass("Verify Checked- " + pageName + "; " + section,
                        "Sucessfully Verified Checked" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Checked" + pageName, "Checkbox/Radio Button",
                        section + "; " + label, "");
                }
            } else if ((action == "Verify Not Enabled")) {
                if (resultsDiv.get(objNumber).isEnabled() == false) {
                    reportUtil.logPass("Verify Not Enabled- " + pageName + "; " + section,
                        "Sucessfully Verified Not Enabled" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Not Enabled" + pageName + "- " + section + "; " + label,
                        "Object", section + "; " + label, "");
                }
            } else if ((action == "Verify Enabled")) {
                if (resultsDiv.get(objNumber).isEnabled()) {
                    reportUtil.logPass("Verify Checked- " + pageName + "; " + section,
                        "Sucessfully Verified  Enabled" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Enabled" + pageName + "- " + section + "; " + label,
                        "Object", section + "; " + label, "");
                }
            } else if ((action == "Verify Not Checked")) {
                if (resultsDiv.get(objNumber).isSelected() == false) {
                    reportUtil.logPass("Verify Not Checked- " + pageName + "; " + section,
                        "Sucessfully Verified Not Checked" + pageName + " for"
                            + section + "; " + label);
                } else {
                    CommonUtil.logError(driver, "", "Verify Not Checked" + pageName, "Checkbox/Radio Button",
                        section + "; " + label, "");
                }
            } else if ((action == "Is Checked")) {
                if (resultsDiv.get(objNumber).isSelected()) {
                    returnValue = "Yes";
                    reportUtil.logPass("Verify is Checked- " + pageName + "; " + section,
                        "Sucessfully found as Checked -" + pageName + " for"
                            + section + "; " + label);
                } else {
                    returnValue = "No";
                    reportUtil.logPass("Verify is Checked- " + pageName + "; " + section,
                        "Sucessfully found as Not Checked -" + pageName + " for"
                            + section + "; " + label);
                }
            } else if ((action == "Is Present")) {
                if (resultsDiv.get(objNumber).getText().length() != 0) {
                    returnValue = "Yes";
                    reportUtil.logPass("Verify is Present- " + pageName + "; " + section,
                        "Sucessfully Present -" + pageName + " for"
                            + section + "; " + label);
                } else {
                    returnValue = "No";
                    reportUtil.logPass("Verify is Present- " + pageName + "; " + section,
                        "Sucessfully Not Present -" + pageName + " for"
                            + section + "; " + label);
                }
            } else if ((action == "Is Image Present")) {
                if (resultsDiv.get(objNumber).getAttribute("src").length() != 0) {
                    returnValue = "Yes";
                } else {
                    returnValue = "No";
                }
            }
        } catch (NoSuchElementException nse) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + nse.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "No Element Found Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
            // System.exit(0);
        } catch (IOException io) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + io.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "IO Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (TimeoutException tout) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + tout.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "Timeout Exception  on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (StaleElementReferenceException sre) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + sre.getStackTrace().toString().replace("\n", "<BR>") + "</font>",
                "Stale Element Reference Exception  on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (IndexOutOfBoundsException ioob) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + ioob.getMessage().toString().replace("\n", "<BR>") + "</font>",
                "Index Out Of Bounds Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        } catch (Exception e) {
            CommonUtil.logError(driver,
                "<font color=\"red\">" + e.getMessage().toString().replace("\n", "<BR>")
                    + e.getStackTrace().toString().replace("\n", "<BR>") + "</font>",
                "Exception on " + pageName + " , "
                    + section + "; " + label, "Exceptions", section + "; " + label, "");
        }
        return returnValue;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param strText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void select(WebDriver driver, String pageName, By uniquePageElement, String strText,
                       String elementNameForReporting) throws Exception {
        performAction(driver, "Select", pageName, elementNameForReporting, "", strText, uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param indexNumber in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void selectByIndex(WebDriver driver, String pageName, By uniquePageElement, Integer indexNumber,
                              String elementNameForReporting) throws Exception {
        performAction(driver, "SelectByIndex", pageName, elementNameForReporting, "", Integer.toString(indexNumber),
            uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param strText in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void selectMultiObj(WebDriver driver, String pageName, By uniquePageElement, String strText,
                               String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Select", pageName, elementNameForReporting, "", strText, uniquePageElement,
            objNumber);
    }

    /**
     * Setter for property text
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param strText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void setText(WebDriver driver, String pageName, By uniquePageElement, String strText,
                        String elementNameForReporting) throws Exception {
        performAction(driver, "Set Text", pageName, elementNameForReporting, "", strText, uniquePageElement);
    }

    public void setPassword(WebDriver driver, String pageName, By uniquePageElement, String strEncryptedPassword,
                        String elementNameForReporting) throws Exception {
       try {
           driver.findElement(uniquePageElement).clear();
           driver.findElement(uniquePageElement).sendKeys(encryption.decrypt(strEncryptedPassword));
           reportUtil.logPass("Password entered successfully");
       }
       catch (Exception e){
           reportUtil.logFail("Enter password", "Failed to enter Password");
       }
    }

    /**
     * Setter for property text multi obj
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param strText in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void setTextMultiObj(WebDriver driver, String pageName, By uniquePageElement, String strText,
                                String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Set Text", pageName, elementNameForReporting, "", strText, uniquePageElement,
            objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedstrSelection in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void shouldSelect(WebDriver driver, String pageName, By uniquePageElement,
            String expectedstrSelection, String elementNameForReporting) throws Exception {
        if (expectedstrSelection.compareToIgnoreCase("Yes") == 0) {
            if (isChecked(driver, pageName, uniquePageElement, elementNameForReporting).compareToIgnoreCase("No") == 0) {
                click(driver, pageName, uniquePageElement, elementNameForReporting);
            }
        } else {
            if (isChecked(driver, pageName, uniquePageElement, elementNameForReporting).compareToIgnoreCase("Yes") == 0) {
                click(driver, pageName, uniquePageElement, elementNameForReporting);
            }
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void submit(WebDriver driver, String pageName, By uniquePageElement, String elementNameForReporting)
    throws Exception {
        performAction(driver, "Submit", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyChecked(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Checked", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyCheckedMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Checked", pageName, elementNameForReporting, "", "", uniquePageElement,
            objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyEnabled(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Enabled", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyEnabledMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Enabled", pageName, elementNameForReporting, "", "", uniquePageElement,
            objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyExist(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Exist", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyExistMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Exist", pageName, elementNameForReporting, "", "", uniquePageElement,
            objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedstrChecked in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyIsChecked(WebDriver driver, String pageName, By uniquePageElement,
            String expectedstrChecked, String elementNameForReporting) throws Exception {
        if (expectedstrChecked.compareToIgnoreCase("Yes") == 0) {
            verifyChecked(driver, pageName, uniquePageElement, elementNameForReporting);
        } else {
            verifyNotChecked(driver, pageName, uniquePageElement, elementNameForReporting);
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedstrChecked in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyIsCheckedMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String expectedstrChecked, String elementNameForReporting, int objNumber) throws Exception {
        if (expectedstrChecked.compareToIgnoreCase("Yes") == 0) {
            verifyCheckedMultiObj(driver, pageName, uniquePageElement, elementNameForReporting, objNumber);
        } else {
            verifyNotCheckedMultiObj(driver, pageName, uniquePageElement, elementNameForReporting, objNumber);
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyNotChecked(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Not Checked", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyNotCheckedMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Not Checked", pageName, elementNameForReporting, "", "",
            uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyNotEnabled(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Not Enabled", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyNotEnabledMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Not Enabled", pageName, elementNameForReporting, "", "",
            uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyNotExist(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Not Exist", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyNotExistMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Not Exist", pageName, elementNameForReporting, "", "", uniquePageElement,
            objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedObjectValue in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyObjectSourceMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String expectedObjectValue, String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Object Source", pageName, elementNameForReporting, "",
            expectedObjectValue, uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedObjectValue in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyObjectValue(WebDriver driver, String pageName, By uniquePageElement,
            String expectedObjectValue, String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Object Value", pageName, elementNameForReporting, "", expectedObjectValue,
            uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedObjectValue in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyObjectValueMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String expectedObjectValue, String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Object Value", pageName, elementNameForReporting, "", expectedObjectValue,
            uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyText(WebDriver driver, String pageName, By uniquePageElement, String expectedText,
                           String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Text", pageName, elementNameForReporting, "", expectedText, uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyTextContains(WebDriver driver, String pageName, By uniquePageElement,
            String expectedText, String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Text Contains", pageName, elementNameForReporting, "", expectedText,
            uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyTextContainsObjNumberMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String expectedText, String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Text Contains with Object Number", pageName, elementNameForReporting, "",
            expectedText, uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyTextDoesNotContains(WebDriver driver, String pageName, By uniquePageElement,
            String expectedText, String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Text Does not Contains", pageName, elementNameForReporting, "", expectedText,
            uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param objectList in value
     * @param strText in value
     *
     * @return out value
     */
    public int verifyTextFromObjects(List<WebElement> objectList, String strText) {
        int returnValue = 0;
        for (int i = 0; i < objectList.size(); i++) {
            if (objectList.get(i).getText().contains(strText)) {
                returnValue = i;
                i = objectList.size() + 1;
            }
        }
        return returnValue;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyTextNotExistWithOutObjNumberMultiObj(WebDriver driver, String pageName,
                                                           By uniquePageElement, String expectedText, String elementNameForReporting) throws Exception {
        performActionMultiObjects(driver, "Verify Text does not Exist withOut Object Number", pageName,
            elementNameForReporting, "", expectedText, uniquePageElement, -1);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     * @param objNumber in value
     *
     * @throws Exception on error
     */
    public void verifyTextObjNumberMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String expectedText, String elementNameForReporting, int objNumber) throws Exception {
        performActionMultiObjects(driver, "Verify Text with Object Number", pageName, elementNameForReporting, "",
            expectedText, uniquePageElement, objNumber);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyTextRegex(WebDriver driver, String pageName, By uniquePageElement, String expectedText,
                                String elementNameForReporting) throws Exception {
        performAction(driver, "Verify Text Using Regex", pageName, elementNameForReporting, "", expectedText,
            uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param expectedText in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void verifyTextWithOutObjNumberMultiObj(WebDriver driver, String pageName, By uniquePageElement,
            String expectedText, String elementNameForReporting) throws Exception {
        performActionMultiObjects(driver, "Verify Text withOut Object Number", pageName, elementNameForReporting, "",
            expectedText, uniquePageElement, -1);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void wait(WebDriver driver, String pageName, By uniquePageElement, String elementNameForReporting)
    throws Exception {
        performAction(driver, "Wait", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void waitForElementToBeClickable(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Wait Element Clickable", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param uniquePageElement in value
     */
    public void waitForElementToBecomeBlock(WebDriver driver, By uniquePageElement) {
        WebElement elementToDissappear = driver.findElement(uniquePageElement);
        for (int second = 0;; second++) {
            if (second >= 20) {
                try {
                    if (!("block".equals(elementToDissappear.getCssValue("display")))) {
                        break;
                    }
                } catch (Exception e) {
                    ;
                }
            }
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void waitForInvisibilityOfElementLocated(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Wait until element Not Visible", pageName, elementNameForReporting, "", "",
            uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void waitForVisibilityOfElementLocated(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Wait Visibility of Element", pageName, elementNameForReporting, "", "", uniquePageElement);
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageName in value
     * @param uniquePageElement in value
     * @param elementNameForReporting in value
     *
     * @throws Exception on error
     */
    public void waitUntilElementToBeSelected(WebDriver driver, String pageName, By uniquePageElement,
            String elementNameForReporting) throws Exception {
        performAction(driver, "Wait Element Selected", pageName, elementNameForReporting, "", "", uniquePageElement);
    }


}
