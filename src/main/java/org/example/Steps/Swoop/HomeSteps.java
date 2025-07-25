package org.example.Steps.Swoop;


import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.example.Pages.swoop.HomePage;

public class HomeSteps {
    private final Page page;
    private final HomePage homePage;

    public HomeSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
    }

    @Step("Entering search query: {query}")
    public HomeSteps enterSearchQuery(String query) {
        homePage.searchInput().fill(query);
        return this;
    }

    @Step("Clicking search button")
    public HomeSteps clickSearchButton() throws InterruptedException {
        Thread.sleep(2000);
        clickToElement(homePage.searchButton());
        return this;
    }

    @Step("Navigating to categories")
    public HomeSteps navigateToCategories() {
        clickToElement(homePage.categoriesButton());
        return this;
    }

    @Step("Clicking element")
    public HomeSteps clickToElement(Locator element) {
        element.click();
        return this;
    }

    @Step("Hovering over 'ესთეტიკა' category")
    public HomeSteps hoverOverEstetikaCategory() {
        hover(homePage.estetikaCategory());
        return this;
    }

    @Step("Selecting 'თმა/წვერი' sub-category")
    public HomeSteps selectHairBeardCategory() {
        clickToElement(homePage.hairBeardSubCategory());
        return this;
    }

    @Step("Hover over 'გართობა' category")
    public HomeSteps hoverFunCategory() {
        hover(homePage.funCategory());
        return this;
    }

    @Step("Hover element")
    public HomeSteps hover(Locator element) {
        element.hover();
        return this;
    }

    @Step("Select 'ღამის კლუბი' subcategory")
    public HomeSteps selectNightClubCategory() {
        clickToElement(homePage.nightClubSubCategory());
        return this;
    }

    @Step("Navigate to 'კვება' category (Eat and Drink)")
    public HomeSteps navigateToEatAndDrink() {
        clickToElement(homePage.eatAndDrinkCategory());
        return this;
    }

    @Step("Validate non-existent element is visible (expect failure)")
    public void validateNonExistentElement() {
        boolean isVisible = homePage.nonExistentElement().isVisible();
        if (!isVisible) {
            throw new AssertionError("Non-existent element is not visible, as expected.");
        }
    }

    @Step("Close non important popup")
    public HomeSteps closePopup (){
        homePage.cancelPopup().click();
        return this;
    }
}

