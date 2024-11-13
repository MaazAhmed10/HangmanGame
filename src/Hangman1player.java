import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman1player {

    public static void main(String[] args) throws FileNotFoundException {
        
        // Import the file using (new File(path))
        Scanner scanner = new Scanner(new File("C:\\Users\\mehfo\\OneDrive\\Documents\\wordsForHangman.txt"));
        Scanner keyboard = new Scanner(System.in);
        
        // Create a list and put all the words in it
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.nextLine());
        }
        
        Random rand = new Random();
        
        // Get a random word from the list "words" where the index of the word is random between 0 to size of that list
        String word = words.get(rand.nextInt(words.size())).toLowerCase();  // Make the word lowercase
        
        System.out.println(word);  // For testing purposes
        
        // List to store all the input characters given by the player
        List<Character> playerGuesses = new ArrayList<>();
        
        int wrongCount = 0;  // Counter for wrong guesses
        while (wrongCount < 6) {  // Limit wrong guesses to 6 chances
            
            printHangedMan(wrongCount);  // Print hangman figure based on wrong guesses
            
            // Consolidated call to print the word's state and check if all letters have been guessed
            boolean allLettersGuessed = printWordState(word, playerGuesses); 
            
            if (allLettersGuessed) {  // Check if player has guessed the entire word
                System.out.println("You Win!");
                break;
            }
            
            // If the guessed letter is not part of the word, increment wrongCount
            if (!getPlayerGuess(keyboard, word, playerGuesses)) {
                wrongCount++;
            }
            
            // Ask player to guess the entire word
            System.out.println("Please enter your guess for the word:");
            if (keyboard.nextLine().toLowerCase().equals(word)) {  // Case-insensitive word guess
                System.out.println("You Win!");
                break;
            } else {
                System.out.println("Nope! Try again.");
            }
        }

        if (wrongCount == 6) {
            System.out.println("You lose! The word was: " + word);  // Player loses after 6 wrong guesses
        }
    }

    // Method to print the hangman figure based on the wrong count
    private static void printHangedMan(int wrongCount) {
        System.out.println(" ----------");
        System.out.println(" |        |");
        
        if (wrongCount >= 1) {
            System.out.println(" O");  // Head
        } else {
            System.out.println();  // Empty line if no wrong guess yet
        }

        if (wrongCount >= 2) {
            if (wrongCount == 2) {
                System.out.println(" |");  // Body only
            } else if (wrongCount >= 3) {
                System.out.println("\\|/");  // Body with both arms
            }
        } else {
            System.out.println();  // Empty line if no body yet
        }

        if (wrongCount >= 4) {
            System.out.println(" |");  // Body
        } else {
            System.out.println();  // Empty line if no body yet
        }

        if (wrongCount == 5) {
            System.out.println("/ ");  // Left leg only
        } else if (wrongCount >= 6) {
            System.out.println("/ \\");  // Both legs
        } else {
            System.out.println();  // Empty line if no legs yet
        }
    }

    // Method to get the player's guess and update the list of guessed letters
    private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses) {
        System.out.println("Please enter a letter:");
        String letterGuess = keyboard.nextLine().toLowerCase();  // Convert to lowercase for case insensitivity
        
        // Validate that the input is a single letter
        if (letterGuess.length() != 1) {
            System.out.println("Please enter a single letter.");
            return true;  // No penalty for incorrect input
        }
        
        char guess = letterGuess.charAt(0);
        
        // Check if the letter was already guessed
        if (playerGuesses.contains(guess)) {
            System.out.println("You already guessed that letter.");
            return true;  // No penalty for repeating guesses
        }
        
        // Add the guessed letter to the player's guesses list
        playerGuesses.add(guess);
        
        // Return true if the guessed letter is part of the word, false otherwise
        return word.contains(letterGuess);
    }

    // Method to print the current state of the word with guessed and missing letters
    private static boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        
        // Loop through each character in the word and check if it was guessed
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctCount++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();
        
        // Return true if all the letters have been guessed
        return correctCount == word.length();
    }

}
