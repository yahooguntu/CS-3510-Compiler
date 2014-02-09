/**
* @author Mitch Birti
* @author Seth Yost
* @version 1.0
* File: CMinusScanner.java
* Created: Feb 2014
* ©Copyright the authors. All rights reserved.
*
* Description: Implements the Scanner interface.
 * This is a tokenizer for the C- language.
*/

package scanner;

import java.io.BufferedReader;
import java.io.File;

import scanner.Token.TokenType;

public class CMinusScanner implements Scanner
{
	// the next token that will be spit out is pre-read into this
	private Token nextToken;
	// this is the character source
	private CharReader charReader;
	// if this is true, we only return error tokens
	private boolean erroredOut = false;
	
	// list of possible states we can be in
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
	
	public CMinusScanner (BufferedReader rdr)
	{
		// set up the character stream reader
		charReader = new CharReader(rdr);
		nextToken = scanToken();
	}
	
	/**
	 * Gets and consumes the next token.
	 */
	public Token getNextToken()
	{
		Token returnToken = nextToken;
		
		// only load up the next token if we aren't done yet and haven't errored
		if(nextToken.getType() != Token.TokenType.EOF && nextToken.getType() != Token.TokenType.ERROR)
		{
			nextToken = scanToken();
		}
		
		return returnToken;
	}
	
	/**
	 * Consumes the next token.
	 */
	public Token viewNextToken()
	{
		return nextToken;
	}
	
	/**
	 * Deciphers the next token from the character stream.
	 * @return
	 */
	private Token scanToken()
	{
		// we only return error tokens if we've errored out
		if (erroredOut)
		{
			return new Token(TokenType.ERROR);
		}
		
		// start in the start state
		State state = State.START;
		// this is where we store ID names, NUM chars, etc.
		StringBuilder tokenData = new StringBuilder();
		
		char c = charReader.viewNextChar();
		// while we haven't reached end-of-stream...
		while (c != 0xFFFF)
		{
			// state machine on state "state"
			switch (state)
			{
			case START:
				
				/* Handles the split-offs from START,
				 * including single-character tokens. */
				
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
			
			/* Comment machine. */
			
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
			
			/* Logic for <, >, <=, >=, ==, and != */
			
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
			
			/* Standard numeric and identifier tokens. */
			
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
					// runs it through the reserved word checker before returning it
					return reservedWordTest(tokenData.toString());
				}
				else
				{
					erroredOut = true;
					return new Token(TokenType.ERROR);
				}
				break;
			}
			
			// prep the next character for the next round through
			c = charReader.viewNextChar();
		}
		
		// if we reach the end of the stream, do not pass go. do not collect $200.
		if (c == 0xFFFF)
		{
			return new Token(TokenType.EOF);
		}
		
		return new Token(null);
	}
	
	/*
	 * Checks for a reserved word. If there is one, it returns
	 * that token. If not, it returns an ID token.
	 */
	private Token reservedWordTest(String tokenData)
	{
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
