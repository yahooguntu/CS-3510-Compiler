package scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class CMinusScanner implements Scanner
{
	private BufferedReader inFile;
	//private BufferedWriter outFile;
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
	
	/*
	private void writeToken()
	{
		try {
			outFile.write(viewNextToken().toString());
			outFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
}
