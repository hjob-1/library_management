package com.obsidi.library_management;

public abstract class Util {
	public static final String ANSI_CYAN = "\u001B[96m";
	public static final String ANSI_WHITE = "\u001B[97m";
	public static final String ANSI_YELLOW = "\u001B[93m";
	public static final String ANSI_GREEN = "\u001B[92m";
	public static final String ANSI_RED = "\u001B[91m";
	public static final String ANSI_RESET = "\u001B[0m"; // Reset color

	public static void print(String outPut) {
		System.out.print(outPut);
	}

	public static void displayMenu(String[] options) {

		for (String option : options) {
			System.out.println(ANSI_YELLOW + "\t\t" + option + ANSI_RESET);
		}
	}

	public static void displayHeader(String title, String subTitle) {
		hashSymbol(100);
		System.out.println(ANSI_CYAN + "\n\t\t\t" + title + "\t\t\n" + ANSI_RESET);
		hashSymbol(100);
		System.out.println("\t\t\t\t " + subTitle + " \t\t\n\n");
	}

	public static void hashSymbol(int num) {
		while (num >= 1) {

			System.out.print("-");
			num--;
		}
		System.out.println();
	}

	public static void printSymbol(int num, String pattern) {
		while (num >= 1) {

			print(pattern);
			num--;
		}
		print("\n");
	}

	public static void displaySubHeader(String header) {
		print("\n\n");
		printSymbol(70, ANSI_CYAN + "*");
		print("\t\t        " + header + "           \t\t\t\n");
		printSymbol(70, "*");
		print(ANSI_RESET);
	}

	public static void table(String[] headings, String symbol) {
		print(ANSI_CYAN);
		printSymbol(130, symbol);
		for (String heading : headings) {
			print(heading + "\t\t\t\t\t");
		}
		printSymbol(130, symbol);
		print(ANSI_RESET);
	}

}
