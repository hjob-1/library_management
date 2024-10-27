package com.obsidi.library_management.jUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.obsidi.library_management.Util;

public class UtilTest {

	@Test
	public void testWithInputTwo() {
		// simulate when user input 2
		ByteArrayInputStream inputTwo = new ByteArrayInputStream("2\n".getBytes());
		Scanner scanner = new Scanner(inputTwo);
		int userEnteredTwo = Util.getValidChoice(1, 5, scanner);
		assertEquals(2, userEnteredTwo, "Should be Equal 2 = 2");

	}

}
