package com.obsidi.library_management.models;
import java.util.UUID;
public class Book {
private String id;
private String title;
private String author;
private boolean isAvailable;
public Book() {

}

public Book( String title, String author) {
	id = UUID.randomUUID().toString();
	this.title = title;
	this.author = author;
	this.isAvailable = true;
	
}

// Read only. We can not modify book ID since it's automatically assigned on book creation 
public String getId() {
	return id;
}
public void setId(String id) {
	 this.id = id;
}

public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
public boolean isAvailable() {
	return isAvailable;
}
public void setAvailable(boolean isAvailable) {
	this.isAvailable = isAvailable;
} 
public String toString() {
	return  id + "," + author + "," +title + "," + isAvailable;
	   
}
}
