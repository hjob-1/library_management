package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Scanner;

import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.models.Book;

public class BookMenu {

	private BookBussinesLogic bookLogic;
	private Scanner scanner;
	private final String[] options = { "1. Add a New Book", "2. Remove an Existing Book", "3. Update Book Information",
			"4. Search for a Book", "5. View All Books", "6. Go back to Main Menu", "0 Exit" };

	public BookMenu() {
		this.bookLogic = new BookBussinesLogic();
		this.scanner = new Scanner(System.in);
	}

	public void dashSymbol(int num) {
		while (num >= 1) {

			System.out.print("_");
			num--;
		}
		System.out.println();
	}

	public void bookMenu() {
		int choice;
		System.out.println("\n\t\tWelcome To \uD83D\uDCD5 Book Management\t\t\n");
		for (String option : options) {
			System.out.println("\t" + option);
		}
		System.out.print("\n\tPlease choose an option (0-6): ");
		choice = getValidChoice(0, 6);

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
		System.out.println("All books");
		dashSymbol(130);
		System.out.println("Id\t\t\t\t\t title\t\t\t\t\t  author\t\t   \tavailable");
		for (Book book : bookLogic.getAll()) {
			dashSymbol(130);
			System.out.println(book.getId() + " \t " + book.getTitle() + "\t\t\t " + book.getAuthor() + "\t\t\t "
					+ book.isAvailable());
		}
		dashSymbol(130);

	}

	public void searchBook() {
		Scanner scanner = new Scanner(System.in);
		int choice;
		List<Book> filtered = null;

		System.out.println("Choose search criteria:");
		System.out.println("1. Search by Title");
		System.out.println("2. Search by Author");
		System.out.println("3. Search by Book Availablity");

		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1:
			System.out.println("Start typing ...");
			String title = scanner.nextLine();
			filtered = bookLogic.searchByField("title", title);
			break;
		case 2:
			System.out.println("Start typing ...");
			String author = scanner.nextLine();
			filtered = bookLogic.searchByField("author", author);
			break;
		case 3:
			System.out.println("To search for books, do you want to find books that are available or unavailable?");
			System.out
					.println("Type 'true' to search for available books, or 'false' to search for unavailable books.");

			String available = scanner.nextLine().trim();
			filtered = bookLogic.searchByField("available", available);
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
		}

		if (filtered.isEmpty()) {
			System.out.println("The book you are looking is not in our system.");
		} else {
			dashSymbol(130);
			System.out.println("Id\t\t\t\t\t title\t\t\t\t\t  author\t\t   \tavailable");
			for (Book book : filtered) {
				dashSymbol(130);
				System.out.println(book.getId() + " \t " + book.getTitle() + "\t\t\t " + book.getAuthor() + "\t\t\t "
						+ book.isAvailable());
			}
			dashSymbol(130);

		}
	}

	public void deleteBook() {
		Scanner scanner = new Scanner(System.in);
		String id;
		System.out.println("Please Enter the Book Id you want to delete?");
		id = scanner.nextLine();
		bookLogic.delete(id);
		// delete the book
		// show a message if there is no book available display an error message
		// ask them if they want to go back to the main menu or book page

		System.out.println("book deleted");

	}

	public void updateBook() {
		try (Scanner scanner = new Scanner(System.in)) {
			int choice;

			String id;
			String author;
			String title;

			System.out.println("Please choose the field you would like to update:");
			System.out.println("1. Update Book Title");
			System.out.println("2. Update Book Author");
			System.out.print("Enter the number corresponding to your choice => ");

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Please enter the book's ID:");
				id = scanner.nextLine().trim(); // Get the book's ID

				System.out.println("Please enter the new book title:");
				title = scanner.nextLine().trim(); // Get the new book title

				bookLogic.updateFieldById(id, "title", title);
				System.out.println("The book has been successfully updated.");
				System.out.println("Updated Book - ID: " + id + ", New Title: " + title);
				break;
			case 2:
				System.out.println("Please enter the book's ID:");
				id = scanner.nextLine().trim(); // Get the book's ID

				System.out.println("Please enter the author's name:");
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
		Scanner scanner = new Scanner(System.in);
		String title;
		String author;
		System.out.println("Please Enter book title");
		title = scanner.nextLine();
		System.out.println("Please Enter book's Author");
		author = scanner.nextLine();

		bookLogic.add(new Book(title, author));
		System.out.println("Successfully added your book");
		System.out.println(title + " by" + author);

	}

	private int getValidChoice(int min, int max) {
		int choice = 0;
		while (choice < min || choice > max) {
			System.out.println("Enter a number between " + min + " and " + max + ":");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		}
		return choice;
	}
}
