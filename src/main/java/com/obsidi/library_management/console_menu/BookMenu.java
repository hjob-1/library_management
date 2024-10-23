package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Scanner;

import static com.obsidi.library_management.Util.*;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.models.Book;

public class BookMenu {
	private final static String TITLE = " 📕 Welcome to the Book Management Section 📕";
	private final static String SUB_TITLE = "Easily Manage Your Books";

	private final static String[] OPTIONS = { "[1] Add a New Book", "[2] Remove an Existing Book",
			"[3] Update Book Information", "[4] Search for a Book", "[5] View All Books", "[6] Go back to Main Menu",
			"\u001B[91m[0] Exit" };

	private BookBussinesLogic bookLogic;
	private Scanner scanner;

	public BookMenu() {
		this.bookLogic = new BookBussinesLogic();
		this.scanner = new Scanner(System.in);
	}

	public void bookMenu() {
		int choice;
		displayHeader(TITLE, SUB_TITLE);
		displayMenu(OPTIONS);
		System.out.print("\n\t Enter your choice (0-6) and press Enter to continue:  ");
		choice = scanner.nextInt();
		scanner.nextLine();
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
			System.out.print("\t selection 6");
			break;
		default:
			break;
		}

	}

	public void viewAllBook() {

		// add different color for the heading
		// add different color for available or not (red or green)
		print(ANSI_CYAN + "\nLIST OF ALL BOOKS\n");
		table(new String[] { "ID", "Title", "Author", "Available" }, "_");
		List<Book> books = bookLogic.getAll();
		displayBookTableContent(books);
	}

	public void searchBook() {
		List<Book> filtered = null;
		displaySubHeader("SEARCH FOR A BOOK");

		print("\n\tChoose search criteria:\n");
		displayMenu(new String[] { "[1] Search by Title", "[2] Search by Author", "[3] Search by Book Availablity" });
		print("\n\tEnter the number corresponding to your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

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

		// Do validation here
		if (filtered.isEmpty()) {
			System.out.println("The book you are looking is not in our system.");
		} else {
			table(new String[] { "ID", "Title", "Author", "Available" }, "_");
			displayBookTableContent(filtered);

		}
	}

	public void deleteBook() {
		String id;
		displaySubHeader("DELETE A BOOK");
		print("\n\t>> Please Enter the Book ID you want to delete: ");
		id = scanner.nextLine();
		bookLogic.delete(id);
		// delete the book
		// show a message if there is no book available display an error message
		// ask them if they want to go back to the main menu or book page

	}

	public void updateBook() {
		try (Scanner scanner = new Scanner(System.in)) {
			int choice;

			String id;
			String author;
			String title;
			displaySubHeader("UPDATE A BOOK");

			print("\n\tPlease choose the field you would like to update:\n\n");
			displayMenu(new String[] { "[1] Update Book Title", "[2] Update Book Author" });
			print("\n\tEnter the number corresponding to your choice => ");

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {

			case 1:
				print(ANSI_CYAN + "\n\t>> Enter Book's ID:");
				id = scanner.nextLine().trim(); // Get the book's ID

				print("\n\t>> Enter the new Book Title:");
				title = scanner.nextLine().trim(); // Get the new book title

				// Validation before adding it
				bookLogic.updateFieldById(id, "title", title);
				System.out.println("The book has been successfully updated.");
				System.out.println("Updated Book - ID: " + id + ", New Title: " + title);
				break;
			case 2:
				print("\n\t>> Please enter the book's ID:");
				id = scanner.nextLine().trim(); // Get the book's ID

				System.out.println("\n\t>> Enter the new author name:");
				author = scanner.nextLine().trim(); // Get the author's name

				bookLogic.updateFieldById(id, "author", author);
				System.out.println("Your book is updated");
				System.out.println("updated book " + id + "  " + author);
				break;

			}
			scanner.close();
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
		System.out.println("Successfully added your book");
		System.out.println(title + " by" + author);

	}

//	private int getValidChoice(int min, int max) {
//		int choice = 0;
//		while (choice < min || choice > max) {
//			System.out.println("Enter a number between " + min + " and " + max + ":");
//			try {
//				choice = Integer.parseInt(scanner.nextLine());
//			} catch (NumberFormatException e) {
//				System.out.println("Invalid input. Please enter a valid number.");
//			}
//		}
//		return choice;
//	}

	public void displayBookTableContent(List<Book> books) {
		for (Book book : books) {
			printSymbol(130, "_");
			System.out.println(book.getId() + " \t " + book.getTitle() + "\t\t\t " + book.getAuthor() + "\t\t\t\t\t "
					+ (book.isAvailable() ? ANSI_GREEN + "yes" : ANSI_RED + "No"));
			// Reset the color applied by book availability
			print(ANSI_RESET);
		}

	}
}
