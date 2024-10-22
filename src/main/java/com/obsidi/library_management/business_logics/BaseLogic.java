package com.obsidi.library_management.business_logics;

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

	public abstract void verify();

	public ICrudOperation<T> getRepository() {
		return this.repository;
	}

}
