package com.roguemninenine.apitesting;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * This is based off of EvilTesters practice APIs challenge site. They are
 * created from scratch and do not mimic the example the challenge site
 * provided.
 * Simply a basic exercise.
 * url: <a href="https://apichallenges.eviltester.com/practice-modes/simulation">...</a>
 */
public class ApiPracticeTest {
    private final String baseURI = "https://apichallenges.eviltester.com/sim/entities";

    @Test
    public void testCheckEnt() {
        given()
            .when()
                .get(baseURI)
            .then()
                .statusCode(200);
    }

    @Test
    public void testCheckEntOne() {
        given()
            .when()
                .get(baseURI+"/1")
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
                .get(baseURI+"/10")
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
            .post(baseURI+"/10")
        .then()
            .statusCode(200)
        .and()
            .body("name", equalTo("eris"));
    }

    @Test
    public void testPostAmendEntJSONObjReqBody() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "eris");

        given().queryParam(requestParams.toString());
        when()
            .post(baseURI+"/10")
        .then()
            .statusCode(200)
        .and()
            .body("name", equalTo("eris"));
    }

    @Test
    public void testPutAmendEntJSONObjReqBody() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "eris");

        given().queryParam(requestParams.toString());
        when()
            .post(baseURI+"/10")
        .then()
            .statusCode(200)
        .and()
            .body("name", equalTo("eris"));
    }

    @Test
    public void testDelete() {
        given().
            when()
                .delete(baseURI+"/9")
            .then()
                .statusCode(204);
    }

    @Test
    public void testGetAfterDelete() {
        given().
            when()
                .get(baseURI+"/9")
            .then()
                .statusCode(404)
            .and()
                .body("errorMessages", hasItem("Could not find Entity with ID 9"));
    }

    @Test
    public void testDeleteWhenUnable() {
        int[] userIds = {1,2,3,4,5,6,7,8,10};
        for (int userId : userIds) {
            given().
                when()
                    .delete(baseURI +"/"+ userId)
                .then()
                    .statusCode(403)
                .and()
                    .body("errorMessages", hasItem("Not authorised to delete that entity"));
        }
    }

    @Test
    public void testDeleteNonExistent() {
        int userId = 88;
        given().
            when()
                .delete(baseURI+"/"+userId)
            .then()
                .statusCode(404)
                .and()
                .body("errorMessages", hasItem("Could not find Entity with ID "+userId));
    }

    @Test
    public void testOptionsOnBaseURI() {
        Response response = given().when().options(baseURI);
        response.then()
                .statusCode(204);
        String headerValue = response.getHeader("Allow");
        assertThat(headerValue, is("GET, POST, PUT, HEAD, OPTIONS"));
    }

    @Test
    public void testNegativeDelete() {
        Response response = given().when().delete(baseURI);
        response.then()
                .statusCode(405);
    }

    @Test
    public void testNegativePatch() {
        Response response = given().when().patch(baseURI);
        response.then()
                .statusCode(501);
    }

    @Test
    public void testBaseHead() {
        Response response = given().when().head(baseURI);
        response.then()
                .statusCode(200);
    }

    @Test
    public void testIncorrectEndpointError() {
        String shortURI = "https://apichallenges.eviltester.com/sim/nogo";
        String[] invalidEndpoints = {"","nogo", "admin","user"};
        for (String endpoint: invalidEndpoints) {
            given().
                    when()
                    .delete(shortURI + endpoint)
                    .then()
                    .statusCode(404);
        }
    }

}
