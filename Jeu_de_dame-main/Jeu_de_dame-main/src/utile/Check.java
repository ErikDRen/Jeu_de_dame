package utile;

import java.util.Map;

import data.Data;
import model.Piece;

public class Check {

	//--------------------------------------------------------------------------------------------------------------

	public static boolean checkIfPlayerCanMove(Data d) {
		char color = d.isPlayer1Turn() ? d.getColorPlayer1() : d.getColorPlayer2();
		char kingColor = d.isPlayer1Turn() ? d.getKingColorPlayer1() : d.getKingColorPlayer2();
		for (Piece p : d.getAlPieces()) {
			if (p.getColor() == color || p.getColor() == kingColor) {
				if (checkIfPieceCanMove(p, d)) {
					return true;
				}
			}
		}
		return false;
	}

	//--------------------------------------------------------------------------------------------------------------

	public static boolean checkSelectedPieceInComestible(Piece selectedPiece, Map<Piece, int[]> comestible) {
		if (comestible.containsKey(selectedPiece)) {
			System.out.println("piece can eat");
			return true;
		}
		System.out.println("another piece can eat !");
		return false;
	}

	//--------------------------------------------------------------------------------------------------------------

	public static boolean checkSelectedPiece(Piece pi, Data d) {
		if ((!d.isPlayer1Turn() && (pi.getColor() == d.getColorPlayer1() || pi.getColor() == d.getKingColorPlayer1())
				|| (d.isPlayer1Turn()) && (pi.getColor() == d.getColorPlayer2() || pi.getColor() == d.getKingColorPlayer2()))) {
			System.out.println("it's not the turn of this piece");
			return false;
		}
		if (!checkIfPieceCanMove(pi, d)) {
			return false;
		}
		System.out.println("piece in " + pi.getX() + ", " + pi.getY() + " selected !");
		return true;
	}

	//--------------------------------------------------------------------------------------------------------------

	public static boolean checkIfPieceCanMove(Piece pi, Data d) {
		// Check if piece can move in 1 3 7 9 direction
		if ((pi.getColor() == d.getColorPlayer2() && d.getBoard()[pi.getX() - 1][pi.getY() + 1] != '-' && // check for direction 1
				d.getBoard()[pi.getX() + 1][pi.getY() + 1] != '-') || // check for direction 3
				(pi.getColor() == d.getColorPlayer1() && d.getBoard()[pi.getX() - 1][pi.getY() - 1] != '-' && // check for direction 7
					d.getBoard()[pi.getX() + 1][pi.getY() - 1] != '-')) { // check for direction 9
			System.out.println("Can't move this piece");
			return false;
		}
		return true;
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public static Map<Piece, int[]> checkIfCanEat(Map<Piece, int[]> comestible, Data d) {
		char color = d.isPlayer1Turn() ? d.getColorPlayer1() : d.getColorPlayer2();
		char enemi = d.isPlayer1Turn() ? d.getColorPlayer2() : d.getColorPlayer1();
		char kingEnemi = d.isPlayer1Turn() ? d.getKingColorPlayer2() : d.getKingColorPlayer1();
		
		for (Piece pi : d.getAlPieces()) {
			if (pi.getColor() == color) {
				if (d.getBoard()[pi.getX() - 1][pi.getY() + 1] == enemi
						|| d.getBoard()[pi.getX() - 1][pi.getY() + 1] == kingEnemi) {
					if (d.getBoard()[pi.getX() - 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 1 });
					}
				}
				if (d.getBoard()[pi.getX() + 1][pi.getY() + 1] == enemi
						|| d.getBoard()[pi.getX() + 1][pi.getY() + 1] == kingEnemi) {
					if (d.getBoard()[pi.getX() + 2][pi.getY() + 2] == '-') {
						comestible.put(pi, new int[] { 3 });
					}
				}
				if (d.getBoard()[pi.getX() - 1][pi.getY() - 1] == enemi
						|| d.getBoard()[pi.getX() - 1][pi.getY() - 1] == kingEnemi) {
					if (d.getBoard()[pi.getX() - 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 7 });
					}
				}
				if (d.getBoard()[pi.getX() + 1][pi.getY() - 1] == enemi
						|| d.getBoard()[pi.getX() + 1][pi.getY() - 1] == kingEnemi) {
					if (d.getBoard()[pi.getX() + 2][pi.getY() - 2] == '-') {
						comestible.put(pi, new int[] { 9 });
					}
				}
			}
		}
		return comestible;
	}

	//--------------------------------------------------------------------------------------------------------------

	public static Map<Piece, int[]> checkIfKingCanEat(Data d,
			Map<Piece, int[]> comestible) {

		char kingAlly = d.isPlayer1Turn() ? d.getKingColorPlayer1() : d.getKingColorPlayer2();
		char enemi = d.isPlayer1Turn() ? d.getColorPlayer2() : d.getColorPlayer1();
		char kingEnemi = d.isPlayer1Turn() ? d.getKingColorPlayer2() : d.getKingColorPlayer1();
		
		for (Piece pi : d.getAlPieces()) {
			int i = 0;
			if (pi.getColor() == kingAlly) {
				// 1
				do {
					i++;
					if (d.getBoard()[pi.getX() - i][pi.getY() + i] == enemi
							|| d.getBoard()[pi.getX() - i][pi.getY() + i] == kingEnemi) {
						if (d.getBoard()[pi.getX() - i - 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 1 });
						}
					}
				} while (d.getBoard()[pi.getX() - i][pi.getY() + i] != '*');
				i = 0;
				// 3
				do {
					i++;
					if (d.getBoard()[pi.getX() + i][pi.getY() + i] == enemi
							|| d.getBoard()[pi.getX() + i][pi.getY() + i] == kingEnemi) {
						if (d.getBoard()[pi.getX() + i + 1][pi.getY() + i + 1] == '-') {
							comestible.put(pi, new int[] { 3 });
						}
					}
				} while (d.getBoard()[pi.getX() + i][pi.getY() + i] != '*');
				i = 0;
				// 7
				do {
					i++;
					if (d.getBoard()[pi.getX() - i][pi.getY() - i] == enemi
							|| d.getBoard()[pi.getX() - i][pi.getY() - i] == kingEnemi) {

						if (d.getBoard()[pi.getX() - i - 1][pi.getY() - i - 1] == '-') {
							comestible.put(pi, new int[] { 7 });
						}
					}
				} while (d.getBoard()[pi.getX() - i][pi.getY() - i] != '*');
				i = 0;
				// 9
				do {
					i++;
					if (d.getBoard()[pi.getX() + i][pi.getY() - i] == enemi
							|| d.getBoard()[pi.getX() + i][pi.getY() - i] == kingEnemi) {

						if (d.getBoard()[pi.getX() + i + 1][pi.getY() - i - 1] == '-') {
							comestible.put(pi, new int[] { 9 });
						}
					}
				} while (d.getBoard()[pi.getX() + i][pi.getY() - i] != '*');
			}
		}
		return comestible;
	}
}
