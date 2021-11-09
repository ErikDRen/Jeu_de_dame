package game;

import java.io.IOException;

import utile.Utilitaires;

public class Menu {

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
				appliOn = false;
				break;
			case 2:
				System.out.println("you choose 2");
				startPlayervsPlayerGame();
				appliOn = false;
				break;
			default:
				System.out.println("Something is wrong, what did you put ??"
						+ "\nWe only accept Int btw 1-2");
				break;
			}
		} while (appliOn);
	}

	private void startPlayervsPlayerGame() throws IOException {
		
		System.out.println("Joueur 1, choisissez un pseudo : ");
		String player1 = Utilitaires.giveString();
		System.out.println("Joueur 2, choisissez un pseudo : ");
		String player2 = Utilitaires.giveString();
		Game g = new Game(player1, player2);
		
		g.game();
	}
	
	private void chooseAPseudo() {
		String choice = Utilitaires.giveString();

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