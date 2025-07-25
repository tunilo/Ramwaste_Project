package org.example.data;
import org.testng.annotations.DataProvider;

public class SearchDataProvider {

    @DataProvider(name = "searchQueries")
    public static Object[][] searchQueries() {
        return new Object[][]{
                {"კლუბი", true},
                {"invalidQuery", false},
        };
    }
}
