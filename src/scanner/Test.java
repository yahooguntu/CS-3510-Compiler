package scanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.sound.midi.SysexMessage;

import scanner.Token.TokenType;

public class Test
{
	static Scanner scanner;
	public static void main(String[] args)
	{
		try {
			scanner = new CMinusScanner(new BufferedReader(new FileReader("")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		parseA();
	}
	
	private static void parseA()
	{
		switch (scanner.viewNextToken().getType())
		{
		case OPEN_PAREN:
			match(TokenType.OPEN_PAREN);
			parseA();
			match(TokenType.CLOSE_PAREN);
			parseA();
			break;
			
		case CLOSE_PAREN:
			break;
			
		case EOF: // special case at the end of a file ($)
			break;
			
		default:
			System.err.println("Error!");
			System.exit(1);
		}
	}
	
	private static void match(TokenType t)
	{
		if (scanner.viewNextToken().getType() == t)
			scanner.getNextToken();
		else
		{
			System.err.println("Token not matched!");
			System.exit(1);
		}
	}
}
