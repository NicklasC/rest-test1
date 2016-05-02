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
public class BooksAndAuthors {

    private static final String BASE_URL="http://localhost:8080/librarytest/rest/books";
    BookOperations bookOperations = new BookOperations();
    AuthorOperations authorOperations= new AuthorOperations();

    public BooksAndAuthors() {
    }

    @Test
    public void booksByAuthorId(){
        Response postResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/byauthor/999999999");
        assertEquals("Books by authors",200,postResponse.statusCode());
    
    }
    @Test
    public void booksGetSpecificBookAuthors200(){
        // /books/{book_id}/authors
        // Making sure we have a book with authors
        bookOperations.createNewBook();
        Response getResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/"+bookOperations.getLastBookId()+"/authors");
        //getResponse.prettyPeek();
        assertEquals("Author for an existing specified book should be retrieved",200,getResponse.getStatusCode());
    }
    @Test
    public void booksGetSpecificBookAuthors404(){
        ///books/{book_id}/authors
        // Making sure we have a book with authors
        bookOperations.createNewBook();
        Response getResponse = given().contentType(ContentType.JSON).get(BASE_URL+"/3459963463463456356/authors");
        
        assertEquals("Author for an nonexisting specified cannot be retrieved",404,getResponse.getStatusCode());
    }
    @Test
    public void booksAddAuthor200(){
            ///books/{book_id}/authors, POST
        // Making sure we have a book with authors
        bookOperations.createNewBook();
        
        String postBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":\"Sven Svensson\",\n" +
        "    \"id\":88888888\n" +
        "  }\n" +
        "}";
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL+"/"+bookOperations.getLastBookId()+"/authors");
        postResponse.prettyPeek();
        assertEquals("Author for an existing specified book should added",200,postResponse.getStatusCode());
        // TODO: Lägg till kontroll av att den verkligen lagts till        
        //Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL);
    }
    @Test
    public void booksAddAuthor400(){
            ///books/{book_id}/authors, POST
        // Making sure we have a book with authors
        bookOperations.createNewBook();
        
        String postBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":\"Nicklas Carlsson\",\n" +
        "    \"id\":999999999\n" +
        "  }\n" +
        "}";
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL+"/"+bookOperations.getLastBookId()+"/authors");
        
        assertEquals("Adding same author twice should not be possible",400,postResponse.getStatusCode());
        
    }
    
    @Test
    public void booksAddAuthor404(){
            ///books/{book_id}/authors, POST
        // Making sure we have a book with authors
        bookOperations.createNewBook();
        
        String postBody="{\n" +
        "\"author\":\n" +
        "  {\n" +
        "    \"name\":\"Nicklas Carlsson\",\n" +
        "    \"id\":999999999\n" +
        "  }\n" +
        "}";
        
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL+"/657fd34h23473598679678996789/authors");
        
        assertEquals("Adding author to non-existing book should not be possible",404,postResponse.getStatusCode());
    }
    
}
