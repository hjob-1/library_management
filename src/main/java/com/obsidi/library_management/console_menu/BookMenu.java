package com.obsidi.library_management.console_menu;

import java.util.Scanner;

import com.obsidi.library_management.business_logics.BookBussinesLogic;
import com.obsidi.library_management.models.Book;

public class BookMenu {
	
	private BookBussinesLogic bookLogic;
	public  BookMenu() {
		this.bookLogic = new BookBussinesLogic();
	}
	
	public  void dashSymbol(int num) {
		while(num >=1 ) {
			
			System.out.print("_");
			num--;
		}
		System.out.println();
	}

	public  void bookMenu() {
		Scanner scanner = new Scanner(System.in);
        String[] options = {"1. Add a New Book","2. Remove an Existing Book", "3. Update Book Information","4. Search for a Book","5. View All Books","6. Go back to Main Menu","0 Exit"};
        int choice;
        
        System.out.println("\n\t\tWelcome To \uD83D\uDCD5 Book Management\t\t\n");
		for(String option:options) {
			System.out.println("\t"+option);
		}
		System.out.print("\n\tPlease choose an option (0-6): ");
		choice = scanner.nextInt();
		
		switch(choice) {
		case 1:
			addBook();
			break;
		case 2:
			deleteBook();
			break;
		case 3:
			updateBook();
			break;
		case 4:
			searchBook();
			break;
		case 5:
			viewAllBook();
			break;
		case 6: 
			System.out.print("\t selection 6");
			break;
		default:
			break;
		}
		
	}

public  void viewAllBook() {
	    
		
		// add different color for the heading 
		// add different color for available or not (red or green)
		System.out.println("All books");
		dashSymbol(110);
		System.out.println("Id\t\t\t title\t\t\t\t\t  author\t\t   \tavailable");
		for(Book book: bookLogic.getAll()) {
			dashSymbol(110);
			System.out.println(book.getId()+" \t\t\t "+book.getTitle()+"\t\t\t "+book.getAuthor() +"\t\t\t "+book.isAvailable());
		}
		dashSymbol(110);
		
		
		
	}

	public  void searchBook() {
		Scanner scanner = new Scanner(System.in);
		String id;
		System.out.println("please enter book ID.");
		id = scanner.nextLine();
		// what if the user enters a non existed ID
		// What if they enter wrong data type
		// display a message based on the abover criteria use red and green color for success or failuer
		System.out.println("search by id found"+id);
		
		
		
	}

	public  void deleteBook() {
		Scanner scanner = new Scanner(System.in); 
		String id;
		System.out.println("Please Enter the Book Id you want to delete?");
		id = scanner.nextLine();
		bookLogic.delete(id);
		// delete the book
		// show a message if there is no book available display an error message
		// ask them if they want to go back to the main menu or book page
		
		System.out.println("book deleted");
		
		
	}

	public  void updateBook() {
		Scanner scanner = new Scanner(System.in);
		int choice;
		
		System.out.println("What do you want to update?");
		System.out.println("1. Book's Title");
		System.out.println("2. Book's Author");
		
		choice = scanner.nextInt();
		
		switch(choice) {
		case 1:
			String title;
			String id;
			System.out.println("Enter books ID");
			id = scanner.nextLine();
			scanner.next();
			System.out.println("Enter books Title");
			title = scanner.next();
			
			System.out.println("Your book is updated");
			System.out.println("updated book "+id+"  "+title);
			break;
		case 2:
			String author;
			String id1;
			System.out.println("Enter books ID");
			id1 = scanner.nextLine();
			scanner.next();
			System.out.println("Enter books Title");
			author = scanner.next();
			
			System.out.println("Your book is updated");
			System.out.println("updated book "+id1+"  "+author);
			break;
			
			
		}
		
	}

	public  void addBook() {
		Scanner scanner = new Scanner(System.in);
		String title;
		String author;
		System.out.println("Please Enter book title");
		title = scanner.nextLine();
		System.out.println("Please Enter book's Author");
		author = scanner.nextLine();
		
		
		bookLogic.add(new Book(title,author));
		System.out.println("Successfully added your book");
		System.out.println(title+" by"+author);

}
	}
