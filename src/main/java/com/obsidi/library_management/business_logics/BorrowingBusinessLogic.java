package com.obsidi.library_management.business_logics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.obsidi.library_management.Util.notifyMsg;
import com.obsidi.library_management.data_storage.BorrowStore;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class BorrowingBusinessLogic {
	private BorrowStore borrowingRecordStore;
	private BookBussinesLogic bookLogic;
	private UserBusinessLogic userLogic;

	public BorrowingBusinessLogic() {

		this.borrowingRecordStore = new BorrowStore();
		this.bookLogic = new BookBussinesLogic();
		this.userLogic = new UserBusinessLogic();
	}

	// Method to borrow a book
	public void borrowBook(String userId, String bookId) {
		// Retrieve user and book details
		User user = userLogic.get(userId);
		Book book = bookLogic.get(bookId);

		try {
			// Validate user, book, and borrowing conditions
			verify(user, book);

			// create a borrowing record
			BorrowingRecord record = new BorrowingRecord(user.getUserId(), book.getId(), new Date());
			borrowingRecordStore.add(record);

			// Add The book to userBorrowedBook List
			user.addBorrowedBook(book);
			userLogic.update(userId, user);// update user file
			bookLogic.updateFieldById(bookId, "availablity", "false");

			// notify the user of success
			notifyMsg("success", "\n\tBorrowing record added: " + user.getName() + " borrowed " + book.getTitle());
		} catch (InvalidInputException e) {
			// Handles exception thrown by input validation
			notifyMsg("error", e.getMessage());
		}

	}

	// Method to return a book
	public void returnBook(String userId, String bookId) {
		// Retrieve user and book details
		User user = userLogic.get(userId);
		Book book = bookLogic.get(bookId);
		// Validate user, book, and borrowing conditions
		try {
			check(user, book);
			boolean isReturned = borrowingRecordStore.returnBook(user, book);
			// remove book from user book list
			if (isReturned) {
				// user.removeBorrowedBook(book);
				boolean removeBookFromUser = user.getBorrowedBooks().removeIf(item -> item.getId().equals(bookId));
				if (removeBookFromUser) {
					book.setAvailable(true);
					userLogic.update(userId, user);
					// Update book availability
					bookLogic.update(bookId, book);
				}

			}

		} catch (InvalidInputException e) {

			notifyMsg("error", e.getMessage());
		}

	}

	// Get all borrowing records (you can filter by user, date, etc., if needed)
	public List<BorrowingRecord> getBorrowingRecords() {
		return borrowingRecordStore.getAll();
	}

	public void verify(User user, Book book) throws InvalidInputException {
		List<String> errorMessages = new ArrayList<>();

		if (user == null) {
			errorMessages.add("Error: User not found! Please make sure you're registered.");
		}
		if (book == null) {
			errorMessages.add("Error: book not found");
		}
		// only Available book can be borrowed
		if (book != null && !book.isAvailable()) {
			errorMessages.add("Error: book is already borrowed by another user");
		}
		if (user != null && user.getBorrowedBooks().size() > 2) {
			errorMessages.add("Error: You have reached the maximum borrowing limit of 2 books per person");

		}
		if (!errorMessages.isEmpty()) {
			throw new InvalidInputException(String.join("\n\t", errorMessages));
		}

	}

	public void check(User user, Book book) throws InvalidInputException {
		List<String> errorMessages = new ArrayList<>();

		if (user == null) {
			errorMessages.add("Error: User not found! Please make sure you're registered.");
		}
		if (book == null) {
			errorMessages.add("Error: book not found");
		}
		if (!errorMessages.isEmpty()) {
			throw new InvalidInputException(String.join("\n\t", errorMessages));
		}
	}

}
