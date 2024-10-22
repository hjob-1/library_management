package com.obsidi.library_management.business_logics;

import java.util.Date;
import java.util.List;

import com.obsidi.library_management.data_storage.BorrowStore;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class BorrowingBusinessLogic {
	private BorrowStore borrowingRecordStore;

	public BorrowingBusinessLogic() {

		this.borrowingRecordStore = new BorrowStore();
	}

	// Method to borrow a book
	public void borrowBook(User user, Book book) {
		// verify if the user or book existed
		// return to main menu to let if the borrow is success or not.

		BorrowingRecord record = new BorrowingRecord(user.getUserId(), book.getId(), new Date());
		borrowingRecordStore.add(record);

		System.out.println("Borrowing record added: " + user.getName() + " borrowed " + book.getTitle());

	}

	// Method to return a book
	public void returnBook(User user, Book book) {
		borrowingRecordStore.returnBook(user, book);
	}

	// Get all borrowing records (you can filter by user, date, etc., if needed)
	public List<BorrowingRecord> getBorrowingRecords() {
		return borrowingRecordStore.getAll();
	}
}
