package com.obsidi.library_management.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
	private String userId;
	private String name;
	private String email;
	private String password;
	private List<Book> borrowedBooks;
	private List<Book> overdueBooks;

	// jackson
	public User() {
	}

	// Constructor
	public User(String name, String email, String password) {
		this.userId = UUID.randomUUID().toString();
		this.name = name;
		this.email = email;
		this.password = password;
		this.borrowedBooks = new ArrayList<>();
		this.overdueBooks = new ArrayList<>();
	}

	// Getters and Setters
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Book> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void addBorrowedBook(Book book) {
		borrowedBooks.add(book);
	}

	public void removeBorrowedBook(Book book) {
		borrowedBooks.remove(book);
	}

	public List<Book> getOverdueBooks() {
		return overdueBooks;
	}

	public void addOverdueBook(Book book) {
		overdueBooks.add(book);
	}

	public void removeOverdueBook(Book book) {
		overdueBooks.remove(book);
	}

	// Check if user has overdue books
	public boolean hasOverdueBooks() {
		return !overdueBooks.isEmpty();
	}

	// Display user's borrowed books
	public void displayBorrowedBooks() {
		if (borrowedBooks.isEmpty()) {
			System.out.println(name + " has not borrowed any books.");
		} else {
			System.out.println(name + "'s borrowed books:");
			for (Book book : borrowedBooks) {
				System.out.println(book.getTitle());
			}
		}
	}

	// Display overdue books
	public void displayOverdueBooks() {
		if (overdueBooks.isEmpty()) {
			System.out.println(name + " has no overdue books.");
		} else {
			System.out.println(name + "'s overdue books:");
			for (Book book : overdueBooks) {
				System.out.println(book.getTitle());
			}
		}
	}
}
