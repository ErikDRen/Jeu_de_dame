package model;

import game.Coordonee;
import game.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Data;
import utile.Check;
import utile.Utilitaires;

public class IA {


    


    public ArrayList<Piece> getAlPiecesCanMoveIA(Data d) {
    	
    	ArrayList<Piece> temp = new ArrayList<Piece>();
    	
        for (Piece p : d.getAlPieces())
        {
        	//Check.checkIfPieceCanMove(p, d);
        	if(Check.checkIfPieceCanMove(p, d) == true) {
        		temp.add(p);
        		return temp;
        	}
        }
        System.out.println("nothing can move !");
		return null;
    } 

    // -------------------------------------------------------------------------------------------------------------

    public Piece selectPieceIA(Data d) {
    	
    	ArrayList<Piece> temp;
    	temp = getAlPiecesCanMoveIA(d);

        String location = temp.get(Utilitaires.getRandomNumberInRange(0, temp.size())).toString();
		boolean selecting = true;
		do {
			//System.out.println("Choose a valid position (ex : a3) : ");
			String selectedXY = location;
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

    // -------------------------------------------------------------------------------------------------------------

    public boolean movePieceSelectedIA(Piece piece, Data d) {
        switch (Utilitaires.getRandomNumberInRange(1, 4)) {
            case 1:
                if (piece.getColor() == d.getColorPlayer2()
                        && d.getBoard()[piece.getX() - 1][piece.getY() + 1] == '-') {
                    d.getBoard()[piece.getX()][piece.getY()] = '-';
                    piece.setX(piece.getX() - 1);
                    piece.setY(piece.getY() + 1);
                    if (piece.getColor() == d.getColorPlayer2() && piece.getY() == 10) {
                        System.out.print("One of your piece became a king.");
                        Game.becomeKings(piece, d);
                    }
                    d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (piece.getColor() == d.getColorPlayer2()
                        && d.getBoard()[piece.getX() + 1][piece.getY() + 1] == '-') {
                    d.getBoard()[piece.getX()][piece.getY()] = '-';
                    piece.setX(piece.getX() + 1);
                    piece.setY(piece.getY() + 1);
                    if (piece.getColor() == d.getColorPlayer2() && piece.getY() == 10) {
                        System.out.print("One of your piece became a King.");
                        Game.becomeKings(piece, d);
                    }
                    d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
                    return true;
                } else {
                    return false;
                }
            case 3:
                if (piece.getColor() == d.getColorPlayer1()
                        && d.getBoard()[piece.getX() - 1][piece.getY() - 1] == '-') {
                    d.getBoard()[piece.getX()][piece.getY()] = '-';
                    piece.setX(piece.getX() - 1);
                    piece.setY(piece.getY() - 1);
                    if (piece.getColor() == d.getColorPlayer1() && piece.getY() == 1) {
                        System.out.print("One of your piece became a king.");
                        Game.becomeKings(piece, d);
                    }
                    d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
                    return true;
                } else {
                    return false;
                }
            case 4:
                if (piece.getColor() == d.getColorPlayer1()
                        && d.getBoard()[piece.getX() + 1][piece.getY() - 1] == '-') {
                    d.getBoard()[piece.getX()][piece.getY()] = '-';
                    piece.setX(piece.getX() + 1);
                    piece.setY(piece.getY() - 1);

                    if (piece.getColor() == d.getColorPlayer1() && piece.getY() == 1) {
                        System.out.print("One of your piece became a king.");
                        Game.becomeKings(piece, d);
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

    // --------------------------------------------------------------------------------------------------------------


	public boolean moveKingSelectedIA(Data d, Piece piece) {
		int nbMove = 0;
		boolean cantMove = false;
		switch (Utilitaires.getRandomNumberInRange(1, 4)) {
			case 1:
				//System.out.println("How many times do you want to go in this way?");
				nbMove = Utilitaires.getRandomNumberInRange(1, 9);
				if ((piece.getX() - nbMove) > 0 && (piece.getY() + nbMove) <= 10) {
					for (int i = 1; i <= nbMove; i++) {
						if (d.getBoard()[piece.getX() - i][piece.getY() + i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						//System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() - nbMove);
						piece.setY(piece.getY() + nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
						return true;
					}
				} else {
					//System.out.println("Too high.");
					return false;
				}
			case 2:
				//System.out.println("How many times do you want to go in this way?");
				nbMove = Utilitaires.getRandomNumberInRange(1, 9);
				if ((piece.getX() + nbMove) <= 10 && (piece.getY() + nbMove) <= 10) {
					for (int i = 1; i <= nbMove; i++) {
						if (d.getBoard()[piece.getX() + i][piece.getY() + i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						//System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() + nbMove);
						piece.setY(piece.getY() + nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
						return true;
					}
				} else {
					//System.out.println("Too high.");
					return false;
				}
				// break;
			case 3:
				//System.out.println("How many times do you want to go in this way?");
				nbMove = Utilitaires.getRandomNumberInRange(1, 9);
				if ((piece.getX() - nbMove) > 0 && (piece.getY() - nbMove) > 0) {

					for (int i = 1; i <= nbMove; i++) {
						if (d.getBoard()[piece.getX() - i][piece.getY() - i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						//System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() - nbMove);
						piece.setY(piece.getY() - nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
						return true;
					}
				} else {
					//System.out.println("Too high.");
					return false;
				}
				// break;
			case 4:
				//System.out.println("How many times do you want to go in this way?");
				nbMove = Utilitaires.getRandomNumberInRange(1, 9);
				if ((piece.getX() + nbMove) <= 10 && (piece.getY() - nbMove) > 0) {
					for (int i = 1; i <= nbMove; i++) {
						if (d.getBoard()[piece.getX() + i][piece.getY() - i] != '-') {
							cantMove = true;
						}
					}
					if (cantMove == true) {
						//System.out.println("Bad range, insert another one.");
						return false;
					} else {
						piece.setX(piece.getX() + nbMove);
						piece.setY(piece.getY() - nbMove);
						d.getBoard()[piece.getX()][piece.getY()] = piece.getColor();
						return true;
					}
				} else {
					//System.out.println("Too high.");
					return false;
				}
				// break;
			default:
				//System.out.println("default error");
				return false;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public boolean eatIA(Data d, Piece piece) {
		int x = piece.getX();
		int y = piece.getY();
		int indexToRemove = 0;
		switch (Utilitaires.getRandomNumberInRange(1, 4)) {
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
			case 2:
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
			case 3:
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
			case 4:
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
				//System.out.println("default error Piece");
				return false;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	private boolean moveKingToEat(Piece selectedPiece, Map<Piece, int[]> comestible,  Data d) {
		char enemi = d.isPlayer1Turn() ? d.getColorPlayer2() : d.getColorPlayer1();
		char kingEnemi = d.isPlayer1Turn() ? d.getKingColorPlayer2() : d.getKingColorPlayer1();

		switch (Utilitaires.getRandomNumberInRange(0, 4)) {

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

				//System.out.print("Where do you want to go ? : ");
				//for (Coordonee mv : ListPositionValide) {
					//System.out.print(mv + " ");
				//}
				//System.out.println("?");
				

				String reponse = ListPositionValide.get(Utilitaires.getRandomNumberInRange(0, ListPositionValide.size())).toString();
				Coordonee validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				//System.out.println(validPos);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) { 
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = Game.findPiece(i, j,d);
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
						p = Game.findPiece(i, j,d);
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
			case 2:
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

				//System.out.print("Where do you want to go ? : ");
				//for (Coordonee mv : ListPositionValide) {
					//System.out.print(mv.toString() + " ");
				//}
				//System.out.println("?");

				reponse = ListPositionValide.get(Utilitaires.getRandomNumberInRange(0, ListPositionValide.size())).toString();
				validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) { 
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = Game.findPiece(i, j,d);
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
			case 3:
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

				//System.out.print("Where do you want to go ? : ");
				//for (Coordonee mv : ListPositionValide) {
				//	System.out.print(mv + " ");
				//}
				//System.out.println("?");

				reponse = ListPositionValide.get(Utilitaires.getRandomNumberInRange(0, ListPositionValide.size())).toString();
				validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) { 
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = Game.findPiece(i, j,d);
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
			case 4:
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

				//System.out.print("Where do you want to go ? : ");
				//for (Coordonee mv : ListPositionValide) {
				//	System.out.print(mv + " ");
				//}
				//System.out.println("?");

				reponse = ListPositionValide.get(Utilitaires.getRandomNumberInRange(0, ListPositionValide.size())).toString();
				validPos = Utilitaires.convertStringNumberToCoordonee(reponse);
				for (Coordonee co : ListPositionValide) {
					if (co.getX() == validPos.getX() && co.getY() == validPos.getY()) {
						Piece p;
						i = selectedPiece.getX();
						int j = selectedPiece.getY();

						while (i != validPos.getX() && j != validPos.getY()) {
							p = Game.findPiece(i, j,d);
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
				System.out.println("ErrorIA");
		}
		return true;
	}
}
