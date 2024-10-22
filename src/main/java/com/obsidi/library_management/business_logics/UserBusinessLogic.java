package com.obsidi.library_management.business_logics;

import java.util.List;

import com.obsidi.library_management.data_storage.UserStore;
import com.obsidi.library_management.models.User;

public class UserBusinessLogic extends BaseLogic<User> {

	// Initialize the UserStore to handle user-related data storage
	public UserBusinessLogic() {
		super(new UserStore());
	}

	@Override
	// Validation logic (e.g., checking if user data is correct or required fields
	// are provided)
	public void verify() {
		// TODO: Implement verification logic if needed
		// For example, checking if a user's email is valid or if a password is strong
		// enough
	}

	@Override
	// Add a new user
	public void add(User item) {
		// verify item before adding to the file
		super.add(item); // Calls the add method from the BaseLogic class
	}

	@Override
	// Update a specific field of the user by userId
	public void updateFieldById(String id, String fieldName, String newValue) {
		// Perform necessary validation before updating
		verify(); // Call the verify method if needed
		super.updateFieldById(id, fieldName, newValue); // Calls the updateFieldById method from BaseLogic
	}

	// Search for users based on a specific field (e.g., name or email)
	public List<User> searchByField(String fieldName, String value) {
		// Verify that the correct data types are provided, if necessary
		return super.searchByField(fieldName, value); // Calls the searchByField method from BaseLogic
	}

}