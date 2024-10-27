package com.obsidi.library_management.console_menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.obsidi.library_management.Util.*;

import com.obsidi.library_management.LibraryManagementSystem;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.models.Book;

public class BookMenu {
	private static final Scanner scanner = new Scanner(System.in); // Static scanner instance

	private final static String TITLE = " 📕 Welcome to the Book Management Section 📕";
	private final static String SUB_TITLE = "Easily Manage Your Books";

	private final static String[] OPTIONS = { "[1] Add a New Book", "[2] Remove an Existing Book",
			"[3] Update Book Information", "[4] Search for a Book", "[5] View All Books", "[6] Go back to Main Menu", };

	private BookBussinesLogic bookLogic;

	public BookMenu() {
		this.bookLogic = new BookBussinesLogic();
	}

	public void bookMenu() {

		int choice;
		try {
			do {
				print("\n");
				displayHeader(TITLE, SUB_TITLE);
				displayMenu(OPTIONS);

				choice = getValidChoice(1, 6, scanner);

				switch (choice) {
				case 1:
					addBook();
					break;
				case 2:
					deleteBook();
					break;
				case 3:
					updateBook();
					break;
				case 4:
					searchBook();
					break;
				case 5:
					viewAllBook();
					break;
				case 6:
					LibraryManagementSystem.mainMenu();
					break;
				default:
					break;
				}
			} while (choice != 0);
		} finally {
			scanner.close();
		}
	}

	public void viewAllBook() {
		List<Book> books = bookLogic.getAll();
		// file not existed exception / file deleted
		if (books == null) {
			return;
		} else if (books.isEmpty()) { // file empty
			print("\n\tThere is no book added yet.");
			// file existed it will display an empty table or with data
		} else {
			print(ANSI_CYAN + "\nLIST OF ALL BOOKS\n");
			printDynamicBookTable(new ArrayList<>(Arrays.asList("Book ID", "Title", "Author", "Available")), books);
		}

	}

	public void searchBook() {
		List<Book> filtered = null;
		displaySubHeader("SEARCH FOR A BOOK");

		print("\n\tChoose search criteria:\n");
		displayMenu(new String[] { "[1] Search by Title", "[2] Search by Author", "[3] Search by Book Availablity" });

		int choice = getValidChoice(1, 3, scanner);

		switch (choice) {
		case 1:
			print("\tEnter the Book Title:");
			String title = scanner.nextLine();
			// do the validation
			filtered = bookLogic.searchByField("title", title);
			break;
		case 2:
			print("\tEnter the Book Author:");
			String author = scanner.nextLine();
			// do the validation
			filtered = bookLogic.searchByField("author", author);
			break;
		case 3:
			print("\n\t>> Type " + ANSI_GREEN + "true" + ANSI_RESET + " for available books, or " + ANSI_RED + "false"
					+ ANSI_RESET + " for unavailable books: ");
			String available = scanner.nextLine().trim();
			filtered = bookLogic.searchByField("available", available);
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
		}

		if (filtered == null || filtered.isEmpty()) {
			print("\n\tThe book you are looking is not in our system.\n");
		} else {

			printDynamicBookTable(new ArrayList<>(Arrays.asList("Book ID", "Title", "Author", "Available")), filtered);

		}
	}

	public void deleteBook() {
		String id;
		displaySubHeader("DELETE A BOOK");
		print("\n\t>> Please Enter the Book ID you want to delete: ");
		id = scanner.nextLine();

		bookLogic.delete(id);
	}

	public void updateBook() {

		int choice;

		String id;
		String author;
		String title;
		displaySubHeader("UPDATE A BOOK");

		print("\n\tPlease choose the field you would like to update:\n\n");
		displayMenu(new String[] { "[1] Update Book Title", "[2] Update Book Author" });

		choice = getValidChoice(1, 2, scanner);

		switch (choice) {

		case 1:
			print(ANSI_CYAN + "\n\t>> Enter Book's ID:");
			id = scanner.nextLine().trim(); // Get the book's ID

			print("\n\t>> Enter the new Book Title:");
			title = scanner.nextLine().trim(); // Get the new book title

			// Validation before adding it
			bookLogic.updateFieldById(id, "title", title);
			break;
		case 2:
			print("\n\t>> Please enter the book's ID:");
			id = scanner.nextLine().trim(); // Get the book's ID

			print("\n\t>> Enter the new author name:");
			author = scanner.nextLine().trim(); // Get the author's name
			print(ANSI_RESET);
			bookLogic.updateFieldById(id, "author", author);
			break;

		}

	}

	public void addBook() {
		String title;
		String author;
		displaySubHeader("ADD A NEW BOOK");
		print("\n\t>> Please Enter Book Title: ");
		title = scanner.nextLine();
		print("\n\t>> Please Enter Book Author: ");
		author = scanner.nextLine();

		bookLogic.add(new Book(title, author));

	}

}
