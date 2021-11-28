package model;

import game.Coordonee;

public class Piece {
	
	int x, y;
	Coordonee coordoneeXY; // Va falloir remplacé tout les setX et setY par des setCoordoneeXY
	char couleur;
	char kingColor;
	public char getKingColor() {
		return kingColor;
	}
	public void setKingColor(char kingColor) {
		this.kingColor = kingColor;
	}
	boolean goBack;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public char getCouleur() {
		return couleur;
	}
	public void setCouleur(char couleur) {
		this.couleur = couleur;
	}
	public Coordonee getCoordoneeXY() {
		return coordoneeXY;
	}
	public void setCoordoneeXY(Coordonee coordoneeXY) {
		this.coordoneeXY = coordoneeXY;
	}
	public boolean isGoBack() {
		return goBack;
	}
	public void setGoBack(boolean goBack) {
		this.goBack = goBack;
	}
	public Piece(int x, int y, char couleur, char kingColor, boolean goBack) {
		super();
		this.x = x;
		this.y = y;
		this.couleur = couleur;
		this.goBack = goBack;
		this.kingColor = kingColor;
	}
	@Override
	public String toString() {
		return "Piece [x=" + x + ", y=" + y + ", couleur=" + couleur + ", goBack=" + goBack + "]";
	}
	

}
