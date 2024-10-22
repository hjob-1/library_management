package com.obsidi.library_management.console_menu;

import java.util.List;
import java.util.Scanner;

import com.obsidi.library_management.business_logics.UserBusinessLogic;
import com.obsidi.library_management.models.User;

public class UserMenu {

	private UserBusinessLogic userLogic;
	private Scanner scanner;
	private final String[] options = { "1. Add a New User", "2. Remove an Existing User", "3. Update User Information",
			"4. Search for a User", "5. View All Users", "6. Go back to Main Menu", "0 Exit" };

	public UserMenu() {
		this.userLogic = new UserBusinessLogic();
		this.scanner = new Scanner(System.in);
	}

	public void dashSymbol(int num) {
		while (num >= 1) {
			System.out.print("_");
			num--;
		}
		System.out.println();
	}

	public void userMenu() {
		int choice;
		System.out.println("\n\t\tWelcome To \uD83D\uDCD5 User Management\t\t\n");
		for (String option : options) {
			System.out.println("\t" + option);
		}
		System.out.print("\n\tPlease choose an option (0-6): ");
		choice = scanner.nextInt();

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
			System.out.print("\t selection 6");
			break;
		default:
			break;
		}
	}

	public void viewAllUsers() {
		System.out.println("All users");
		dashSymbol(130);
		System.out.println("Id\t\t\t\t\t name\t\t\t\t\t  email");
		for (User user : userLogic.getAll()) {
			dashSymbol(130);
			System.out.println(user.getUserId() + " \t " + user.getName() + "\t\t\t " + user.getEmail());
		}
		dashSymbol(130);
	}

	public void searchUser() {
		Scanner scanner = new Scanner(System.in);
		int choice;
		List<User> filtered = null;

		System.out.println("Choose search criteria:");
		System.out.println("1. Search by Name");
		System.out.println("2. Search by Email");

		choice = scanner.nextInt();
		scanner.nextLine();
		switch (choice) {
		case 1:
			System.out.println("Start typing ...");
			String name = scanner.nextLine();
			filtered = userLogic.searchByField("name", name);
			break;
		case 2:
			System.out.println("Start typing ...");
			String email = scanner.nextLine();
			filtered = userLogic.searchByField("email", email);
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
		}

		if (filtered.isEmpty()) {
			System.out.println("The user you are looking for is not in our system.");
		} else {
			dashSymbol(130);
			System.out.println("Id\t\t\t\t\t name\t\t\t\t\t  email");
			for (User user : filtered) {
				dashSymbol(130);
				System.out.println(user.getUserId() + " \t " + user.getName() + "\t\t\t " + user.getEmail());
			}
			dashSymbol(130);
		}
	}

	public void deleteUser() {
		Scanner scanner = new Scanner(System.in);
		String id;
		System.out.println("Please Enter the User Id you want to delete?");
		id = scanner.nextLine();
		userLogic.delete(id);
		System.out.println("User deleted");
	}

	public void updateUser() {
		try (Scanner scanner = new Scanner(System.in)) {
			int choice;

			String id;
			String name;
			String email;

			System.out.println("Please choose the field you would like to update:");
			System.out.println("1. Update User Name");
			System.out.println("2. Update User Email");
			System.out.print("Enter the number corresponding to your choice => ");

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Please enter the user's ID:");
				id = scanner.nextLine().trim();

				System.out.println("Please enter the new user name:");
				name = scanner.nextLine().trim();

				userLogic.updateFieldById(id, "name", name);
				System.out.println("The user has been successfully updated.");
				System.out.println("Updated User - ID: " + id + ", New Name: " + name);
				break;
			case 2:
				System.out.println("Please enter the user's ID:");
				id = scanner.nextLine().trim();

				System.out.println("Please enter the new user email:");
				email = scanner.nextLine().trim();

				userLogic.updateFieldById(id, "email", email);
				System.out.println("The user has been successfully updated.");
				System.out.println("Updated User - ID: " + id + ", New Email: " + email);
				break;
			}
		}
	}

	public void addUser() {
		Scanner scanner = new Scanner(System.in);
		String name;
		String email;
		String password;
		System.out.println("Please Enter user name");
		name = scanner.nextLine();
		System.out.println("Please Enter user's Email");
		email = scanner.nextLine();
		System.out.println("Please Enter user's Password");
		password = scanner.nextLine();

		userLogic.add(new User(name, email, password));
		System.out.println("Successfully added your user");
		System.out.println(name + " with email: " + email);
	}

	private int getValidChoice(int min, int max) {
		int choice = 0;
		while (choice < min || choice > max) {
			System.out.println("Enter a number between " + min + " and " + max + ":");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		}
		return choice;
	}
}
