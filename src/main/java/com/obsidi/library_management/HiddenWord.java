package com.obsidi.library_management;

import java.util.ArrayList;

public class HiddenWord{

    /** Declare any fields (instance variables) **/
     private String hiddenWord;
    /** Declare a constructor */
     public HiddenWord(String hiddenWord){
        this.hiddenWord = hiddenWord.toUpperCase();
     }
    /** Write the getHint method */

//    public String getHint(String guess){
//    	// Change to upperCase 
//        String guessUppperCase = guess.toUpperCase();
//        // Declare a variable that store the hint string 
//        String hint ="";
//        ArrayList<Integer> matchedIndexes  = new  ArrayList<>();
//        
//        // loop through user guessed value
//        for(int i = 0; i < guess.length(); i++){
//            
//        	// check if the current character is found in the hidden word
//            if(hiddenWord.contains(Character.toString(guessUppperCase.charAt(i)))){
//            	// get the index of the hidden character 
//            	System.out.println("index"+matchedIndexes);
//                int indexOfChar = hiddenWord.indexOf(guess.charAt(i));
//                // if the index of the hidden character is equal to the guess index meaning its a match 
//                if(indexOfChar == i && !matchedIndexes.contains(i)){
//                	// append the char to the hint string
//                    hint += guessUppperCase.charAt(i);
//                    matchedIndexes.add(i);
//                }
//                else{
//                	// The character is found but in different position so append +
//                    hint += "+";
//                }
//            }
//            // If the character is not found in the hidden word append * to the hint string
//            else{
//            	
//                hint += "*";
//            }
//
//        }
//        return hint;
//
//    }
//     
     
     public String getHint(String guess) {
         // Convert guess to upperCase
         String guessUpperCase = guess.toUpperCase();
         StringBuilder hint = new StringBuilder();  
        
         for (int i = 0; i < guessUpperCase.length(); i++) {
             char currentChar = guessUpperCase.charAt(i);
             // Exact match
             if (hiddenWord.charAt(i) == currentChar) { 
            	// Append the exact match character
                 hint.append(currentChar);  
               
             } else {
            	// reserve empty space for later processing
                 hint.append(" ");  
             }
         }

         // Check for correct letters in wrong positions
         for (int i = 0; i < guessUpperCase.length(); i++) {
        	// Only check if no exact match was found
             if (hint.charAt(i) == ' ') {  
                 char currentChar = guessUpperCase.charAt(i);

                 // Search for the character in hiddenWord at unused positions
                 boolean found = false;
                 for (int j = 0; j < hiddenWord.length(); j++) {
                     if (hiddenWord.charAt(j) == currentChar) {
                    	// letter found but in the wrong position 
                         hint.setCharAt(i, '+');  
                         found = true;
                         break;
                     }
                 }
              // letter not found 
                 if (!found) {
                     hint.setCharAt(i, '*');  
                 }
             }
         }
         return hint.toString();
     }
     
     
    /** This is a main method for testing the class */
    public static void main(String[] args) {
        HiddenWord puzzle = new HiddenWord("CSAWESOME");
        System.out.println(puzzle.getHint("AAAAA") + " it should print +A+++");
        System.out.println(puzzle.getHint("HELLO") + " it should print H****");
        System.out.println(puzzle.getHint("HEART") + " it should print H*++*");
        System.out.println(puzzle.getHint("HARMS") + " it should print HAR*S");
        System.out.println(puzzle.getHint("HARPS") + " it should print HARPS");
        System.out.println(puzzle.getHint("CSCSCSZZZ") + " it should print CS+++S***");
        System.out.println(puzzle.getHint("EZZZZSOME") + " it should print +****[SOME]");

    } // end of main

} // end of class
