package com.obsidi.library_management;

import java.util.List;
import java.util.Scanner;

import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.User;

public abstract class Util {
	public static final String ANSI_CYAN = "\u001B[96m";
	public static final String ANSI_WHITE = "\u001B[97m";
	public static final String ANSI_YELLOW = "\u001B[93m";
	public static final String ANSI_GREEN = "\u001B[92m";
	public static final String ANSI_RED = "\u001B[91m";
	public static final String ANSI_YELLOW_BG = "\033[43m"; // Yellow background
	public static final String ANSI_BLACK_TEXT = "\033[30m"; // Black text to contrast with yellow
	public static final String ANSI_BOLD = "\033[1m";
	public static final String ANSI_UNDERLINE = "\033[4m";
	public static final String ANSI_BLACK_BG = "\033[40m";
	public static final String ANSI_WHITE_TEXT = "\033[37m";
	public static final String ANSI_LIGHT_BLUE_BG = "\033[104m"; // Light blue background
	public static final String ANSI_RESET = "\u001B[0m"; // Reset color
	public static final String ANSI_LIGHT_GRAY_BG = "\033[47m"; // Light gray background

	public static void print(String outPut) {
		System.out.print(outPut);
	}

	public static void displayMenu(String[] options) {

		for (String option : options) {
			print(ANSI_YELLOW + "\t\t" + option + "\n" + ANSI_RESET);
		}
	}

	public static void displayHeader(String title, String subTitle) {
		printSymbol(100, "-");
		print(ANSI_CYAN + "\n\t\t\t" + title + "\t\t\n" + ANSI_RESET);
		printSymbol(100, "-");
		print("\t\t\t\t " + subTitle + " \t\t\n\n");
	}

	public static void printSymbol(int num, String pattern) {
		while (num >= 1) {

			print(pattern);
			num--;
		}
		print("\n");
	}

	public static void printDynamicBookTable(List<String> headings, List<Book> books) {
		// List to hold the maximum lengths for each column
		int[] maxLengths = new int[headings.size()];

		// Calculate the maximum length for each column (including headers)
		for (int i = 0; i < headings.size(); i++) {
			maxLengths[i] = headings.get(i).length(); // Start with header lengths
		}

		// Include data lengths in the max lengths calculation
		for (Book book : books) {
			maxLengths[0] = Math.max(maxLengths[0], book.getId().length()); // Book ID
			maxLengths[1] = Math.max(maxLengths[1], book.getTitle().length()); // Title
			maxLengths[2] = Math.max(maxLengths[2], book.getAuthor().length()); // Author
			maxLengths[3] = Math.max(maxLengths[3], book.isAvailable() ? "Yes".length() : "No".length()); // Availability
		}

		// Print headers with dynamic spacing
		for (int i = 0; i < headings.size(); i++) {
			System.out.printf(ANSI_LIGHT_GRAY_BG + ANSI_BLACK_TEXT + ANSI_BOLD + ANSI_UNDERLINE + "%-"
					+ (maxLengths[i] + 4) + "s", headings.get(i));
		}
		print(ANSI_RESET);
		System.out.println(); // New line after the header

		// Print data rows with dynamic spacing
		for (int i = 0; i < books.size(); i++) {
			String rowColor = i % 2 == 1 ? ANSI_LIGHT_BLUE_BG + ANSI_WHITE_TEXT + ANSI_UNDERLINE : "";
			print(rowColor);
			System.out.printf("%-" + (maxLengths[0] + 4) + "s", books.get(i).getId());
			System.out.printf("%-" + (maxLengths[1] + 4) + "s", books.get(i).getTitle());
			System.out.printf("%-" + (maxLengths[2] + 4) + "s", books.get(i).getAuthor());
			System.out.printf("%-" + (maxLengths[3] + 4) + "s\n",
					books.get(i).isAvailable() ? ANSI_GREEN + "Yes" : ANSI_RED + "No");
			print(ANSI_RESET);
		}

		System.out.println("\n"); // New line after the table
	}

	public static void displaySubHeader(String header) {
		print("\n\n");
		printSymbol(70, ANSI_CYAN + "*");
		print("\t\t        " + header + "           \t\t\t\n");
		printSymbol(70, "*");
		print(ANSI_RESET);
	}

	public static void table(String[] headings, String symbol) {
		for (String heading : headings) {

			print(ANSI_LIGHT_GRAY_BG + ANSI_BLACK_TEXT + ANSI_BOLD + ANSI_UNDERLINE + heading + "\t\t\t\t\t");
		}
		// This \n used to jump it to the newline After creating table headers
		print("\n" + ANSI_RESET);
	}

	public static void notifyMsg(String type, String message) {
		String msgColor = type.equalsIgnoreCase("error") ? ANSI_RED : ANSI_GREEN;

		print(msgColor + "\n\t" + message + ANSI_RESET);
	}

	public static int getValidChoice(int min, int max, Scanner scanner) {
		int choice = -1; // Initialize choice with an invalid value
		boolean valid = false;

		while (!valid) {
			print("\n\tEnter your choice (" + min + "-" + max + ") and press Enter to continue:  ");
			String input = scanner.nextLine(); // Read the entire line

			try {
				choice = Integer.parseInt(input); // Try to parse the input as an integer

				// Validate the choice is within the desired range
				if (choice >= min && choice <= max) {
					valid = true; // Exit loop when a valid choice is made
				} else {
					notifyMsg("error", "Please enter a number between " + min + " and " + max + ".");
				}
			} catch (NumberFormatException e) {
				notifyMsg("error", "Invalid input. Please enter a valid number.");
			}
		}

		return choice; // Return the valid choice
	}

	public static void printDynamicUserTable(List<String> headings, List<User> users) {
		// List to hold the maximum lengths for each column
		int[] maxLengths = new int[headings.size()];

		// Calculate the maximum length for each column (including headers)
		for (int i = 0; i < headings.size(); i++) {
			maxLengths[i] = headings.get(i).length(); // Start with header lengths
		}

		// Include data lengths in the max lengths calculation
		for (User user : users) {
			maxLengths[0] = Math.max(maxLengths[0], user.getUserId().length()); // Book ID
			maxLengths[1] = Math.max(maxLengths[1], user.getName().length()); // Title
			maxLengths[2] = Math.max(maxLengths[2], user.getEmail().length()); // Author
			maxLengths[3] = Math.max(maxLengths[3], user.getPassword().length());
		}

		// Print headers with dynamic spacing
		for (int i = 0; i < headings.size(); i++) {
			System.out.printf(ANSI_LIGHT_GRAY_BG + ANSI_BLACK_TEXT + ANSI_BOLD + ANSI_UNDERLINE + "%-"
					+ (maxLengths[i] + 4) + "s", headings.get(i));
		}
		print(ANSI_RESET);
		System.out.println(); // New line after the header

		// Print data rows with dynamic spacing
		for (int i = 0; i < users.size(); i++) {
			String rowColor = i % 2 == 1 ? ANSI_LIGHT_BLUE_BG + ANSI_WHITE_TEXT + ANSI_UNDERLINE : "";
			print(rowColor);
			System.out.printf("%-" + (maxLengths[0] + 4) + "s", users.get(i).getUserId());
			System.out.printf("%-" + (maxLengths[1] + 4) + "s", users.get(i).getName());
			System.out.printf("%-" + (maxLengths[2] + 4) + "s", users.get(i).getEmail());
			System.out.printf("%-" + (maxLengths[3] + 4) + "s\n", users.get(i).getPassword());
			print(ANSI_RESET);
		}

		System.out.println("\n"); // New line after the table
	}

}
