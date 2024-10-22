package com.obsidi.library_management.data_storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidi.library_management.models.User;

public class UserStore extends FileHandler<User> implements ICrudOperation<User> {

	private final String filePathUser = "C:\\Users\\hjob1\\Documents\\workspace-library_management\\library_management\\src\\main\\java\\com\\obsidi\\library_management\\data_storage\\users.json";
	private ObjectMapper objMapper;

	public UserStore() {
		super("users.json");
		this.objMapper = new ObjectMapper();
	}

	@Override
	public ArrayList<User> getAll() {
		ArrayList<User> list = null;
		try {
			list = objMapper.readValue(new File("users.json"), new TypeReference<ArrayList<User>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User get(String id) {
		for (User user : getAll()) {
			if (user.getUserId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void update(String id, User item) {
		List<User> users = getAll();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (user.getUserId().equals(id)) {
				// Update user information
				users.set(i, item);
				break;
			}
		}
		save(users);
	}

	@Override
	public void delete(String id) {
		ArrayList<User> users = getAll();
		// Remove if ID exists
		users.removeIf(user -> user.getUserId().equals(id));
		save(users);
	}

	@Override
	public void updateFieldById(String id, String fieldName, String newValue) {
		List<User> users = getAll();
		for (User user : users) {
			if (user.getUserId().equals(id)) {
				switch (fieldName.toLowerCase()) {
				case "name":
					user.setName(newValue);
					break;
				case "email":
					user.setEmail(newValue);
					break;
				case "password":
					user.setPassword(newValue);
					break;
				default:
					System.out.println("Invalid field name: " + fieldName);
					return;
				}
				System.out.println("User with ID " + id + " updated: " + fieldName + " changed to " + newValue);
				save(users);
				return;
			}
		}
		System.out.println("User with ID " + id + " not found.");
	}

	@Override
	public List<User> searchByField(String fieldName, String value) {
		ArrayList<User> users = getAll();
		List<User> filteredUsers = null;
		switch (fieldName) {
		case "name":
			filteredUsers = users.stream().filter(user -> user.getName().toLowerCase().contains(value.toLowerCase()))
					.toList();
			break;
		case "email":
			filteredUsers = users.stream().filter(user -> user.getEmail().toLowerCase().contains(value.toLowerCase()))
					.toList();
			break;
		default:
			System.out.println("Invalid field name: " + fieldName);
		}
		return filteredUsers;
	}
}
