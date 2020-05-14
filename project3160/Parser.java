package project3160;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Parser {
    private String s;
    private int currIndex;
    private int n;
    private char inputToken;

    //constructor 
	public Parser(String s) {
        this.s = s;
        currIndex = 0;
        n = s.length();
        nextToken();
        Symbol.clear();
	}
	
	//Get next Token
    void nextToken(){
        char c;
        do {
            if (currIndex == n){
                inputToken = '$';
                return;
            }
            c = s.charAt(currIndex++);
        } while (Character.isWhitespace(c));
        inputToken = c;
    }
    
    //Parse the program assignment by assignment.
    //	Assignment*
    void parse(){
    	try {
            while (inputToken != '$'){
                assign();
                nextToken();
            }
            Symbol.print();//print key and value
    	} catch (RuntimeException e) {
    		if (e != null)
    			System.out.println(e.getMessage());
    		else
    			System.out.println("error");
    	}
    }
    
    //Return true if inputToken is letter, otherwise false
    boolean isletter() {
 	   return (inputToken >= 'a' && inputToken <= 'z') || 
     		   (inputToken >= 'A' && inputToken <= 'Z') || 
     		   inputToken == '_' ;
    }
    
    //Return true if inputToken is digit, otherwise false
    boolean isdigit() {
 	   return inputToken >= '0' && inputToken <= '9';
    }
    
    //Parse the identifier by rule 
    //	Letter [Letter | Digit]*
    
    String identifier() {
 	   String id = new String();
 	   if (isletter()) {
 		   id += inputToken;
 		   nextToken();
 	   } else {
 		   throw new RuntimeException("error");
 	   }

 	   while(isletter() || isdigit()) {
 		   id += inputToken;
 		   nextToken();
 	   }
 	   return id;
    }
    
    //Get the expression string ended by ';'
    String exp() {
    	String e = new String();
    	while(inputToken != ';') {
    		e += inputToken;
    		nextToken();
    	}
    	return e;
    }
    
    //Assignment:
    //Identifier = Exp;
    void assign() {
        String x = identifier();
        
        if(inputToken == '=')
        nextToken();
        else
        	 throw new RuntimeException("syntax error");
        
        ExpEvaluator ee = new ExpEvaluator(exp());
        Integer v = ee.eval();
        
        Symbol.put(x, v);
    }
    
    
    public static void main(String []args){
    	
    	//command line argument
    	if (args.length == 0) {
    		System.out.println("Usage: java Parser <inputfile>");
    		return;
    	}
    	
        InputStream inputStream;
        String program = new String();
        
        try {
            inputStream = new FileInputStream(args[0]);
            System.setIn(inputStream);
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNext()) {
            	program += scanner.nextLine();
            }
            scanner.close();
            Parser pp = new Parser(program);
            pp.parse();
        } catch (FileNotFoundException e) {
        	System.out.println("File Not Found: " + args[0]);
            return;
        }
    	
    }
	

}
