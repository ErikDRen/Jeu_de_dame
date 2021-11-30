package model;

import java.io.IOException;
import game.Game;
import java.util.ArrayList;
import java.util.List;
import model.Piece;
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

}
