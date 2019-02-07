package jbehave.junitStoriesRunner.driverProvider;

import org.apache.commons.io.IOUtils;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;

public class DriverProvider extends DriverFactory implements WebDriverProvider {

    private WebDriver webDriver;

    public DriverProvider(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    @Override
    public WebDriver get() {
        return webDriver;
    }

    @Override
    public void initialize() {
        String browser = (System.getProperty("browser", ""));

        if(browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            webDriver = new ChromeDriver();
        } else {
            webDriver = initDriver(browser);
        }
    }

    @Override
    public boolean saveScreenshotTo(String path) {
        if (get() instanceof TakesScreenshot) {
            byte[] bytes = ((TakesScreenshot) get()).getScreenshotAs(OutputType.BYTES);
            String pathToScreenshots = path + System.currentTimeMillis() + ".png";
            try {
                IOUtils.write(bytes, new FileOutputStream(pathToScreenshots));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            System.out.println("Screenshot cannot be taken with " + get().getClass().getName());
            return false;
        }
    }

    public void clearCookies() {
        get().manage().deleteAllCookies();
    }

    @Override
    public void end() {
        get().close();
        get().quit();
    }
}
