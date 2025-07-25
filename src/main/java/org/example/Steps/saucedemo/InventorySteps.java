package org.example.Steps.saucedemo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.testng.Assert;

public class InventorySteps {

    private final Page page;
    private final Locator images;
    private final Locator burgerMenu;
    private final Locator logoutLink;
    private final Locator appLogo;

    public InventorySteps(Page page) {
        this.page = page;
        this.images = page.locator(".inventory_item_img img");
        this.burgerMenu = page.locator("#react-burger-menu-btn");
        this.logoutLink = page.locator("#logout_sidebar_link");
        this.appLogo = page.locator(".app_logo");
    }

    @Step("Validate successful login")
    public InventorySteps validateLoginSuccess() {
        Assert.assertTrue(appLogo.isVisible(), "App logo should be visible after login");
        return this;
    }

    @Step("Validate all product images are loaded")
    public InventorySteps validateImages() {
        int count = images.count();
        for (int i = 0; i < count; i++) {
            Locator img = images.nth(i);
            Assert.assertTrue(img.isVisible(), "Image should be visible");
            String src = img.getAttribute("src");
            Assert.assertNotNull(src, "Image src should not be null");
            Assert.assertFalse(src.isEmpty(), "Image src should not be empty");
        }
        return this;
    }

    @Step("Open menu")
    public InventorySteps openMenu() {
        burgerMenu.click();
        return this;
    }

    @Step("Log out")
    public InventorySteps logOut() {
        logoutLink.click();
        return this;
    }
}
