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

/**
 *
 * @author nicklas
 */
public class BooksById {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/books";
    BookOperations bookOperations = new BookOperations();
    AuthorOperations authorOperations= new AuthorOperations();

    
    public BooksById() {
    }

    //@Test    
    public void testBooksByIdGet200(){
        // Making sure we have a book to find
        bookOperations.createNewBook();
        String resourceName=bookOperations.getLastBookId();
        Response postResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/"+resourceName);
        
        assertEquals("Fetching existing book with ID should give status 200",200,postResponse.getStatusCode());
    }
    
    //@Test    
    public void testBooksByIdGet404(){
        Response postResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/1234SDF123451aasdf23465756");
        assertEquals("Fetching non-existing book with ID should give status 404",404,postResponse.getStatusCode());
    }
    
    @Test
    public void testBooksByIdDelete(){
        // Making sure we have a book to delete
        bookOperations.createNewBook();
        String resourceName=bookOperations.getLastBookId();
        
        Response postResponse = given().contentType(ContentType.JSON).delete(BASE_URL+"/"+resourceName);
        assertEquals("Deleting book with ID should give correct status",204,postResponse.getStatusCode());
        
        // Verifying book is gone
        
        Response getResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/"+resourceName);
        assertEquals("Getting a deleted book should not be possible",404,getResponse.getStatusCode());
        
    }
}
