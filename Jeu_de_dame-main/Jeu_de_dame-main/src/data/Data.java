package data;

import java.util.ArrayList;

import model.Piece;

public class Data {

	String player1 = "";
	String player2 = "";

	char colorPlayer1;
	char colorPlayer2;

	char kingColorPlayer1;
	char kingColorPlayer2;

	String fileNameP1 = "";
	String fileNameP2 = "";

	boolean player1Turn = true;

	int sizeX = 12;
	int sizeY = 12;
	char[][] board;

	// final static String FILENAME = "./history.txt";
	boolean gameOn = true;
	ArrayList<Piece> alPieces = new ArrayList<Piece>();

	// List for kings
	ArrayList<Piece> alKings = new ArrayList<Piece>();

	public char getKingColorPlayer1() {
		return kingColorPlayer1;
	}

	public void setKingColorPlayer1(char kingColorPlayer1) {
		this.kingColorPlayer1 = kingColorPlayer1;
	}

	public char getKingColorPlayer2() {
		return kingColorPlayer2;
	}

	public void setKingColorPlayer2(char kingColorPlayer2) {
		this.kingColorPlayer2 = kingColorPlayer2;
	}

	public char getColorPlayer1() {
		return colorPlayer1;
	}

	public void setColorPlayer1(char player1) {
		this.colorPlayer1 = player1;
	}

	public char getColorPlayer2() {
		return colorPlayer2;
	}

	public void setColorPlayer2(char player2) {
		this.colorPlayer2 = player2;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getFileNameP1() {
		return fileNameP1;
	}

	public void setFileNameP1(String fileNameP1) {
		this.fileNameP1 = fileNameP1;
	}

	public String getFileNameP2() {
		return fileNameP2;
	}

	public void setFileNameP2(String fileNameP2) {
		this.fileNameP2 = fileNameP2;
	}

	public boolean isPlayer1Turn() {
		return player1Turn;
	}

	public void setPlayer1Turn(boolean player1Turn) {
		this.player1Turn = player1Turn;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public boolean isGameOn() {
		return gameOn;
	}

	public void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
	}

	public ArrayList<Piece> getAlPieces() {
		return alPieces;
	}

	public void setAlPieces(ArrayList<Piece> alPieces) {
		this.alPieces = alPieces;
	}

	public ArrayList<Piece> getAlKings() {
		return alKings;
	}

	public void setAlKings(ArrayList<Piece> alKings) {
		this.alKings = alKings;
	}

}
