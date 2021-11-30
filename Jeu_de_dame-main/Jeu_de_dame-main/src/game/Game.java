package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utile.Check;
import data.Data;
import model.Piece;
import utile.Utilitaires;

public class Game {

	Data d;
	Check c = new Check();

	public Game(String p1, String p2, Data d) {
		super();
		this.d = d;

		d.setPlayer1(p1);
		d.setPlayer2(p2);

		d.setKingColorPlayer1('#');
		d.setKingColorPlayer2('@');
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * The game function is the core of the program, it will call functions for the execution of the game.
	 * @throws IOException redirect error
	 */
	public void game() throws IOException {
		initFile();
		d.setBoard(new char[d.getSizeX()][d.getSizeY()]);
		Utilitaires.createPieces(d);
		do {
			Utilitaires.fillTab(d.getBoard(), d.getAlPieces());
			Utilitaires.printTab(d.getBoard(), d.getSizeX(), d.getSizeY());
			Map<Piece, int[]> comestible = new HashMap<Piece, int[]>();
			comestible = Check.checkIfCanEat(comestible, d);
			comestible = Check.checkIfKingCanEat(d, comestible);
			playerTurn(comestible);

			Utilitaires.saveTab(d.getBoard(), d.getFileNameP1(), d.getSizeY());
			Utilitaires.saveTab(d.getBoard(), d.getFileNameP2(), d.getSizeY());
		} while (d.isGameOn());
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * The turn manager ! 
	 * manage the mooving and eating part, switch the playerTurn variable at the end
	 * @param comestible map to get directions where you can eat
	 */
	private void playerTurn(Map<Piece, int[]> comestible) {
		boolean check = false;
		boolean mooved = false;
		Piece selectedPiece;
		if(checkIfStillHavePieces()){
			do {
				if (comestible.isEmpty()) {
					if (!c.checkIfPlayerCanMove(d)) {
						System.out.println("/!\\ Vous ne pouvez pas jouer, votre tour est pass� /!\\");
						d.setPlayer1Turn(!d.isPlayer1Turn());
						return;
					}
					do {
						selectedPiece = selectPieceToMove(d.getBoard(), d.getAlPieces());
						check = c.checkSelectedPiece(selectedPiece,d);
					} while (!check);
					if (selectedPiece.getColor() == d.getKingColorPlayer2()
							|| selectedPiece.getColor() == d.getKingColorPlayer1()) {
						mooved = moveKingSelected(Utilitaires.giveString(), selectedPiece);
					} else {
						mooved = movePieceSelected(Utilitaires.giveString(), selectedPiece);
					}
	
				} else {
					System.out.println("nop");
					do {
						selectedPiece = selectPieceToMove(d.getBoard(), d.getAlPieces());
						check = c.checkSelectedPieceInComestible(selectedPiece, comestible);
					} while (!check);
	
					do {
						if (selectedPiece.getColor() == d.getColorPlayer2()
								|| selectedPiece.getColor() == d.getKingColorPlayer1()) {
							// mooveKingToEat ??
						} // else ?
						mooved = mooveToEat(selectedPiece, comestible);
						comestible.clear();
						comestible = Check.checkIfCanEat(comestible, d);
						comestible = Check.checkIfKingCanEat(d, comestible);
	
					} while (c.checkSelectedPieceInComestible(selectedPiece, comestible));
				}
			} while (!mooved);
			if (selectedPiece.getColor() == d.getColorPlayer2() && selectedPiece.getY() == 10) {
				System.out.print("One of your piece became a king.");
				becomeKings(selectedPiece,d);
			}
			if (selectedPiece.getColor() == d.getColorPlayer1() && selectedPiece.getY() == 1) {
				System.out.print("One of your piece became a king.");
				becomeKings(selectedPiece,d);
			}
			d.setPlayer1Turn(!d.isPlayer1Turn());
		}

	}

	private boolean checkIfStillHavePieces() {
		for(Piece p : d.getAlPieces()) {
			//if(p.getColor())
		} 
		return false;
	}
	
	//--------------------------------------------------------------------------------------------------------------


	/**
	 * if the player can eat, that will force him to choose (or not if there is only one 
	 * enemy that can be eat) an enemy to eat. There is a condition to call the eat function for
	 * pawn or the king.
	 * @param selectedPiece
	 * @param comestible
	 * @return
	 */ 
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
			if (selectedPiece.getColor() == '@' || selectedPiece.getColor() == '#') {
				moveKingToEat(selectedPiece, comestible, rep);
			} else {
				eat(rep, selectedPiece);
			}
		}
		return true;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Check all direction where the king could eat,
	 * if it finds a pawn with '-' backward ,
	 * if it founds a '*', the loop will stop,
	 * then we remove all eatable pawns. 
	 * 
	 * @param selectedPiece
	 * @param comestible
	 * @param rep
	 * @return
	 */
	private boolean moveKingToEat(Piece selectedPiece, Map<Piece, int[]> comestible, int rep) {
		char enemi = d.isPlayer1Turn() ? d.getColorPlayer2() : d.getColorPlayer1();
		char kingEnemi = d.isPlayer1Turn() ? d.getKingColorPlayer2() : d.getKingColorPlayer1();

		switch (rep) {

			case 1:
				// Check if there is a pawn (or more) or a king to eat on the diagonal Northeast.
				// Then we'll move the king and eat pawns/king in his way.
				int i = 0;
				List<Coordonee> ListPositionValide = new ArrayList<Coordonee>(); // position of eatable's pawns/kings.
				do {
					// check if the pawn/king is followed by a '-' (empty case).
					if ((d.getBoard()[selectedPiece.getX() - i][selectedPiece.getY() + i] == enemi ||
							d.getBoard()[selectedPiece.getX() - i][selectedPiece.getY() + i] == kingEnemi) &&
							d.getBoard()[selectedPiece.getX() - i - 1][selectedPiece.getY() + i + 1] == '-') {
						ListPositionValide
								.add(new Coordonee(selectedPiece.getX() - i - 1, selectedPiece.getY() + i + 1));
					}
					i++;
				} while (d.getBoard()[selectedPiece.getX() - i - 1][selectedPiece.getY() + i + 1] != '*');
				
				//Like the king can eat and go further than the case the enemy, we ask the player where he wants to go
				// while is eating

				System.out.print("Where do you want to go ? : ");
				for (Coordonee mv : ListPositionValide) {
					System.out.print(mv + " ");
				}
				System.out.println("?");
				

				String reponse = Utilitaires.giveString();
				Coordonee validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				System.out.println(validPos);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) { 
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = findPiece(i, j);
							if (p != null && p.getColor() != selectedPiece.getColor()) {
								d.getBoard()[p.getX()][p.getY()] = '-';
								d.getAlPieces().remove(p);
							}
							i--;
							j++;
						}
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = '-';
						selectedPiece.setX(validPos.getX());
						selectedPiece.setY(validPos.getY());
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getColor();
					}
				}
				if (ListPositionValide.contains(validPos)) { 
					Piece p;
					i = selectedPiece.getX();
					int j = selectedPiece.getY();

					while (i != validPos.getX() && j != validPos.getY()) {
						p = findPiece(i, j);
						if (p != null && p.getColor() != selectedPiece.getColor()) {
							d.getBoard()[p.getX()][p.getY()] = '-';
							d.getAlPieces().remove(p);
						}
						i--;
						j++;
					}
					d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = '-';
					selectedPiece.setX(validPos.getX());
					selectedPiece.setY(validPos.getY());
					d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getColor();
				}

				break;
			case 3:
				i = 0;
				ListPositionValide = new ArrayList<Coordonee>(); 
				do {
				
					if ((d.getBoard()[selectedPiece.getX() + i][selectedPiece.getY() + i] == enemi ||
							d.getBoard()[selectedPiece.getX() + i][selectedPiece.getY() + i] == kingEnemi) &&
							d.getBoard()[selectedPiece.getX() + i + 1][selectedPiece.getY() + i + 1] == '-') {
						ListPositionValide
								.add(new Coordonee(selectedPiece.getX() + i + 1, selectedPiece.getY() + i + 1));
					}
					i++;
				} while (d.getBoard()[selectedPiece.getX() + i + 1][selectedPiece.getY() + i + 1] != '*');

				System.out.print("Where do you want to go ? : ");
				for (Coordonee mv : ListPositionValide) {
					System.out.print(mv.toString() + " ");
				}
				System.out.println("?");

				reponse = Utilitaires.giveString();
				validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) { 
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = findPiece(i, j);
							if (p != null && p.getColor() != selectedPiece.getColor()) {
								d.getBoard()[p.getX()][p.getY()] = '-';
								d.getAlPieces().remove(p);
							}
							i++;
							j++;
						}
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = '-';
						selectedPiece.setX(validPos.getX());
						selectedPiece.setY(validPos.getY());
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getColor();
					}
				}
				break;
			case 7:
				i = 0;
				ListPositionValide = new ArrayList<Coordonee>(); 
				do {

					if ((d.getBoard()[selectedPiece.getX() - i][selectedPiece.getY() - i] == enemi ||
							d.getBoard()[selectedPiece.getX() - i][selectedPiece.getY() - i] == kingEnemi) &&
							d.getBoard()[selectedPiece.getX() - i - 1][selectedPiece.getY() - i - 1] == '-') {
						ListPositionValide
								.add(new Coordonee(selectedPiece.getX() - i - 1, selectedPiece.getY() - i - 1));
					}
					i++;
				} while (d.getBoard()[selectedPiece.getX() - i - 1][selectedPiece.getY() - i - 1] != '*');

				System.out.print("Where do you want to go ? : ");
				for (Coordonee mv : ListPositionValide) {
					System.out.print(mv + " ");
				}
				System.out.println("?");

				reponse = Utilitaires.giveString();
				validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) { 
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = findPiece(i, j);
							if (p != null && p.getColor() != selectedPiece.getColor()) {
								d.getBoard()[p.getX()][p.getY()] = '-';
								d.getAlPieces().remove(p);
							}
							i--;
							j--;
						}
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = '-';
						selectedPiece.setX(validPos.getX());
						selectedPiece.setY(validPos.getY());
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getColor();
					}
				}
				break;
			case 9:
				i = 0;
				ListPositionValide = new ArrayList<Coordonee>(); 
				do {

					if ((d.getBoard()[selectedPiece.getX() + i][selectedPiece.getY() - i] == enemi ||
							d.getBoard()[selectedPiece.getX() + i][selectedPiece.getY() - i] == kingEnemi) &&
							d.getBoard()[selectedPiece.getX() + i + 1][selectedPiece.getY() - i - 1] == '-') {
						ListPositionValide
								.add(new Coordonee(selectedPiece.getX() + i + 1, selectedPiece.getY() - i - 1));
					}
					i++;
				} while (d.getBoard()[selectedPiece.getX() + i + 1][selectedPiece.getY() - i - 1] != '*');

				System.out.print("Where do you want to go ? : ");
				for (Coordonee mv : ListPositionValide) {
					System.out.print(mv + " ");
				}
				System.out.println("?");

				reponse = Utilitaires.giveString();
				validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) {
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = findPiece(i, j);
							if (p != null && p.getColor() != selectedPiece.getColor()) {
								d.getBoard()[p.getX()][p.getY()] = '-';
								d.getAlPieces().remove(p);
							}
							i++;
							j--;
						}
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = '-';
						selectedPiece.setX(validPos.getX());
						selectedPiece.setY(validPos.getY());
						d.getBoard()[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece.getColor();
					}
				}
				break;
			default:
				System.out.println("Error");
		}
		return true;
	}
	
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * This function will create a folder with the player's name, add a .txt call player1's name + 
	 * player2's name. It 
	 * @throws IOException
	 */
	private void initFile() throws IOException {
		System.out.println("player 1 = " + d.getPlayer1() + "\tplayer2 = " + d.getPlayer2());
		// takes the date for the history
		String dateNow = Utilitaires.giveDate();
		// Create a folder if there is no folder for the player to save their game.
		Utilitaires.createFolderForUser(d.getPlayer1());
		Utilitaires.createFolderForUser(d.getPlayer2());
		// Create name file for history
		d.setFileNameP1("./" + d.getPlayer1() + "/history " + d.getPlayer1() + " VS " + d.getPlayer2() + ".txt");
		d.setFileNameP2("./" + d.getPlayer2() + "/history " + d.getPlayer2() + " VS " + d.getPlayer1() + ".txt");
		// Write the date in both players file
		Utilitaires.newMatch(dateNow, d.getFileNameP1());
		Utilitaires.newMatch(dateNow, d.getFileNameP2());
	}

	//--------------------------------------------------------------------------------------------------------------

/**
 * Choose one piece among alPieces on the map and return this piece
 * @param map
 * @param alPieces
 * @return
 */
	public Piece selectPieceToMove(char[][] map, ArrayList<Piece> alPieces) {

		boolean selecting = true;
		do {
			System.out.println("Choose a valid position (ex : a3) : ");
			String selectedXY = Utilitaires.giveString();
			if (selectedXY.length() == 2 || selectedXY.length() == 3) {
				if (Utilitaires.isACorrectPosition(selectedXY)) {
					List<String> selected = Utilitaires.convertAJToStringNumber(selectedXY);
					for (Piece piece : d.getAlPieces()) {
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

	//--------------------------------------------------------------------------------------------------------------

/**
 * get information of one piece on the map
 * @param x
 * @param y
 * @return
 */
	public Piece findPiece(int x, int y) {
		for (Piece piece : d.getAlPieces()) {
			if (x == piece.getX()
					&& y == piece.getY()) {
				return piece;
			}
		}
		return null;
	}

	//--------------------------------------------------------------------------------------------------------------

/**
 * eat enemie piece
 change the postion , and delete piece who get eat
 * @param move
 * @param piece
 * @return
 */
	public boolean eat(int move, Piece piece) {
		int x = piece.getX();
		int y = piece.getY();
		int indexToRemove = 0;
		switch (move) {
			case 1:
				d.getBoard()[x][y] = '-';
				d.getBoard()[x - 1][y + 1] = '-';
				for (int i = 0; i < d.getAlPieces().size(); i++) {
					if (d.getAlPieces().get(i).getX() == x - 1 && d.getAlPieces().get(i).getY() == y + 1) {
						indexToRemove = i;
					}
				}
				d.getAlPieces().remove(indexToRemove);
				piece.setX(x - 2);
				piece.setY(y + 2);
				d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
				System.out.println(piece.getX() + " " + piece.getY());
				return true;
			case 3:
				d.getBoard()[x][y] = '-';
				d.getBoard()[x + 1][y + 1] = '-';
				for (int i = 0; i < d.getAlPieces().size(); i++) {
					if (d.getAlPieces().get(i).getX() == x + 1 && d.getAlPieces().get(i).getY() == y + 1) {
						indexToRemove = i;
					}
				}
				d.getAlPieces().remove(indexToRemove);
				piece.setX(x + 2);
				piece.setY(y + 2);
				System.out.println(piece.getX() + "  crash" + piece.getY());
				d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
				System.out.println(piece.getX() + " " + piece.getY());
				return true;
			case 7:
				d.getBoard()[x][y] = '-';
				d.getBoard()[x - 1][y - 1] = '-';
				for (int i = 0; i < d.getAlPieces().size(); i++) {
					if (d.getAlPieces().get(i).getX() == x - 1 && d.getAlPieces().get(i).getY() == y - 1) {
						indexToRemove = i;
					}
				}
				d.getAlPieces().remove(indexToRemove);
				piece.setX(x - 2);
				piece.setY(y - 2);
				d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
				System.out.println(piece.getX() + " " + piece.getY());
				return true;
			case 9:
				d.getBoard()[x][y] = '-';
				d.getBoard()[x + 1][y - 1] = '-';
				for (int i = 0; i < d.getAlPieces().size(); i++) {
					if (d.getAlPieces().get(i).getX() == x + 1 && d.getAlPieces().get(i).getY() == y - 1) {
						indexToRemove = i;
					}
				}
				d.getAlPieces().remove(indexToRemove);
				piece.setX(x + 2);
				piece.setY(y - 2);
				d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
				System.out.println(piece.getX() + " " + piece.getY());
				return true;
			default:
				System.out.println("default error Piece");
				return false;
		}
	}


	//--------------------------------------------------------------------------------------------------------------

	/**
	 * move choosen piece and if get to the edge become a king
	 * @param move
	 * @param piece
	 * @return
	 */
	public boolean movePieceSelected(String move, Piece piece) {
		switch (move) {
			case "1":
				if (piece.getColor() == d.getColorPlayer2() && d.getBoard()[piece.getX() - 1][piece.getY() + 1] == '-') {
					d.getBoard()[piece.getX()][piece.getY()] = '-';
					piece.setX(piece.getX() - 1);
					piece.setY(piece.getY() + 1);
					if (piece.getColor() == d.getColorPlayer2() && piece.getY() == 10) {
						System.out.print("One of your piece became a king.");
						becomeKings(piece,d);
					}
					d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
					return true;
				} else {
					return false;
				}
			case "3":
				if (piece.getColor() == d.getColorPlayer2() && d.getBoard()[piece.getX() + 1][piece.getY() + 1] == '-') {
					d.getBoard()[piece.getX()][piece.getY()] = '-';
					piece.setX(piece.getX() + 1);
					piece.setY(piece.getY() + 1);
					if (piece.getColor() == d.getColorPlayer2() && piece.getY() == 10) {
						System.out.print("One of your piece became a King.");
						becomeKings(piece,d);
					}
					d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
					return true;
				} else {
					return false;
				}
			case "7":
				if (piece.getColor() == d.getColorPlayer1() && d.getBoard()[piece.getX() - 1][piece.getY() - 1] == '-') {
					d.getBoard()[piece.getX()][piece.getY()] = '-';
					piece.setX(piece.getX() - 1);
					piece.setY(piece.getY() - 1);
					if (piece.getColor() == d.getColorPlayer1() && piece.getY() == 1) {
						System.out.print("One of your piece became a king.");
						becomeKings(piece,d);
					}
					d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
					return true;
				} else {
					return false;
				}
			case "9":
				if (piece.getColor() == d.getColorPlayer1() && d.getBoard()[piece.getX() + 1][piece.getY() - 1] == '-') {
					d.getBoard()[piece.getX()][piece.getY()] = '-';
					piece.setX(piece.getX() + 1);
					piece.setY(piece.getY() - 1);

					if (piece.getColor() == d.getColorPlayer1() && piece.getY() == 1) {
						System.out.print("One of your piece became a king.");
						becomeKings(piece,d);
					}
					d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
					return true;
				} else {
					return false;
				}
			default:
				System.out.println("default error");
				return false;
		}
	}


		//--------------------------------------------------------------------------------------------------------------

	/**
	 * move choosen piece who become a king
	 set a new position and delete the old one
	 * @param move
	 * @param piece
	 * @return
	 */
	public boolean moveKingSelected(String move, Piece piece) {
		int nbMove = 0;
		boolean cantMove = false;
		switch (move) {
			case "1":
				System.out.println("How many times do you want to go in this way?");
				nbMove = Utilitaires.giveInt();
				if ((piece.getX() - nbMove) > 0 && (piece.getY() + nbMove) <= 10) {
					for (int i = 1; i <= nbMove; i++) {
						if (d.getBoard()[piece.getX() - i][piece.getY() + i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() - nbMove);
						piece.setY(piece.getY() + nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
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
						if (d.getBoard()[piece.getX() + i][piece.getY() + i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() + nbMove);
						piece.setY(piece.getY() + nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
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
						if (d.getBoard()[piece.getX() - i][piece.getY() - i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() - nbMove);
						piece.setY(piece.getY() - nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
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
						if (d.getBoard()[piece.getX() + i][piece.getY() - i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() + nbMove);
						piece.setY(piece.getY() - nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
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


		//--------------------------------------------------------------------------------------------------------------

	/**
	 * @param piece
	 */
	public static void becomeKings(Piece piece,Data d) {
		piece.setCouleur(piece.getKingColor());
		d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
	}

	

}