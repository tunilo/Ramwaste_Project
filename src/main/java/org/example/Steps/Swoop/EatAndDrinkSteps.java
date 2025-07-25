package org.example.Steps.Swoop;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.Pages.swoop.EatAndDrinkPage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EatAndDrinkSteps  {
    private final EatAndDrinkPage eatAndDrinkPage;

    public EatAndDrinkSteps(Page page) {
        this.eatAndDrinkPage = new EatAndDrinkPage(page);
    }

    @Step("Filter offers by number of guests (2-5)")
    public EatAndDrinkSteps filterByNumberOfGuests() {
        eatAndDrinkPage.guestsFilter2to5().click();

        // Wait for loader to disappear
        eatAndDrinkPage.loader().waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN));

        return this;
    }

    @Step("Validate that filtered results match the guest filter criteria")
    public EatAndDrinkSteps validateFilteredResults() {
        List<String> titles = eatAndDrinkPage.getOfferTitlesText();

        boolean allValid = titles.stream()
                .allMatch(this::validateGuestsFilter);

        if (!allValid) {
            throw new AssertionError("Filter validation failed!");
        }

        return this;
    }

    @Step("Validate that the title matches the guests filter criteria")
    private boolean validateGuestsFilter(String title) {
        String regex = "(\\d+)([-+])?(\\d+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(title);

        while (matcher.find()) {
            int start = Integer.parseInt(matcher.group(1));
            int end = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : start;

            if ((start <= 5 && end >= 2)) {
                return true;
            }
        }

        return false;
    }
}

