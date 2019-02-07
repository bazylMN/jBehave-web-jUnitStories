package jbehave.junitStoriesRunner.jbehaveSteps;

import jbehave.junitStoriesRunner.jbehavePageObjects.MyStepsTwoPageObject;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

public class MyStepsTwo {

    private MyStepsTwoPageObject myStepsTwoPageObject;

    public MyStepsTwo(MyStepsTwoPageObject myStepsTwoPageObject) {
        this.myStepsTwoPageObject = myStepsTwoPageObject;
    }

    @Then("I should be on ${page} page")
    public void givenIShouldBeOnHttplocalhostPage(String page) {
        myStepsTwoPageObject.clickCookiesButtonIfExists();
        Assert.assertTrue(myStepsTwoPageObject.getUrl(), myStepsTwoPageObject.getUrl().contains(page));
    }
}
