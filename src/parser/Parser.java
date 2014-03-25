package parser;

import java.util.ArrayList;

import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import parser.expression.*;
import parser.statement.*;

public class Parser
{
	private Scanner scanner;
	
	/*
	 * First/Follow Sets
	 */
	private TokenType[] FIRST_PROGRAM = { TokenType.INT, TokenType.VOID };
	private TokenType[] FIRST_DECLARATION_LIST = { TokenType.INT, TokenType.VOID };
	private TokenType[] FIRST_DECLARATION = { TokenType.INT, TokenType.VOID };
	private TokenType[] FIRST_DECLARATION_PRIME = { TokenType.OPEN_PAREN, TokenType.OPEN_BRACE, TokenType.END_STATEMENT };
	private TokenType[] FIRST_VAR_DECLARATION = { TokenType.INT };
	private TokenType[] FIRST_VAR_DECLARATION_PRIME = { TokenType.OPEN_BRACE, TokenType.END_STATEMENT };
	private TokenType[] FIRST_FUN_DECLARATION_PRIME = { TokenType.OPEN_PAREN };
	private TokenType[] FIRST_PARAMS = { TokenType.INT, TokenType.VOID };
	private TokenType[] FIRST_PARAM_LIST = { TokenType.INT };
	private TokenType[] FIRST_PARAM = { TokenType.INT };
	private TokenType[] FIRST_COMPOUND_STMT = { TokenType.OPEN_BRACKET };
	private TokenType[] FIRST_LOCAL_DECLARATIONS = { TokenType.INT, TokenType.EPSILON };
	private TokenType[] FIRST_STATEMENT_LIST = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON };
	private TokenType[] FIRST_STATEMENT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN };
	private TokenType[] FIRST_EXPRESSION_STMT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT };
	private TokenType[] FIRST_SELECTION_STMT = { TokenType.IF };
	private TokenType[] FIRST_ITERATION_STMT = { TokenType.WHILE };
	private TokenType[] FIRST_RETURN_STMT = { TokenType.RETURN };
	private TokenType[] FIRST_EXPRESSION = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FIRST_EXPRESSION_PRIME = { TokenType. ASSIGNMENT, TokenType.OPEN_PAREN, TokenType.OPEN_BRACE, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON };
	private TokenType[] FIRST_EXPRESSION_PRIME_PRIME = { TokenType. ASSIGNMENT, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON };
	private TokenType[] FIRST_VAR = { TokenType.OPEN_BRACE, TokenType.EPSILON };
	private TokenType[] FIRST_SIMPLE_EXPRESSION_PRIME = { TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON };
	private TokenType[] FIRST_RELOP = { TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS };
	private TokenType[] FIRST_ADDITIVE_EXPRESSION = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FIRST_ADDITIVE_EXPRESSION_PRIME = { TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS, TokenType.EPSILON };
	private TokenType[] FIRST_ADDOP = { TokenType.PLUS, TokenType.MINUS };
	private TokenType[] FIRST_TERM = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FIRST_TERM_PRIME = { TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON };
	private TokenType[] FIRST_MULOP = { TokenType.MULTIPLY, TokenType.DIVIDE };
	private TokenType[] FIRST_FACTOR = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FIRST_VARCALL = { TokenType.EPSILON, TokenType.OPEN_BRACE, TokenType.OPEN_PAREN };
	private TokenType[] FIRST_CALL = { TokenType.OPEN_PAREN };
	private TokenType[] FIRST_ARGS = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.EPSILON };
	private TokenType[] FIRST_ARG_LIST = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FOLLOW_PROGRAM = { TokenType.EOF };
	private TokenType[] FOLLOW_DECLARATION_LIST = { TokenType.EOF };
	private TokenType[] FOLLOW_DECLARATION = { TokenType.INT, TokenType.VOID, TokenType.EOF };
	private TokenType[] FOLLOW_DECLARATION_PRIME = { TokenType.INT, TokenType.VOID, TokenType.EOF };
	private TokenType[] FOLLOW_VAR_DECLARATION = { TokenType.INT, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON };
	private TokenType[] FOLLOW_VAR_DECLARATION_PRIME = { TokenType.INT, TokenType.VOID, TokenType.EOF };
	private TokenType[] FOLLOW_FUN_DECLARATION_PRIME = { TokenType.INT, TokenType.VOID, TokenType.EOF };
	private TokenType[] FOLLOW_PARAMS = { TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_PARAM_LIST = { TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_PARAM = { TokenType.COMMA, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_COMPOUND_STMT = { TokenType.INT, TokenType.VOID, TokenType.EOF, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_LOCAL_DECLARATIONS = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON };
	private TokenType[] FOLLOW_STATEMENT_LIST = { TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_STATEMENT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_EXPRESSION_STMT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_SELECTION_STMT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_ITERATION_STMT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_RETURN_STMT = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET };
	private TokenType[] FOLLOW_EXPRESSION = { TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_EXPRESSION_PRIME = { TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_EXPRESSION_PRIME_PRIME = { TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_VAR = { TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_SIMPLE_EXPRESSION_PRIME = { TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_RELOP = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FOLLOW_ADDITIVE_EXPRESSION = { TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_ADDITIVE_EXPRESSION_PRIME = { TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_ADDOP = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FOLLOW_TERM = { TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_TERM_PRIME = { TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_MULOP = { TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID };
	private TokenType[] FOLLOW_FACTOR = { TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_VARCALL = { TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_CALL = { TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_ARGS = { TokenType.CLOSE_PAREN };
	private TokenType[] FOLLOW_ARG_LIST = { TokenType.CLOSE_PAREN };
	
	public Parser(Scanner s)
	{
		scanner = s;
	}
	
	/*
	 * Matches and consumes the given token.
	 * @returns True if matched, false if the next token isn't the one we are looking for.
	 */
	private boolean matchToken(TokenType t)
	{
		if (scanner.viewNextToken().getType() == t)
		{
			scanner.getNextToken();
			return true;
		}
		return false;
	}
	
	private TokenType nextTokenType()
	{
		return scanner.viewNextToken().getType();
	}
	
	private boolean inSet(TokenType tt, TokenType[] searchArr)
	{
		for (TokenType i : searchArr)
		{
			if (tt == i)
				return true;
		}
		return false;
	}
	
	public Program parseProgram()
	{
		ArrayList<Declaration> declList = new ArrayList<Declaration>();
		
		return new Program(null);
	}
	
	private Expression parseFactor()
	{
		Expression toReturn;
		
		if (matchToken(TokenType.OPEN_PAREN))
		{
			toReturn = parseExpression();
			
			if (!matchToken(TokenType.CLOSE_PAREN))
			{
				throw new RuntimeException("parseFactor(): No close paren found!");
			}
		}
		else if (nextTokenType() == TokenType.NUM)
		{
			toReturn = new NumberExpression((Integer)(scanner.getNextToken().getData()));
		}
		else if (nextTokenType() == TokenType.ID)
		{
			Token id = scanner.getNextToken();
			// parse varcall
			if (matchToken(TokenType.OPEN_PAREN))
			{
				//List<Expression> args = new List<Expression>();
				//TODO
			}
		}
		
		//TODO
		return null;
	}
	
	private Expression parseExpression()
	{
		//TODO
		return null;
	}
}
