package TicTacToeGame;


/*****************************************************************
 * Program: TicTacToe 
 * Description: This program allows the user to play a game of
 *				Tic Tac Toe.  The main entry point is the play()
 *				method, which begins the game.  Several private
 *				utility methods are used to facility the game
 *				functionality.
 *
 ******************************************************************/

import java.util.Scanner;

public class TicTacToe1  {

	/*
	 * Declare instance variables
	 */
	 
	private char[][] board; //the game board, a 2D array
	private boolean xTurn; //true if X's turn, false if O's turn
	private Scanner keyboard; //Scanner for reading things in from keyboard
	
	/*
	 * Constructor, sets things up for the game to run
	 */
	
	public TicTacToe1()  {
	
		//create the board
		board = new char[3][3];
			
		//initialize the board to all spaces
		for(int r = 0; r < 3; r++)  {
			
			for(int c = 0; c < 3; c++)
				board[r][c] = ' ';
		}
		
		//it's X's turn when we start
		xTurn = true;
		
		//create our keyboard object
		keyboard = new Scanner(System.in);
	}
	
	/*
	 * Displays a single row of the game board specified by the row parameter
	 */
	
	private void displayRow(int row)  {
	
		System.out.println(" " + board[row][0] + " | " + board[row][1] + " | " + board[row][2]);
	}
	
	/*
	 * Displays the entire game board
	 */
	
	private void displayBoard()  {
	
		displayRow(0);
		System.out.println("-----------");
		displayRow(1);
		System.out.println("-----------");
		displayRow(2);
	}
	
	/*
	 * Displays the basic menu options available to the user
	 */
	
	private void displayMenu()  {
	
		//print whose turn it is
		if(xTurn)
			System.out.println("X's Turn!");
		else
			System.out.println("O's Turn!");
			
		//print the options menu			
		System.out.println("What would you like to do?");
		System.out.println("1: Make a move");
		System.out.println("2: Start Over");
		System.out.println("3: Quit");
		System.out.print("Choice: ");
	}
	
	/*
	 * Gets the position from the user of where the next move should be made
	 * The board is then updated with a valid move
	 *
	 * Returns true if there is a winner and false if there is no winner
	 * 
	 */
	
	private boolean getMove()  {
	
		boolean invalid = true;
		int row = 0, column = 0;
		
		//keep asking for a position until the user enters a valid one
		while(invalid)  {
	
			System.out.println("Which row, column would you like to move to? Enter two numbers between 0-2 separated by a space to indicate position.");
			row = keyboard.nextInt();
			column = keyboard.nextInt();
			
			//check that the position is within bounds
			if(row >= 0 && row <= 2 && column >= 0 && column <= 2)  {
			
				//check that the position is not already occupied
				if(board[row][column] != ' ')
					System.out.println("That position is already taken");
				else
					invalid = false;
			}
			else
				System.out.println("Invalid position");
		}
		
		//fill in the game board with the valid position
		if(xTurn)
			board[row][column] = 'X';
		else
			board[row][column] = 'O';
		
		return winner(row,column);
	}
	
	/*
	 * Starts the game over by resetting variables
	 */
	
	private void restart()  {
	
		//empty the game board
		for(int r = 0; r < 3; r++)  {
			
			for(int c = 0; c < 3; c++)
				board[r][c] = ' ';
		}

			
		//reset whose turn it is
		xTurn = true;
	}
	
	/*
	 * Given the row and column where the last move was made, this method
	 * return true if the move resulted in a win and false otherwise
	 */
	
	private boolean winner(int lastR, int lastC)  {
	
		boolean winner = false; //assume there's no winner
		char symbol = board[lastR][lastC]; //the symbol for the last move either X or O
		
		//check left-right
		//the last move we made was in the row lastR, check that row for three of the same symbol
		int numFound = 0;
		for(int c = 0; c < 3; c++)  {
			if(board[lastR][c] == symbol)
				numFound++;
		}
		
		if(numFound == 3)
			winner = true;
	
		//check up-down
		//the last move we made was in the column lastC, check that column for three of the same symbol
		numFound = 0;
		for(int r = 0; r < 3; r++)  {
			if(board[r][lastC] == symbol)
				numFound++;
		}
		
		if(numFound == 3)
			winner = true;

		//check both diagonals
		numFound = 0;
		for(int i = 0; i < 3; i++)  {
			if(board[i][i] == symbol)
				numFound++;
		}
		
		if(numFound == 3)
			winner = true;
		
		numFound = 0;
		for(int i = 0; i < 3; i++)  {
			if(board[i][2-i] == symbol)
				numFound++;
		}

		if(numFound == 3)
			winner = true;
			
		return winner;
	}
	
	/*
	 * Checks whether the board is full and the game is over
	 *
	 * Return true if full and false otherwise
	 */
	
	private boolean boardFull()  {
	
		//find the number of spots that are occupied by either an X or an O
		int numSpotsFilled = 0;
		
		for(int r = 0; r < 3; r++)  {
			
			for(int c = 0; c < 3; c++)  {
				if(board[r][c] == 'X' || board[r][c] == 'O')
					numSpotsFilled++;
			}
		}
		
		return numSpotsFilled == 9;
	}
	
	/*
	 * Main entry point to the game.  Starts the game.
	 */
	
	public void play()  {
	
		while(true)  {
		
			displayBoard();
			displayMenu();
			
			int choice = keyboard.nextInt();
		
			if(choice == 1)  {
			
				if(getMove())  {
					//we have a winner!
					displayBoard();	//display board one last time
					
					if(xTurn)
						System.out.println("X Wins!");
					else
						System.out.println("O Wins!");
						
					System.exit(0);
				}
				else if(boardFull())  {
					//we have a draw
					displayBoard(); //display board one last time
					
					System.out.println("Draw!");
					
					System.exit(0);
				}
				else 
				{
					//no winner yet
					xTurn = !xTurn;  //switch whose turn it is
				}
			}
			else if(choice == 2)
				restart();
			else if(choice == 3)
				System.exit(0);	//quit
			else
				System.out.println("Invalid Option");
		}	
	}
	
	public static void main(String[] args)  {
	
		TicTacToe1 game = new TicTacToe1();
		
		game.play();
		
		
	}
}