package com.obsidi.library_management.data_storage;

import java.util.List;

public interface ICrudOperation<Tmodel> {

	public void add(Tmodel item);

	public void update(String id, Tmodel item);

	public void updateFieldById(String id, String fieldName, String newValue);

	public Tmodel get(String id);

	// This will be changed later
	public List<Tmodel> getAll();

	public void delete(String id);

	public List<Tmodel> searchByField(String fieldName, String value);

}
