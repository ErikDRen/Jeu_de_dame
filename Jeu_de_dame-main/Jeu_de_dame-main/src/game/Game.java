package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Piece;
import utile.Utilitaires;

public class Game {

	String player1 = "";
	String player2 = "";

	String fileNameP1 = "";
	String fileNameP2 = "";

	boolean player1Turn = true;
	int nbPiecesO = 20;
	int nbPiecesX = 20;
	int sizeX = 12;
	int sizeY = 12;
	char[][] tabMap;
	// final static String FILENAME = "./history.txt";
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
		Utilitaires.createPieces(tabMap, alPieces);
		do {
			Utilitaires.fillTab(tabMap, alPieces);
			Utilitaires.printTab(tabMap, sizeY, sizeX);
			Map<Piece, int[]> comestible = new HashMap<Piece, int[]>();
			comestible = checkIfCanEat(tabMap, alPieces, comestible);
			playerTurn(comestible);

			Utilitaires.saveTab(tabMap, fileNameP1, sizeY);
			Utilitaires.saveTab(tabMap, fileNameP2, sizeY);
		} while (gameOn);
	}

	private Map<Piece, int[]> checkIfCanEat(char[][] tabMap2, ArrayList<Piece> alPieces2,
			Map<Piece, int[]> comestible) {
		char color;
		char enemi;
		if (player1Turn) {
			color = 'X';
			enemi = 'O';
		} else {
			color = 'O';
			enemi = 'X';
		}
		for (Piece pi : alPieces2) {
			if (pi.getCouleur() == color) {
				if (tabMap[pi.getX() - 1][pi.getY() + 1] == enemi) {
					if (tabMap[pi.getX() - 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 1 });
					}
				}
				if (tabMap[pi.getX() + 1][pi.getY() + 1] == enemi) {
					if (tabMap[pi.getX() + 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 3 });
					}
				}
				if (tabMap[pi.getX() - 1][pi.getY() - 1] == enemi) {
					if (tabMap[pi.getX() - 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 7 });
					}
				}
				if (tabMap[pi.getX() + 1][pi.getY() - 1] == enemi) {
					if (tabMap[pi.getX() + 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 9 });
					}
				}
			}
		}
		return comestible;
	}

	private void playerTurn(Map<Piece, int[]> comestible) {
		boolean check = false;
		boolean mooved = false;
		Piece selectedPiece;
		do {

			if (comestible.isEmpty()) {
				do {
					selectedPiece = selectPieceToMove(tabMap, alPieces);
					check = checkSelectedPiece(selectedPiece);
				} while (!check);
				mooved = movePieceSelected(Utilitaires.giveString(), selectedPiece);
			} else {
				do {
					selectedPiece = selectPieceToMove(tabMap, alPieces);
					check = checkSelectedPieceInComestible(selectedPiece, comestible);
				} while (!check);
				mooved = mooveToEat(selectedPiece, comestible);
			}

		} while (!mooved);
		player1Turn = !player1Turn;
	}

	private boolean mooveToEat(Piece selectedPiece, Map<Piece, int[]> comestible) {
		// TODO Auto-generated method stub
		List<Integer> tempDir = new ArrayList<Integer>();
		System.out.print("In wich direction do you want to eat : ");
		for(int mv : comestible.get(selectedPiece)) {
			tempDir.add(mv);
			System.out.print(mv + " ");
		}
		System.out.println("?");
		
		int rep = Utilitaires.giveInt();
		if(tempDir.contains(rep)) {
			eat(rep, selectedPiece);
		}
		return true;
	}

	private boolean checkSelectedPieceInComestible(Piece selectedPiece, Map<Piece, int[]> comestible) {
		if (comestible.containsKey(selectedPiece)) {
			System.out.println("piece can eat");
			return true;
		}
		System.out.println("another piece need to eat !");
		return false;

	}

	private boolean checkSelectedPiece(Piece pi) {
		if ((!player1Turn && pi.getCouleur() == 'X') || (player1Turn) && pi.getCouleur() == 'O') {
			System.out.println("it's not the turn of this piece");
			return false;
		}
		// Check if piece can move in 1 3 7 9 direction
		if ((pi.getCouleur() == 'O' && tabMap[pi.getX() - 1][pi.getY() + 1] != '-' && // check for direction 1
				tabMap[pi.getX() + 1][pi.getY() + 1] != '-') || // check for direction 3
				(pi.getCouleur() == 'X' && tabMap[pi.getX() - 1][pi.getY() - 1] != '-' && // check for direction 7
						tabMap[pi.getX() + 1][pi.getY() - 1] != '-')) { // check for direction 9
			System.out.println("Can't move this piece");
			return false;
		}
		System.out.println("piece in " + pi.getX() + ", " + pi.getY() + " selected !");
		return true;
	}

	private void initFile() throws IOException {
		System.out.println("player 1 = " + player1 + "\tplayer2 = " + player2);
//takes the date for the history
		String dateNow = Utilitaires.giveDate();
// Create a folder if there is no folder for the player to save their game.
		Utilitaires.createFolderForUser(player1);
		Utilitaires.createFolderForUser(player2);
// Create name file for history
		fileNameP1 = "./" + player1 + "/history " + player1 + " VS " + player2 + ".txt";
		fileNameP2 = "./" + player2 + "/history " + player2 + " VS " + player1 + ".txt";
// Write the date in both players file
		Utilitaires.newMatch(dateNow, fileNameP1);
		Utilitaires.newMatch(dateNow, fileNameP2);
	}

	public static Piece selectPieceToMove(char[][] map, ArrayList<Piece> alPieces) {

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
	public boolean eat(int move, Piece piece) {
		int x = piece.getX();
		int y = piece.getY();
		int indexToRemove = 0;
		switch (move) {
		case 1:
				tabMap[x][y] = '-';
				tabMap[x-1][y+1] = '-';
				for(int i = 0; i < alPieces.size(); i++) {
					if(alPieces.get(i).getX() == x-1 && alPieces.get(i).getY() == y+1) {
						indexToRemove = i;
					}
				}
				alPieces.remove(indexToRemove);
				piece.setX(x - 2);
				piece.setY(y + 2);
				tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
				System.out.println(piece.getX() + " " + piece.getY());
				return true;
		case 3:
			tabMap[x][y] = '-';
			tabMap[x+1][y+1] = '-';
			for(int i = 0; i < alPieces.size(); i++) {
				if(alPieces.get(i).getX() == x+1 && alPieces.get(i).getY() == y+1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x + 2);
			piece.setY(y + 2);
			tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
		case 7:
			
			
			tabMap[x][y] = '-';
			tabMap[x-1][y-1] = '-';
			for(int i = 0; i < alPieces.size(); i++) {
				if(alPieces.get(i).getX() == x-1 && alPieces.get(i).getY() == y-1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x - 2);
			piece.setY(y - 2);
			tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
		case 9:
			
			tabMap[x][y] = '-';
			tabMap[x+1][y-1] = '-';
			for(int i = 0; i < alPieces.size(); i++) {
				if(alPieces.get(i).getX() == x+1 && alPieces.get(i).getY() == y-1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x + 2);
			piece.setY(y - 2);
			tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
			
		default:
			System.out.println("default error");
			return false;
		}
	}
	
	public boolean movePieceSelected(String move, Piece piece) {
		switch (move) {
		case "1":
			if (piece.getCouleur() == 'O' && tabMap[piece.getX() - 1][piece.getY() + 1] == '-') {
				tabMap[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() - 1);
				piece.setY(piece.getY() + 1);
				tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}
		case "3":
			if (piece.getCouleur() == 'O' && tabMap[piece.getX() + 1][piece.getY() + 1] == '-') {
				tabMap[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() + 1);
				piece.setY(piece.getY() + 1);
				tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}
		case "7":
			if (piece.getCouleur() == 'X' && tabMap[piece.getX() - 1][piece.getY() - 1] == '-') {
				tabMap[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() - 1);
				piece.setY(piece.getY() - 1);
				tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}
		case "9":
			if (piece.getCouleur() == 'X' && tabMap[piece.getX() + 1][piece.getY() - 1] == '-') {
				tabMap[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() + 1);
				piece.setY(piece.getY() - 1);
				tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}

		default:
			System.out.println("default error");
			return false;
		}
	}

}
