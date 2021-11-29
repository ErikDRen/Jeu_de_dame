package utile;

public class Check {
    /*private Map<Piece, int[]> checkIfCanEat(Map<Piece, int[]> comestible) {
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
		// Check if piece can move in 1 3 7 9 direction
		if ((pi.getCouleur() == 'O' && board[pi.getX() - 1][pi.getY() + 1] != '-' && // check for direction 1
				board[pi.getX() + 1][pi.getY() + 1] != '-') || // check for direction 3
				(pi.getCouleur() == 'X' && board[pi.getX() - 1][pi.getY() - 1] != '-' && // check for direction 7
				board[pi.getX() + 1][pi.getY() - 1] != '-')) { // check for direction 9
			System.out.println("Can't move this piece");
			return false;
		}
		System.out.println("piece in " + pi.getX() + ", " + pi.getY() + " selected !");
		return true;
	}
    */
}
