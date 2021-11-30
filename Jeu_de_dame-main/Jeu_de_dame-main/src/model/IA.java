package model;

import java.util.ArrayList;
import java.util.List;
import model.Piece;
import data.Data;
import utile.Check;
import utile.Utilitaires;

public class IA {

    List<Piece> temp = new ArrayList<Piece>();

    public List<Piece> getAlPiecesCanMoveIA(Data d) {
        for (Piece p : d.getAlPieces())
        {
            if (Check.checkIfPieceCanMove(p, d) == true) {
            
            }
        }
    }

	// public boolean checkIfPieceCanMoveIA(Piece pi, Data d) {
	// 	// Check if piece can move in 1 3 7 9 direction
	// 	if ((pi.getColor() == d.getColorPlayer2() && d.getBoard()[pi.getX() - 1][pi.getY() + 1] != '-' && // check for direction 1
	// 			d.getBoard()[pi.getX() + 1][pi.getY() + 1] != '-') || // check for direction 3
	// 			(pi.getColor() == d.getColorPlayer1() && d.getBoard()[pi.getX() - 1][pi.getY() - 1] != '-' && // check for direction 7
	// 					d.getBoard()[pi.getX() + 1][pi.getY() - 1] != '-')) { // check for direction 9
	// 		System.out.println("Can't move this piece");
	// 		return false;
	// 	}
	// 	return true;
	// }

    // -------------------------------------------------------------------------------------------------------------

    public Piece selectPieceIA(char[][] map, ArrayList<Piece> alPieces, Data d) {

        String[] letters = {"a","b","c","d","e","f","g","h","i","j"};
        String location = letters[Utilitaires.getRandomNumberInRange(0,9)] + Utilitaires.getRandomNumberInRange(1,10);
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
                        becomeKings(piece);
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
                        becomeKings(piece);
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
                        becomeKings(piece);
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
                        becomeKings(piece);
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
