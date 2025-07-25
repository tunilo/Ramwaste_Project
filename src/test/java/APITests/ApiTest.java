package APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    private final String BASE_URL = "https://reqres.in/api";
    private final Map<String, String> HEADERS = new HashMap<>();
    private String userId;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        HEADERS.put("Content-Type", "application/json");
        HEADERS.put("x-api-key", "reqres-free-v1");
    }

    @Test(priority = 1)
    public void loginTest() {
        String body = """
            {
              "email": "eve.holt@reqres.in",
              "password": "cityslicka"
            }
            """;

        given()
                .headers(HEADERS)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test(priority = 2)
    public void getUsersTest() {
        given()
                .headers(HEADERS)
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("data.size()", greaterThan(0));
    }

    @Test(priority = 3)
    public void createUserTest() {
        String body = """
            {
              "name": "Tes",
              "job": "QA Engineer"
            }
            """;

        Response response = given()
                .headers(HEADERS)
                .body(body)
                .when()
                .post("/users");

        response.then()
                .statusCode(201)
                .body("id", notNullValue());

        userId = response.jsonPath().getString("id");
    }

    @Test(priority = 4, dependsOnMethods = "createUserTest")
    public void updateUserTest() {
        String body = """
            {
              "name": "Tes Updated",
              "job": "Senior QA"
            }
            """;

        given()
                .headers(HEADERS)
                .body(body)
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Tes Updated"));
    }

    @Test(priority = 5, dependsOnMethods = "createUserTest")
    public void deleteUserTest() {
        given()
                .headers(HEADERS)
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(204);
    }
}

