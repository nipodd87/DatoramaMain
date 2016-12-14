package com.ignitionone.datastorm.datorama.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Enter Javadoc
 */
public class ElementAction {

    //~ Static fields/initializers -------------------------------------------------------------------------------------

    public static final String SELECTOR_ID = "ID";
    public static final String SELECTOR_XPATH = "XPATH";
    public static final String SELECTOR_LINKTEXT = "LINKTEXT";
    public static final String SELECTOR_CLASS = "CLASS";
    public static final String SELECTOR_CSS = "CSS";
    public static final int timeoutInSec = 1000;

    /**
     * Getter for property element present
     *
     * @param driver in value
     * @param pageElements in value
     *
     * @return out value
     */
    public static boolean isElementPresent(WebDriver driver, By pageElements) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        try {
            List<WebElement> webElements = lperform(driver, pageElements);
            driver.manage().timeouts().implicitlyWait(timeoutInSec, TimeUnit.SECONDS);
            if (webElements.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageElements in value
     *
     * @return out value
     *
     * @throws Exception on error
     */
    public static List<WebElement> lperform(WebDriver driver, By pageElements) throws Exception {
        List<WebElement> webElements = driver.findElements(pageElements);
        return webElements;
    }

    /**
     * TODO: Enter Javadoc
     *
     * @param driver in value
     * @param pageElements in value
     *
     * @return out value
     */
    public static WebElement perform(WebDriver driver, By pageElements) {
        WebElement webElement = driver.findElement(pageElements);
        return webElement;
    }



}
