package com.obsidi.library_management.console_menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.obsidi.library_management.Util.*;

import com.obsidi.library_management.LibraryManagementSystem;
import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.User;

public class UserMenu {
	private static final Scanner scanner = new Scanner(System.in); // Static scanner instance

	private final static String TITLE = "Welcome To \uD83D\uDCD5 User Management";

	private final String[] OPTIONS = { "[1] Add a New User", "[2] Remove an Existing User",
			"[3] Update User Information", "[4] Search for a User", "[5] View All Users", "[6] Go back to Main Menu", };

	private UserBusinessLogic userLogic;

	public UserMenu() {
		this.userLogic = new UserBusinessLogic();

	}

	public void userMenu() {
		int choice;
		try {
			do {
				print("\n");
				displayHeader(TITLE, "");
				displayMenu(OPTIONS);
				choice = getValidChoice(1, 6, scanner);

				switch (choice) {
				case 1:
					addUser();
					break;
				case 2:
					deleteUser();
					break;
				case 3:
					updateUser();
					break;
				case 4:
					searchUser();
					break;
				case 5:
					viewAllUsers();
					break;
				case 6:
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

	public void viewAllUsers() {
		List<User> users = userLogic.getAll();
		// file does not existed / file deleted
		if (users == null) {
			return;
		} else if (users.isEmpty()) { // file empty
			print("\n\tThere is no user registered yet.");
		} else { // display list of the users if users list is not empty
			print(ANSI_CYAN + "\nLIST OF ALL USERS\n");
			printDynamicUserTable(new ArrayList<>(Arrays.asList("User ID", "NAME", "EMAIL", "password")), users);
		}

	}

	public void searchUser() {
		int choice;
		List<User> filtered = null;
		displaySubHeader("SEARCH FOR USERS");
		print("\n\tChoose search criteria:\n");
		displayMenu(new String[] { "[1] Search by Name", "[2] Search by Email" });

		choice = getValidChoice(1, 2, scanner);
		scanner.nextLine();
		switch (choice) {
		case 1:
			print("\tEnter user name:  ");
			String name = scanner.nextLine();
			filtered = userLogic.searchByField("name", name);
			break;
		case 2:
			print("\tEnter user email: ");
			String email = scanner.nextLine();
			filtered = userLogic.searchByField("email", email);
			break;
		default:
			print("Invalid choice. Please try again.");
		}

		if (filtered.isEmpty()) {
			print("\n\tThe user you are looking for is not in our system.");
		} else {

			printDynamicUserTable(new ArrayList<>(Arrays.asList("User ID", "NAME", "EMAIL", "password")), filtered);

		}
	}

	public void deleteUser() {
		String id;
		displaySubHeader("DELETE A USER");
		print("\n\t>> Please Enter the User Id you want to delete?");
		id = scanner.nextLine();
		// do validation
		userLogic.delete(id);
	}

	public void updateUser() {
		try (Scanner scanner = new Scanner(System.in)) {
			int choice;

			String id;
			String name;
			String email;
			displaySubHeader("UPDATE A USER");

			print("\n\tPlease choose the field you would like to update: \n");
			displayMenu(new String[] { "[1] Update user name", "[2] Update user email" });
			print("\n\tEnter the number corresponding to your choice => ");

			choice = getValidChoice(1, 2, scanner);

			switch (choice) {
			case 1:
				print(ANSI_CYAN + "\n\t>> Please Enter the user ID: ");
				id = scanner.nextLine().trim();

				print("\n\t>> Please Enter the new user name: ");
				name = scanner.nextLine().trim();
				// do validation
				userLogic.updateFieldById(id, "name", name);
				break;
			case 2:
				print("\n\t>> Please Enter the user ID: ");
				id = scanner.nextLine().trim();

				print("\n\t>> Please enter the new user email: ");
				email = scanner.nextLine().trim();
				// do validation
				userLogic.updateFieldById(id, "email", email);
				break;
			}
		}
	}

	public void addUser() {
		String name;
		String email;
		String password;
		displaySubHeader("ADD A NEW USER");
		print("\n\t>> Please Enter user name =>  ");
		name = scanner.nextLine();
		print("\n\t>> Please Enter user Email => ");
		email = scanner.nextLine();
		print("\n\t>> Please Enter user Password => ");
		password = scanner.nextLine();

		// do validation
		userLogic.add(new User(name, email, password));
	}

	public void displayUSerTableContent(List<User> users) {

		for (User user : users) {
			print("\n" + user.getName() + "\t\t\t\t\t" + user.getEmail() + "\t\t\t " + user.getPassword() + ANSI_RESET
					+ "\n");
			printSymbol(130, "_");
		}

	}
}
