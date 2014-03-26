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
			{ TokenType.OPEN_PAREN, TokenType.OPEN_BRACKET, TokenType.END_STATEMENT },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF }
	};
	private TokenType[][] VAR_DECLARATION = {
			{ TokenType.INT },
			{ TokenType.INT, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.CLOSE_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON }
	};
	private TokenType[][] VAR_DECLARATION_PRIME = {
			{ TokenType.OPEN_BRACKET, TokenType.END_STATEMENT },
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
			{ TokenType.OPEN_CBRACE },
			{ TokenType.INT, TokenType.VOID, TokenType.EOF, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] LOCAL_DECLARATIONS = {
			{ TokenType.INT, TokenType.EPSILON },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.CLOSE_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON }
	};
	private TokenType[][] STATEMENT_LIST = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.EPSILON },
			{ TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] STATEMENT = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] EXPRESSION_STMT = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] SELECTION_STMT = {
			{ TokenType.IF },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] ITERATION_STMT = {
			{ TokenType.WHILE },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] RETURN_STMT = {
			{ TokenType.RETURN },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_CBRACE, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.ELSE, TokenType.CLOSE_CBRACE }
	};
	private TokenType[][] EXPRESSION = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] EXPRESSION_PRIME = {
			{ TokenType. ASSIGNMENT, TokenType.OPEN_PAREN, TokenType.OPEN_BRACKET, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] EXPRESSION_PRIME_PRIME = {
			{ TokenType. ASSIGNMENT, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] VAR = {
			{ TokenType.OPEN_BRACKET, TokenType.EPSILON },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] SIMPLE_EXPRESSION_PRIME = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] RELOP = {
			{ TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID }
	};
	private TokenType[][] ADDITIVE_EXPRESSION = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] ADDITIVE_EXPRESSION_PRIME = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS, TokenType.EPSILON },
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] ADDOP = {
			{ TokenType.PLUS, TokenType.MINUS },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID }
	};
	private TokenType[][] TERM = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] TERM_PRIME = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.EPSILON },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] MULOP = {
			{ TokenType.MULTIPLY, TokenType.DIVIDE },
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID }
	};
	private TokenType[][] FACTOR = {
			{ TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] VARCALL = {
			{ TokenType.EPSILON, TokenType.OPEN_BRACKET, TokenType.OPEN_PAREN },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
	};
	private TokenType[][] CALL = {
			{ TokenType.OPEN_PAREN },
			{ TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.END_STATEMENT, TokenType.COMMA, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACKET, TokenType.CLOSE_PAREN }
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
	
	private Token matchOrDie(TokenType tt, String msg)
	{
		if (scanner.viewNextToken().getType() != tt)
		{
			throw new RuntimeException(msg + nextTokenType().name());
		}
		return scanner.getNextToken();
	}
	
	/*
	 * Parse methods, BEGIN!
	 */
	
	public Program parseProgram()
	{
		ArrayList<Declaration> declList = new ArrayList<Declaration>();
		
		while (contains(nextTokenType(), DECLARATION[0]))
		{
			declList.add(parseDeclaration());
		}
		
		if (!matchToken(TokenType.EOF))
		{
			throw new RuntimeException("parseProgram(): illegal token: " + nextTokenType().name());
		}
		
		return new Program(null);
	}
	
	/**
	 * Parses a Declaration.
	 * @return
	 */
	public Declaration parseDeclaration()
	{
		Declaration toReturn;
		// strip off the first two tokens
		Token typeSpecifier = scanner.getNextToken();
		Token identifier = scanner.getNextToken();
		
		if (typeSpecifier.getType() == TokenType.VOID || (typeSpecifier.getType() == TokenType.INT && nextTokenType() == TokenType.OPEN_PAREN))
		{
			// declaration -> void ID fun-declaration'
			// OR  decl    -> int ID fun-declaration'
			// this isn't exactly according to the grammar, but it's better coding practice
			
			matchOrDie(TokenType.OPEN_PAREN, "parseDeclaration(): parsing function, open paren expected, got ");
			Params params = parseParams();
			matchOrDie(TokenType.CLOSE_PAREN, "parseDeclaration(): parsing function, close paren expected, got ");
			
			CompoundStatement body = parseCompoundStatement();
			
			toReturn = new FunctionDeclaration((String) identifier.getData(), params, body);
		}
		else if (typeSpecifier.getType() == TokenType.INT)
		{
			// declaration -> int ID declaration'
			// AND decl'   -> var-declaration'
			
			if (matchToken(TokenType.OPEN_BRACKET))
			{
				Token number = matchOrDie(TokenType.NUM, "parseDeclaration(): parsing array declaration, expected NUM, got ");
				
				matchOrDie(TokenType.CLOSE_BRACKET, "parseDeclaration(): parsing array declaration, expected ']', got " + nextTokenType().name());
				
				toReturn = new VariableDeclaration((String) identifier.getData(), (Integer) number.getData());
			}
			else if (nextTokenType() == TokenType.END_STATEMENT)
			{
				toReturn = new VariableDeclaration((String) identifier.getData());
			}
			else
			{
				throw new RuntimeException("parseDeclaration(): parsing variable, '[' or ';' expected, got " + nextTokenType().name());
			}
			
			matchOrDie(TokenType.END_STATEMENT, "parseDeclaration(): parsing variable declaration, ';' expected, got ");
			
		}
		else
		{
			throw new RuntimeException("parseDeclaration(): type specifier expected, received " + typeSpecifier.getType().name());
		}
		
		return toReturn;
	}
	
	/**
	 * Parses function parameters.
	 * @return
	 */
	private Params parseParams()
	{
		List<VariableDeclaration> params = new ArrayList<VariableDeclaration>();
		
		if (matchToken(TokenType.VOID))
		{
			//do nothing
		}
		else if (nextTokenType() == TokenType.INT)
		{
			do {
				matchToken(TokenType.INT);
				Token id = matchOrDie(TokenType.ID, "parseParams(): identifier expected, got ");
				if (matchToken(TokenType.OPEN_BRACKET))
				{
					params.add(new VariableDeclaration((String) id.getData(), 0));
				}
				else
				{
					params.add(new VariableDeclaration((String) id.getData(), 0));
				}
			} while (nextTokenType() == TokenType.COMMA);
		}
		else
		{
			throw new RuntimeException("parseParams(): expected 'void' or 'int', got " + nextTokenType().name());
		}
		
		return new Params(params);
	}
	
	/**
	 * Parses a CompoundStatement.
	 * @return
	 */
	private CompoundStatement parseCompoundStatement()
	{
		//TODO
		return null;
	}
	
	/**
	 * Parses a Factor.
	 * @return
	 */
	private Expression parseFactor()
	{
		Expression toReturn;
		
		if (matchToken(TokenType.OPEN_PAREN))
		{
			// factor -> ( expression )
			toReturn = parseExpression();
			
			matchOrDie(TokenType.CLOSE_PAREN, "parseFactor(): No ')' found, got ");
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
				
				matchOrDie(TokenType.CLOSE_PAREN, "parseFactor(): No ')' found after args in function call, got ");
			}
			else if (matchToken(TokenType.OPEN_BRACKET))
			{
				// varcall -> var -> [ expression ]
				Expression xpr = parseExpression();
				toReturn = new VariableExpression((String) id.getData(), xpr);
				
				matchOrDie(TokenType.CLOSE_BRACKET, "parseFactor(): No ']' found after '[', got");
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
		Expression toReturn;
		
		if(matchToken(TokenType.OPEN_PAREN))
		{
			//Expression -> ( expression ) simple-expression'
			
		}
		else if(nextTokenType() == TokenType.ID)
		{
			//Expression -> ID expression'
			if(matchToken(TokenType.ASSIGNMENT))
			{
				//expression' -> = expression
				
			}
			else if(matchToken(TokenType.OPEN_PAREN))
			{
				//expression' -> ( args ) simple-expression'
				
			}
			else if(matchToken(TokenType.OPEN_BRACKET))
			{
				//expression' -> [ expression ] expression''
					
					Expression exp = parseExpression();
					Expression varExp = new VariableExpression(null, exp);
					Expression assignExpres;
					Expression simpleExp; 
					
					if(!matchToken(TokenType.CLOSE_BRACKET))
					{
						throw new RuntimeException("parseExpression(): No ']' found after '['!");
					}
					
					if(matchToken(TokenType.ASSIGNMENT))
					{
						//expression'' -> = expression
						assignExpres = new AssignExpression(null, parseExpression());
					}
					else if(nextTokenType() == TokenType.DIVIDE || nextTokenType() == TokenType.MULTIPLY)
					{
						//expression'' -> simple-expression'
						simpleExp = parseSimpleExpression()
					}
					else
					{
						throw new RuntimeException("parseExpression(): Illegal token following ]!");
					}
					
					
			}
			else if(nextTokenType() == TokenType.DIVIDE || nextTokenType() == TokenType.MULTIPLY)
			{
				//expression' -> simple-expression'
			}
			else
			{
				throw new RuntimeException("parseExpression(): Illegal token following ID!");
			}
		}
		else if(nextTokenType() == TokenType.NUM)
		{
			//Expression -> NUM simple-expression'
		}
		else
		{
			throw new RuntimeException("parseExpression(): Illegal token for Expression!");
		}
		return toReturn;
	}
	
	private Expression parseSimpleExpression() {
		//TODO
		return null;
	}
	
	private List<Expression> parseArgs()
	{
		List<Expression> args = new ArrayList<Expression>();
		
		while (nextTokenType() != TokenType.CLOSE_PAREN)
		{
			args.add(parseExpression());
		}
		
		return args;
	}
}
