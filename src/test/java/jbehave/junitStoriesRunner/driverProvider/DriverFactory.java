package jbehave.junitStoriesRunner.driverProvider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {

    private WebDriver webDriver;

    public DriverFactory(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver initDriver(String browser) {
        if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            webDriver = new FirefoxDriver(capabilities);
        } else if (browser.equals("chromeHeadless")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            ChromeOptions chromeHeadless = new ChromeOptions();
            chromeHeadless.setHeadless(true);
            webDriver = new ChromeDriver(chromeHeadless);
        } else if (browser.equals("iexplorer")) {
            System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
            DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
            capabilitiesIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            webDriver = new InternetExplorerDriver(capabilitiesIE);
        } else if (browser.equals("edge")) {
            System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");
            webDriver = new EdgeDriver();
        } else if (browser.equals("opera")) {
            System.setProperty("webdriver.opera.driver", "operadriver.exe");
            webDriver = new OperaDriver();
        }
        return webDriver;
    }
}
