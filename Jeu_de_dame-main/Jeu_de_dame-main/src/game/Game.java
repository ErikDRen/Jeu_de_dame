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
	char[][] board;
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
		board = new char[sizeX][sizeY];
		Utilitaires.createPieces(board, alPieces);
		do {
			Utilitaires.fillTab(board, alPieces);
			Utilitaires.printTab(board, sizeY, sizeX);
			Map<Piece, int[]> comestible = new HashMap<Piece, int[]>();
			comestible = checkIfCanEat(comestible);
			comestible = checkIfKingCanEat(board, alPieces, comestible);
			playerTurn(comestible);

			Utilitaires.saveTab(board, fileNameP1, sizeY);
			Utilitaires.saveTab(board, fileNameP2, sizeY);
		} while (gameOn);
	}

	private void playerTurn(Map<Piece, int[]> comestible) {
		boolean check = false;
		boolean mooved = false;
		Piece selectedPiece;
		do {
			if (comestible.isEmpty()) {
				if(!checkIfPlayerCanMove()) {
					System.out.println("/!\\ Vous ne pouvez pas jouer, votre tour est pass� /!\\");
					player1Turn = !player1Turn;
					return;
				}
				do {
					selectedPiece = selectPieceToMove(board, alPieces);
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
					selectedPiece = selectPieceToMove(board, alPieces);
					check = checkSelectedPieceInComestible(selectedPiece, comestible);
				} while (!check);
				
				do {
					if (selectedPiece.getCouleur() == '@' || selectedPiece.getCouleur() == '#') {
						//mooveKingToEat ??
					} // else ?
					mooved = mooveToEat(selectedPiece, comestible);
					comestible.clear();
					comestible = checkIfCanEat(comestible);
					comestible = checkIfKingCanEat(board, alPieces, comestible);
					
				}while(checkSelectedPieceInComestible(selectedPiece, comestible));
			}

		} while (!mooved);
		if (selectedPiece.getCouleur() == 'O' && selectedPiece.getY() == 10) {
			System.out.print("One of your piece became a king.");
			becomeKings(selectedPiece);
		}
		if (selectedPiece.getCouleur() == 'X' && selectedPiece.getY() == 1) {
			System.out.print("One of your piece became a king.");
			becomeKings(selectedPiece);
		}
		player1Turn = !player1Turn;
	}

	private boolean checkIfPlayerCanMove() {
		char color = player1Turn ? 'X' : 'O';
		char kingColor = player1Turn ? '#' : '@';
		for(Piece p : alPieces) {
			if (p.getCouleur() == color || p.getCouleur() == kingColor) {
				if(checkIfPieceCanMove(p)) {
					return true;
				}
			}
		}
		return false;
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
			if(selectedPiece.getCouleur() == '@' || selectedPiece.getCouleur() == '#') {
				moveKingToEat(selectedPiece, comestible, rep);
			}else {
				eat(rep, selectedPiece);	
			}
		}
		return true;
	}

	/*
	check toute la colonne de la direction
	si tu trouve une piece suivie de '-' avec tabmap 
	ListMovePossible add coordonn� de '-'
	si tu trouve un * tu arrete la boucle
	
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
			// On cherche les position de kill existante dans la diagonale
			//On ira ensuite ce d�plac� a ce point et tuer tout enemi sur le trajet !
			int i = 0;
			List<Coordonee> ListPositionValide = new ArrayList<Coordonee>(); // position de kill valide
			do {
				// check pion ennemi suivie de '-' (position de kill valide)
				if((board[selectedPiece.getX() - i][selectedPiece.getY() + i] == enemi ||
						board[selectedPiece.getX() - i][selectedPiece.getY() + i] == kingEnemi) &&
						board[selectedPiece.getX() - i -1][selectedPiece.getY() + i +1] == '-') {
					ListPositionValide.add(new Coordonee(selectedPiece.getX() - i -1, selectedPiece.getY() + i +1));
				}
				i++;
			}while(board[selectedPiece.getX() - i-1][selectedPiece.getY() + i+1] != '*');
			
			System.out.print("Where do you want to go ? : ");
			for (Coordonee mv : ListPositionValide) {
				System.out.print(mv + " ");
			}
			System.out.println("?");
			
			String reponse = Utilitaires.giveString();
			Coordonee validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
			System.out.println(validPos);
			for(Coordonee co : ListPositionValide) {
				if(co.getX() == validPos.getX() && co.getY() == validPos.getY()) { // if(ListPositionValide.contains(validPos)) {
					Piece p;
					i = selectedPiece.getX();
					int j = selectedPiece.getY();
					
					while(i != validPos.getX() && j != validPos.getY()) {
						p = findPiece(i,j);
						if(p != null && p.getCouleur() != selectedPiece.getCouleur()) {
							board[p.getX()][p.getY()] = '-';
							alPieces.remove(p);
						}
						i--;
						j++;
					}
					board[selectedPiece.getX()][selectedPiece.getY()] = '-';
					selectedPiece.setX(validPos.getX());
					selectedPiece.setY(validPos.getY());
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				}
			}
			if(ListPositionValide.contains(validPos)) { // if(ListPositionValide.contains(validPos)) {
				Piece p;
				i = selectedPiece.getX();
				int j = selectedPiece.getY();
				
				while(i != validPos.getX() && j != validPos.getY()) {
					p = findPiece(i,j);
					if(p != null && p.getCouleur() != selectedPiece.getCouleur()) {
						board[p.getX()][p.getY()] = '-';
						alPieces.remove(p);
					}
					i--;
					j++;
				}
				board[selectedPiece.getX()][selectedPiece.getY()] = '-';
				selectedPiece.setX(validPos.getX());
				selectedPiece.setY(validPos.getY());
				board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
			}
			//if(ListPositionValide.contains(response))
			/*if (tabMap[selectedPiece.getX() - 1][selectedPiece.getY() + 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() - 1);
					selectedPiece.setY(selectedPiece.getY() + 1);
					tabMap[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (tabMap[selectedPiece.getX() - 1][selectedPiece.getY() + 1] == '-');
			}*/
			break;
		case 3:
			i = 0;
			ListPositionValide = new ArrayList<Coordonee>(); // position de kill valide
			do {
				// check pion ennemi suivie de '-' (position de kill valide)
				if((board[selectedPiece.getX() + i][selectedPiece.getY() + i] == enemi ||
						board[selectedPiece.getX() + i][selectedPiece.getY() + i] == kingEnemi) &&
						board[selectedPiece.getX() + i +1][selectedPiece.getY() + i +1] == '-') {
					ListPositionValide.add(new Coordonee(selectedPiece.getX() + i +1, selectedPiece.getY() + i +1));
				}
				i++;
			}while(board[selectedPiece.getX() + i+1][selectedPiece.getY() + i+1] != '*');
			
			System.out.print("Where do you want to go ? : ");
			for (Coordonee mv : ListPositionValide) {
				System.out.print(mv.toString() + " ");
			}
			System.out.println("?");
			
			reponse = Utilitaires.giveString();
			validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
			for(Coordonee co : ListPositionValide) {
				if(co.getX() == validPos.getX() && co.getY() == validPos.getY()) { // if(ListPositionValide.contains(validPos)) {
					Piece p;
					i = selectedPiece.getX();
					int j = selectedPiece.getY();
					
					while(i != validPos.getX() && j != validPos.getY()) {
						p = findPiece(i,j);
						if(p != null && p.getCouleur() != selectedPiece.getCouleur()) {
							board[p.getX()][p.getY()] = '-';
							alPieces.remove(p);
						}
						i++;
						j++;
					}
					board[selectedPiece.getX()][selectedPiece.getY()] = '-';
					selectedPiece.setX(validPos.getX());
					selectedPiece.setY(validPos.getY());
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				}
			}
			/*if (board[selectedPiece.getX() + 1][selectedPiece.getY() + 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() + 1);
					selectedPiece.setY(selectedPiece.getY() + 1);
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (board[selectedPiece.getX() + 1][selectedPiece.getY() + 1] == '-');
			}*/
			break;
		case 7:
			i = 0;
			ListPositionValide = new ArrayList<Coordonee>(); // position de kill valide
			do {
				// check pion ennemi suivie de '-' (position de kill valide)
				if((board[selectedPiece.getX() - i][selectedPiece.getY() - i] == enemi ||
						board[selectedPiece.getX() - i][selectedPiece.getY() - i] == kingEnemi) &&
						board[selectedPiece.getX() - i -1][selectedPiece.getY() - i -1] == '-') {
					ListPositionValide.add(new Coordonee(selectedPiece.getX() - i -1, selectedPiece.getY() - i -1));
				}
				i++;
			}while(board[selectedPiece.getX() - i-1][selectedPiece.getY() - i-1] != '*');
			
			System.out.print("Where do you want to go ? : ");
			for (Coordonee mv : ListPositionValide) {
				System.out.print(mv + " ");
			}
			System.out.println("?");
			
			reponse = Utilitaires.giveString();
			validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
			for(Coordonee co : ListPositionValide) {
				if(co.getX() == validPos.getX() && co.getY() == validPos.getY()) { // if(ListPositionValide.contains(validPos)) {
					Piece p;
					i = selectedPiece.getX();
					int j = selectedPiece.getY();
					
					while(i != validPos.getX() && j != validPos.getY()) {
						p = findPiece(i,j);
						if(p != null && p.getCouleur() != selectedPiece.getCouleur()) {
							board[p.getX()][p.getY()] = '-';
							alPieces.remove(p);
						}
						i--;
						j--;
					}
					board[selectedPiece.getX()][selectedPiece.getY()] = '-';
					selectedPiece.setX(validPos.getX());
					selectedPiece.setY(validPos.getY());
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				}
			}
			/*
			if (board[selectedPiece.getX() - 1][selectedPiece.getY() - 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() - 1);
					selectedPiece.setY(selectedPiece.getY() - 1);
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (board[selectedPiece.getX() - 1][selectedPiece.getY() - 1] == '-');
			}*/
			break;
		case 9:
			i = 0;
			ListPositionValide = new ArrayList<Coordonee>(); // position de kill valide
			do {
				// check pion ennemi suivie de '-' (position de kill valide)
				if((board[selectedPiece.getX() + i][selectedPiece.getY() - i] == enemi ||
						board[selectedPiece.getX() + i][selectedPiece.getY() - i] == kingEnemi) &&
						board[selectedPiece.getX() + i +1][selectedPiece.getY() - i -1] == '-') {
					ListPositionValide.add(new Coordonee(selectedPiece.getX() + i +1, selectedPiece.getY() - i -1));
				}
				i++;
			}while(board[selectedPiece.getX() + i+1][selectedPiece.getY() - i-1] != '*');
			
			System.out.print("Where do you want to go ? : ");
			for (Coordonee mv : ListPositionValide) {
				System.out.print(mv + " ");
			}
			System.out.println("?");
			
			reponse = Utilitaires.giveString();
			validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
			for(Coordonee co : ListPositionValide) {
				if(co.getX() == validPos.getX() && co.getY() == validPos.getY()) { // if(ListPositionValide.contains(validPos)) {
					Piece p;
					i = selectedPiece.getX();
					int j = selectedPiece.getY();
					
					while(i != validPos.getX() && j != validPos.getY()) {
						p = findPiece(i,j);
						if(p != null && p.getCouleur() != selectedPiece.getCouleur()) {
							board[p.getX()][p.getY()] = '-';
							alPieces.remove(p);
						}
						i++;
						j--;
					}
					board[selectedPiece.getX()][selectedPiece.getY()] = '-';
					selectedPiece.setX(validPos.getX());
					selectedPiece.setY(validPos.getY());
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				}
			}
			/*
			if (board[selectedPiece.getX() + 1][selectedPiece.getY() - 1] == '-') {
				do {
					selectedPiece.setX(selectedPiece.getX() + 1);
					selectedPiece.setY(selectedPiece.getY() - 1);
					board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getCouleur();
				} while (board[selectedPiece.getX() + 1][selectedPiece.getY() + 1] == '-');
			}*/
			break;
		default:
			System.out.println("Error");
		}
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
			if (selectedXY.length() == 2 || selectedXY.length() == 3) {
				if (Utilitaires.isACorrectPosition(selectedXY)) {
					List<String> selected = Utilitaires.convertAJToStringNumber(selectedXY);
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
			board[x][y] = '-';
			board[x - 1][y + 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x - 1 && alPieces.get(i).getY() == y + 1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x - 2);
			piece.setY(y + 2);
			board[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
		case 3:
			board[x][y] = '-';
			board[x + 1][y + 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x + 1 && alPieces.get(i).getY() == y + 1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x + 2);
			piece.setY(y + 2);
			System.out.println(piece.getX() + "  crash" + piece.getY());
			board[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
		case 7:
			board[x][y] = '-';
			board[x - 1][y - 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x - 1 && alPieces.get(i).getY() == y - 1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x - 2);
			piece.setY(y - 2);
			board[piece.getX()][piece.getY()] = piece.getCouleur();
			System.out.println(piece.getX() + " " + piece.getY());
			return true;
		case 9:
			board[x][y] = '-';
			board[x + 1][y - 1] = '-';
			for (int i = 0; i < alPieces.size(); i++) {
				if (alPieces.get(i).getX() == x + 1 && alPieces.get(i).getY() == y - 1) {
					indexToRemove = i;
				}
			}
			alPieces.remove(indexToRemove);
			piece.setX(x + 2);
			piece.setY(y - 2);
			board[piece.getX()][piece.getY()] = piece.getCouleur();
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
			if (piece.getCouleur() == 'O' && board[piece.getX() - 1][piece.getY() + 1] == '-') {
				board[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() - 1);
				piece.setY(piece.getY() + 1);
				if (piece.getCouleur() == 'O' && piece.getY() == 10) {
					System.out.print("One of your piece became a king.");
					becomeKings(piece);
				}
				board[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}
		case "3":
			if (piece.getCouleur() == 'O' && board[piece.getX() + 1][piece.getY() + 1] == '-') {
				board[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() + 1);
				piece.setY(piece.getY() + 1);
				if (piece.getCouleur() == 'O' && piece.getY() == 10) {
					System.out.print("Une pion devient dame.");
					becomeKings(piece);
				}
				board[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}
		case "7":
			if (piece.getCouleur() == 'X' && board[piece.getX() - 1][piece.getY() - 1] == '-') {
				board[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() - 1);
				piece.setY(piece.getY() - 1);
				if (piece.getCouleur() == 'X' && piece.getY() == 1) {
					System.out.print("One of your piece became a king.");
					becomeKings(piece);
				}
				board[piece.getX()][piece.getY()] = piece.getCouleur();
				return true;
			} else {
				return false;
			}
		case "9":
			if (piece.getCouleur() == 'X' && board[piece.getX() + 1][piece.getY() - 1] == '-') {
				board[piece.getX()][piece.getY()] = '-';
				piece.setX(piece.getX() + 1);
				piece.setY(piece.getY() - 1);

				if (piece.getCouleur() == 'X' && piece.getY() == 1) {
					System.out.print("One of your piece became a king.");
					becomeKings(piece);
				}
				board[piece.getX()][piece.getY()] = piece.getCouleur();
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
					if (board[piece.getX() - i][piece.getY() + i] != '-') {
						cantMove = true;
					}
				}
				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() - nbMove);
					piece.setY(piece.getY() + nbMove);
					board[piece.getX()][piece.getY()] = piece.getCouleur();
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
					if (board[piece.getX() + i][piece.getY() + i] != '-') {
						cantMove = true;
					}
				}
				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() + nbMove);
					piece.setY(piece.getY() + nbMove);
					board[piece.getX()][piece.getY()] = piece.getCouleur();
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
					if (board[piece.getX() - i][piece.getY() - i] != '-') {
						cantMove = true;
					}
				}
				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() - nbMove);
					piece.setY(piece.getY() - nbMove);
					board[piece.getX()][piece.getY()] = piece.getCouleur();
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
					if (board[piece.getX() + i][piece.getY() - i] != '-') {
						cantMove = true;
					}
				}
				if (cantMove == true) {
					System.out.println("Bad range, insert another one.");
					return false;
				} else {
					piece.setX(piece.getX() + nbMove);
					piece.setY(piece.getY() - nbMove);
					board[piece.getX()][piece.getY()] = piece.getCouleur();
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
			board[piece.getX()][piece.getY()] = piece.getCouleur();
	}

	private Map<Piece, int[]> checkIfCanEat(Map<Piece, int[]> comestible) {
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
		for (Piece pi : alPieces) {
			if (pi.getCouleur() == color) {
				if (board[pi.getX() - 1][pi.getY() + 1] == enemi
						|| board[pi.getX() - 1][pi.getY() + 1] == kingEnemi) {
					if (board[pi.getX() - 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 1 });
					}
				}
				if (board[pi.getX() + 1][pi.getY() + 1] == enemi
						|| board[pi.getX() + 1][pi.getY() + 1] == kingEnemi) {
					if (board[pi.getX() + 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 3 });
					}
				}
				if (board[pi.getX() - 1][pi.getY() - 1] == enemi
						|| board[pi.getX() - 1][pi.getY() - 1] == kingEnemi) {
					if (board[pi.getX() - 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 7 });
					}
				}
				if (board[pi.getX() + 1][pi.getY() - 1] == enemi
						|| board[pi.getX() + 1][pi.getY() - 1] == kingEnemi) {
					if (board[pi.getX() + 2][pi.getY() - 2] == '-') {
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
					if (board[pi.getX() - i][pi.getY() + i] == enemi
							|| board[pi.getX() - i][pi.getY() + i] == kingEnemi) {
						if (board[pi.getX() - i - 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 1 });
						}
					}
				} while (board[pi.getX() - i][pi.getY() + i] != '*');
				i = 0;
				// 3
				do {
					i++;
					if (board[pi.getX() + i][pi.getY() + i] == enemi
							|| board[pi.getX() + i][pi.getY() + i] == kingEnemi) {
						if (board[pi.getX() + i + 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 3 });
						}
					}
				} while (board[pi.getX() + i][pi.getY() + i] != '*');
				i = 0;
				// 7
				do {
					i++;
					if (board[pi.getX() - i][pi.getY() - i] == enemi
							|| board[pi.getX() - i][pi.getY() - i] == kingEnemi) {

						if (board[pi.getX() - i - 1][pi.getY() - i - 1] == '-') {
							comestible.put(pi, new int[] { 7 });
						}
					}
				} while (board[pi.getX() - i][pi.getY() - i] != '*');
				i = 0;
				// 9
				do {
					i++;
					if (board[pi.getX() + i][pi.getY() - i] == enemi
							|| board[pi.getX() + i][pi.getY() - i] == kingEnemi) {

						if (board[pi.getX() + i + 1][pi.getY() - i - 1] == '-') {
							comestible.put(pi, new int[] { 9 });
						}
					}
				} while (board[pi.getX() + i][pi.getY() - i] != '*');
			}
		}
		return comestible;
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
		if(!checkIfPieceCanMove(pi)) {
			return false;
		}
		System.out.println("piece in " + pi.getX() + ", " + pi.getY() + " selected !");
		return true;
	}

	private boolean checkIfPieceCanMove(Piece pi) {
		// Check if piece can move in 1 3 7 9 direction
		if ((pi.getCouleur() == 'O' && board[pi.getX() - 1][pi.getY() + 1] != '-' && // check for direction 1
				board[pi.getX() + 1][pi.getY() + 1] != '-') || // check for direction 3
				(pi.getCouleur() == 'X' && board[pi.getX() - 1][pi.getY() - 1] != '-' && // check for direction 7
				board[pi.getX() + 1][pi.getY() - 1] != '-')) { // check for direction 9
			System.out.println("Can't move this piece");
			return false;
		}
		return true;
	}
}