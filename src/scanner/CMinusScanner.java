package scanner;

import java.io.BufferedReader;

public class CMinusScanner implements Scanner
{
	private BufferedReader inFile;
	private Token nextToken;
	
	public CMinusScanner (BufferedReader file)
	{
		inFile = file;
		nextToken = scanToken();
	}
	
	public Token getNextToken()
	{
		Token returnToken = nextToken;
		if(nextToken.getType() != Token.TokenType.EOF_TOKEN)
		{
			nextToken = scanToken();
		}
		return returnToken;
	}
	
	public Token viewNextToken(){
		return nextToken;
	}
	
	public Token scanToken()
	{
		return new Token();
	}
}
