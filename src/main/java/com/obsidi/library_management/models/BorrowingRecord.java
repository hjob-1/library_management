package com.obsidi.library_management.models;

import java.util.Date;

public class BorrowingRecord {
	private String userId; // ID of the user borrowing the book
	private String bookId; // ID of the borrowed book
	private Date borrowDate; // Date when the book was borrowed
	private Date returnDate; // Date when the book was returned
	private Date dueDate; // Date by which the book should be returned
	private boolean isReturned; // Flag to indicate if the book has been returned

	// for jackson
	public BorrowingRecord() {

	}

	// Constructor
	public BorrowingRecord(String userId, String bookId, Date borrowDate) {
		this.userId = userId;
		this.bookId = bookId;
		this.borrowDate = borrowDate;
		this.isReturned = false;
		this.returnDate = null;
		this.dueDate = calculateDueDate(borrowDate); // Example: set due date 14 days after borrowDate
	}

	// Getter and Setter methods for each attribute
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
		this.isReturned = true;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

	// Utility method to calculate a due date (optional)
	private Date calculateDueDate(Date borrowDate) {
		// For example, setting the due date to 14 days after the borrow date
		long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
		return new Date(borrowDate.getTime() + (14 * MILLIS_PER_DAY));
	}

}
