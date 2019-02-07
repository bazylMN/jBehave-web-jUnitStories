package jbehave.junitStoriesRunner.jbehavePageObjects;

import jbehave.junitStoriesRunner.driverProvider.DriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyStepsTwoPageObject {

    private DriverProvider driverProvider;

    public MyStepsTwoPageObject(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    private static final String COOKIES_ACCEPTANCE_TEXT = "PRZECHODZĘ DO SERWISU";
    private final By BUTTON = By.cssSelector("button");


    public void clickCookiesButtonIfExists() {
        List<WebElement> elements = driverProvider.get().findElements(BUTTON);
        clickOneFromElementsWithText(elements, COOKIES_ACCEPTANCE_TEXT);
    }

    public String getUrl() {
        return driverProvider.get().getCurrentUrl().toLowerCase();
    }

    private void clickOneFromElementsWithText(List<WebElement> elements, String text) {
        for (WebElement element : elements) {
            boolean has = element.getText().contains(text);
            if (has) {
                element.click();
            }
        }
    }
}
