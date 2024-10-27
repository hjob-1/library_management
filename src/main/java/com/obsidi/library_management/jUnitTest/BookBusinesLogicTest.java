package com.obsidi.library_management.jUnitTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.obsidi.library_management.Util;
import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.models.Book;

public class BookBusinesLogicTest {
	// To capture the console output
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	private BookBussinesLogic bookLogic;

	@BeforeEach
	public void setUp() {
		this.bookLogic = new BookBussinesLogic();
		System.setOut(new PrintStream(outContent));
	}

	@Test
	public void addBook() {
		// Valid book
		Book validBook = new Book("Title test 1", "Author 2");
		bookLogic.add(validBook);
		String expectedOutPut = Util.ANSI_GREEN + "\n\tSuccesfully Added to the file\n" + Util.ANSI_RESET;
		assertEquals(expectedOutPut.trim(), outContent.toString().trim(), "Expected =>" + expectedOutPut.trim());
	}

	@Test
	void testAddInvalidBook() {
		Book inValidBook = new Book("", "");
		String ExpectdResult = Util.ANSI_RED + "\n\t" + "Error: Title can not be null or empty.\n"
				+ "\tError: Title should be greater than 2 characters\n" + "\tError: Author can not be null or empty.\n"
				+ "\tError: Author should be greater than 2 characters" + Util.ANSI_RESET;

		bookLogic.add(inValidBook);

		// Check if the exception message is correct
		assertEquals(ExpectdResult.trim(), outContent.toString().trim(), "Expected =>" + outContent.toString().trim());

	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
	}

}
