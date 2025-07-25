package org.example.Steps.Swoop;



import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.example.Pages.swoop.LanguagePage;
import org.testng.asserts.SoftAssert;
import org.example.data.Constants;

import static org.testng.Assert.assertTrue;

public class LanguageSteps {
    private final Page page;
    private final LanguagePage languagePage;

    public LanguageSteps(Page page) {
        this.page = page;
        this.languagePage = new LanguagePage(page);
    }

    @Step("Open the language dropdown")
    public LanguageSteps openLanguageDropdown() {
        clickToElement(languagePage.languageDropdownButton());
        return this;
    }

    @Step("Switch to English language")
    public LanguageSteps switchToEnglish() throws InterruptedException {
        clickToElement(languagePage.englishButton());
        waitForUrlToContain(Constants.ENGLISH_TAG);
        Thread.sleep(3000);
        return this;
    }

    @Step("Switch to Georgian language")
    public LanguageSteps switchToGeorgian() {
        clickToElement(languagePage.georgianButton());
        waitForUrlToNotContain(Constants.ENGLISH_TAG);
        return this;
    }

    @Step("Wait for URL to contain: {expectedUrlPart}")
    public void waitForUrlToContain(String expectedUrlPart) {
        page.waitForURL(url -> url.contains(expectedUrlPart));
    }

    @Step("Wait for URL to NOT contain: {expectedUrlPart}")
    public void waitForUrlToNotContain(String expectedUrlPart) {
        page.waitForURL(url -> !url.contains(expectedUrlPart));
    }

    @Step("Validate that the UI is displayed in English")
    public LanguageSteps validateEnglishLanguage() {
        String actualText = languagePage.categoryLabel().textContent();
        System.out.println(actualText);
        assertTrue(actualText.contains(Constants.ENTERTAINMENT_EN), "Expected label to contain 'Entertainment'");
        return this;
    }

    @Step("Validate that the UI is displayed in Georgian")
    public LanguageSteps validateGeorgianLanguage() throws InterruptedException {
        Thread.sleep(3000);
        String actualText = languagePage.categoryLabel().textContent();
        assertTrue(actualText.contains(Constants.ENTERTAINMENT_GE), "Expected label to contain 'გართობა'");
        return this;
    }

    @Step("Validate translations for all categories")
    public LanguageSteps validateTranslations(String[] expectedCategories) {
        SoftAssert softAssert = new SoftAssert();
        var actualTexts = languagePage.getCategoryLabelTexts();

        softAssert.assertEquals(actualTexts.size(), expectedCategories.length,
                "Category count mismatch.");

        for (int i = 0; i < expectedCategories.length && i < actualTexts.size(); i++) {
            softAssert.assertEquals(actualTexts.get(i).trim(), expectedCategories[i].trim(),
                    "Mismatch at index " + i);
        }

        softAssert.assertAll();
        return this;
    }

    @Step("Click element")
    public LanguageSteps clickToElement(Locator element) {
        element.click();
        return this;
    }
}

