package scanner;

import java.io.File;
import scanner.Token.TokenType;

public class CMinusScanner implements Scanner
{
	private Token nextToken;
	private CharReader charReader;
	private boolean erroredOut = false;
	
	private enum State {
		DIV_OR_COMMENT,
		COMMENT,
		BEGIN_END_COMMENT,
		GT_LT_HALF_COMPARE,
		ASSIGN_COMPARE,
		HALF_NEQ,
		NUM,
		ID_RESERVED,
		START
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
		if (erroredOut)
		{
			return new Token(TokenType.ERROR);
		}
		
		State state = State.START;
		StringBuilder tokenData = new StringBuilder();
		
		char c = charReader.viewNextChar();
		while (c != 0xFFFF)
		{
			
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
					state = State.HALF_NEQ;
					charReader.munchNextChar();
				}
				else if(c == '/')
				{
					state = State.DIV_OR_COMMENT;
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
				else if(c == 0xFFFF)
				{
					// we've reached the end
					return new Token(TokenType.EOF);
				}
				else
				{
					erroredOut = true;
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
				else // it must be a slash
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
						return new Token(TokenType.LESS_EQUAL_THAN);
					}
					else if (tokenData.toString().equals(">"))
					{
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
					erroredOut = true;
					return new Token(TokenType.ERROR);
				}
				
				break;
				
			case ASSIGN_COMPARE:
				if (c == '=')
				{
					return new Token(TokenType.EQUALS);
				}
				else
				{
					return new Token(TokenType.ASSIGNMENT);
				}
				
			case HALF_NEQ:
				if (c == '=')
				{
					charReader.munchNextChar();
					return new Token(TokenType.NOT_EQUALS);
				}
				else
				{
					erroredOut = true;
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
					erroredOut = true;
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
					erroredOut = true;
					return new Token(TokenType.ERROR);
				}
				break;
			}
			
			c = charReader.viewNextChar();
		}
		
		if (c == 0xFFFF)
		{
			return new Token(TokenType.EOF);
		}
		
		return new Token(null);
	}
	
	private Token reservedWordTest(String tokenData)
	{
		//TODO we need to add a list of reserved words
		return new Token(TokenType.ID, tokenData);
	}
}
