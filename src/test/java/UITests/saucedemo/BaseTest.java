package UITests.saucedemo;

import com.microsoft.playwright.*;
import org.example.data.saucedemo.Constants;
import org.testng.annotations.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null)); // This makes the window "maximized" in terms of full size

        page = context.newPage();
        page.navigate(Constants.SAUCEDEMO_URL);
    }

//    @AfterClass(alwaysRun = true)
//    public void tearDown() {
//        if (context != null) context.close();
//        if (browser != null) browser.close();
//        if (playwright != null) playwright.close();
//    }
}
