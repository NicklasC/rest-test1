/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.rest.test;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.given;
import java.util.UUID;

/**
 *
 * @author nicklas
 */
public class Authors {
    
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/authors";
    BookOperations bookOperations = new BookOperations();
    AuthorOperations authorOperations= new AuthorOperations();

    Response postResponse;
    public Authors() {
    }
    
    @Test    
    public void authorsPost(){

        String title = UUID.randomUUID().toString();
        String postBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":"+title+",\n" +
        "  }\n" +
        "}";

        postResponse=given().contentType(ContentType.JSON).body(postBody).post(BASE_URL);
        assertEquals("Author created",201,postResponse.getStatusCode());

        // Checking that author was created
        assertEquals("Author should exist",title,authorOperations.getLastAuthorName());
        
    }  
    
    @Test
    public void authorsPost400(){
        authorOperations.createStaticAuthors();
        String title = UUID.randomUUID().toString();
        String postBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":\"Nicklas Carlsson\",\n" +
        "    \"id\":\"999999999\",\n"+
        "  }\n" +
        "}";

        postResponse=given().contentType(ContentType.JSON).body(postBody).post(BASE_URL);
        assertEquals("Author already existed",400,postResponse.getStatusCode());
    }  
    
    //TODO: Add authorsPut 200, 404
    
    
}
