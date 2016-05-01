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
import static com.jayway.restassured.RestAssured.given;


//import com.jayway.restassured.http.ContentType;
//import com.jayway.restassured.response.Response;

/**
 *
 * @author nicklas
 */
public class BookOperations {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/";
    AuthorOperations authorOperations = new AuthorOperations();
    public void BookOperations(){
    
    }
    
    public String getLastBookTitle(){
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+"books");
        return getResponse.jsonPath().getString("books.book[-1].title");
    }
    public String getLastBookDescription(){
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+"books");
        return getResponse.jsonPath().getString("books.book[-1].description");
    }

    public String getLastBookId(){
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+"books");
        return getResponse.jsonPath().getString("books.book[-1].id");
    }
    public String getLastBookIsbn(){
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+"books");
        return getResponse.jsonPath().getString("books.book[-1].isbn");
    }
    
    
    public Response createNewBook() {
        // making sure static author exists
        authorOperations.createStaticAuthors();
        
        
        AuthorOperations authorOperations= new AuthorOperations();
        String resourceName = "books";
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String isbn = UUID.randomUUID().toString();

        String latestAuthorName="Nicklas Carlsson";
        String latestAuthorId="999999999";

        
        
        String postBodyTemplate =""
                + "{\n" 
                +"\"book\":\n" 
                +"  {\n" 
                +"    \"description\":\"%s\",\n" 
                +"    \"isbn\":\"%s\",\n" 
                +"    \"nbOfPage\":411,\n" 
                +"    \"title\":\"%s\",\n" 
                +"    \"author\":\n" 
                +"    {\n" 
                +"      \"name\":\"%s\",\n" 
                +"      \"id\":%s\n" 
                +"    }\n" 
                +"  }\n" 
                +"}";
        String postBody = String.format(postBodyTemplate,description,isbn,title,latestAuthorName,latestAuthorId);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourceName);
        //System.out.println("Codeis:"+postResponse.getStatusCode());
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+resourceName);
        //getResponse.jsonPath().getJsonObject("book.books[-1]");
        return getResponse;
        
    }
    
}
