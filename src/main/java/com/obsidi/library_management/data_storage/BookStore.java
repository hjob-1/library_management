package com.obsidi.library_management.data_storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidi.library_management.models.Book;

public class BookStore extends FileHandler<Book> implements ICrudOperation<Book> {

	private final String filePathBook = "C:\\Users\\hjob1\\Documents\\workspace-library_management\\library_management\\src\\main\\java\\com\\obsidi\\library_management\\data_storage\\books.txt";
	private ObjectMapper objMapper;

	public BookStore() {
		super("book.json");
		this.objMapper = new ObjectMapper();
//		books = new ArrayList<>();
//		fileHandler = new FileHandler<Book>("book.json");
//		loadBooks();

	}

	@Override
	public ArrayList<Book> getAll() {
		ArrayList<Book> list = null;
		try {
			list = objMapper.readValue(new File("book.json"), new TypeReference<ArrayList<Book>>() {
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
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
		List<Book> books = super.getAll();
		for (Book book : books) {
			if (book.getId().equals(id)) {
				// update the whole book information.
				book.setAuthor(item.getAuthor());
				book.setTitle(item.getTitle());
				book.setAvailable(item.isAvailable());
				break;
			}
		}
		save(books);
	}

	@Override
	public void delete(String id) {
		ArrayList<Book> books = getAll();
		// RemoveIf ID existed
		books.removeIf(book -> book.getId().equals(id));
		save(books);
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
					System.out.println("Invalid field name: " + fieldName);
					return;
				}
				System.out.println("Book with ID " + id + " updated: " + fieldName + " changed to " + newValue);
				save(books);
				return;
			}
		}
		System.out.println("Book with ID " + id + " not found.");

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
			filteredBook = books.stream().filter(book -> book.isAvailable() == boolValue).toList();
			break;

		}

		return filteredBook;
	}

	// Helper Methods not accessible from outside.
//	private void loadBooks() {
//        List<String> lines = fileHandler.loadFromFile();
//        for (String line : lines) {
//            books.add(parseBookFromText(line));
//        }
//    }

//    private void saveBooks() {
//        List<String> lines = new ArrayList<>();
//        for (Book book : books) {
//            lines.add(formatBookToText(book));
//        }
//        fileHandler.saveToFile(lines);
//    }

	// This method is going to format the book attributes
	// in the form of id,author,title,isAvailable
//    private String formatBookToText(Book book) {
//        return book.getId() + "," + book.getAuthor() + "," + book.getTitle() + "," + book.isAvailable();
//    }

//    private Book parseBookFromText(String text) {
//        String[] tokens = text.split(",");
//        String id = tokens[0];
//        String author = tokens[1];
//        String title = tokens[2];
//        boolean isAvailable = Boolean.parseBoolean(tokens[3]);
//        return new Book( author, title);
//    }	

}
