package com.ignitionone.datastorm.datorama.apiUtil;

/**
 * Created by nitin.poddar on 1/12/2017.
 */

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class APIUtil {

    public static String getResponseAsString(String requestPath, String requestBody){
        String jsonAsString = given().contentType(ContentType.JSON).body(requestBody).when().post(requestPath).body().asString();
        return jsonAsString;
    }
    public static String getResportAsString(String requestPath, String requestBody, String token){
        String jsonAsString = given().contentType(ContentType.JSON).queryParam("token", token).body(requestBody).when().post(requestPath).body().asString();
        return jsonAsString;
    }

}
