package com.obsidi.library_management.business_logics;

import java.util.ArrayList;
import java.util.List;

import com.obsidi.library_management.data_storage.ICrudOperation;

public abstract class BaseLogic<T> implements ICrudOperation<T> {

	// Create an instance variable that can hold repostitory value
	private ICrudOperation<T> repository;

	public BaseLogic(ICrudOperation<T> repository) {
		this.repository = repository;
	}

	@Override
	public void add(T item) {
		repository.add(item);

	}

	@Override
	public void update(String id, T item) {
		repository.update(id, item);
	}

	public void updateFieldById(String id, String fieldName, String newValue) {
		repository.updateFieldById(id, fieldName, newValue);

	}

	@Override
	public T get(String id) {
		// TODO Auto-generated method stub
		return repository.get(id);
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return repository.getAll();
	}

	public List<T> searchByField(String fieldName, String value) {

		return repository.searchByField(fieldName, value);
	}

	@Override
	public void delete(String id) {
		repository.delete(id);

	}

	public void validateFields(String id, String fieldName, String newValue) throws InvalidInputException {
		List<String> errorMessages = new ArrayList<>();
		if (id == null || id.isEmpty()) {
			errorMessages.add("Error: ID can not be null or empty.");
		}
		if (newValue == null || newValue.isEmpty()) {
			errorMessages.add("Error: " + fieldName + " can not be null or empty.");
		}
		if (id.matches("\\d+")) {
			errorMessages.add("Error: Id can not be number");
		}
		if (newValue.matches("\\d+")) {
			errorMessages.add("Error: " + fieldName + " can not be number");
		}
		if (!errorMessages.isEmpty()) {
			throw new InvalidInputException(String.join("\n\t", errorMessages));
		}
	}

	public abstract void verify(T item) throws InvalidInputException;

	public ICrudOperation<T> getRepository() {
		return this.repository;
	}

}
