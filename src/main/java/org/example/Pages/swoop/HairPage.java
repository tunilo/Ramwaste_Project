package org.example.Pages.swoop;
import com.microsoft.playwright.*;
import java.util.List;

public class HairPage {
    private final Page page;

    public HairPage(Page page) {
        this.page = page;
    }

    public Locator products() {
        return page.locator("a.group.flex.flex-col.gap-3");
    }

    public Locator nextButton() {
        return page.locator("//div[contains(@class, 'cursor-pointer')]//img[contains(@alt, 'right arrow')]");
    }

    public Locator prevButton() {
        return page.locator("//div[contains(@class, 'cursor-pointer') and .//img[@alt='left arrow']]");
    }

    public Locator pageHeader() {
        return page.locator("h3.font-tbcx-bold.text-2lg.text-primary_black-100-value");
    }

    public List<String> getAllProductTexts() {
        return products().allTextContents();
    }
}

