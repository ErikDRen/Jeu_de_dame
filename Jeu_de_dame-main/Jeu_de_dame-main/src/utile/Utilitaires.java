package utile;

import java.util.Scanner;

public class Utilitaires {
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
}
