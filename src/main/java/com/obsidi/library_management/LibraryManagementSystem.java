package com.obsidi.library_management;

import static com.obsidi.library_management.Util.getValidChoice;

import java.util.Scanner;

import com.obsidi.library_management.console_menu.BookMenu;
import com.obsidi.library_management.console_menu.BorrowMenu;
import com.obsidi.library_management.console_menu.ReportMenu;
import com.obsidi.library_management.console_menu.UserMenu;

public class LibraryManagementSystem {
	private static final Scanner scanner = new Scanner(System.in); // Static scanner instance

	private final static String TITLE = "Welcome to the Library Management System";
	private final static String SUB_TITLE = "Unlock a World of Knowledge";
	private final static String[] OPTIONS = { "[1] \uD83D\uDCD5 Book Management", "[2] \uD83D\uDC64 User Management",
			"[3] \uD83D\uDCD6 Borrowing & Returning Books", "[4] \uD83D\uDCDD Report Generation",
			Util.ANSI_RED + "[5] \uD83D\uDEAA Exit" };

	public static void main(String[] args) {

		mainMenu();

	}

	public static void mainMenu() {

		BookMenu bookOptions = new BookMenu();
		UserMenu userOptions = new UserMenu();
		BorrowMenu borrowOptions = new BorrowMenu();
		ReportMenu report = new ReportMenu();

		int choice;
		try {
			do {
				Util.displayHeader(TITLE, SUB_TITLE);
				Util.displayMenu(OPTIONS);

				choice = getValidChoice(1, 5, scanner);

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
					report.displayElegantReportMenu();
					break;
				case 5:
					System.out.print(Util.ANSI_CYAN + "\n\t✨ Thank you for using the Library Management System! ✨\n"
							+ "\t\t\t🚪 Exiting the program... \n\t\t\t\tGoodbye! 👋");
					break;
				default:
					break;
				}

			} while (choice != 5);
		} finally {

			scanner.close();
		}

	}

}
