package com.obsidi.library_management.data_storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class BorrowStore extends FileHandler<BorrowingRecord> {

	private ObjectMapper objMapper;

	public BorrowStore() {
		super("borrowRecords.json");

		this.objMapper = new ObjectMapper();
	}

	@Override
	public ArrayList<BorrowingRecord> getAll() {
		ArrayList<BorrowingRecord> list = null;
		try {
			list = objMapper.readValue(new File("borrowRecords.json"), new TypeReference<ArrayList<BorrowingRecord>>() {
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public BorrowingRecord getById(String id) {
		// Since borrowing records don't have IDs, you can add custom search criteria
		// here if needed
		return null;
	}

	public void add(BorrowingRecord record) {
		super.add(record);

	}

	public void returnBook(User user, Book book) {
		BorrowingRecord record = findActiveBorrowingRecord(user.getUserId(), book.getId());

		if (record != null) {
			record.setReturnDate(new Date());
			update(record); // Update the record in the store
			System.out.println("Book returned: " + book.getTitle() + " by " + user.getName());
		} else {
			System.out.println("No active borrowing record found for this book.");
		}
	}

	public void delete(String id) {
		// Implement deletion logic if needed, though usually, borrowing records are not
		// deleted
	}

	public void update(BorrowingRecord updatedRecord) {
		ArrayList<BorrowingRecord> list = getAll();
		for (int i = 0; i < list.size(); i++) {
			BorrowingRecord record = list.get(i);
			if (record.getUserId().equals(updatedRecord.getUserId())
					&& record.getBookId().equals(updatedRecord.getBookId())) {
				list.set(i, updatedRecord);
				save(list);
				return;
			}
		}

	}

	// Method to find an active borrowing record (i.e., one without a return date)
	private BorrowingRecord findActiveBorrowingRecord(String userId, String bookId) {
		List<BorrowingRecord> records = getAll();
		for (BorrowingRecord record : records) {
			if (record.getUserId().equals(userId) && record.getBookId().equals(bookId)
					&& record.getReturnDate() == null) {
				return record;
			}

		}
		return null;
	}

}
