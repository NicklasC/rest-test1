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
    
    @Test
    public void authorsPut200(){
        authorOperations.createRandomAuthor();
        String id=authorOperations.getLastAuthorId();
        String name = UUID.randomUUID().toString();
        String putBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":"+name+",\n" +
        "    \"id\":"+id+"\n"+
        "  }\n" +
        "}";
        
        Response postResponse = given().contentType(ContentType.JSON).body(putBody).put(BASE_URL);
        assertEquals("Author should be updated, exp status 200",200,postResponse.getStatusCode());
        
        // Verifying name is updated
        assertEquals("Author title should be updated",name,authorOperations.getLastAuthorName());

    }
    @Test
    public void authorsPut404(){
        authorOperations.createRandomAuthor();
        String id=authorOperations.getLastAuthorId();
        String name = UUID.randomUUID().toString();
        String putBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":"+name+",\n" +
        "    \"id\":\"2345234523SGDFG2345\"\n"+
        "  }\n" +
        "}";
        
        Response postResponse = given().contentType(ContentType.JSON).body(putBody).put(BASE_URL);
        assertEquals("Author should not exist",404,postResponse.getStatusCode());
    }
    
    @Test
    public void authorsIdGet200(){
        // Making sure static authors exists...
        authorOperations.createStaticAuthors();
        Response getResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/"+999999999);
        getResponse.prettyPeek();
        assertEquals("Author should exist",200,getResponse.getStatusCode());

    }
   @Test
   public void authorsIdGet404(){
        // Making sure static authors exists...
        authorOperations.createStaticAuthors();
        Response getResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/999999999aa234r");
        getResponse.prettyPeek();
        assertEquals("Author should not exist",404,getResponse.getStatusCode());
    }
   @Test
   public void authorsDelete204(){
        // Creating a random author
        authorOperations.createRandomAuthor();
        String id=authorOperations.getLastAuthorId();
        
        Response getResponse = given().contentType(ContentType.JSON).delete(BASE_URL+"/"+id);
        getResponse.prettyPeek();
        assertEquals("Author should be deleted",204,getResponse.getStatusCode());
        
        // Now verifying author is deleted
        getResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/"+id);
        assertEquals("Author should not exist",404,getResponse.getStatusCode());
    }
   @Test
   public void authorsDelete404(){
        Response getResponse = given().contentType(ContentType.JSON).delete(BASE_URL+"/sdfgs¤%&/¤%sdfgs");
        assertEquals("Author cannot be deleted as it does not exist",404,getResponse.getStatusCode());
   }

}    



