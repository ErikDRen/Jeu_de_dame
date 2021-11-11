package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Piece;
import utile.Utilitaires;

public class Game {

	String player1 = "";
	String player2 = "";

	String fileNameP1 = "";
	String fileNameP2 = "";
	int nbPiecesO = 20;
	int nbPiecesX = 20;
	int sizeX = 12;
	int sizeY = 12;
	char[][] tabMap;
	//final static String FILENAME = "./history.txt";
	boolean gameOn = true;
	ArrayList<Piece> alPieces = new ArrayList<Piece>();
	
	public Game(String p1, String p2) {
		super();
		this.player1 = p1;
		this.player2 = p2;
	}
	
	public void game() throws IOException {
		initFile();
		tabMap = new char[sizeX][sizeY];
		createPieceO();
		createPieceX();
		do {
			fillTab(tabMap, alPieces);
			printTab(tabMap, sizeY, sizeX);
			selectPieceToMove(tabMap, alPieces);
			Utilitaires.saveTab(tabMap, fileNameP1);
			Utilitaires.saveTab(tabMap, fileNameP2);
		} while (gameOn);
	}
	
	private void initFile() throws IOException {
		System.out.println("player 1 = " + player1 + "\tplayer2 = " + player2);
//takes the date for the history
		String dateNow = Utilitaires.giveDate();
// Create a folder if there is no folder for the player to save their game.
		Utilitaires.createFolderForUser(player1);
		Utilitaires.createFolderForUser(player2);
// Create name file for history
		fileNameP1 = "./"+player1+"/history "+player1 + " VS "+ player2 +".txt";
		fileNameP2 = "./"+player2+"/history "+player2 + " VS "+ player1 +".txt";	
// Write the date in both players file
		Utilitaires.newMatch(dateNow,fileNameP1);
		Utilitaires.newMatch(dateNow,fileNameP2);
	}

	private void createPieceO() {
		// TODO Auto-generated method stub
		// for (int i = 0; i < nbPiecesO;) {
		for (int x = 1; x < 11; x += 2) {
			alPieces.add(new Piece(x, 1, 'O', false));
			alPieces.add(new Piece(x + 1, 2, 'O', false));
			alPieces.add(new Piece(x, 3, 'O', false));
			alPieces.add(new Piece(x + 1, 4, 'O', false));
			// i++;
		}
		// }
	}

	private void createPieceX() {
		// TODO Auto-generated method stub
		// for (int i = 0; i < nbPiecesX;) {
		for (int x = 1; x < 11; x += 2) {
			alPieces.add(new Piece(x, 7, 'X', false));
			alPieces.add(new Piece(x + 1, 8, 'X', false));
			alPieces.add(new Piece(x, 9, 'X', false));
			alPieces.add(new Piece(x + 1, 10, 'X', false));
			// i++;
		}
		// }
	}

	private void fillTab(char[][] map, ArrayList<Piece> alPieces) {
		// TODO Auto-generated method stub

		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = '-';
				map[0][j] = '*';
				map[map.length - 1][j] = '*';
			}
			map[i][0] = '*';
			map[i][map[i].length - 1] = '*';
		}
		for (Piece piece : alPieces) {
			map[piece.getX()][piece.getY()] = piece.getCouleur();
		}
	}

	// This function will print the board "clear and pretty"
	public static void printTab(char[][] map, int sizeY, int sizeX) {
		char c = 'a';
		System.out.print("  ");

		for (int i = 0; i < map.length; i++) {
			if (i != 0 && i != sizeY - 1) {
				System.out.print(c);
				c++;
				System.out.print(" ");

			}

			if (i == sizeY - 1) {
				System.out.print("  ");
			}

			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[j][i]);
				System.out.print(" ");
			}

			System.out.println();

		}
		System.out.print("    ");
		for (int i = 0; i < sizeX; i++) {
			if (i != 0 && i != sizeX - 1) {
				System.out.print(i);
				System.out.print(" ");
			}

		}
	}

	public static Piece selectPieceToMove(char[][] map, ArrayList<Piece> alPieces) {
		/*
		 * int selectedX = Utilitaires.giveInt(); int selectedY = Utilitaires.giveInt();
		 * for (Piece piece : alPieces) { //map[piece.getX()][piece.getY()]=
		 * piece.getCouleur(); if(selectedX == piece.getX() && selectedY ==
		 * piece.getY()) { piece.setCouleur('T'); //juste un test pour voir si la
		 * selection marche } }
		 */
		boolean selecting = true;
		do {
			System.out.println("Choose a valid position (ex : a3) : ");
			String selectedXY = Utilitaires.giveString();
			if (selectedXY.length() == 2) {
				if (Utilitaires.isACorrectPosition(selectedXY)) {
					List<String> selected = Utilitaires.convertAJToNumber(selectedXY);
					for (Piece piece : alPieces) {
						if (Integer.parseInt(selected.get(1)) == piece.getX()
								&& Integer.parseInt(selected.get(0)) == piece.getY()) {
							selecting = false;
							return piece;
						}
					}
				}
			}
		} while (selecting);
		return null;
	}

	public void moveOPieceSelected(String move, Piece piece) {
		switch (move) {
		case "1":
			if (tabMap[piece.getX() - 1][piece.getY() + 1] == '-') {
				tabMap[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX()-1);
				piece.setY(piece.getY()+1);
			}
		}
	}

}
