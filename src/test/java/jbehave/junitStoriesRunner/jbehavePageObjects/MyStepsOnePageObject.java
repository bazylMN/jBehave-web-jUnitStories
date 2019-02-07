package jbehave.junitStoriesRunner.jbehavePageObjects;

import jbehave.junitStoriesRunner.driverProvider.DriverProvider;

public class MyStepsOnePageObject {

    private DriverProvider provider;

    public MyStepsOnePageObject(DriverProvider provider) {
        this.provider = provider;
    }

    public void goToPage(String page) {
        provider.get().get(page);
    }
}
