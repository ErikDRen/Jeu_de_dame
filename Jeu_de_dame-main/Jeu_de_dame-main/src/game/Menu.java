package game;

import java.io.IOException;

import data.Data;
import utile.Utilitaires;

public class Menu {

	Data d = new Data();
	public void displayMenu() throws IOException {
		boolean gameOn = true;
		System.out.println("!!! WELCOME TO DRAUGHT GAME !!!");
		do {
			chooseNumberOfPlayerMenu();
			System.out.println("Do you want to continue playing (y-n)?");
			if(!chooseToContinueMenu()) {
				gameOn = false;
			}
		}while(gameOn);
	}

	public void chooseNumberOfPlayerMenu() throws IOException {
		boolean appliOn = true;
		int choice = 0;
		do {
			System.out.println("Choose a number of player (btw 1-2) : ");
			choice = Utilitaires.giveInt();

			switch (choice) {
			case 1:
				System.out.println("you choose 1");
				startPlayervsIAGame();
				appliOn = false;
				break;
			case 2:
				System.out.println("you choose 2");
				startPlayervsPlayerGame();
				appliOn = false;
				break;
			default:
				System.out.println("Something is wrong, what did you put ?"
						+ "\nWe only accept number btw 1-2");
				break;
			}
		} while (appliOn);
	}

	private void startPlayervsIAGame() throws IOException {
		System.out.println("Player 1, choose a pseudo : ");
		String player1 = Utilitaires.giveString();
		System.out.println(player1 + "Choose a character to represent your pawn : ");
		String pseudoPlayer1 = Utilitaires.giveString();

		d.setColorPlayer1(pseudoPlayer1.charAt(0));
		d.setColorPlayer2('O');
		
		d.setKingColorPlayer1('#');
		d.setKingColorPlayer2('@');
		
		d.setVsIA(true);
		Game g = new Game(player1, "", d, true);
		
		g.game();
	}

	private void startPlayervsPlayerGame() throws IOException {
		
		System.out.println("Player 1, choose a pseudo : ");
		String player1 = Utilitaires.giveString();
		System.out.println(player1 + " Choose a character to represent your pawn : ");
		String pseudoPlayer1 = Utilitaires.giveString();
		System.out.println("Player 2, choose a pseudo : ");
		String player2 = Utilitaires.giveString();
		String pseudoPlayer2;
		boolean check;
		do{
			System.out.println(player2 + " Choose a character to represent your pawn : ");
			pseudoPlayer2 = Utilitaires.giveString();
			check = !(pseudoPlayer1.contentEquals(pseudoPlayer2));
			if (!check){
				System.out.println("you can't choose the same character as player 1, plz retry ...");
			}
		}while(!check);
		
		d.setColorPlayer1(pseudoPlayer1.charAt(0));
		d.setColorPlayer2(pseudoPlayer2.charAt(0));
		
		d.setKingColorPlayer1('#');
		d.setKingColorPlayer2('@');
		
		Game g = new Game(player1, player2, d);
		
		g.game();
	}
	
	private boolean chooseToContinueMenu() {
		String choice = Utilitaires.giveString();
		switch(choice) {
		case "y":
			return true;
		case "yes":
			return true;
		default:
			return false;
		}
	}
}