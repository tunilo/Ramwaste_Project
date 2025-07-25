package org.example.Pages.saucedemo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage {
    private final Page page;

    public final Locator images;
    public final Locator burgerMenu;
    public final Locator logoutLink;
    public final Locator appLogo;

    public InventoryPage(Page page) {
        this.page = page;
        this.images = page.locator("img.inventory_item_img");
        this.burgerMenu = page.locator(".bm-burger-button");
        this.logoutLink = page.locator("#logout_sidebar_link");
        this.appLogo = page.locator("div.app_logo");
    }
}
