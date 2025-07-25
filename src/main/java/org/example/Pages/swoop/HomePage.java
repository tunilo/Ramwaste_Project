package org.example.Pages.swoop;

import com.microsoft.playwright.*;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public Locator searchInput() {
        return page.locator("input[placeholder='მოძებნე კომპანია ან შეთავაზება']");
    }

    public Locator searchButton() {
        return page.locator("//img[@alt='search']/parent::button");
    }

    public Locator categoriesButton() {
        return page.locator("button.text-sm.bg-white").first();
    }

    public Locator estetikaCategory() {
        return page.locator("//h4[contains(text(), 'ესთეტიკა')]").first();
    }

    public Locator hairBeardSubCategory() {
        return page.locator("//h4[contains(text(), 'თმა/წვერი')]");
    }

    public Locator funCategory() {
        return page.locator("//h4[contains(text(), 'გართობა')]");
    }

    public Locator nightClubSubCategory() {
        return page.locator("//h4[contains(text(), 'ღამის კლუბი')]");
    }
    public Locator eatAndDrinkCategory() {
        return page.locator("//p[contains(text(), 'კვება')]").first();
    }
    public Locator nonExistentElement() {
        return page.locator("div#non-existent-element");
    }
    public Locator cancelPopup(){
        return page.locator("button:has(img[src*='closeEmpty.svg'])");
    }
}
