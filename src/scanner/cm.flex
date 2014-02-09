package scanner;
import scanner.Token.TokenType;

%%

%public
%class LexCMinusScanner
%implements Scanner

%unicode

%line
%column

%type Token

%{
    private Token nextToken;
    private Boolean initialized = false;
    
    /**
	 * Gets and consumes the next token.
	 */
	public Token getNextToken()
	{
	    if (!initialized)
	    {
	        initialized = true;
	        try {
			nextToken = yylex();
			} catch (Exception e) {
			    throw new RuntimeException("Stuff really broken.");
			}
		}
		Token returnToken = nextToken;
		
		// only load up the next token if we aren't done yet and haven't errored
		if(nextToken.getType() != Token.TokenType.EOF)
		{
		    try {
			nextToken = yylex();
			} catch (Exception e) {
			    throw new RuntimeException("Stuff really broken.");
			}
		}
		
		return returnToken;
	}
	
	/**
	 * Consumes the next token.
	 */
	public Token viewNextToken()
	{
	    if (!initialized)
	        {
	            initialized = true;
    	        try {
		    	nextToken = yylex();
		    	} catch (Exception e) {
		    	    throw new RuntimeException("Stuff really broken.");
		    	}
	    }
		return nextToken;
	}

%}

/* main character classes */
LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
/* Comment = "/ *" [^*] ~"* /" | "/ *" "*"+ "/" */
Comment = "/*" ~"*/"

/* identifiers */
Identifier = [:jletter:]+

/* integer literals */
DecIntegerLiteral = [0-9]+

%%

<YYINITIAL> {

  /* keywords */
  "else"                         { return new Token(TokenType.ELSE); /* yyline+1, yycolumn+1 */ }
  "int"                          { return new Token(TokenType.INT); }
  "if"                           { return new Token(TokenType.IF); }
  "return"                       { return new Token(TokenType.RETURN); }
  "void"                         { return new Token(TokenType.VOID); }
  "while"                        { return new Token(TokenType.WHILE); }
  
  /* separators */
  "("                            { return new Token(TokenType.OPEN_PAREN); }
  ")"                            { return new Token(TokenType.CLOSE_PAREN); }
  "{"                            { return new Token(TokenType.OPEN_BRACKET); }
  "}"                            { return new Token(TokenType.CLOSE_BRACKET); }
  "["                            { return new Token(TokenType.OPEN_BRACE); }
  "]"                            { return new Token(TokenType.CLOSE_BRACE); }
  ";"                            { return new Token(TokenType.END_STATEMENT); }
  ","                            { return new Token(TokenType.COMMA); }
  
  /* operators */
  "="                            { return new Token(TokenType.ASSIGNMENT); }
  ">"                            { return new Token(TokenType.GREATER_THAN); }
  "<"                            { return new Token(TokenType.LESS_THAN); }
  "=="                           { return new Token(TokenType.EQUALS); }
  "<="                           { return new Token(TokenType.LESS_EQUAL_THAN); }
  ">="                           { return new Token(TokenType.GREATER_EQUAL_THAN); }
  "!="                           { return new Token(TokenType.NOT_EQUALS); }
  "+"                            { return new Token(TokenType.PLUS); }
  "-"                            { return new Token(TokenType.MINUS); }
  "*"                            { return new Token(TokenType.MULTIPLY); }
  "/"                            { return new Token(TokenType.DIVIDE); }
  
  /* numeric literals */
  {DecIntegerLiteral}            { return new Token(TokenType.NUM, new Integer(yytext())); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return new Token(TokenType.ID, yytext()); }  
}

/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return new Token(TokenType.EOF); }
