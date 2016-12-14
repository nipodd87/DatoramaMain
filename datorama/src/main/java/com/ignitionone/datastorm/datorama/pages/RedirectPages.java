package com.ignitionone.datastorm.datorama.pages;

/**
 * Created by karthik.inuganti on 12/6/2016.
 */

import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ElementUtil;
import org.openqa.selenium.WebDriver;

public class RedirectPages extends BasePage {


    private static String PAGE_NAME = "RedirectPages";
    public RedirectPages(CommonUtil commonUtil, ElementUtil elementUtil) {
        super(commonUtil, elementUtil);
    }


    public void clickTest(WebDriver driver, String t) throws Exception {
        redirect(driver, t);
    }

}
