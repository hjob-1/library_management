package com.obsidi.library_management;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.data_storage.BookStore;
import com.obsidi.library_management.data_storage.FileHandler;
import com.obsidi.library_management.models.Book;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
    	
//        System.out.println( "Hello World!" );
        ArrayList<Book> books = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
       
        
		
       
		
        
        Book book = new Book("book1","eyob");
        Book book2 = new Book("book2","eyob");
        Book book3 = new Book("book3","helen");
        books.add(book2);
        books.add(book);
        
//        			 try {
//						objectMapper.writeValue(new FileWriter("book.json",false), books);
//					} catch (StreamWriteException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (DatabindException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
		//        	String jsonData = objectMapper.readTree(new File("book.json")).toString();
		//			 //Book[] Jsonbook = objectMapper.readValue(jsonData, Book[].class);
		//        	ArrayList<Book> Jsonbook = objectMapper.readValue(jsonData, new TypeReference<ArrayList<Book>>() {});
		//			
		//				 System.out.println("bokk"+Jsonbook.get(1));
					// FileHandler<Book> file =new FileHandler<>("book.json");
					BookStore bookStore = new BookStore();
					
					// file.add(book3);
					//file.getAll();
					//bookStore.save(books);
				 System.out.println(""	+bookStore.getAll());
					 //bookStore.delete("80a4abed-3bb5-4751-ae1d-8fa2ebd730b6");
					//bookStore.getAll();
					 System.out.println(""+	bookStore.getAll());
				
       
		System.out.println("Employee JSON is\n");
        
        
//        Book book = new Book("1","Book1","hjob",true);
//        Book book2 = new Book("2","no way ","helen",true);
//        Book book3 = new Book("3","book 3 ","new Author",true);
//        BookBussinesLogic bl = new BookBussinesLogic();
//       
//        bl.add(book);
//        bl.add(book2);
//        bl.add(book3);
//        books.add(book);
//        System.out.println("after updating");
//        System.out.println(bl.get("2"));
//        List<Book> result2 = bl.getAll();
//        for(Book b: result2) {
//        	 System.out.println(b);
//        }
      
       
       
       // System.out.println(books.getFirst().getTitle());
        
       
    }
}
