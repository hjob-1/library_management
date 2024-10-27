package com.obsidi.library_management.data_storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.obsidi.library_management.Util.notifyMsg;

public class FileHandler<T> {
	private ObjectMapper objMapper;
	private File file;

	public FileHandler(String filePath) {
		this.objMapper = new ObjectMapper();
		this.file = new File(filePath);

	}

	public ArrayList<T> getAll() {
		ArrayList<T> list = null;
		try {

			list = objMapper.readValue(file, new TypeReference<ArrayList<T>>() {
			});

		} catch (FileNotFoundException fne) {
			notifyMsg("error", "File does not exist\n");
		} catch (IOException e) {
			// display the error message
			notifyMsg("error", e.getMessage());
		}
		return list == null ? new ArrayList<T>() : list;
	}

	public void add(T item) {
		List<T> newItem = getAll();
		newItem.add(item);
		save(newItem);
		notifyMsg("sucess", "Succesfully Added to the file\n");

	}

	public void save(List<T> list) {
		try {
			objMapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
		} catch (StreamWriteException e) {
			// display the error message
			notifyMsg("error", e.getMessage());
		} catch (DatabindException e) {
			// display the error message
			notifyMsg("error", e.getMessage());
		} catch (IOException e) {
			// display the error message
			notifyMsg("error", e.getMessage());
		}

	}

}
