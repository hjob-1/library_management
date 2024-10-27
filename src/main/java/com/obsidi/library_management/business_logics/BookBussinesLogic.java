package com.obsidi.library_management.business_logics;

import java.util.ArrayList;
import java.util.List;

import static com.obsidi.library_management.Util.notifyMsg;
import com.obsidi.library_management.data_storage.BookStore;
import com.obsidi.library_management.models.Book;

public class BookBussinesLogic extends BaseLogic<Book> {

	// Create a Repository object instance that can store to files
	// Initialize in the repository

	public BookBussinesLogic() {
		super(new BookStore());
	}

	@Override
	public void add(Book book) {
		try {
			verify(book);
			super.add(book);
		} catch (InvalidInputException e) {

			notifyMsg("error", e.getMessage());
		}

	}

	@Override
	public void updateFieldById(String id, String fieldName, String newValue) {

		// Do the validation before updating the values
		try {
			super.validateFields(id, fieldName, newValue);
			super.updateFieldById(id, fieldName, newValue);
		} catch (InvalidInputException e) {
			notifyMsg("error", e.getMessage());
		}

	}

	public List<Book> searchByField(String fieldName, String value) {
		// verify if they put the required data types.

		return super.searchByField(fieldName, value);
	}

	@Override
	// validation occur here
	public void verify(Book book) throws InvalidInputException {

		List<String> errorMessages = new ArrayList<>();

		if (book.getTitle() == null || book.getTitle().isEmpty()) {
			errorMessages.add("Error: Title can not be null or empty.");
		}
		if (book.getTitle().matches("\\d+")) {
			errorMessages.add("Error: Title can not be number");
		}
		if (book.getTitle().length() < 2) {
			errorMessages.add("Error: Title should be greater than 2 characters");

		}
		if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
			errorMessages.add("Error: Author can not be null or empty.");
		}

		if (book.getAuthor().matches("\\d+")) {
			errorMessages.add("Error: Author can not be number");
		}

		if (book.getAuthor().length() < 2) {
			errorMessages.add("Error: Author should be greater than 2 characters");

		}

		if (!errorMessages.isEmpty()) {
			throw new InvalidInputException(String.join("\n\t", errorMessages));
		}

	}

}
