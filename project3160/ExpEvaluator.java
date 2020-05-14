package project3160;


   /*
    * Based on the demo code, to evaluate an expression.
    */
   public class ExpEvaluator {
       private String s;
       private int currIndex;
       private int n;
       private char inputToken;
           
       public ExpEvaluator(String s){
           this.s = s;
           currIndex = 0;
           n = s.length();
           nextToken();
       }

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
      
      void match(char token){
          if (inputToken == token){
              nextToken();
          } else {
              throw new RuntimeException("error");
          }
      }
      
      //Evaluate the expression
      int eval(){
          int x = exp();
          if (inputToken == '$'){
              return x;
          } else {
              throw new RuntimeException("error");
          }
      }
      
      //Evaluate the expression by rule
      //	Exp + Term | Exp - Term | Term
      int exp(){
          int x = term();
          while (inputToken == '+' || inputToken == '-'){
              char op = inputToken;
              nextToken();
              int y = term();
              x = apply(op, x, y);
          }
          return x;
      } 
      
      //Parse term by rule
      //Term * Fact  | Fact
      int term(){
          int x = factor();
          while (inputToken == '*'){
              char op = inputToken;
              nextToken();
              int y = factor();
              x = apply(op, x, y);
          }
          return x;
      }
      
      //a|...|z|A|...|Z|_
      boolean isletter() {
   	  return (inputToken >= 'a' && inputToken <= 'z') || 
       		   (inputToken >= 'A' && inputToken <= 'Z') || 
       		   inputToken == '_' ;
    	  
      }
      
      //0|1|...|9
      boolean isdigit() {
   	   return inputToken >= '0' && inputToken <= '9';
      }
      
      //Get id by rule Letter [Letter | Digit]*
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
      //0 | NonZeroDigit Digit*
      int literal() {
   	   int x;
          x = inputToken - '0';
          nextToken();
         	
         	while(inputToken >= '0' && inputToken <= '9') {
         		if (x==0) //01 is not allowed.
         			throw new RuntimeException("error");
         		x = x*10 + (inputToken - '0');//x ->[1-9]
         		nextToken();
         	}
         	return x;
      }
      //Parse factor by rule
      //		( Exp ) | - Fact | + Fact | Literal | Identifier
      int factor(){
          int x;
          if (inputToken == '(') {
       	   
              nextToken();
              x = exp();
              match(')');
              return x;
          } else if (inputToken == '-') {
       	   nextToken();
       	   int y = 0 - factor();
       	   return y;
       	   //return 0 - factor();
          } else if (inputToken == '+' ) {
          		nextToken();
          		return factor();
          } else if (inputToken >= '0' && inputToken <= '9') {
        	  int z = literal();
               return z;//literal();
          } else if ((inputToken >= 'a' && inputToken <= 'z') || 
       		   (inputToken >= 'A' && inputToken <= 'Z') || 
       		   inputToken == '_') {
       	   String s = identifier();
       	   try {
       		   
       	   return Symbol.get(s);// static/ class method
       	   } catch (Exception e) {
       		   throw new RuntimeException(s + " uninitiated variable");
       	   }
          } else {
       	   throw new RuntimeException("error");
          }
      }
      
       static int apply(char op, int x, int y){
           int z = 0;
           switch (op){
               case '+': z = x + y; break;
               case '-': z = x - y; break;
               case '*': z = x * y; break;
               case '/': z = x / y; break;
           }
           return z;
       }
       
       
      /*  public static void main(String []args){
            ExpEvaluator ee = new ExpEvaluator("3+2*3-4");
            System.out.println(ee.eval());
        }*/
   }

   
   
   