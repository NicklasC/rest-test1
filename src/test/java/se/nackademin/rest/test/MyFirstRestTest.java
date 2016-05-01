/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.rest.test;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;


/**
 *
 * @author Nicklas
 */
public class MyFirstRestTest {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/";
    public MyFirstRestTest() {
    }
    
    //@Test
    public void testFetchBook(){
        String resourceName = "books/3";
        // get(BASE_URL+resourceName).body().prettyPrint();
        //given().accept(ContentType.JSON).log().all().get(BASE_URL+resourceName).body().prettyPrint();
        Response response = given().accept(ContentType.JSON).log().all().get(BASE_URL+resourceName);
        System.out.println("Status code:" + response.getStatusCode());
        
        String title = response.body().jsonPath().getString("book.title");
        System.out.println("title:" + title);
        assertEquals("Status should be 200",200,response.statusCode());
    }
    @Test
    public void testingCreateBook(){
        BookOperations bookOperations = new BookOperations();
        
        Response book = bookOperations.createNewBook();
        //System.out.println(book.jsonPath().getJsonObject(basePath)prettyPrint());
        //System.out.println(("statuscode:")+book.getStatusCode());
        System.out.println(book.jsonPath().getString("books.book[-1]"));
    }
}
