package utile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import model.Piece;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Utilitaires {
	
	//Map for coordinates
    static Map<String, String> myMap = new HashMap<String, String>() {{
        put("a", "1");
        put("b", "2");
        put("c", "3");
        put("d", "4");
        put("e", "5");
        put("f", "6");
        put("g", "7");
        put("h", "8");
        put("i", "9");
        put("j", "10");
    }};
    
   
	public static String giveString() {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		return s;
	}
	
	
	public static int giveInt() {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		return num;
	}
	
	public static boolean isACorrectPosition(String s) {
		return s.matches("[a-j]+([1-9]|10)");
	}

	public static List<String> convertAJToNumber(String selectedXY) {
		String AJ = myMap.get(String.valueOf(selectedXY.charAt(0)));
		List<String> res = Arrays.asList(AJ,String.valueOf(selectedXY.charAt(1)) );
		return res;
	}
	
	
	//This function will create a file .txt to save the map each turn 
	public static void saveTab(char[][] tabMap,String FILENAME, int sizeY) throws IOException {
		

			
		//BufferedWriter writer = new BufferedWriter(new FileWriter("./" + player1Name + " VS " + player2Name + " at " + dateNow + ".txt",true));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME,true));
	    
		StringBuffer str = new StringBuffer();

		
		for (int i = 0; i < tabMap.length; i++) {



			for (int j = 0; j < tabMap[i].length; j++) {
				str.append(tabMap[j][i]);
				
				
			}

			str.append('\n');

		}
		
		
		str.append("\n");
		
		writer.write(str.toString()); 
	    writer.close();
	}
	
	
	
	//And put it in a directory which is the player's name
	public static void createFolderForUser(String playerName) throws IOException {
		
		Path path = Paths.get("./"+ playerName);

	    //java.nio.file.Files;
	    Files.createDirectories(path);
		
	}
	
	


	//This function will give the date
	public static String giveDate() {
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
	    String dateNow = s.format(date);
	    return dateNow;
	  }


	// We will write the date in the history file
	public static void newMatch(String dateNow, String fileName) throws IOException {
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
	    
		StringBuffer str = new StringBuffer();

			str.append("This match was played at ");
			str.append(dateNow);
			str.append("\n");
			str.append("\n");
		
		writer.write(str.toString()); 
	    writer.close();
		
		
		
	}

		// This function will print the board "clear and pretty"
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

		public static void createPieces(char[][] map, ArrayList<Piece> alPieces) {
		// TODO Auto-generated method stub
		int xEndLine = 4;
		int oEndLine = 10;
		for (int x = 1; x < map.length-2; x += 2) {
			for (int i = 1; i < xEndLine; i+=2) {
				alPieces.add(new Piece(x, i, 'O', false));
				alPieces.add(new Piece(x + 1, i+1, 'O', false));	
			}
			for (int i = 7; i < oEndLine; i+=2) {
				alPieces.add(new Piece(x, i, 'X', false));
				alPieces.add(new Piece(x + 1, i+1, 'X', false));
			}
		}
	}

	public static void fillTab(char[][] map, ArrayList<Piece> alPieces) {
		// TODO Auto-generated method stub

		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = '-';
				map[0][j] = '*';
				map[map.length - 1][j] = '*';
			}
			map[i][0] = '*';
			map[i][map[i].length - 1] = '*';
		}
		for (Piece piece : alPieces) {
			map[piece.getX()][piece.getY()] = piece.getCouleur();
		}
	}

	
}
