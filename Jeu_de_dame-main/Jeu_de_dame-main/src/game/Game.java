package game;

import java.util.ArrayList;

import model.Piece;
import utile.Utilitaires;

public class Game {
	
	int sizeX = 12;
	int sizeY = 12;
	
	char[][] tabMap;
	
	boolean gameOn = true;
	
	ArrayList<Piece> alPieces = new ArrayList<Piece>();
	
	public void game() {
		// TODO Auto-generated method stub
		tabMap = new char[sizeX][sizeY];
		createPiece();
		do {
			fillTab(tabMap, alPieces);
			printTab(tabMap,sizeY,sizeX);
			selectPieceToMove(tabMap,alPieces);			
		} while (gameOn);
	}

	private void createPiece() {
		// TODO Auto-generated method stub
		Piece p1 = new Piece(1, 1, 'R', false);
		Piece p2 = new Piece(3, 1, 'R', false);
		alPieces.add(p1);
		alPieces.add(p2);
	}

	private void fillTab(char[][] map, ArrayList<Piece> alPieces) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < map.length; i++) {
			
			for(int j = 0; j < map[i].length; j++) {
				map[i][j]='-';
				map[0][j]='*';
				map[map.length-1][j]='*';
				
			}
			map[i][0]='*';
			map[i][map[i].length-1]='*';
		}
		for (Piece piece : alPieces) {
			map[piece.getX()][piece.getY()]= piece.getCouleur();
		}
	}
	
	
	
	public static void printTab(char[][] map, int sizeY, int sizeX) {
		char c = 'a';
		System.out.print("  ");

		
		for(int i = 0; i<map.length; i++) {
			if (i != 0 && i != sizeY - 1) {
				System.out.print(c);
				c++;
				System.out.print(" ");
				
			}
			
			if (i == sizeY - 1) {
				System.out.print("  ");
			}
			
			for(int j = 0; j<map[i].length; j++) {
				System.out.print(map[j][i]);
				System.out.print(" ");
			}

			System.out.println();
			
			
		}
		System.out.print("    ");
		for(int i = 0; i < sizeX; i++) {
			if (i != 0 && i != sizeX - 1) {
				System.out.print(i);
				System.out.print(" ");
			}
			
			
			
			
		}
	}
	
	public static void selectPieceToMove(char[][] map, ArrayList<Piece> alPieces) {
		int selectedX = Utilitaires.giveInt();
		int selectedY = Utilitaires.giveInt();
		for (Piece piece : alPieces) {
			//map[piece.getX()][piece.getY()]= piece.getCouleur();
			if(selectedX == piece.getX() && selectedY == piece.getY()) {
				piece.setCouleur('T');
				//juste un test pour voir si la selection marche
			}
		}
	}
	
	

}
