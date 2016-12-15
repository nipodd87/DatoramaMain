package com.ignitionone.datastorm.datorama.pages;

import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ElementAction;
import com.ignitionone.datastorm.datorama.util.ElementUtil;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected CommonUtil commonUtil;
    protected ElementAction elementAction;
    protected ElementUtil elementUtil;

    public BasePage(CommonUtil commonUtil, ElementUtil elementUtil) {
        this.elementUtil = elementUtil;
        this.commonUtil = commonUtil;
        this.elementAction = new ElementAction();
    }
    public void refreshPage(WebDriver driver){
        driver.navigate().refresh();
    }

    public void redirect(WebDriver driver,String Domain){
        driver.get(Domain);
    }
}
