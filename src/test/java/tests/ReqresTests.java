package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresTests {

    final String URL = "https://reqres.in";

    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {

        String userBody = """
                {
                    "name": "morpheus",
                    "job": "leader"
                }
                """;

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(userBody)
                .contentType(JSON)
        .when()
                .post(URL + "/api/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    @DisplayName("Успешная авторизация")
    void successfulLoginTest() {

        String loginBody = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "cityslicka"
                }
                """;

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(loginBody)
                .contentType(JSON)
        .when()
                .post(URL + "/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLoginTest() {

        String loginBody = """
                {
                    "email": "peter@klaven"
                }
                """;

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(loginBody)
                .contentType(JSON)
        .when()
                .post(URL + "/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    void unsuccessfulRegisterTest() {

        String loginBody = """
                {
                    "email": "sydney@fife"
                }
                """;

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(loginBody)
                .contentType(JSON)
        .when()
                .post(URL + "/api/register")
        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {

        given()
                .log().uri()
                .log().method()
                .pathParam("userId", 2)
        .when()
                .delete(URL + "/api/{userId}")
        .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

}
