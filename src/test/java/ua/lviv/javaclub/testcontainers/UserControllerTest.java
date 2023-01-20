package ua.lviv.javaclub.testcontainers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class UserControllerTest
// extends BaseTest
 extends BaseComposeTest
    {

    @BeforeAll
    public static void setup() {
        RestAssured.port = 9191;
    }

    @Test
    void getUsers() {
        get("/users")
        .then()
        .statusCode(200)
        .assertThat()
        .body("size()", equalTo(5));
    }

    @Test
    void getUser() {
        get("/users/1")
        .then()
        .statusCode(200)
        .assertThat()
        .body("name", notNullValue());
    }

    @Test
    void getNotExistUser() {
        get("/users/111")
        .then()
        .statusCode(404)
        .assertThat();
    }
}
