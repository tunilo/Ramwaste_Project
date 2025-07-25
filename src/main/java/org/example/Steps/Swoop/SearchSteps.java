package org.example.Steps.Swoop;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.Pages.swoop.SearchPage;
import org.testng.Assert;

import java.util.List;

public class SearchSteps {
    private final Page page;
    private final SearchPage searchPage;

    public SearchSteps(Page page) {
        this.page = page;
        this.searchPage = new SearchPage(page);
    }

    @Step("Validating search result based on existence")
    public SearchSteps validateSearchResult(String query, boolean exist) {
        searchPage.searchResultTitle()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Assert.assertTrue(
                searchPage.searchResultTitle().textContent().toLowerCase().contains(query.toLowerCase()),
                "Search result title should contain query"
        );

        if (exist) {
            validateResultsContainQuery(query);
        } else {
            validateNoResultsMessage();
        }

        return this;
    }

    @Step("Validating that all search results contain query: {query}")
    public SearchSteps validateResultsContainQuery(String query) {
        String lowerCaseQuery = query.toLowerCase();
        List<String> resultTitles = searchPage.getSearchResultTitlesText();

        for (String title : resultTitles) {
            Assert.assertTrue(
                    title.toLowerCase().contains(lowerCaseQuery),
                    "Search result does not contain query: " + title
            );
        }

        return this;
    }

    @Step("Validating 'No Results Found' message is displayed")
    public SearchSteps validateNoResultsMessage() {
        Locator message = searchPage.noResultsMessage();
        message.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(message.isVisible(), "Expected 'No results' message to be visible.");
        return this;
    }
}