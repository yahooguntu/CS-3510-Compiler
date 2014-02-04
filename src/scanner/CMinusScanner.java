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
				if(Character.isLetter(c))
				{
					state = State.ID_RESERVED;
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if(Character.isDigit(c))
				{
					state = State.NUM;
					tokenData.append(c);
					charReader.munchNextChar();
					
				}
				else if(Character.isWhitespace(c))
				{
					charReader.munchNextChar();
					break;
				}
				else if(c == '=')
				{
					state = State.ASSIGN_COMPARE;
					charReader.munchNextChar();
				}
				else if(c == '<')
				{
					state = State.GT_LT_HALF_COMPARE;
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if(c == '>')
				{
					state = State.GT_LT_HALF_COMPARE;
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if(c == '!')
				{
					state = State.GT_LT_HALF_COMPARE;
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if(c == '/')
				{
					state = State.DIV_OR_COMMENT;
					tokenData.append(c);
					charReader.munchNextChar();
				}
				else if(c == '+')
				{
					charReader.munchNextChar();
					return new Token(TokenType.PLUS);
				}
				else if(c == '-')
				{
					charReader.munchNextChar();
					return new Token(TokenType.MINUS);
				}
				else if(c == '*')
				{
					charReader.munchNextChar();
					return new Token(TokenType.MULTIPLY);
				}
				else if(c == ';')
				{
					charReader.munchNextChar();
					return new Token(TokenType.END_STATEMENT);
				}
				else if(c == ',')
				{
					charReader.munchNextChar();
					return new Token(TokenType.COMMA);
				}
				else if(c == '(')
				{
					charReader.munchNextChar();
					return new Token(TokenType.OPEN_PAREN);
				}
				else if(c == ')')
				{
					charReader.munchNextChar();
					return new Token(TokenType.CLOSE_PAREN);
				}
				else if(c == '[')
				{
					charReader.munchNextChar();
					return new Token(TokenType.OPEN_BRACKET);
				}
				else if(c == ']')
				{
					charReader.munchNextChar();
					return new Token(TokenType.CLOSE_BRACKET);
				}
				else if(c == '{')
				{
					charReader.munchNextChar();
					return new Token(TokenType.OPEN_BRACE);
				}
				else if(c == '}')
				{
					charReader.munchNextChar();
					return new Token(TokenType.CLOSE_BRACE);
				}
				else
				{
					state = State.ERROR;
					return new Token(TokenType.ERROR);
				}
				break;
			
			case DIV_OR_COMMENT:
				if(c == '*')
				{
					state = State.COMMENT;
					charReader.munchNextChar();
				}
				else
				{
					charReader.munchNextChar();
					return new Token(TokenType.DIVIDE);
				}
				break;
				
			case COMMENT:
				if(c == '*')
				{
					charReader.munchNextChar();
					state = State.BEGIN_END_COMMENT;
				}
				else
				{
					charReader.munchNextChar();
					state = State.COMMENT;
				}
				break;
				
			case BEGIN_END_COMMENT:
				if(c == '*')
				{
					charReader.munchNextChar();
				}
				else if(c != '/')
				{
					state = State.COMMENT;
					charReader.munchNextChar();
				}
				else
				{
					state = State.START;
					charReader.munchNextChar();
				}
				break;
				
			case GT_LT_HALF_COMPARE:
				if (c == '=')
				{
					charReader.munchNextChar();
					if (tokenData.toString().equals("<"))
					{
						state = State.FULL_COMPARE;
						return new Token(TokenType.LESS_EQUAL_THAN);
					}
					else if (tokenData.toString().equals(">"))
					{
						state = State.FULL_COMPARE;
						return new Token(TokenType.GREATER_EQUAL_THAN);
					}
				}
				else if (tokenData.toString().equals("<"))
				{
					return new Token(TokenType.LESS_THAN);
				}
				else if (tokenData.toString().equals(">"))
				{
					return new Token(TokenType.GREATER_THAN);
				}
				else
				{
					state = State.ERROR;
					return new Token(TokenType.ERROR);
				}
				
				break;
				
			case FULL_COMPARE: //TODO
				break;
				
			case ASSIGN_COMPARE:
				if (c == '=')
				{
					state = State.FULL_COMPARE;
					return new Token(TokenType.EQUALS);
				}
				else
				{
					state = State.ERROR;
					return new Token(TokenType.ERROR);
				}
				
			case HALF_NEQ:
				if (c == '=')
				{
					charReader.munchNextChar();
					state = State.FULL_COMPARE;
					return new Token(TokenType.NOT_EQUALS);
				}
				else
				{
					state = State.ERROR;
					return new Token(TokenType.ERROR);
				}
				
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
		//TODO we need to add a list of reserved words
		if(tokenData.toLowerCase().equals("if"))
		{
			return new Token(TokenType.IF);
		}
		else if(tokenData.toLowerCase().equals("else"))
		{
			return new Token(TokenType.ELSE);
		}
		else if(tokenData.toLowerCase().equals("int"))
		{
			return new Token(TokenType.INT);
		}
		else if(tokenData.toLowerCase().equals("return"))
		{
			return new Token(TokenType.RETURN);
		}
		else if(tokenData.toLowerCase().equals("while"))
		{
			return new Token(TokenType.WHILE);
		}
		else if(tokenData.toLowerCase().equals("void"))
		{
			return new Token(TokenType.VOID);
		}
		else
		{
			return new Token(TokenType.ID, tokenData);
		}
	}
}
