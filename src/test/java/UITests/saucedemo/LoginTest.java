package UITests.saucedemo;

import io.qameta.allure.*;
import org.example.Steps.saucedemo.InventorySteps;
import org.example.Steps.saucedemo.LoginSteps;
import org.example.data.saucedemo.DatabaseManager;
import org.example.data.saucedemo.userDataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;

@Epic("Sauce Demo Platform Tests")
@Feature("Login Functionality")
@Test
public class LoginTest extends BaseTest {

    private LoginSteps loginSteps;
    private InventorySteps inventorySteps;
    private final DatabaseManager databaseManager = new DatabaseManager();

    @BeforeClass
    public void initSteps() {
        loginSteps = new LoginSteps(page);         // Page comes from BaseTest
        inventorySteps = new InventorySteps(page); // Page comes from BaseTest
    }

    @Test(priority = 1)
    @Story("Database Setup")
    @Description("Create Users Table in the database")
    public void testCreateUsersTable() {
        databaseManager.createUsersTable();
    }

    @Test(priority = 2, dataProvider = "userData", dataProviderClass = userDataProvider.class)
    @Story("Database Setup")
    @Description("Add test users to the database")
    public void addUsers(String username, String password) {
        databaseManager.addUser(username, password);
        System.out.println(username + " " + password);
    }

    @Story("Successful Login")
    @Description("Verify successful login and image loading.")
    @Test(priority = 3, groups = {"smoke"}, dependsOnMethods = "addUsers")
    public void successfulLoginTest() {
        loginSteps.correctLogin();
        inventorySteps
                .validateLoginSuccess()
                .validateImages()
                .openMenu()
                .logOut();
    }

    @Story("Banned User Login")
    @Description("Verify login fails for a locked out user.")
    @Test(priority = 4, groups = {"regression"})
    public void bannedUserLoginTest() {
        loginSteps.inCorrectLogin()
                .validateErrorMessage()
                .validateRedXIconIsVisible();
    }

    @Story("Logout Test")
    @Description("Verify logout functionality.")
    @Test(priority = 5, groups = {"regression"})
    public void logOutTest() {
        loginSteps.correctLogin();
        inventorySteps
                .validateLoginSuccess()
                .openMenu()
                .logOut();
        loginSteps.validateInputsAreEmpty();
    }
}
