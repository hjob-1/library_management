package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.obsidi.library_management.Util;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.business_logics.BorrowingBusinessLogic;
import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class ReportMenu {

	private final static String TITLE = "📊 Library Analytics Dashboard";
	private final static String SUB_TITLE = "";
	private final static String[] OPTIONS = { "[1] 📚 Books Overview Report ", "[2] 📋 Top Borrowed Books",
			"[3] 📋 Active Users", Util.ANSI_RED + "[0] Exit" };

	private BorrowingBusinessLogic borrowingLogic;
	private BookBussinesLogic bookLogic;
	private UserBusinessLogic userLogic;
	private Scanner scanner;

	public ReportMenu() {
		this.borrowingLogic = new BorrowingBusinessLogic();
		this.bookLogic = new BookBussinesLogic();
		this.userLogic = new UserBusinessLogic();
		this.scanner = new Scanner(System.in);
	}

	public void displayElegantReportMenu() {
		Util.displayHeader(TITLE, SUB_TITLE);
		Util.displayMenu(OPTIONS);

		System.out.print("\n\t Enter your choice (0 - 3) and press Enter to continue:   ");

		int choice = getValidChoice(0, 3);

		switch (choice) {
		case 1:
			showBooksOverviewReport();
			break;
		case 2:
			showTopBorrowedBooks();
			break;
		case 3:
			showActiveUsers();
			break;
		case 0:
			System.out.println("Exiting report menu...");
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
		}
	}

	private int getValidChoice(int min, int max) {
		int choice = 0;
		try {
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number.");
		}
		return (choice >= min && choice <= max) ? choice : getValidChoice(min, max);
	}

	// 1. Books Overview Report
	private void showBooksOverviewReport() {
		System.out.println("\n\n--- 📚 Books Overview Report ---\n");
		List<Book> allBooks = bookLogic.getAll();
		long availableBooks = allBooks.stream().filter(book -> book.isAvailable()).count();
		System.out.println("Total Books: " + allBooks.size());
		System.out.println("Available: " + availableBooks);
		System.out.println("Borrowed: " + (allBooks.size() - availableBooks) + "\n\n");
		System.out.printf("%-10s %-30s %-20s %-10s\n", "Book ID", "Title", "Author", "Available");
		System.out.println("------------------------------------------------------------");

		// Not Available means its borrowed
		List<Book> borrowedBooks = allBooks.stream().filter(book -> !book.isAvailable()).toList();

		for (Book book : borrowedBooks) {
			String availability = book.isAvailable() ? "Yes" : "No";
			System.out.printf("%-10s %-30s %-20s %-10s\n", book.getId(), book.getTitle(), book.getAuthor(),
					availability);
		}
		System.out.println("\n");
	}

	// 2. Top Borrowed Books Report
	private void showTopBorrowedBooks() {
		System.out.println("\n\n--- 🔝 Top Borrowed Books ---\n");
		List<BorrowingRecord> borrowingRecords = borrowingLogic.getBorrowingRecords();
		// store ID and number of times borrowed
		Map<String, Long> borrowCountByBook = borrowingRecords.stream()
				// group borrowing record by book ID -> Total number
				.collect(Collectors.groupingBy(BorrowingRecord::getBookId, Collectors.counting()));

		// Sort the books by borrow count in descending order
		List<Map.Entry<String, Long>> sortedBooks = borrowCountByBook.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed()).collect(Collectors.toList());

		System.out.printf("%-10s %-30s %-20s %-10s\n", "Book ID", "Title", "Author", "Times Borrowed");
		System.out.println("------------------------------------------------------------");

		for (Map.Entry<String, Long> entry : sortedBooks) {
			Book book = bookLogic.get(entry.getKey());
			System.out.printf("%-10s %-30s %-20s %-10d\n", book.getId(), book.getTitle(), book.getAuthor(),
					entry.getValue());
		}
		System.out.println("\n");
	}

	// 3. Active Users Report
	private void showActiveUsers() {
		System.out.println("\n\n--- 📋 Active Users Report ---\n");
		List<User> activeUsers = userLogic.getAll();
		System.out.printf("%-10s %-20s %-10s\n", "User ID", "Name", "Total Borrowed Books");
		System.out.println("----------------------------------------------------");

		for (User user : activeUsers) {
			long borrowedCount = borrowingLogic.getBorrowingRecords().stream()
					.filter(record -> record.getUserId().equals(user.getUserId())).count();
			System.out.printf("%-10s %-20s %-10d\n", user.getUserId(), user.getName(), borrowedCount);
		}
		System.out.println("\n");
	}
}
