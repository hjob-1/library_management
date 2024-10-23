package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Scanner;

import static com.obsidi.library_management.Util.*;
import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.Book;
import com.obsidi.library_management.models.User;

public class UserMenu {
	private final static String TITLE = "Welcome To \uD83D\uDCD5 User Management";

	private final String[] OPTIONS = { "[1] Add a New User", "[2] Remove an Existing User",
			"[3] Update User Information", "[4] Search for a User", "[5] View All Users", "[6] Go back to Main Menu",
			ANSI_RED + "[0] Exit" };

	private UserBusinessLogic userLogic;
	private Scanner scanner;

	public UserMenu() {
		this.userLogic = new UserBusinessLogic();
		this.scanner = new Scanner(System.in);
	}

	public void userMenu() {
		int choice;
		displayHeader(TITLE, "");
		displayMenu(OPTIONS);
		print("\n\tEnter your choice (0-6) and press Enter to continue:  ");
		choice = scanner.nextInt();
		scanner.nextLine();
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
			print("\t selection 6");
			break;
		default:
			break;
		}
	}

	public void viewAllUsers() {
		List<User> users = userLogic.getAll();
		print(ANSI_CYAN + "\nLIST OF ALL USERS\n");
		table(new String[] { "ID", "NAME", "EMAIL" }, "_");
		displayUSerTableContent(users);

	}

	public void searchUser() {
		int choice;
		List<User> filtered = null;
		displaySubHeader("SEARCH FOR USERS");
		print("\n\tChoose search criteria:\n");
		displayMenu(new String[] { "[1] Search by Name", "[2] Search by Email" });

		print("\n\tEnter the number corresponding to your choice: ");
		choice = scanner.nextInt();
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
			print("The user you are looking for is not in our system.");
		} else {
			table(new String[] { "ID", "NAME", "EMAIL" }, "_");
			displayUSerTableContent(filtered);

		}
	}

	public void deleteUser() {
		String id;
		displaySubHeader("DELETE A USER");
		print("\n\t>> Please Enter the User Id you want to delete?");
		id = scanner.nextLine();
		// do validation
		userLogic.delete(id);
		// show message
		print("User deleted");
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

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				print(ANSI_CYAN + "\n\t>> Please Enter the user ID: ");
				id = scanner.nextLine().trim();

				print("\n\t>> Please Enter the new user name: ");
				name = scanner.nextLine().trim();
				// do validtion
				userLogic.updateFieldById(id, "name", name);
				print("The user has been successfully updated.");
				print("Updated User - ID: " + id + ", New Name: " + name);
				break;
			case 2:
				print("\n\t>> Please Enter the user ID: ");
				id = scanner.nextLine().trim();

				print("\n\t>> Please enter the new user email: ");
				email = scanner.nextLine().trim();
				// do validtion
				userLogic.updateFieldById(id, "email", email);
				print("The user has been successfully updated.");
				print("Updated User - ID: " + id + ", New Email: " + email);
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
		print("Successfully added your user");
		print(name + " with email: " + email);
	}

	private int getValidChoice(int min, int max) {
		int choice = 0;
		while (choice < min || choice > max) {
			print("Enter a number between " + min + " and " + max + ":");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				print("Invalid input. Please enter a valid number.");
			}
		}
		return choice;
	}

	public void displayUSerTableContent(List<User> users) {
		printSymbol(130, "_");
		for (User user : users) {
			System.out.println(user.getUserId() + " \t " + user.getName() + "\t\t\t " + user.getEmail());

		}

	}
}
