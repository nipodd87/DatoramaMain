package com.ignitionone.datastorm.datorama.pages;

import com.ignitionone.datastorm.datorama.util.CommonUtil;
import com.ignitionone.datastorm.datorama.util.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage  {

    // defining object Properties
    private static String URL = "https://dev-dms.ignitionone.com/";
    private static String PAGE_NAME = "Login";
    private static By USERNAME = By.id("Username");
    private static By PASSWORD = By.id( "Password");
    private static By LOGIN = By.id("submit");
    private static By FORGOT_PASSWORD = By.id("forgotpassword_link'");
    private static By REMEMBER_ME = By.id("RememberMe");

    /**
     * Creates a new BasePage object.
     *
     * @param commonUtil  in value
     * @param elementUtil in value
     */
    public LoginPage(CommonUtil commonUtil, ElementUtil elementUtil) {
        super(commonUtil, elementUtil);
    }


    public void goToLogin(WebDriver driver, String Url) {
        driver.get(Url);
    }

    public void goToLogin(WebDriver driver) {
        driver.get(URL);
    }

    public void enterUserName(WebDriver driver, String userName) throws Exception {
        elementUtil.setText(driver,PAGE_NAME, USERNAME,userName,"User Name");
    }

    public void enterPassword(WebDriver driver, String encryptedPasswordValue) throws Exception {
        elementUtil.setPassword(driver,PAGE_NAME, PASSWORD,encryptedPasswordValue,"Password");
    }

    public void clickLoginButton(WebDriver driver) throws Exception {
        elementUtil.click(driver,PAGE_NAME, LOGIN,"Log In Button");
    }

    public void clickForgotPasswordLink(WebDriver driver) throws Exception {
        elementUtil.click(driver,PAGE_NAME, FORGOT_PASSWORD,"Forgot Password Link");
    }

    public void clickRememberMeCheckBox(WebDriver driver) throws Exception {
        elementUtil.click(driver,PAGE_NAME, REMEMBER_ME,"Remember Me Checkbox");
    }
}
