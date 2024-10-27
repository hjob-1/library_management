package com.obsidi.library_management.console_menu;

import static com.obsidi.library_management.Util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.obsidi.library_management.LibraryManagementSystem;
import static com.obsidi.library_management.Util.*;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.business_logics.BorrowingBusinessLogic;
import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class ReportMenu {
	private static final Scanner scanner = new Scanner(System.in); // Static scanner instance

	private final static String TITLE = "📊 Library Analytics Dashboard";
	private final static String SUB_TITLE = "";
	private final static String[] OPTIONS = { "[1] 📚 Books Overview Report ", "[2] 📋 Top Borrowed Books",
			"[3] 📋 Active Users", "[4] Go Back To mainMenu" };

	private BorrowingBusinessLogic borrowingLogic;
	private BookBussinesLogic bookLogic;
	private UserBusinessLogic userLogic;

	public ReportMenu() {
		this.borrowingLogic = new BorrowingBusinessLogic();
		this.bookLogic = new BookBussinesLogic();
		this.userLogic = new UserBusinessLogic();

	}

	public void displayElegantReportMenu() {

		try {
			int choice;
			do {
				print("\n");
				displayHeader(TITLE, SUB_TITLE);
				displayMenu(OPTIONS);

				choice = getValidChoice(1, 4, scanner);

				switch (choice) {
				case 1:
					showBorrowedBooksOverviewReport();
					break;
				case 2:
					showTopBorrowedBooks();
					break;
				case 3:
					showActiveUsers();
					break;
				case 4:
					LibraryManagementSystem.mainMenu();
					break;
				default:
					break;
				}
			} while (choice != 0);
		} finally {
			// close scanner to prevent resource leak
			scanner.close();
		}
	}

	// 1. Books Overview Report
	private void showBorrowedBooksOverviewReport() {

		displaySubHeader("--- 📚 Books Overview Report ---");

		List<Book> allBooks = bookLogic.getAll();
		// count available books
		long availableBooks = allBooks.stream().filter(Book::isAvailable).count();
		// display books summary information
		printBookOverview(allBooks.size(), availableBooks);

		// Get borrowed books
		List<Book> borrowedBooks = allBooks.stream().filter(book -> !book.isAvailable()).toList();

		printDynamicBookTable(new ArrayList<>(Arrays.asList("Book ID", "Title", "Author", "Available")), borrowedBooks);

		System.out.println("\n");
	}

	// 2. Top Borrowed Books Report
	private void showTopBorrowedBooks() {
		displaySubHeader("--- 🔝 Top Borrowed Books ---");

		List<BorrowingRecord> borrowingRecords = borrowingLogic.getBorrowingRecords();
		Map<String, Long> borrowCountByBook = getBorrowCountByBook(borrowingRecords);

		List<Map.Entry<String, Long>> sortedBooks = sortBooksByBorrowCount(borrowCountByBook);

		printTopBorrowedBooksHeader();
		printTopBorrowedBooks(sortedBooks);
	}

	// 3. Active Users Report

	private void showActiveUsers() {

		displaySubHeader("--- 📋 Active Users Report ---");
		// Fetch all active users
		List<User> activeUsers = userLogic.getAll();

		// Print table headers with formatted spacing
		System.out.printf(
				ANSI_LIGHT_GRAY_BG + ANSI_BLACK_TEXT + ANSI_BOLD + ANSI_UNDERLINE + "%-50s %-20s %-20s %-10s\n",
				"User ID", "Name", "Email", "Total Borrowed Books");
		print(ANSI_RESET);

		// Loop through each active user
		for (int i = 0; i < activeUsers.size(); i++) {
			User user = activeUsers.get(i); // Store current user

			// Calculate the total borrowed books for the current user
			long borrowedCount = borrowingLogic.getBorrowingRecords().stream()
					.filter(record -> record.getUserId().equals(user.getUserId())).count();
			// Alternate row colors for better readability
			String rowColor = i % 2 == 1 ? ANSI_LIGHT_BLUE_BG + ANSI_WHITE_TEXT + ANSI_UNDERLINE : "";
			print(rowColor);
			// display only borrowed user
			if (borrowedCount > 0) {

				// Print the user data with formatted output
				System.out.printf("%-50s %-20s %-20s %18d\n", user.getUserId(), // User ID
						user.getName(), // User Name
						user.getEmail(), // User Email
						borrowedCount); // Total Borrowed Books
				print(ANSI_RESET); // Reset color formatting

			}

		}
		print(ANSI_RESET); // Reset color formatting

		System.out.println("\n");
	}

	private void printBookOverview(int totalBooks, long availableBooks) {
		print("Total Books: " + totalBooks);
		print(ANSI_GREEN + "\nAvailable: " + availableBooks);
		print(ANSI_RED + "\nBorrowed: " + (totalBooks - availableBooks) + "\n\n" + ANSI_RESET);
	}

	private Map<String, Long> getBorrowCountByBook(List<BorrowingRecord> borrowingRecords) {
		return borrowingRecords.stream()
				.collect(Collectors.groupingBy(BorrowingRecord::getBookId, Collectors.counting()));
	}

	private List<Map.Entry<String, Long>> sortBooksByBorrowCount(Map<String, Long> borrowCountByBook) {
		return borrowCountByBook.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.collect(Collectors.toList());
	}

	private void printTopBorrowedBooksHeader() {
		System.out.printf(
				ANSI_LIGHT_GRAY_BG + ANSI_BLACK_TEXT + ANSI_BOLD + ANSI_UNDERLINE + "%-45s %-20s %-20s %-10s\n",
				"Book ID", "Title", "Author", "Times Borrowed");
		print(ANSI_RESET);
	}

	private void printTopBorrowedBooks(List<Map.Entry<String, Long>> sortedBooks) {
		int index = 0;
		for (Map.Entry<String, Long> entry : sortedBooks) {
			Book book = bookLogic.get(entry.getKey());
			String rowColor = index % 2 == 1 ? ANSI_LIGHT_BLUE_BG + ANSI_WHITE_TEXT + ANSI_UNDERLINE : "";
			print(rowColor);
			System.out.printf("%-45s %-20s %-20s %-15d\n", book.getId(), book.getTitle(), book.getAuthor(),
					entry.getValue());
			print(ANSI_RESET);
			// This will keep track table row number
			index++;
		}
		System.out.println("\n");
	}

}
