package com.roguemninenine.apitesting;

import org.json.JSONObject;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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
            .and()
                .body("name", equalTo("entity number 1"))
            .and()
                .body("description", equalTo(""));
    }

    @Test
    public void testCheckEntTen() {
        given()
            .when()
                .get("https://apichallenges.eviltester.com/sim/entities/10")
            .then()
                .body("id", equalTo(10))
            .and()
                .body("name", equalTo("eris"))
            .and()
                .body("description", equalTo(""));
    }

    @Test
    public void testPostAmendEnStringReqBody() {
        String requestBody = "{\n" +
                "  \"name\": \"eris\",\n";

        given().queryParam(requestBody);
        when()
            .post("https://apichallenges.eviltester.com/sim/entities/10")
        .then()
            .statusCode(200)
        .and()
            .body("name", equalTo("eris"));
    };

    @Test
    public void testPostAmendEntJSONObjReqBody() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "eris");

        given().queryParam(requestParams.toString());
        when()
            .post("https://apichallenges.eviltester.com/sim/entities/10")
        .then()
            .statusCode(200)
        .and()
            .body("name", equalTo("eris"));
    };

    @Test
    public void testPutAmendEntJSONObjReqBody() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "eris");

        given().queryParam(requestParams.toString());
        when()
            .post("https://apichallenges.eviltester.com/sim/entities/10")
        .then()
            .statusCode(200)
        .and()
            .body("name", equalTo("eris"));
    };

    @Test
    public void testDelete() {
        given().
        when()
            .get("https://apichallenges.eviltester.com/sim/entities/9")
        .then()
            .statusCode(404)
        .and()
            .body("errorMessages", hasItem("Could not find Entity with ID 9"));
    };

    @Test
    public void testGetAfterDelete() {
        given().
            when()
                .get("https://apichallenges.eviltester.com/sim/entities/9")
            .then()
                .statusCode(404)
            .and()
                .body("errorMessages", hasItem("Could not find Entity with ID 9"));
    };

}
