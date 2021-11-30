package utile;

import java.util.Map;

import data.Data;
import model.Piece;

public class Check {

	/**
	 * 
	 * @param d
	 * @return
	 */
	public boolean checkIfPlayerCanMove(Data d) {
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

	/**
	 * 
	 * @param selectedPiece
	 * @param comestible
	 * @return
	 */
	public boolean checkSelectedPieceInComestible(Piece selectedPiece, Map<Piece, int[]> comestible) {
		if (comestible.containsKey(selectedPiece)) {
			System.out.println("piece can eat");
			return true;
		}
		System.out.println("another piece can eat !");
		return false;
	}

	/**
	 * 
	 * @param pi
	 * @param d
	 * @return
	 */
	public boolean checkSelectedPiece(Piece pi, Data d) {
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

	/**
	 * 
	 * @param pi
	 * @param d
	 * @return
	 */
	public boolean checkIfPieceCanMove(Piece pi, Data d) {
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
}
