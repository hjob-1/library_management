package com.obsidi.library_management.data_storage;

import static com.obsidi.library_management.Util.notifyMsg;

import java.io.File;
import java.io.FileNotFoundException;
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
	private final static String filePathBook = "C:\\Users\\hjob1\\Documents\\workspace-library_management\\library_management\\src\\main\\java\\com\\obsidi\\library_management\\data_storage\\borrowRecords.json";

	private ObjectMapper objMapper;

	public BorrowStore() {
		super(filePathBook);

		this.objMapper = new ObjectMapper();
	}

	@Override
	public ArrayList<BorrowingRecord> getAll() {
		ArrayList<BorrowingRecord> list = null;
		try {
			list = objMapper.readValue(new File(filePathBook), new TypeReference<ArrayList<BorrowingRecord>>() {
			});

		} catch (FileNotFoundException fne) {
			notifyMsg("error", "File does not exist\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list == null ? new ArrayList<BorrowingRecord>() : list;
	}

	public BorrowingRecord getById(String id) {
		// Since borrowing records don't have IDs, you can add custom search criteria
		// here if needed
		return null;
	}

	public void add(BorrowingRecord record) {
		super.add(record);

	}

	public boolean returnBook(User user, Book book) {
		BorrowingRecord record = findActiveBorrowingRecord(user.getUserId(), book.getId());

		if (record != null) {
			record.setReturnDate(new Date());
			update(record); // Update the record in the store
			notifyMsg("success", "Book returned: " + book.getTitle() + " by " + user.getName());
			return true;
		} else {
			notifyMsg("error", "No active borrowing record found for this book.\n");
			return false;
		}
	}

	// record usually not deleted
	// left empty intentionally so that records will not be deleted
	public void delete(String id) {

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
