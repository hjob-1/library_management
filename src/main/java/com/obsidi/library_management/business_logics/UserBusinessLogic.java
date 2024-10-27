package com.obsidi.library_management.business_logics;

import java.util.ArrayList;
import java.util.List;
import static com.obsidi.library_management.Util.notifyMsg;
import com.obsidi.library_management.data_storage.UserStore;
import com.obsidi.library_management.models.User;

public class UserBusinessLogic extends BaseLogic<User> {
	private final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	// Initialize the UserStore to handle user-related data storage
	public UserBusinessLogic() {
		super(new UserStore());
	}

	@Override
	// Add a new user
	public void add(User user) {
		// verify item before adding to the file
		try {
			verify(user);
			super.add(user);
		} catch (InvalidInputException e) {
			// display the error message
			notifyMsg("error", e.getMessage());
		}

	}

	@Override
	// update user
	public void update(String id, User user) {
		try {
			verify(user);
			super.update(id, user);
		} catch (InvalidInputException e) {
			// display the error message
			notifyMsg("error", e.getMessage());
		}

	}

	@Override
	// Update a specific field of the user by userId
	public void updateFieldById(String id, String fieldName, String newValue) {
		// Perform necessary validation before updating
		// Call the verify method if needed
		try {
			validateFields(id, fieldName, newValue);
			super.updateFieldById(id, fieldName, newValue);
		} catch (InvalidInputException e) {
			// display error message if validation failed
			notifyMsg("error", e.getMessage());
		}

	}

	// Search for users based on a specific field (e.g., name or email)
	public List<User> searchByField(String fieldName, String value) {

		return super.searchByField(fieldName, value);
	}

	@Override
	// Validation logic (e.g., checking if user data is correct or required fields
	// are provided)
	public void verify(User user) throws InvalidInputException {
		List<String> errorMessages = new ArrayList<>();

		if (user.getName() == null || user.getName().isEmpty()) {
			errorMessages.add("Error: User name can not be null or empty.");
		}
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			errorMessages.add("Error: user password can not be null or empty.");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			errorMessages.add("Error: user Email can not be null or empty.");
		}
		if (user.getName().matches("\\d+")) {
			errorMessages.add("Error: user name can not be number");
		}
		if (user.getName().length() < 2) {
			errorMessages.add("Error: user name should be greater than two letters");
		}
		if (!user.getEmail().matches(emailRegex)) {
			errorMessages.add("Error: Email should be in valid format. e.g doe@gmail.com");
		}
		if (user.getPassword().length() < 5) {
			errorMessages.add("Error: password length should be at least 5 characters");

		}
		if (!errorMessages.isEmpty()) {
			throw new InvalidInputException(String.join("\n\t", errorMessages));
		}

	}

	@Override
	public void validateFields(String id, String fieldName, String newValue) throws InvalidInputException {
		super.validateFields(id, fieldName, newValue);
		if (fieldName.equalsIgnoreCase("email") && !newValue.matches(emailRegex)) {
			throw new InvalidInputException("Error: Email should be in valid format. e.g doe@gmail.com");
		}
		if (fieldName.equalsIgnoreCase("name") && newValue.length() < 2) {
			throw new InvalidInputException("Error: user name should be greater than two letters");
		}

	}

}