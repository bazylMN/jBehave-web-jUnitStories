package jbehave.junitStoriesRunner.driverProvider;

import org.jbehave.core.annotations.*;

import static org.jbehave.core.annotations.AfterScenario.*;

public class BeforeAfterHelper {

    private final String SCREENSHOTS_PATH = "reports/jbehave/screenshots/failed-scenario-";

    private DriverProvider driverProvider;

    public BeforeAfterHelper(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @BeforeScenario
    public void beforeScenario() {
        driverProvider.clearCookies();
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void deleteCookiessWhenFailed() {
        driverProvider.saveScreenshotTo(SCREENSHOTS_PATH);
        driverProvider.clearCookies();
    }

    @AfterStories
    public void afterStories(){
        driverProvider.end();
    }
}
