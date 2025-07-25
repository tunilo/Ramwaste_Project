package org.example.Pages.swoop;
import com.microsoft.playwright.*;

import java.util.List;

public class SearchPage {
    private final Page page;

    public SearchPage(Page page) {
        this.page = page;
    }
    public Locator searchResultTitle() {
        return page.locator("h3.font-tbcx-bold");
    }

    public Locator searchResultTitles() {
        return page.locator("div.font-tbcx-regular.line-clamp-1");
    }

    public List<String> getSearchResultTitlesText() {
        return searchResultTitles().allTextContents();
    }

    public Locator noResultsMessage() {
        return page.locator("h2.font-tbcx-bold.text-center");
    }

    public static final String NO_OFFER_FOUND = "შეთავაზება არ მოიძებნება";
}