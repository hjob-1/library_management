package com.obsidi.library_management.business_logics;

import java.util.List;

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
		super.add(item);

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

}
