package scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import scanner.Token.TokenType;
import sun.security.jgss.TokenTracker;

public class CMinusScanner implements Scanner
{
	private Token nextToken;
	private CharReader charReader;
	
	private enum State {
		DIV_OR_COMMENT,
		COMMENT,
		BEGIN_END_COMMENT,
		GT_LT_HALF_COMPARE,
		FULL_COMPARE,
		ASSIGN_COMPARE,
		HALF_NEQ,
		NUM,
		ID_RESERVED,
		START,
		DONE,
		ERROR
	}
	
	public CMinusScanner (File file)
	{
		charReader = new CharReader(file);
		nextToken = scanToken();
	}
	
	public Token getNextToken()
	{
		Token returnToken = nextToken;
		if(nextToken.getType() != Token.TokenType.EOF && nextToken.getType() != Token.TokenType.ERROR)
		{
			nextToken = scanToken();
		}
		return returnToken;
	}
	
	public Token viewNextToken()
	{
		return nextToken;
	}
	
	public Token scanToken()
	{
		State state = State.START;
		StringBuilder tokenData = new StringBuilder();
		
		while (state != State.DONE)
		{
			char c = charReader.viewNextChar();
			
			switch (state)
			{
			case START:
				break;
			
			case DIV_OR_COMMENT:
				break;
				
			case COMMENT:
				break;
				
			case BEGIN_END_COMMENT:
				break;
				
			case GT_LT_HALF_COMPARE:
				break;
				
			case FULL_COMPARE:
				break;
				
			case ASSIGN_COMPARE:
				break;
				
			case HALF_NEQ:
				if (c == '=')
				{
					state = State.FULL_COMPARE;
					return new Token(TokenType.NOT_EQUALS);
				}
				break;
				
			case NUM:
				if (Character.isDigit(c))
				{
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if (!Character.isLetter(c))
				{
					return new Token(TokenType.NUM, Integer.parseInt(tokenData.toString()));
				}
				else
				{
					state = State.ERROR;
					return new Token(TokenType.ERROR);
				}
				break;
				
			case ID_RESERVED:
				if (Character.isLetter(c))
				{
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if (!Character.isDigit(c))
				{
					return reservedWordTest(tokenData.toString());
				}
				else
				{
					state = State.ERROR; //TODO
					return new Token(TokenType.ERROR);
				}
				break;
				
			case DONE: //TODO
				break;
				
			case ERROR: //TODO
				return new Token(TokenType.ERROR);
				
			}
		}
		
		return new Token(null);
	}
	
	private Token reservedWordTest(String tokenData)
	{
		//TODO
		return new Token(TokenType.ID, tokenData);
	}
}
