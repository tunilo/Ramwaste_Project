package org.example.Steps.Swoop;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.Pages.swoop.OfferPage;

public class OfferSteps {
    private final Page page;
    private final OfferPage offerPage;

    public OfferSteps(Page page) {
        this.page = page;
        this.offerPage = new OfferPage(page);
    }

    @Step("Select first item from offers")
    public OfferSteps selectFirstOffer() {
        offerPage.firstItem().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        offerPage.firstItem().click();
        return this;
    }

    @Step("Click on Location button")
    public OfferSteps clickLocationButton() {
        offerPage.locationButton().scrollIntoViewIfNeeded();
        offerPage.locationButton().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        offerPage.locationButton().click();
        return this;
    }

    @Step("Validate map is in view")
    public OfferSteps validateMapIsInView() throws InterruptedException {
        offerPage.mapSection().scrollIntoViewIfNeeded();
        Thread.sleep(3000);
        offerPage.mapSection().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }
}
