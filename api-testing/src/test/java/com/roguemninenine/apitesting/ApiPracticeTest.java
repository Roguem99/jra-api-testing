package com.roguemninenine.apitesting;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ApiPracticeTest {

    @Test
    public void testCheckEnt() {
        given()
                .when()
                .get("https://apichallenges.eviltester.com/sim/entities")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCheckEntOne() {
        given()
                .when()
                    .get("https://apichallenges.eviltester.com/sim/entities/1")
                .then()
                    .body("id", equalTo(1))
                    .body("name", equalTo("entity number 1"))
                    .body("description", equalTo(""));
    }

}
