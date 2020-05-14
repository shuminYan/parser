package project3160;
import java.util.HashMap;

/*
 * This is a static global symbol table for initialized variables.
 */
public class Symbol {
	public static HashMap<String, Integer> symbols = new HashMap<String, Integer>();

	private Symbol() {}
	
	//Put a variable and its value in table.
	static void put(String s, int v) {
		symbols.put(s, v); 
	}
	
	//Get the value of a symbol 
	static Integer get(String s) {
		try {
			return symbols.get(s);
		} catch (Exception e) {
			throw new RuntimeException(s + " not defined");
		}
		
	}
	
	//Print all the variables in symbol table
	static void print() {
		for(String k : symbols.keySet())
			System.out.println(k + " = " + symbols.get(k));
	}
	
	//Clear current symbols table
	static void clear() {
		symbols.clear();
	}
}
