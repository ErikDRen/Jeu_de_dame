package utile;

import java.util.HashMap;
import java.util.Map;

public class Tester {
	public static Map<String, String> articleMapOne;
	public void test() {
		System.out.println(Utilitaires.isACorrectPosition("c9"));
	    articleMapOne = new HashMap<>();
	    articleMapOne.put("a", "1");
	    articleMapOne.put("b", "2");
		
	    Map<String, String> myMap = new HashMap<String, String>() {{
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

		System.out.println(Utilitaires.convertAJToStringNumber("j2"));
    
	}
}
