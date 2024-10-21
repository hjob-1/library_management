package com.obsidi.library_management;

import java.util.Scanner;

import com.obsidi.library_management.console_menu.BookMenu;

public class LibraryManagementSystem {
	
	public static void hashSymbol(int number) {
		while(number >= 1) {
			System.out.print("#");
			number--;
		}
		System.out.println();
		
	}
	
	public static void mainOptions() {
		String[] options = {"1. \uD83D\uDCD5 Book Management","2. \uD83D\uDC64 User Management", "3. \uD83D\uDCD6 Borrowing & Returning Books","4. \u23F3 Overdue Books Management","5. \uD83D\uDCDD Report Generation","6. \uD83D\uDEAA Exit"};
		
		for(String option:options) {
			System.out.println("\t"+option);
		}
	}
	
	
		public static void main(String[] args) {
		BookMenu bookOptions = new BookMenu(); 
		Scanner scanner = new Scanner(System.in);
		int choice;
		hashSymbol(100);
		System.out.println("\t\t\tWelcome to the Library Management System\t\t");
		hashSymbol(100);
		mainOptions();
		
		System.out.print("\n\tPlease choose an option (1-6): ");
		choice = scanner.nextInt();
		
		switch(choice) {
		case 1:
			bookOptions.bookMenu();
			break;
		case 2:
			System.out.print("\t selection 2");
			break;
		case 3:
			System.out.print("\t selection 3");
			break;
		case 4:
			System.out.print("\t selection 4");
			break;
		case 5:
			System.out.print("\t selection 5");
			break;
		case 6: 
			System.out.print("\t selection 6");
			break;
		default:
			break;
		}
		
		
		

	}

}