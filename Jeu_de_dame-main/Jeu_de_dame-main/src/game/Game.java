package game;

import java.io.IOException;
import java.util.ArrayList;
import model.Piece;
import utile.Utilitaires;

public class Game {
		
	int nbPiecesO = 20;
	int nbPiecesX = 20;
	
	int sizeX = 12;
	int sizeY = 12;
	
	
	
	char[][] tabMap;
	
	final static String FILENAME = "./history.txt";
	
	boolean gameOn = true;
	
	String nom = "Grégoire";
	
	
	
	ArrayList<Piece> alPieces = new ArrayList<Piece>();
	
	public void game() throws IOException {
		// TODO Auto-generated method stub
		tabMap = new char[sizeX][sizeY];
		createPieceO();
		createPieceX();
		do {
			fillTab(tabMap, alPieces);
			printTab(tabMap, sizeY, sizeX);
			selectPieceToMove(tabMap, alPieces);
			Utilitaires.saveTab(tabMap,FILENAME);
			Utilitaires.createFolderForUser(nom);
		} while (gameOn);
	}
	
	
	private void createPieceO() {
		// TODO Auto-generated method stub
		for (int i = 0; i < nbPiecesO;) {
			for (int x = 1; x < 11; x += 2) {
				alPieces.add(new Piece(x, 1, 'O', false));
				alPieces.add(new Piece(x + 1, 2, 'O', false));
				alPieces.add(new Piece(x, 3, 'O', false));
				alPieces.add(new Piece(x + 1, 4, 'O', false));
				i++;
			}
		}
	}

	private void createPieceX() {
		// TODO Auto-generated method stub
		for (int i = 0; i < nbPiecesX;) {
			for (int x = 1; x < 11; x += 2) {
				alPieces.add(new Piece(x, 7, 'X', false));
				alPieces.add(new Piece(x + 1, 8, 'X', false));
				alPieces.add(new Piece(x, 9, 'X', false));
				alPieces.add(new Piece(x + 1, 10, 'X', false));
				i++;
			}
		}
		}

	private void fillTab(char[][] map, ArrayList<Piece> alPieces) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = '-';
				map[0][j] = '*';
				map[map.length - 1][j] = '*';
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

		
		for (int i = 0; i < map.length; i++) {
			if (i != 0 && i != sizeY - 1) {
				System.out.print(c);
				c++;
				System.out.print(" ");
				
			}
			
			if (i == sizeY - 1) {
				System.out.print("  ");
			}
			
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[j][i]);
				System.out.print(" ");
			}

			System.out.println();
			
			
		}
		System.out.print("    ");
		for (int i = 0; i < sizeX; i++) {
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
