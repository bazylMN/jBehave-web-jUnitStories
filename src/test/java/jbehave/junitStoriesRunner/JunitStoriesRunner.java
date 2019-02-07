package jbehave.junitStoriesRunner;

import jbehave.junitStoriesRunner.driverProvider.BeforeAfterHelper;
import jbehave.junitStoriesRunner.driverProvider.DriverProvider;
import jbehave.junitStoriesRunner.jbehavePageObjects.MyStepsOnePageObject;
import jbehave.junitStoriesRunner.jbehavePageObjects.MyStepsTwoPageObject;
import jbehave.junitStoriesRunner.jbehaveSteps.MyStepsOne;
import jbehave.junitStoriesRunner.jbehaveSteps.MyStepsTwo;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.StoryControls;

import org.jbehave.core.embedder.executors.DirectExecutorService;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.*;
import org.jbehave.web.selenium.*;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import static org.jbehave.core.reporters.Format.*;
import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;

public class JunitStoriesRunner extends JUnitStories{

    private WebDriver webDriver;
    private WebDriverProvider driverProvider =  new DriverProvider(webDriver);

    private WebDriverSteps lifecycleSteps = new PerStoriesWebDriverSteps(driverProvider);
    private SeleniumContext context = new SeleniumContext();
    private ContextView contextView = new LocalFrameContextView().sized(500, 100);
    private final CrossReference xref = new CrossReference();


    public JunitStoriesRunner() {
        super();
        // If configuring lifecycle per-stories, you need to ensure that you a same-thread executor via DirectExecutorService
        if ( lifecycleSteps instanceof PerStoriesWebDriverSteps ){
            configuredEmbedder()
                    .useExecutorService(new DirectExecutorService()
                            .create(configuredEmbedder()
                                    .embedderControls()
                                    .doGenerateViewAfterStories(true)
                                    .doIgnoreFailureInStories(true)
                                    .doIgnoreFailureInView(true)
                                    .useThreads(2)
                                    .useStoryTimeouts("30")));
        }
    }

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        return new SeleniumConfiguration()
                .useSeleniumContext(context)
                .useWebDriverProvider(driverProvider)
                .useStoryControls(new StoryControls()
                        .doDryRun(false)
                        .doSkipScenariosAfterFailure(true)
                        .doResetStateBeforeScenario(true)
                        .doResetStateBeforeStory(true))
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStepMonitor(new SeleniumStepMonitor(contextView, context, new SilentStepMonitor()))
                .useStoryLoader(new LoadFromClasspath(embeddableClass.getClassLoader()))
                .useStepPatternParser(new RegexPrefixCapturingPatternParser("$"))
                .useParameterConverters(new ParameterConverters()
                        .addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd"))))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(CodeLocations.codeLocationFromPath("reports/jbehave"))
                        .withViewResources(viewResources)
                        .withDefaultFormats()
                        .withFormats(CONSOLE, TXT, STATS, WEB_DRIVER_HTML)
                        .withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                        .withFailureTrace(true)
                        .withFailureTraceCompression(true)
                        .withCrossReference(xref));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(),
                new BeforeAfterHelper((DriverProvider) driverProvider),
                new MyStepsOne(new MyStepsOnePageObject((DriverProvider) driverProvider)),
                new MyStepsTwo(new MyStepsTwoPageObject((DriverProvider) driverProvider)),
                lifecycleSteps);
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(org.jbehave.core.io.CodeLocations.codeLocationFromPath("src/test/resources"), "**/stories/*.story", "");
    }

    @Test
    public void run() {
        configuredEmbedder()
                .runStoriesAsPaths(storyPaths());
    }
}
