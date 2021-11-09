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

public class Utilitaires {
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
