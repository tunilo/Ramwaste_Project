package UITests;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import java.awt.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void createContextAndPage() {
        // Get screen size using AWT
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        // Set viewport to screen size (simulate maximize)
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(width, height);

        context = browser.newContext(contextOptions);
        page = context.newPage();
    }

    @Test
    public void openSwoopGe() {
        page.navigate("https://swoop.ge");
        System.out.println("Title: " + page.title());
    }

    @AfterMethod
    public void closeContext() {
        context.close();
    }

    @AfterClass
    public void closeBrowser() {
        browser.close();
        playwright.close();
    }
}
