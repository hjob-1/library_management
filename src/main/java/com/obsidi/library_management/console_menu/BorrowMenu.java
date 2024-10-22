package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Scanner;

import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.business_logics.BorrowingBusinessLogic;
import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class BorrowMenu {
	private final String[] options = { "1. Borrow a Book", "2. Return a Book", "3. View Borrowing Records",
			"4. Go back to Main Menu", "0. Exit" };
	private BorrowingBusinessLogic borrowingLogic;
	private BookBussinesLogic bookLogic;
	private UserBusinessLogic userLogic;
	private Scanner scanner;

	public BorrowMenu() {
		this.borrowingLogic = new BorrowingBusinessLogic();
		this.bookLogic = new BookBussinesLogic();
		this.userLogic = new UserBusinessLogic();
		this.scanner = new Scanner(System.in);
	}

	public void dashSymbol(int num) {
		while (num >= 1) {
			System.out.print("_");
			num--;
		}
		System.out.println();
	}

	public void borrowingMenu() {
		int choice;
		System.out.println("\n\t\tWelcome To \uD83D\uDCD5 Borrowing Management\t\t\n");
		for (String option : options) {
			System.out.println("\t" + option);
		}
		System.out.print("\n\tPlease choose an option (0-4): ");
		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1:
			borrowBook();
			break;
		case 2:
			returnBook();
			break;
		case 3:
			viewBorrowingRecords();
			break;
		case 4:
			System.out.println("Going back to main menu...");
			break;
		default:
			break;
		}
	}

	// Borrow a book
	// Borrow a book
	public void borrowBook() {

		System.out.println("Please enter your User ID:");
		String userId = scanner.nextLine();

		// This whole code should be placed in verify bussines logic

		// Check if user is registered
		User user = userLogic.get(userId);
		if (user == null) {
			System.out.println("User not found! Please make sure you're registered.");
			return;
		}

		System.out.println("Please enter the Book ID you want to borrow:");
		String bookId = scanner.nextLine();

		// Check if the book exists and is available
		Book book = bookLogic.get(bookId);
		if (book == null) {
			System.out.println("Book not found.");
			return;
		}

		if (!book.isAvailable()) {
			System.out.println("Sorry, the book is currently unavailable.");
			return;
		}

		// Perform the borrowing operation
		borrowingLogic.borrowBook(user, book);
		user.addBorrowedBook(book);
		userLogic.update(userId, user);
		System.out.println("You have successfully borrowed the book: " + book.getTitle());

		// Update book availability
		bookLogic.updateFieldById(bookId, "availablity", "false");
	}

	// Return a borrowed book
	public void returnBook() {
		System.out.println("Please enter your User ID:");
		String userId = scanner.nextLine();

		// Check if user is registered
		User user = userLogic.get(userId);
		if (user == null) {
			System.out.println("User not found.");
			return;
		}

		System.out.println("Please enter the Book ID you want to return:");
		String bookId = scanner.nextLine();

		// Check if the book was borrowed by this user
		Book book = bookLogic.get(bookId);
		if (book == null) {
			System.out.println("Book not found.");
			return;
		}

		// Perform the return operation
		borrowingLogic.returnBook(user, book);
		System.out.println("You have successfully returned the book: " + book.getTitle());
		user.removeBorrowedBook(book);
		userLogic.update(userId, user);

		// Update book availability
		bookLogic.updateFieldById(bookId, "availablity", "true");

	}

	// View borrowing records
	public void viewBorrowingRecords() {
		System.out.println("Borrowing Records:");
		dashSymbol(130);
		System.out.println("User ID\t\tBook ID\t\tBorrow Date\t\tReturn Date\t\tStatus");

		List<BorrowingRecord> records = borrowingLogic.getBorrowingRecords();
		for (BorrowingRecord record : records) {
			dashSymbol(130);
			String status = (record.getReturnDate() == null) ? "Borrowed" : "Returned";
			System.out.println(record.getUserId() + "\t\t" + record.getBookId() + "\t\t" + record.getBorrowDate()
					+ "\t\t" + record.getReturnDate() + "\t\t" + status);
		}
		dashSymbol(130);
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
