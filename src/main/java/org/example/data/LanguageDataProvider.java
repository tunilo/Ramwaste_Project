package org.example.data;

import org.testng.annotations.DataProvider;

public class LanguageDataProvider {

    @DataProvider(name = "languageData")
    public static Object[][] languageData() {
        return new Object[][]{
                {"English", new String[]{
                        "Movie", "Holiday","Swimming Pool", "Entertainment", "Eat & Drinks",
                        "Kids Land", "Sport", "Esthetics", "Healthcare",
                        "Courses", "Pets"
                }},
                {"Georgian", new String[]{
                        "კინო", "დასვენება", "აუზი", "გართობა", "კვება",
                        "საბავშვო", "სპორტი", "ესთეტიკა", "ჯანმრთელობა",
                        "კურსები", "ცხოველები"
                }}
        };
    }
}

