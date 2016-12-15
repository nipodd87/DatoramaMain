package com.ignitionone.datastorm.datorama.util;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

/**
 * TODO: Enter Javadoc
 */
public class Browser {

    //~ Static fields/initializers -------------------------------------------------------------------------------------

    private static int os_def = -1;
    public static String SEPARATOR = System.getProperty("file.separator");
    public static String driverFolder = "/drivers";
    public static final int timeoutInSec = 40;

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Getter for property os define
     *
     * @return out value
     */
    public static int getOsDefine() {
        if (os_def == -1) {
            String os = System.getProperty("os.name").toLowerCase();
            if ((os.indexOf("win") >= 0)) {
                os_def = 0;
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                os_def = 1;
            } else if (os.indexOf("mac") >= 0) {
                os_def = 2;
            }
        }
        return os_def;
    }

    /**
     *Method to create driver based on BrowserName
     *
     * @param browserName in value
     *
     * @return out value
     */
    public WebDriver createDriver(String browserName) {
        String s = browserName.toUpperCase();
        if (s.equals("CHROME")) {
    /*        String drivernameFormat = "chromedriver_%s";
            String drivername = "chromedriver.exe";

            *//*
             * -1: Unknown 0: Window 1: Linux 2: Mac
             *//*
            int os = getOsDefine();
            if (os == 0) {
                drivername = String.format(drivernameFormat, "win.exe");
            } else if (os == 1) {
                drivername = String.format(drivernameFormat, "linux");
            } else if (os == 2) {
                drivername = String.format(drivernameFormat, "mac");
            }

            String url = this.getClass().getResource(driverFolder + SEPARATOR + drivername).getPath();

            // System.out.println("The url is " + url);
            // System.out.println("The url is " + url.replace("%5c", "/"));
            url = url.replace("%5c", "/");

            System.setProperty("webdriver.chrome.driver", url);

            DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
            capabilitiesChrome.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                UnexpectedAlertBehaviour.ACCEPT);

            WebDriver driver = new ChromeDriver(capabilitiesChrome);

            driver.manage().timeouts().pageLoadTimeout(timeoutInSec, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(timeoutInSec, TimeUnit.SECONDS);*/
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            return driver;
        } else if (s.equals("FIREFOX")) {
            WebDriver driver;
            Proxy proxy = new Proxy();
            proxy.setAutodetect(true);

            DesiredCapabilities capabilitiesFirefox = DesiredCapabilities.firefox();
            capabilitiesFirefox.setCapability(CapabilityType.PROXY, proxy);
            capabilitiesFirefox.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                UnexpectedAlertBehaviour.ACCEPT);
            driver = new FirefoxDriver(capabilitiesFirefox);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(timeoutInSec, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(timeoutInSec, TimeUnit.SECONDS);
            return driver;
        } else if (s.equals("INTERNET_EXPLORER") || s.equals("IE7") || s.equals("IE8") || s.equals("IE9")
                || s.equals("IE10") || s.equals("IE")) {
            String url;
            WebDriver driver;
            url = this.getClass().getResource(driverFolder + SEPARATOR + "IEDriverServer.exe").getPath();

            // System.out.println("The url is " + url.replace("%5c", "/"));
            url = url.replace("%5c", "/");
            System.setProperty("webdriver.ie.driver", url);
            DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
            capabilitiesIE.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
            capabilitiesIE.setCapability("ignoreZoomSetting", true);
            capabilitiesIE.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driver = new InternetExplorerDriver(capabilitiesIE);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(timeoutInSec, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(timeoutInSec, TimeUnit.SECONDS);
            return driver;
        } else if (s.equals("SAFARI")) {
            WebDriver driver;
            DesiredCapabilities capabilitiesSafari = DesiredCapabilities.safari();
            capabilitiesSafari.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                UnexpectedAlertBehaviour.ACCEPT);
            driver = new SafariDriver(capabilitiesSafari);
            driver.manage().timeouts().pageLoadTimeout(timeoutInSec, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(timeoutInSec, TimeUnit.SECONDS);
            return driver;
        } else if (s.equals("HTMLUNIT")) {
            WebDriver driver;
            driver = new HtmlUnitDriver();
            return driver;
        } else {
            ;
        }
        return null;
    }
}
