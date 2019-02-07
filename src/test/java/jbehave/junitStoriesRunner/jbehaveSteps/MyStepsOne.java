package jbehave.junitStoriesRunner.jbehaveSteps;

import jbehave.junitStoriesRunner.jbehavePageObjects.MyStepsOnePageObject;
import org.jbehave.core.annotations.*;

public class MyStepsOne {

    private MyStepsOnePageObject myStepsOnePageObject;

    public MyStepsOne(MyStepsOnePageObject myStepsOnePageObject) {
        this.myStepsOnePageObject = myStepsOnePageObject;
    }

    @Given("I go to ${page} page")
    public void givenIGoToHttplocalhostPage(String page) {
        myStepsOnePageObject.goToPage(page);
    }
}
