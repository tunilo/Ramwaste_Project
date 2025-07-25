package org.example.Pages.swoop;

import com.microsoft.playwright.*;

import java.util.List;

public class LanguagePage {
    private final Page page;

    public LanguagePage(Page page) {
        this.page = page;
    }

    public Locator languageDropdownButton() {
        return page.locator("button[data-testid='outline-button']").nth(1);
    }
    public Locator englishButton() {
        return page.locator("//p[contains(text(), 'English')]");
    }
    public Locator georgianButton() {
        return page.locator("//p[contains(text(), 'Georgian') or contains(text(), 'ქართული')]");
    }
    public Locator categoryLabel() {
        return page.locator("//p[contains(text(), 'გართობა') or contains(text(), 'Entertainment')]").first();
    }
    public Locator categoryLabels() {
        return page.locator("div.overflow-x-scroll p.text-md.leading-5.font-tbcx-medium").first();
    }
    public List<String> getCategoryLabelTexts() {
        return categoryLabels().allTextContents();
    }
}
