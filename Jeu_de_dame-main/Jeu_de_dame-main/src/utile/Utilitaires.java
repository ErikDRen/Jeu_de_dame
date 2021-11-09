package utile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	

	
	public static void saveTab(char[][] tabMap, String fileNames) throws IOException {
			
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileNames,true));
	    
		StringBuffer str = new StringBuffer();
		for (char[] i : tabMap) {
			str.append(i);
			str.append("\n");
		}
		str.append("\n");
		
		writer.write(str.toString()); 
	    writer.close();
	}
	
	
	public static void createFolderForUser(String nom) throws IOException {
		
		Path path = Paths.get("./"+ nom);

	    //java.nio.file.Files;
	    Files.createDirectories(path);
		
	}
}
