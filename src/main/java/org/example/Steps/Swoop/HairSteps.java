package org.example.Steps.Swoop;

import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.example.Pages.swoop.HairPage;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

public class HairSteps {
    private final Page page;
    private final HairPage hairPage;

    public HairSteps(Page page) {
        this.page = page;
        this.hairPage = new HairPage(page);
    }

    @Step("Getting all product titles on the current page")
    public List<String> getAllProductTitles() {
        return hairPage.products().allTextContents();
    }

    @Step("Navigating to the next page")
    public HairSteps clickNextPage() {
        String currentUrl = page.url();
        hairPage.nextButton().scrollIntoViewIfNeeded();
        hairPage.nextButton().click();
        waitForPageHeaderToBeVisible();
        waitForUrlChange(currentUrl);
        return this;
    }

    @Step("Waiting for URL to change from: {currentUrl}")
    public void waitForUrlChange(String currentUrl) {
        page.waitForURL(url -> !url.equals(currentUrl));
    }

    @Step("Waiting until the page header becomes visible")
    public void waitForPageHeaderToBeVisible() {
        hairPage.pageHeader().waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }

    @Step("Navigating to the previous page")
    public HairSteps clickPreviousPage() {
        String currentUrl = page.url();
        hairPage.prevButton().scrollIntoViewIfNeeded();
        hairPage.prevButton().click();
        waitForPageHeaderToBeVisible();
        waitForUrlChange(currentUrl);
        return this;
    }

    @Step("Validating that results differ between two pages")
    public HairSteps validateResultsDiffer(List<String> firstPageResults, List<String> secondPageResults, SoftAssert softAssert) {
        softAssert.assertNotEquals(firstPageResults, secondPageResults, "Results should differ between pages");
        return this;
    }

    @Step("Validate that result differ on second and third pages")
    public HairSteps validatePagination() {
        SoftAssert softAssert = new SoftAssert();

        List<String> firstPageResults = getAllProductTitles();
        clickNextPage();
        waitForResultsToLoad();

        List<String> secondPageResults = getAllProductTitles();
        validateResultsDiffer(firstPageResults, secondPageResults, softAssert);

        clickNextPage();
        waitForResultsToLoad();

        List<String> thirdPageResults = getAllProductTitles();
        validateResultsDiffer(firstPageResults, thirdPageResults, softAssert);

        softAssert.assertAll();
        return this;
    }

    private void waitForResultsToLoad() {
        hairPage.products().first().waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }

    @Step("Navigate to the next page and validate results differ from the current page")
    public HairSteps navigateToNextPageAndValidate() {
        SoftAssert softAssert = new SoftAssert();

        List<String> currentPageResults = getAllProductTitles();
        clickNextPage();
        waitForResultsToLoad();
        List<String> nextPageResults = getAllProductTitles();

        validateResultsDiffer(currentPageResults, nextPageResults, softAssert);
        softAssert.assertAll();
        return this;
    }

    @Step("Navigate to the previous page and validate results differ from the current page")
    public HairSteps navigateToPreviousPageAndValidate() {
        SoftAssert softAssert = new SoftAssert();

        List<String> currentPageResults = getAllProductTitles();
        clickPreviousPage();
        waitForResultsToLoad();
        List<String> previousPageResults = getAllProductTitles();

        validateResultsDiffer(currentPageResults, previousPageResults, softAssert);
        softAssert.assertAll();
        return this;
    }
}

