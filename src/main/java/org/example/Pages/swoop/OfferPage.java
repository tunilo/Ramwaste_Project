package org.example.Pages.swoop;

import com.microsoft.playwright.*;

public class OfferPage {
    private final Page page;

    public OfferPage(Page page) {
        this.page = page;
    }
    public Locator firstItem() {
        return page.locator("//div[contains(@class, 'grid-cols-3') and contains(@class, 'gap-y-8')]//a[contains(@class, 'group')]").first();
    }
    public Locator locationButton() {
        return page.locator("(//button[contains(@class, 'flex') and contains(@class, 'items-center')])[6]");
    }
    public Locator mapSection() {
        return page.locator("div.w-full.laptop\\:h-\\[490px\\]");
    }
}
