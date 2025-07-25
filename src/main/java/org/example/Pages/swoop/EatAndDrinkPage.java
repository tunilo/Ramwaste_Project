package org.example.Pages.swoop;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;
import java.util.stream.Collectors;

public class EatAndDrinkPage {
    private final Page page;

    public EatAndDrinkPage(Page page) {
        this.page = page;
    }

    public Locator guestsFilter2to5() {
        return page.locator("//label[@for='radio-სტუმრების რაოდენობა-0']");
    }

    public List<String> getOfferTitlesText() {
        return page.locator("//h4[@class='text-primary_black-100-value text-md leading-5 font-tbcx-medium line-clamp-2']")
                .allTextContents();
    }

    public Locator loader() {
        return page.locator(".loader");
    }

    public void waitForLoaderToDisappear() {
        loader().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }
}
