package com.obsidi.library_management;

import java.util.Scanner;

import com.obsidi.library_management.console_menu.BookMenu;
import com.obsidi.library_management.console_menu.BorrowMenu;
import com.obsidi.library_management.console_menu.ReportMenu;
import com.obsidi.library_management.console_menu.UserMenu;

public class LibraryManagementSystem {

	private final static String TITLE = "Welcome to the Library Management System";
	private final static String SUB_TITLE = "Unlock a World of Knowledge";
	private final static String[] OPTIONS = { "[1] \uD83D\uDCD5 Book Management", "[2] \uD83D\uDC64 User Management",
			"[3] \uD83D\uDCD6 Borrowing & Returning Books", "[4] \u23F3 Overdue Books Management",
			"[5] \uD83D\uDCDD Report Generation", Util.ANSI_RED + "[6] \uD83D\uDEAA Exit" };

	public static void main(String[] args) {
		BookMenu bookOptions = new BookMenu();
		UserMenu userOptions = new UserMenu();
		BorrowMenu borrowOptions = new BorrowMenu();
		ReportMenu report = new ReportMenu();
		Scanner scanner = new Scanner(System.in);

		Util.displayHeader(TITLE, SUB_TITLE);
		Util.displayMenu(OPTIONS);

		System.out.print("\n        Enter your choice (1-6) and press Enter to continue:  ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			bookOptions.bookMenu();
			break;
		case 2:
			userOptions.userMenu();
			break;
		case 3:
			borrowOptions.borrowingMenu();
			break;
		case 4:
			System.out.print("\t selection 4");
			break;
		case 5:
			report.displayElegantReportMenu();
			break;
		case 6:
			System.out.print("\t selection 6");
			break;
		default:
			break;
		}

	}

}
