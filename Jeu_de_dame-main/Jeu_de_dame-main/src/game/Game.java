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

	int sizeX = 12;
	int sizeY = 12;
	char[][] tabMap;
	// final static String FILENAME = "./history.txt";
	boolean gameOn = true;
	ArrayList<Piece> alPieces = new ArrayList<Piece>();

	// List for kings
	ArrayList<Piece> alKings = new ArrayList<Piece>();

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
			comestible = checkIfKingCanEat(tabMap, alPieces, comestible);
			playerTurn(comestible);

			Utilitaires.saveTab(tabMap, fileNameP1, sizeY);
			Utilitaires.saveTab(tabMap, fileNameP2, sizeY);
		} while (gameOn);
	}

	private Map<Piece, int[]> checkIfCanEat(char[][] tabMap2, ArrayList<Piece> alPieces2,
			Map<Piece, int[]> comestible) {
		char color;
		char enemi;
		char kingEnemi;

		if (player1Turn) {
			color = 'X';
			enemi = 'O';
			kingEnemi = '@';
		} else {
			color = 'O';
			enemi = 'X';
			kingEnemi = '#';
		}
		for (Piece pi : alPieces2) {
			if (pi.getCouleur() == color) {
				if (tabMap[pi.getX() - 1][pi.getY() + 1] == enemi
						|| tabMap[pi.getX() - 1][pi.getY() + 1] == kingEnemi) {
					if (tabMap[pi.getX() - 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 1 });
					}
				}
				if (tabMap[pi.getX() + 1][pi.getY() + 1] == enemi
						|| tabMap[pi.getX() + 1][pi.getY() + 1] == kingEnemi) {
					if (tabMap[pi.getX() + 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 3 });
					}
				}
				if (tabMap[pi.getX() - 1][pi.getY() - 1] == enemi
						|| tabMap[pi.getX() - 1][pi.getY() - 1] == kingEnemi) {
					if (tabMap[pi.getX() - 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 7 });
					}
				}
				if (tabMap[pi.getX() + 1][pi.getY() - 1] == enemi
						|| tabMap[pi.getX() + 1][pi.getY() - 1] == kingEnemi) {
					if (tabMap[pi.getX() + 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 9 });
					}
				}
			}
		}
		return comestible;
	}

	private Map<Piece, int[]> checkIfKingCanEat(char[][] tabMap2, ArrayList<Piece> alPieces2,
			Map<Piece, int[]> comestible) {

		char kingAlly;
		char enemi;
		char kingEnemi;

		if (player1Turn) {
			kingAlly = '#';
			enemi = 'O';
			kingEnemi = '@';
		} else {
			kingAlly = '@';
			enemi = 'X';
			kingEnemi = '#';
		}
		for (Piece pi : alPieces2) {
			int i = 0;
			if (pi.getCouleur() == kingAlly) {
				// 1
				do {
					i++;
					if (tabMap[pi.getX() - i][pi.getY() + i] == enemi
							|| tabMap[pi.getX() - i][pi.getY() + i] == kingEnemi) {
						if (tabMap[pi.getX() - i - 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 1 });
						}
					}
				} while (tabMap[pi.getX() - i][pi.getY() + i] != '*');
				i = 0;
				// 3
				do {
					i++;
					if (tabMap[pi.getX() + i][pi.getY() + i] == enemi
							|| tabMap[pi.getX() + i][pi.getY() + i] == kingEnemi) {
						if (tabMap[pi.getX() + i + 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 3 });
						}
					}
				} while (tabMap[pi.getX() + i][pi.getY() + i] != '*');
				i = 0;
				// 7
				do {
					i++;
					if (tabMap[pi.getX() - i][pi.getY() - i] == enemi
							|| tabMap[pi.getX() - i][pi.getY() - i] == kingEnemi) {

						if (tabMap[pi.getX() - i - 1][pi.getY() - i - 1] == '-') {
							comestible.put(pi, new int[] { 7 });
						}
					}
				} while (tabMap[pi.getX() - i][pi.getY() - i] != '*');
				i = 0;
				// 9
				do {
					i++;
					if (tabMap[pi.getX() + i][pi.getY() - i] == enemi
							|| tabMap[pi.getX() + i][pi.getY() - i] == kingEnemi) {

						if (tabMap[pi.getX() + i + 1][pi.getY() - i - 1] == '-') {
							comestible.put(pi, new int[] { 9 });
						}
					}

				} while (tabMap[pi.getX() + i][pi.getY() - i] != '*');

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
				if (selectedPiece.getCouleur() == '@' || selectedPiece.getCouleur() == '#') {
					mooved = moveKingSelected(Utilitaires.giveString(), selectedPiece);
				} else {
					mooved = movePieceSelected(Utilitaires.giveString(), selectedPiece);
				}

			} else {
				System.out.println("nop");
				do {
					selectedPiece = selectPieceToMove(tabMap, alPieces);
					check = checkSelectedPieceInComestible(selectedPiece, comestible);
				} while (!check);
				
				do {
					if (selectedPiece.getCouleur() == '@' || selectedPiece.getCouleur() == '#') {
						
					}
					mooved = mooveToEat(selectedPiece, comestible);
					comestible.clear();
					comestible = checkIfCanEat(tabMap, alPieces, comestible);
					comestible = checkIfKingCanEat(tabMap, alPieces, comestible);
					
					
				}while(checkSelectedPieceInComestible(selectedPiece, comestible));
			}

		} while (!mooved);
		player1Turn = !player1Turn;
	}

	private boolean mooveToEat(Piece selectedPiece, Map<Piece, int[]> comestible) {
		// TODO Auto-generated method stub
		List<Integer> tempDir = new ArrayList<Integer>();
		System.out.print("In wich direction do you want to eat : ");
		for (int mv : comestible.get(selectedPiece)) {
			tempDir.add(mv);
			System.out.print(mv + " ");
		}
		System.out.println("?");
		
		int rep = Utilitaires.giveInt();

		if (tempDir.contains(rep)) {
			//if(selectedPiece.getCouleur() == '@' || selectedPiece.getCouleur() == '#') {
				moveKingToEat(selectedPiece, comestible, rep);
			//}else {
				eat(rep, selectedPiece);	
			//}
		}
		return true;
	}

	/*
	What we need
	check toute la colonne de la direction
	si tu trouve une piece avec tabmap
	ListPieceTemporaire add findPiece(tabmap x, tabmap y)
	si tu trouve un -
	ajoute ListPieceTemp a ListPieceMangeable
	si tu trouve un * ou que tu t'es d�plac� du nombre de case que tu voulais tu arrete la boucle
	break
	
	
	do {
					i++;
					if (tabMap[pi.getX() - i][pi.getY() + i] == enemi
							|| tabMap[pi.getX() - i][pi.getY() + i] == kingEnemi) {
						if (tabMap[pi.getX() - i - 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 1 });
						}
					}
				} while (tabMap[pi.getX() - i][pi.getY() + i] != '*');
				
	
	ensuite on retire toute les piece Mangeable
	*/
	private boolean moveKingToEat(Piece selectedPiece, Map<Piece, int[]> comestible, int rep) {
		char kingAlly;
		char enemi;
		char kingEnemi;

		if (player1Turn) {
			kingAlly = '#';
			enemi = 'O';
			kingEnemi = '@';
		} else {
			kingAlly = '@';
			enemi = 'X';
			kingEnemi = '#';
		}
		
		switch (rep) {
		case 1:
			/*
			int i = 0;
			List<Piece> ListPieceTemp = new ArrayList<Piece>(); // list de piece que l'on pourrait manger si la chaine etait suivie d'un '-'
			List<Piece> ListPieceMangeable = new ArrayList<Piece>(); // list de piece mangeable car la chaine est suivie d'un '-'
			List<Integer> ListPositionValide = new ArrayList<Integer>(); // nb de case a ce d�plac� (2 = deux case, 3 = etc...)
			do {
				if(tabMap[selectedPiece.getX() - i][selectedPiece.getY() + i] == enemi ||
						tabMap[selectedPiece.getX() - i][selectedPiece.getY() + i] == kingEnemi) {
					ListPieceTemp.add(findPiece(selectedPiece.getX() - i, selectedPiece.getY() + i));
				}
				if(tabMap[selectedPiece.getX() - i][selectedPiece.getY() + i] == '-' && !ListPieceTemp.isEmpty()) {
					for(Piece p : ListPieceTemp) {
						ListPieceMangeable.add(p);
					}
					ListPositionValide.add(i);
				}
				i++;
			}while(tabMap[selectedPiece.getX() - i][selectedPiece.getY() + i] != '*');
			*/
			
			/*if (tabMap[selectedPiece.getX() - 1][selectedPiece.getY() + 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() - 1);
					selectedPiece.setY(selectedPiece.getY() + 1);
					tabMap[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (tabMap[selectedPiece.getX() - 1][selectedPiece.getY() + 1] == '-');
			}*/
			break;
		case 3:
			if (tabMap[selectedPiece.getX() + 1][selectedPiece.getY() + 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() + 1);
					selectedPiece.setY(selectedPiece.getY() + 1);
					tabMap[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (tabMap[selectedPiece.getX() + 1][selectedPiece.getY() + 1] == '-');
			}
			break;
		case 7:
			if (tabMap[selectedPiece.getX() - 1][selectedPiece.getY() - 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() - 1);
					selectedPiece.setY(selectedPiece.getY() - 1);
					tabMap[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (tabMap[selectedPiece.getX() - 1][selectedPiece.getY() - 1] == '-');
			}
			break;
		case 9:
			if (tabMap[selectedPiece.getX() + 1][selectedPiece.getY() - 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() - 1);
					selectedPiece.setY(selectedPiece.getY() + 1);
					tabMap[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (tabMap[selectedPiece.getX() + 1][selectedPiece.getY() + 1] == '-');
			}
			break;
		default:
			System.out.println("Error");
		}
		return true;
	}
	
	private boolean checkSelectedPieceInComestible(Piece selectedPiece, Map<Piece, int[]> comestible) {
		if (comestible.containsKey(selectedPiece)) {
			System.out.println("piece can eat");
			return true;
		}
		System.out.println("another piece can eat !");
		return false;

	}

	private boolean checkSelectedPiece(Piece pi) {
		if ((!player1Turn && (pi.getCouleur() == 'X' || pi.getCouleur() == '#')
				|| (player1Turn) && (pi.getCouleur() == 'O' || pi.getCouleur() == '@'))) {
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
		// takes the date for the history
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

	public Piece findPiece(int x, int y) {
		for (Piece piece : alPieces) {
			if (x == piece.getX()
					&& y == piece.getY()) {
				return piece;
			}
		}
		return null;
	}
	public boolean eat(int move, Piece piece) {
		int x = piece.getX();
		int y = piece.getY();
		int indexToRemove = 0;
		switch (move) {
		case 1:
			tabMap[x][y] = '-';
			tabMap[x - 1][y + 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x - 1 && alPieces.get(i).getY() == y + 1) {
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
			tabMap[x + 1][y + 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x + 1 && alPieces.get(i).getY() == y + 1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x + 2);
			piece.setY(y + 2);

			System.out.println(piece.getX() + "  crash" + piece.getY());

			tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
		case 7:

			tabMap[x][y] = '-';
			tabMap[x - 1][y - 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x - 1 && alPieces.get(i).getY() == y - 1) {
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
			tabMap[x + 1][y - 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x + 1 && alPieces.get(i).getY() == y - 1) {
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
			System.out.println("default error Piece");
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
				if (piece.getCouleur() == 'O' && piece.getY() == 5) {
					System.out.print("One of your piece became a king.");
					becomeKings(piece);
				}

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

				if (piece.getCouleur() == 'O' && piece.getY() == 5) {
					System.out.print("Une pion devient dame.");
					becomeKings(piece);
				}

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

				if (piece.getCouleur() == 'X' && piece.getY() == 5) {
					System.out.print("One of your piece became a king.");
					becomeKings(piece);
				}

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

				if (piece.getCouleur() == 'X' && piece.getY() == 5) {
					System.out.print("One of your piece became a king.");
					becomeKings(piece);
				}
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

	// distance trop grande pas bien.
	public boolean moveKingSelected(String move, Piece piece) {

		int nbMove = 0;
		boolean cantMove = false;
		switch (move) {
		case "1":

			System.out.println("How many times do you want to go in this way?");
			nbMove = Utilitaires.giveInt();

			if ((piece.getX() - nbMove) > 0 && (piece.getY() + nbMove) <= 10) {

				for (int i = 1; i <= nbMove; i++) {

					if (tabMap[piece.getX() - i][piece.getY() + i] != '-') {
						cantMove = true;

					}
				}

				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() - nbMove);
					piece.setY(piece.getY() + nbMove);
					tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
					return true;
				}

			} else {
				System.out.println("Too high.");
				return false;
			}

		case "3":

			System.out.println("How many times do you want to go in this way?");
			nbMove = Utilitaires.giveInt();

			if ((piece.getX() + nbMove) <= 10 && (piece.getY() + nbMove) <= 10) {

				for (int i = 1; i <= nbMove; i++) {

					if (tabMap[piece.getX() + i][piece.getY() + i] != '-') {
						cantMove = true;

					}
				}

				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() + nbMove);
					piece.setY(piece.getY() + nbMove);
					tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
					return true;
				}

			} else {
				System.out.println("Too high.");
				return false;
			}

			// break;

		case "7":
			System.out.println("How many times do you want to go in this way?");
			nbMove = Utilitaires.giveInt();

			if ((piece.getX() - nbMove) > 0 && (piece.getY() - nbMove) > 0) {

				for (int i = 1; i <= nbMove; i++) {
					if (tabMap[piece.getX() - i][piece.getY() - i] != '-') {
						cantMove = true;

					}
				}

				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() - nbMove);
					piece.setY(piece.getY() - nbMove);
					tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
					return true;
				}
			} else {
				System.out.println("Too high.");
				return false;
			}

			// break;

		case "9":

			System.out.println("How many times do you want to go in this way?");
			nbMove = Utilitaires.giveInt();

			if ((piece.getX() + nbMove) <= 10 && (piece.getY() - nbMove) > 0) {

				for (int i = 1; i <= nbMove; i++) {

					if (tabMap[piece.getX() + i][piece.getY() - i] != '-') {
						cantMove = true;

					}
				}

				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() + nbMove);
					piece.setY(piece.getY() - nbMove);
					tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
					return true;
				}
			} else {
				System.out.println("Too high.");
				return false;
			}

			// break;

		default:
			System.out.println("default error");
			return false;
		}
	}

	private void becomeKings(Piece piece) {
			piece.setCouleur(piece.getKingColor());
			tabMap[piece.getX()][piece.getY()] = piece.getCouleur();
	}

}