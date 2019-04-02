/**
 * Starts a Hangman game
 * @author Bernard Ang
 */
package features;
import java.util.Scanner;

import configuration.Response;
public class Hangman implements Feature{
	public Hangman(String query)
	{
		parseQuery(query);
	}

	public void parseQuery(String query) {
		

	}
	public Response setResponse()
	{
		
		Scanner input = new Scanner(System.in);
		//Takes in the secret word from the player 1 and assign to secret
		System.out.println("Welcome to HangMan");
		System.out.println("Player 1, Please enter a word. Player 2, Please close your eyes");
		String secret = input.nextLine();
		boolean end = false; // keeps track of end of game
		int chances = 6;    //keeps track of chances left
		//Print empty lines so that Player 2 does not see the word
		for(int i = 0;i <50 ;i++) {
			System.out.println();
		}
		//Prints the partial word
		String partial = createPartialWord(secret);
		System.out.println("The current Partial word is:" + partial);
		//Prints the Hangman 
		System.out.println("The current Hangman picture is:");
		printHangman(chances);

		//Checks and returns the number of chances the Player has to guess, and handles the next action
		while (chances > 0&& !end) {

			System.out.println ("Player 2, you have "+ chances+ " remaining guesses");
			System.out.println("Would you like to guess the secret word or guess a character?");
			System.out.println("Type \"word\" for word, type \"char\" for character");
			String response = input.nextLine();
			if(response.equals("word")) {
				System.out.println("Type your guess for secret word");
				String guess = input.nextLine();
				//Checks if the player guesses the correct word
				if(guess.equals(secret)) {
					System.out.println("You have guessed the entire word. Congratulations");
					end = true;
				}
				else {
					System.out.println("Bad guess. You lose");
					end = true;
				}
			}else if(response.equals("char")) {
				System.out.println("Please type a character");
				System.out.println();
				char cguess = input.nextLine().charAt(0);
				if(secret.indexOf(cguess)>=0) {
					System.out.println("Good, your character appears in the secret word");
					partial  = updatePartialWord(partial,secret,cguess);
					System.out.println("The current Partial word is:"+ partial);
					System.out.println("The current Hangman picture is");
					printHangman(chances);
				}else {
					chances--;
					System.out.println("Oops, that character does not appear in secret word");
					System.out.println("The current Partial Word is:" + partial);
					System.out.println("The current Hangman picture is");
					printHangman(chances);
				}
			}else {
				System.out.println("PLEASE KEY IN EITHER WORD OR CHAR");
			}
			
			//Check if the word is correctly guessed with letters, or if there is no chances to guess left
			if(partial.equals(secret)) {
				System.out.println ("Congratulations you have guessed the word");
				end = true;
				input.close();
			}
			if(chances <= 0) {
				end = true;
				System.out.println ("You have no chances left. You lose");
			}
		}
		String response = "End of game";
		return new Response(response);
	}

	//Creates the partial word with -
	public static String createPartialWord(String secretWord) {
		String partial= "";
		for (int i=0 ;i<secretWord.length();i++) {
			partial += "-";
		}	
		return partial;
	}
	//Replaces the char with the letter keyed in
	public static String replaceChar(String word, char c, int i) {

		String result = word.substring(0,i)+ c + word.substring(i+1);
		return result;
	}
	//Updates the whole word with replacing the - with the correct char
	public static String updatePartialWord(String partial,String secret,char c) {
		for(int i =0 ; i < secret.length(); i++) {
			if(secret.charAt(i) == c) {
				partial = replaceChar(partial, c, i);
			}
		}
		return partial;
	}
	//Prints the Hangman accordingly
	public static void printHangman(int guessLeft) {
		String HEAD = " ";
		String BODY = " ";
		String LEGS = " ";
		String LEFTARM = " ";
		String RIGHTARM = " ";
		System.out.println("_____");
		System.out.println("| |");
		if (guessLeft < 6) {
			HEAD = "()";
		}
		System.out.println("| " + HEAD);
		if (guessLeft < 5) {
			BODY = "||";
		}
		if (guessLeft < 4) {
			LEFTARM = "\\";
		}
		if (guessLeft < 3) {
			RIGHTARM = "/";
		}
		System.out.println("| " + LEFTARM + BODY + RIGHTARM);
		if (guessLeft < 2) {
			LEGS = "/";
		}
		if (guessLeft < 1) {
			LEGS += "\\";
		}
		System.out.println("| " + LEGS);
		System.out.println("|_____\n\n\n\n");
	}

}







