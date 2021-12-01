package utile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import data.Data;
import game.Coordonee;
import model.Piece;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Utilitaires {

	// --------------------------------------------------------------------------------------------------------------

	public static String giveString() {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		return s;
	}

	// --------------------------------------------------------------------------------------------------------------

	public static int giveInt() {
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextInt()) {
			int a = sc.nextInt();
			return a;
		} else {
			return 3;
		}
	}

	// --------------------------------------------------------------------------------------------------------------

	public static Map<String, String> AJMapToStringNumber = new HashMap<String, String>() {
		{
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
		}
	};

	// --------------------------------------------------------------------------------------------------------------

	static Map<String, String> StringNumberMapToAJ = new HashMap<String, String>() {
		{
			put("1", "a");
			put("2", "b");
			put("3", "c");
			put("4", "d");
			put("5", "e");
			put("6", "f");
			put("7", "g");
			put("8", "h");
			put("9", "i");
			put("10", "j");
		}
	};

	// --------------------------------------------------------------------------------------------------------------

	public static Coordonee convertStringNumberToCoordonee(String reponse) {
		String[] parts = reponse.split(",");

		if (parts.length != 2) {
			return null;

		}
		Coordonee coordonee = new Coordonee(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		System.out.println(coordonee);
		return coordonee;
	}

	// --------------------------------------------------------------------------------------------------------------

	public static String convertCoordoneeToAJ(Coordonee co) {
		String X = co.getStringX();
		String Y = co.getStringY();
		return StringNumberMapToAJ.get(Y) + X;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isACorrectPosition(String s) {
		return s.matches("[a-j]+([1-9]|10)");
	}

	/**
	 * 
	 * @param selectedXY
	 * @return
	 */
	public static List<String> convertAJToStringNumber(String selectedXY) {
		List<String> res = null;
		String AJ = AJMapToStringNumber.get(String.valueOf(selectedXY.charAt(0)));
		if (selectedXY.length() == 3) {
			String numberPart = String.valueOf(selectedXY.charAt(1)) + String.valueOf(selectedXY.charAt(2));
			res = Arrays.asList(AJ, numberPart);

		} else {
			res = Arrays.asList(AJ, String.valueOf(selectedXY.charAt(1)));

		}
		System.out.println(res);
		return res;
	}

	/**
	 * saveTab will write in the player file the map;
	 * 
	 * @param tabMap
	 * @param FILENAME
	 * @param sizeY
	 * @throws IOException
	 */
	public static void saveTab(char[][] tabMap, String FILENAME, int sizeY) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true));

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

	/**
	 * It creates a folder with the player’s name
	 * 
	 * @param playerName
	 * @throws IOException
	 */
	public static void createFolderForUser(String playerName) throws IOException {

		Path path = Paths.get("./" + playerName);

		// java.nio.file.Files;
		Files.createDirectories(path);

	}

	/**
	 * It will return the current date, to know when the match has been played
	 * 
	 * @return
	 */
	public static String giveDate() {
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dateNow = s.format(date);
		return dateNow;
	}

	/**
	 * It will write in the player file when he will play a new match.
	 * 
	 * @param dateNow
	 * @param fileName
	 * @throws IOException
	 */
	public static void newMatch(String dateNow, String fileName) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

		StringBuffer str = new StringBuffer();

		str.append("This match was played at ");
		str.append(dateNow);
		str.append("\n");
		str.append("\n");

		writer.write(str.toString());
		writer.close();

	}

	// This function will print the board "clear and pretty"
	/**
	 * 
	 * @param map
	 * @param sizeX
	 * @param sizeY
	 */
	public static void printTab(char[][] map, int sizeX, int sizeY) {
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

	/**
	 * This function will creates the pawns
	 * 
	 * @param d
	 */
	public static void createPieces(Data d) {
		// TODO Auto-generated method stub
		int xEndLine = 4;
		int oEndLine = 10;
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Uncomment /*2*/ for practical test !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		for (int x = 1; x < d.getBoard().length - 2 /* 2 */; x += 2) {
			for (int i = 1; i < xEndLine; i += 2) {
				d.getAlPieces().add(new Piece(x, i, d.getColorPlayer2(), d.getKingColorPlayer2(), false));
				d.getAlPieces().add(new Piece(x + 1, i + 1, d.getColorPlayer2(), d.getKingColorPlayer2(), false));
			}
			for (int i = 7; i < oEndLine; i += 2) {
				d.getAlPieces().add(new Piece(x, i, d.getColorPlayer1(), d.getKingColorPlayer1(), true));
				d.getAlPieces().add(new Piece(x + 1, i + 1, d.getColorPlayer1(), d.getKingColorPlayer1(), false));
			}
		}
	}

	/**
	 * This function will creates the board and put the pawns in it
	 * 
	 * @param map
	 * @param alPieces
	 */
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
			map[piece.getX()][piece.getY()] = piece.getColor();
		}
	}

	/**
	 * The player can choose his
	 * 
	 * @param d
	 * @param pseudo
	 * @param skin
	 */
	public static void chooseAPseudo(Data d, String pseudo, char skin) {
		if (pseudo == d.getPlayer1()) {
			d.setColorPlayer1(skin);
		} else if (pseudo == d.getPlayer2()) {
			d.setColorPlayer2(skin);
		} else {
			System.out.println("!!! Un probleme est survenue durant l'attribution de votre skin !!!");
		}
	}

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
