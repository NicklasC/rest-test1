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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nicklas
 */
public class Books {
    
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/books";
    BookOperations bookOperations = new BookOperations();
    
    //@Test
    public void testBooksPost201() {
       
        // Creating random data for new book
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String isbn = UUID.randomUUID().toString();

        String postBodyTemplate =""
                + "{\n" 
                +"\"book\":\n" 
                +"  {\n" 
                +"    \"description\":\"%s\",\n" 
                +"    \"isbn\":\"%s\",\n" 
                +"    \"nbOfPage\":411,\n" 
                +"    \"title\":\"%s\"\n" 
                +"  }\n" 
                +"}";
        String postBody = String.format(postBodyTemplate,description,isbn,title);
        
        // Creating new book
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL);
        
        // Verifying creation success status
        assertEquals("Status should be 201",201,postResponse.statusCode());
        
        // Verifying latest created book details are updated
        assertEquals("Latest booktitle is correct",title,bookOperations.getLastBookTitle());
        assertEquals("Latest description is correct",description,bookOperations.getLastBookDescription());
        assertEquals("Latest isbn is correct",isbn,bookOperations.getLastBookIsbn());
        
        
    }

    //@Test
    public void testBooksPost400(){
        // Getting allready existing Id in the DB
        String latestId=bookOperations.getLastBookId();
        
        
        String postBody="{\n" +
        "\"book\":\n" +
        "  {\n" +
        "    \"description\":\"Some description.\",\n" +
        "    \"isbn\":\"Some isbn\",\n" +
        "    \"nbOfPage\":411,\n" +
        "    \"title\":\"Some title\"\n" +
        "    \"id\":"+latestId+"\n" +
        "  }\n" +
        "}";
        
        // Trying to add a book with the existing id
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL);

        // Verifying we get responsecode 400, book with id allready exists.
        assertEquals("Book already exists, expecting status 400",400,postResponse.getStatusCode());


        //System.out.println("test:"+response.body().prettyPrint());//Funkar!



    }

    @Test
    public void testBooksPut200(){
        // Getting allready existing Id in the DB
        
        String latestId;
        latestId=bookOperations.getLastBookId();
        System.out.println("id:"+latestId);
        //String newTitle = UUID.randomUUID().toString();
        
        String postBody="{\n" +
        "\"book\":\n" +
        "  {\n" +
        "    \"description\":\"Some description\",\n" +
        "    \"isbn\":\"some isbn\",\n" +
        "    \"nbOfPage\":411,\n" +
        "    \"title\":\"Some title\"\n" +
        "    \"author\":\n" +
        "    {\n" +
        "      \"name\":\"Margaret Atwood\",\n" +
        "      \"id\":"+latestId+"\n" +
        "    }\n" +
        "  }\n" +
        "}";
        
        
        
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).put(BASE_URL);
        latestId=bookOperations.getLastBookId();
        System.out.println("Status:"+postResponse.getStatusCode());
        System.out.println("id efter:"+latestId);
    
    }
    
}
