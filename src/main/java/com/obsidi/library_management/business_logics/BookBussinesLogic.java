package com.obsidi.library_management.business_logics;

import java.util.List;

import com.obsidi.library_management.Util;
import com.obsidi.library_management.data_storage.BookStore;
import com.obsidi.library_management.models.Book;

public class BookBussinesLogic extends BaseLogic<Book> {

	// Create a Repository object instance that can store to files
	// initilize in the repository
	// private BookStore<T> bookStore;

	public BookBussinesLogic() {
		super(new BookStore());
	}

	@Override
	// validation occur here
	public void verify() {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Book item) {
		try {
			validate(item);
			super.add(item);
		} catch (InvalidInputException e) {

			Util.print(Util.ANSI_RED + e.getMessage() + "\n" + Util.ANSI_RESET);
		}

	}

	@Override
	public void updateFieldById(String id, String fieldName, String newValue) {

		// Do the validation before updating the values
		super.updateFieldById(id, fieldName, newValue);

	}

	public List<Book> searchByField(String fieldName, String value) {
		// verify if they put the required data types.
		return super.searchByField(fieldName, value);
	}

	public void validate(Book book) throws InvalidInputException {

		if (book.getTitle() == null || book.getTitle().isEmpty()) {
			throw new InvalidInputException("Error: Book title can not be null or empty.");
		}
		if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
			throw new InvalidInputException("Error: Book author can not be null or empty.");
		}
		if (book.getTitle().matches("\\d+")) {
			throw new InvalidInputException("Error: Book title can not be number");
		}
		if (book.getAuthor().matches("\\d+")) {
			throw new InvalidInputException("Error: Book Author can not be number");
		}

	}

}
