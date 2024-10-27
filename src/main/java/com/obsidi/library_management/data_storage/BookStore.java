package com.obsidi.library_management.data_storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidi.library_management.models.Book;
import static com.obsidi.library_management.Util.notifyMsg;

public class BookStore extends FileHandler<Book> implements ICrudOperation<Book> {

	private final static String filePathBook = "C:\\Users\\hjob1\\Documents\\workspace-library_management\\library_management\\src\\main\\java\\com\\obsidi\\library_management\\data_storage\\books.json";
	private ObjectMapper objMapper;

	public BookStore() {
		super(filePathBook);
		this.objMapper = new ObjectMapper();
	}

	@Override
	public ArrayList<Book> getAll() {
		ArrayList<Book> list = null;
		try {
			list = objMapper.readValue(new File(filePathBook), new TypeReference<ArrayList<Book>>() {
			});

		} catch (FileNotFoundException fne) {
			notifyMsg("error", "File does not exist\n");
		} catch (IOException e) {
			notifyMsg("error", "something went wrong\n");
		}
		return list == null ? new ArrayList<Book>() : list;
	}

	@Override
	public Book get(String id) {

		for (Book book : getAll()) {
			if (book.getId().equals(id)) {
				return book;
			}
		}
		return null;
	}

	@Override
	public void update(String id, Book item) {
		List<Book> books = getAll();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId().equals(id)) {
				// update the whole book information.
				books.set(i, item);
				break;
			}
		}
		save(books);
	}

	@Override
	public void delete(String id) {
		ArrayList<Book> books = getAll();
		// RemoveIf ID existed
		boolean removed = books.removeIf(book -> book.getId().equals(id));
		if (removed) {
			// save the updated book list
			save(books);
			notifyMsg("success", "Book with ID " + id + " has been successfully removed.\n");
		} else {
			notifyMsg("error", "No book found with ID " + id + ".\n");
		}
	}

	@Override
	public void updateFieldById(String id, String fieldName, String newValue) {
		List<Book> books = getAll();
		for (Book book : books) {
			if (book.getId().equals(id)) {
				switch (fieldName.toLowerCase()) {
				case "title":
					book.setTitle(newValue);
					break;
				case "author":
					book.setAuthor(newValue);
					break;
				case "availablity":
					boolean isAvailable = Boolean.getBoolean(newValue);
					book.setAvailable(isAvailable);
					break;
				default:
					notifyMsg("error", "Invalid field name: " + fieldName + "\n");
					return;
				}
				save(books);
				notifyMsg("success",
						"Book with ID " + id + " updated: " + fieldName + " changed to " + newValue + "\n");
				return;
			}
		}
		notifyMsg("error", "\n\t Book with ID " + id + " not found.\n");

	}

	@Override
	public List<Book> searchByField(String fieldName, String value) {
		// TODO Auto-generated method stub
		ArrayList<Book> books = getAll();
		List<Book> filteredBook = null;
		switch (fieldName) {
		case "title":
			filteredBook = books.stream().filter(book -> book.getTitle().toLowerCase().contains(value.toLowerCase()))
					.toList();
			break;
		case "author":
			filteredBook = books.stream().filter(book -> book.getAuthor().toLowerCase().contains(value.toLowerCase()))
					.toList();

			break;
		case "available":
			boolean boolValue = Boolean.parseBoolean(value);
			if (isBoolean(value)) {
				filteredBook = books.stream().filter(book -> book.isAvailable() == boolValue).toList();
			} else {
				notifyMsg("error", "Enter either true or false\n");
			}
			break;
		default:
			notifyMsg("error", "Invalid field name: " + fieldName + "\n");
			break;

		}

		return filteredBook;
	}

	private boolean isBoolean(String value) {
		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
	}

}
