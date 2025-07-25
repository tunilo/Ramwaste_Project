package org.example.Pages.saucedemo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    public final Locator usernameField;
    public final Locator passwordField;
    public final Locator loginButton;
    public final Locator errorMessage;
    public final Locator redXIcon;

    public LoginPage(Page page) {
        this.page = page;
        this.usernameField = page.locator("#user-name");
        this.passwordField = page.locator("#password");
        this.loginButton = page.locator("#login-button");
        this.errorMessage = page.locator(".error-message-container");
        this.redXIcon = page.locator("button.error-button");
    }
}

