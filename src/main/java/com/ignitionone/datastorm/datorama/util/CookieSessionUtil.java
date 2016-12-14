package com.ignitionone.datastorm.datorama.util;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.Set;


public class CookieSessionUtil {

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * This method adds or creates a cookie
     *
     * @param driver in value
     * @param name in value
     * @param value in value
     * @param domain in value
     * @param path in value
     * @param expiry in value
     */
    public void addCookie(WebDriver driver, String name, String value, String domain, String path, Date expiry) {
        driver.manage().addCookie(new Cookie(name, value, domain, path, expiry));
    }

    /**
     * This method adds set of cookies for a domain
     *
     * @param driver in value
     * @param cookies in value
     * @param domain in value
     * @param name in value
     * @param value in value
     * @param path in value
     * @param expiry in value
     */
    public void addCookiesToBrowser(WebDriver driver, Set<Cookie> cookies, String domain, String name, String value,
                                    String path, Date expiry) {
        for (Cookie c : cookies) {
            if (c != null) {
                if (c.getDomain().contains(domain)) {
                    driver.manage().addCookie(new Cookie(name, value, domain, path, expiry));
                }
            }
        }
        driver.navigate().refresh();
    }

    /**
     * This method deletes all cookies
     *
     * @param driver in value
     */
    public void deleteAllCookies(WebDriver driver) {
        driver.manage().deleteAllCookies();
    }

    /**
     * This method deletes a specific cookie
     *
     * @param driver in value
     * @param name in value
     */
    public void deleteCookieNamed(WebDriver driver, String name) {
        // driver.manage().deleteCookieNamed(name);
        ((JavascriptExecutor) driver).executeScript("document.cookie = '" + name
                + "=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;'");
    }

    /**
     * This method edit a cookie
     *
     * @param driver in value
     * @param name in value
     * @param value in value
     */
    public void editCookie(WebDriver driver, String name, String value) {
        /*        Cookie cookie = driver.manage().getCookieNamed(name);
         *      driver.manage().deleteCookieNamed(name);
         *      driver.manage().addCookie(
         *              new Cookie.Builder(cookie.getName(), value)
         *                      .domain(cookie.getDomain())
         *                      .expiresOn(cookie.getExpiry())
         *                      .path(cookie.getPath())
         *                      .isSecure(cookie.isSecure())
         *                      .build()
         *      );*/
        ((JavascriptExecutor) driver).executeScript("document.cookie = '" + name + "=" + value + "; path=/;'");
    }

    /**
     * This method gets all the cookies
     *
     * @param driver in value
     *
     * @return out value
     */
    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    /*
         **
                 * This method gets all the cookies in the inter domain and redirects back to the landing page.
          *
                  * @param driver in value
          *
                  * @return out value
                  * @param Domain   in value
                  * @param Redirect   in value
          */
    public Set<Cookie> getAllCookies(WebDriver driver, String Domain, String Redirect) {
        driver.navigate().to(Redirect);
        Set<Cookie> cookie = driver.manage().getCookies();
        driver.navigate().to(Domain);
        return cookie;
    }


    /**
     * This method gets a specified cookie
     *
     * @param driver in value
     * @param name in value
     *
     * @return out value
     */
    public Cookie getCookieNamed(WebDriver driver, String name) {
        return driver.manage().getCookieNamed(name);
    }

    /**
     * Retrive value of the Cookie
     *
     * @param driver in value
     * @param name in value
     *
     * @return out value
     */
    public String getValueOfCookieNamed(WebDriver driver, String name) {
        if (IsCookiePresent(driver, name)) {
            return driver.manage().getCookieNamed(name).getValue();
        }
        return "Cookie Not Present";
    }
    /**
     * Retrive a value of the particular Cookie and redirect to landing page
     *
     * @param driver   in value
     * @param name     in value
     * @param Domain   in value
     * @param Redirect in value
     * @return out value
     */
    public String getValueOfCookieNamed(WebDriver driver, String name, String Domain, String Redirect) {
        driver.navigate().to(Redirect);

        if (IsCookiePresent(driver, name)) {
            String cookie = driver.manage().getCookieNamed(name).getValue();
            String domain = driver.manage().getCookieNamed(name).getDomain();
            Date enddate= driver.manage().getCookieNamed(name).getExpiry();
            System.out.println("UCookie " + cookie );
            System.out.println("EndDate" + enddate);
            System.out.println("domain" + domain);

            driver.navigate().to(Domain);
            return cookie;
        }

        return "Cookie Not Present";
    }

    /**
     * This method verified if the Cookis is Present
     *
     * @param driver in value
     * @param cookieName in value
     *
     * @return out value
     */
    public boolean IsCookiePresent(WebDriver driver, String cookieName) {
        return driver.manage().getCookieNamed(cookieName) != null;
    }
    public String getValueOfCookieNamed(WebDriver driver, String name, String Domain) {


        try {
            if (IsCookiePresent(driver, name)) {
                String cookie = driver.manage().getCookieNamed(name).getValue();
                //    driver.navigate().to(Domain);
                driver.get(Domain);
                return cookie;

            } else {
                return "Cookie Not Present";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Cookie Not Present";
    }

}

