/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.rest.test;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.UUID;


/**
 *
 * @author nicklas
 */
public class AuthorOperations {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/";    
    
    public String getLastAuthorId(){
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+"authors");
        return getResponse.jsonPath().getString("authors.author[-1].id");
    }
    public String getLastAuthorName(){
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+"authors");
        return getResponse.jsonPath().getString("authors.author[-1].name");
    }
    
}
