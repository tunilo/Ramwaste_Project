package UITests;


import com.microsoft.playwright.*;

import io.qameta.allure.*;
import org.example.Pages.swoop.EatAndDrinkPage;
import org.example.Steps.Swoop.*;
import org.example.data.Constants;
import org.example.data.LanguageDataProvider;
import org.example.data.SearchDataProvider;
import org.testng.Assert;
import org.testng.annotations.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

@Epic("Swoop Platform Tests")
@Feature("Search Feature")
@Test(groups = "SwoopRegression")
public class SwoopTest  {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    protected BrowserContext context;

    // Steps
    private HomeSteps homePageSteps;
    private SearchSteps searchResultsSteps;
    private HairSteps hairSteps;
    private OfferSteps offerSteps;
    private EatAndDrinkSteps eatAndDrinkSteps;
    private LanguageSteps languageSteps;

    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void setup() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        // Set viewport to screen size (simulate maximize)
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(width, height);
       // page = browser.newPage();
        context = browser.newContext(contextOptions);
        page = context.newPage();
        page.navigate(Constants.SWOOP_URL);

        // Init steps with fresh page
        homePageSteps = new HomeSteps(page);
        searchResultsSteps = new SearchSteps(page);
        hairSteps = new HairSteps(page);
        offerSteps = new OfferSteps(page);
        eatAndDrinkSteps = new EatAndDrinkSteps( page);
        languageSteps = new LanguageSteps(page);
    }

    @AfterMethod
    public void closePage() {
        page.close();
    }

    @AfterClass
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to validate search functionality with valid  keywords")
    @Story("Search Validation")
    public void searchTest() throws InterruptedException {
        String validQuery = "car";
        boolean expectResults = true;
        homePageSteps
                .closePopup()
                .enterSearchQuery(validQuery)
                .clickSearchButton();
        Thread.sleep(5000);
        searchResultsSteps.validateSearchResult(validQuery, expectResults);
    }
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to validate search functionality with invalid keywords")
    @Story("Search Validation")
    public void searchInvalidTest() throws InterruptedException {
        String invalidQuery = "asdzxc!@#";
        boolean expectNoResults = false;
        homePageSteps
                .closePopup()
                .enterSearchQuery(invalidQuery)
                .clickSearchButton();
        Thread.sleep(5000);
        searchResultsSteps.validateSearchResult(invalidQuery, expectNoResults);
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test pagination and validate results")
    @Story("Pagination and Results Validation")
    public void paginationTest() {
        homePageSteps
                .closePopup()
                //.navigateToCategories()
                //.hoverOverEstetikaCategory()
                .navigateToEatAndDrink();
               // .selectHairBeardCategory();

        hairSteps.validatePagination()
                .navigateToPreviousPageAndValidate()
                .navigateToNextPageAndValidate();
    }

    @Test(description = "Test location scrolling to map")
    @Severity(SeverityLevel.NORMAL)
    @Story("Scroll to Map Location Validation")
    public void offerLocationTest() throws InterruptedException {
        homePageSteps
                .closePopup()
                .navigateToCategories()
                .hoverFunCategory()
                .selectNightClubCategory();
        Thread.sleep(3000);
        offerSteps
                .selectFirstOffer()
                .clickLocationButton()
                .validateMapIsInView();
    }

    @Story("Filter Validation")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Validate Number Of Guests Filter")
    public void validateNumberOfGuestsFilter() {
        homePageSteps
                .closePopup()
                .navigateToEatAndDrink();
        eatAndDrinkSteps
                .filterByNumberOfGuests()
                .validateFilteredResults();
    }

    @Story("Language Translation Validation")
    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "languageData", dataProviderClass = LanguageDataProvider.class)
    public void validateLanguageTest(String language, String[] expectedCategories) throws InterruptedException {
        homePageSteps.closePopup();
        System.out.println(language);
        System.out.println(Arrays.toString(expectedCategories));
        if (language.equals(Constants.ENGLISH)) {
            languageSteps
                    .openLanguageDropdown()
                    .switchToEnglish();
            Thread.sleep(5000);
        } else if (language.equals(Constants.GEORGIAN)) {
            languageSteps
                    .openLanguageDropdown()
                    .switchToGeorgian();
            Thread.sleep(5000);

        }
        languageSteps.validateTranslations(expectedCategories);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Validate switching between English and Georgian languages")
    @Story("Language Switching")
    public void changeLanguageTest() throws InterruptedException {
        homePageSteps.closePopup();
        languageSteps
                .openLanguageDropdown()
                .switchToEnglish()
                .validateEnglishLanguage();

        languageSteps
                .openLanguageDropdown()
                .switchToGeorgian()
                .validateGeorgianLanguage();
    }

    @Test
    public void snapshotTest() throws Exception {
        String snapshotName = "Home.png";
        String baselinePath = "snapshots/" + snapshotName;

        Path currentScreenshot = Paths.get("temp/" + snapshotName);
        Files.createDirectories(currentScreenshot.getParent());
        page.screenshot(new Page.ScreenshotOptions().setPath(currentScreenshot));

        Path baseline = Paths.get(baselinePath);
        if (!Files.exists(baseline)) {
            Files.createDirectories(baseline.getParent());
            Files.copy(currentScreenshot, baseline);
            System.out.println("Baseline created: " + baseline);
        } else {
            byte[] current = Files.readAllBytes(currentScreenshot);
            byte[] expected = Files.readAllBytes(baseline);
            Assert.assertEquals(current, expected, "Visual regression detected!");
        }
    }

}
