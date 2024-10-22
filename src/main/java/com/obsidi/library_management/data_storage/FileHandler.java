package com.obsidi.library_management.data_storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileHandler<T> {
	private final String filePath;
	private ObjectMapper objMapper;
	private List<T> list;

	public FileHandler(String filePath) {
		this.filePath = filePath;
		this.objMapper = new ObjectMapper();
		// this.list = getAll();
	}

	public ArrayList<T> getAll() {
		ArrayList<T> list = null;
		try {
			// check weather the file found or not then access it.
			list = objMapper.readValue(new File(filePath), new TypeReference<ArrayList<T>>() {
			});
			System.out.println("list" + list);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void add(T item) {
		try {
			// when file deleted it will create and error
			List<T> newItem = getAll();
			newItem.add(item);
			objMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter(filePath, false), newItem);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Books written to file successfully!");

	}

	public void save(List<T> list) {
		try {
			objMapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter(filePath, false), list);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<T> getList() {
		return this.list;
	}

	public List<String> loadFromFile() {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.out.println("No existing data found, starting fresh.");
		}
		return lines;
	}

	public void saveToFile(List<String> lines) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
