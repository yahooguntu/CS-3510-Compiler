package parser;

import java.util.ArrayList;
import java.util.List;

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
	 * The first sub-array is the first set, the following one is the follow set.
	 * (Makes sense, right?)
	 */
	private TokenType[][] PROGRAM = {
			{ TokenType.INT, TokenType.VOID },
			{ TokenType.EOF }
	};
	private TokenType[][] DECLARATION_LIST = {
			{ TokenType.INT, TokenType.VOID },
			{ TokenType.EOF }
	};
	private TokenType[][] DECLARATION = {
			{ TokenType.INT, TokenType.VOID },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF }
	};
	private TokenType[][] DECLARATION_PRIME = {
			{ TokenType.OPEN_PAREN, TokenType.OPEN_BRACE, TokenType.END_STATEMENT },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF }
	};
	private TokenType[][] VAR_DECLARATION = {
			{ TokenType.INT },
			{ TokenType.INT, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.CLOSE_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON }
	};
	private TokenType[][] VAR_DECLARATION_PRIME = {
			{ TokenType.OPEN_BRACE, TokenType.END_STATEMENT },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF }
	};
	private TokenType[][] FUN_DECLARATION_PRIME = {
			{ TokenType.OPEN_PAREN },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF }
	};
	private TokenType[][] PARAMS = {
			{ TokenType.INT, TokenType.VOID },
			{ TokenType.CLOSE_PAREN }
	};
	private TokenType[][] PARAM_LIST = {
			{ TokenType.INT },
			{ TokenType.CLOSE_PAREN }
	};
	private TokenType[][] PARAM = {
			{ TokenType.INT },
			{ TokenType.COMMA, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] COMPOUND_STMT = {
			{ TokenType.OPEN_BRACKET },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] LOCAL_DECLARATIONS = {
			{ TokenType.INT, TokenType.EPSILON },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.CLOSE_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON }
	};
	private TokenType[][] STATEMENT_LIST = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON },
			{ TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] STATEMENT = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] EXPRESSION_STMT = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] SELECTION_STMT = {
			{ TokenType.IF },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] ITERATION_STMT = {
			{ TokenType.WHILE },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] RETURN_STMT = {
			{ TokenType.RETURN },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_BRACKET }
	};
	private TokenType[][] EXPRESSION = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] EXPRESSION_PRIME = {
			{ TokenType. ASSIGNMENT, TokenType.OPEN_PAREN, TokenType.OPEN_BRACE, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] EXPRESSION_PRIME_PRIME = {
			{ TokenType. ASSIGNMENT, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] VAR = {
			{ TokenType.OPEN_BRACE, TokenType.EPSILON },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] SIMPLE_EXPRESSION_PRIME = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] RELOP = {
			{ TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID }
	};
	private TokenType[][] ADDITIVE_EXPRESSION = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] ADDITIVE_EXPRESSION_PRIME = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS, TokenType.EPSILON },
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] ADDOP = {
			{ TokenType.PLUS, TokenType.MINUS },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID }
	};
	private TokenType[][] TERM = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] TERM_PRIME = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] MULOP = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID }
	};
	private TokenType[][] FACTOR = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] VARCALL = {
			{ TokenType.EPSILON, TokenType.OPEN_BRACE, TokenType.OPEN_PAREN },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] CALL = {
			{ TokenType.OPEN_PAREN },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] ARGS = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.EPSILON },
			{ TokenType.CLOSE_PAREN }
	};
	private TokenType[][] ARG_LIST = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.CLOSE_PAREN }
	};
	
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
	
	private boolean contains(TokenType needle, TokenType[] haystack)
	{
		for (TokenType straw : haystack)
		{
			if (needle == straw)
				return true;
		}
		return false;
	}
	
	private TokenType nextTokenType()
	{
		return scanner.viewNextToken().getType();
	}
	
	/*
	 * Parse methods, BEGIN!
	 */
	
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
			// factor -> ( expression )
			toReturn = parseExpression();
			
			if (!matchToken(TokenType.CLOSE_PAREN))
			{
				throw new RuntimeException("parseFactor(): No ')' found!");
			}
		}
		else if (nextTokenType() == TokenType.NUM)
		{
			// factor -> NUM
			toReturn = new NumberExpression((Integer)(scanner.getNextToken().getData()));
		}
		else if (nextTokenType() == TokenType.ID)
		{
			// factor -> ID varcall
			Token id = scanner.getNextToken();
			
			if (matchToken(TokenType.OPEN_PAREN))
			{
				// varcall -> call -> ( args )
				List<Expression> args = parseArgs();
				
				toReturn = new CallExpression((String) id.getData(), args);
				
				if (!matchToken(TokenType.CLOSE_PAREN))
				{
					throw new RuntimeException("parseFactor(): No ')' found after args in function call!");
				}
			}
			else if (matchToken(TokenType.OPEN_BRACE))
			{
				// varcall -> var -> [ expression ]
				Expression xpr = parseExpression();
				toReturn = new VariableExpression((String) id.getData(), xpr);
				
				if (!matchToken(TokenType.CLOSE_BRACE))
				{
					throw new RuntimeException("parseFactor(): No ']' found after '['!");
				}
			}
			else if (contains(nextTokenType(), VARCALL[1]))
			{
				// next token is in $varcall
				// varcall -> var -> EPSILON
				toReturn = new VariableExpression((String) id.getData());
			}
			else
			{
				throw new RuntimeException("parseFactor(): Illegal token after ID!");
			}
		}
		else
		{
			throw new RuntimeException("parseFactor(): Illegal token for factor!");
		}
		
		return toReturn;
	}
	
	private Expression parseExpression()
	{
		//TODO
		return null;
	}
	
	private List<Expression> parseArgs()
	{
		List<Expression> args = new ArrayList<Expression>();
		//TODO
		return null;
	}
}
