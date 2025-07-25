package org.example.Steps.saucedemo;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.data.saucedemo.Constants;
import org.example.data.saucedemo.DatabaseManager;

public class LoginSteps {
    private final Page page;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;
    private final Locator redXIcon;

    private final DatabaseManager databaseManager = new DatabaseManager();

    public LoginSteps(Page page) {
        this.page = page;
        this.usernameField = page.locator("#user-name");
        this.passwordField = page.locator("#password");
        this.loginButton = page.locator("#login-button");
        this.errorMessage = page.locator("[data-test='error']");
        this.redXIcon = page.locator(".error_icon").first();
    }

    @Step("Login with username: {0} and password: {1}")
    public LoginSteps login(String username, String password) {
        usernameField.fill(username);
        passwordField.fill(password);
        loginButton.click();
        return this;
    }

    @Step("Login with correct credentials")
    public LoginSteps correctLogin() {
        String password = "secret_sauce";
        login(Constants.standardUser, password);
        return this;
    }

    @Step("Login with incorrect user")
    public LoginSteps inCorrectLogin() {
        String password = "secret_sauce";
        login(Constants.Username, password); // Make sure Constants.Username is incorrect intentionally
        return this;
    }

    @Step("Validate error message is displayed")
    public LoginSteps validateErrorMessage() {
        errorMessage.waitFor();
        assert errorMessage.textContent().contains(Constants.ERROR_MESSAGE);
        return this;
    }

    @Step("Validate red X icon is visible")
    public LoginSteps validateRedXIconIsVisible() {
        assert redXIcon.isVisible();
        return this;
    }

    @Step("Validate username input is empty")
    public LoginSteps validateUsernameInputIsEmpty() {
        assert usernameField.inputValue().isEmpty();
        return this;
    }

    @Step("Validate password input is empty")
    public LoginSteps validatePasswordInputIsEmpty() {
        assert passwordField.inputValue().isEmpty();
        return this;
    }

    @Step("Validate both username and password inputs are empty")
    public LoginSteps validateInputsAreEmpty() {
        validateUsernameInputIsEmpty();
        validatePasswordInputIsEmpty();
        return this;
    }
}
