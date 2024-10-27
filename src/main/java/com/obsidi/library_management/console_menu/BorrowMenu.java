package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Scanner;

import static com.obsidi.library_management.Util.*;

import com.obsidi.library_management.LibraryManagementSystem;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.business_logics.BorrowingBusinessLogic;
import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.BorrowingRecord;
import com.obsidi.library_management.models.User;

public class BorrowMenu {
	private static final Scanner scanner = new Scanner(System.in); // Static scanner instance

	private static final String[] OPTIONS = { "[1] Borrow a Book", "[2] Return a Book", "[3] View Borrowing Records",
			"[4] Go back to Main Menu" };
	private static final String TITLE = "Welcome To \uD83D\uDCD5 Borrowing Management";
	private BorrowingBusinessLogic borrowingLogic;
	private BookBussinesLogic bookLogic;
	private UserBusinessLogic userLogic;

	public BorrowMenu() {
		this.borrowingLogic = new BorrowingBusinessLogic();
		this.bookLogic = new BookBussinesLogic();
		this.userLogic = new UserBusinessLogic();

	}

	public void borrowingMenu() {
		int choice;
		try {
			do {
				print("\n");
				displayHeader(TITLE, "");
				displayMenu(OPTIONS);

				choice = getValidChoice(1, 4, scanner);
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

	public void borrowBook() {

		print(ANSI_CYAN + "\n\t>> Please Enter your User ID: ");
		String userId = scanner.nextLine();
		print("\t>> Please enter the Book ID you want to borrow: " + ANSI_RESET);
		String bookId = scanner.nextLine();
		borrowingLogic.borrowBook(userId, bookId);
	}

	// Return a borrowed book
	public void returnBook() {
		print("\t>> Please enter your User ID:");
		String userId = scanner.nextLine();
		print("\t>> Please enter the Book ID you want to return:");
		String bookId = scanner.nextLine();

		// Perform the return operation
		borrowingLogic.returnBook(userId, bookId);

	}

	// View borrowing records
	public void viewBorrowingRecords() {
		System.out.println("\n\n\tBORROWING RECORDS:");
		List<BorrowingRecord> records = borrowingLogic.getBorrowingRecords();

		if (records == null) {
			return;
		} else if (records.isEmpty()) { // file empty
			print("\n\tThere is no borrowed book added yet.");
			// file existed it will display an empty table or with data
		} else {
			printSymbol(190, "_");
			System.out.println(ANSI_LIGHT_GRAY_BG + ANSI_BLACK_TEXT + ANSI_BOLD + ANSI_UNDERLINE
					+ "User ID \t\t\t\tUser Name \tBook ID \t\t\t\tBook Title \t\t\t Borrow Date \t\t\t Return Date \t\t\t\t Status");
			print(ANSI_RESET);

			for (int i = 0; i < records.size(); i++) {
				BorrowingRecord record = records.get(i);
				// get user who borrowed a book
				User user = userLogic.get(record.getUserId());
				// get user borrowed book
				Book book = bookLogic.get(record.getBookId());
				// get user status book
				String status = (record.getReturnDate() == null) ? "Borrowed" : "Returned";
				// check if the book is returned or not
				String isReturned = (record.getReturnDate() == null) ? "Not Returned"
						: records.get(i).getReturnDate().toString();
				// row data color
				String rowColor = i % 2 == 1 ? ANSI_LIGHT_BLUE_BG + ANSI_WHITE_TEXT + ANSI_UNDERLINE : "";
				print(rowColor);
				System.out.println(record.getUserId() + "\t" + user.getName() + "\t" + record.getBookId() + "\t"
						+ book.getTitle() + "\t\t" + record.getBorrowDate() + "\t\t" + isReturned + "\t\t" + status);

				print(ANSI_RESET);
			}

		}

	}

}
