package parser;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Variable;

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
	
	/**
	 * Searches First/Follow Sets
	 * @param needle
	 * @param haystack
	 * @return
	 */
	private boolean contains(TokenType needle, TokenType[] haystack)
	{
		for (TokenType straw : haystack)
		{
			if (needle == straw)
				return true;
		}
		return false;
	}
	
	/**
	 * Views the next token type (look ahead character)
	 * @return
	 */
	private TokenType nextTokenType()
	{
		return scanner.viewNextToken().getType();
	}
	
	/**
	 * Matches (and munches) a Token or throws the passed message
	 * @param tt
	 * @param msg
	 * @return
	 */
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
	
	/**
	 * Parses Program
	 * @return
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
		
		return new Program(declList);
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
			//do nothing, since there's no parameters
		}
		else if (nextTokenType() == TokenType.INT)
		{
			// grab the first param
			matchOrDie(TokenType.INT, "parseParams(): INT expected, but got ");
			Token id = matchOrDie(TokenType.ID, "parseParams(): identifier expected, got ");
			if (matchToken(TokenType.OPEN_BRACKET))
			{
				params.add(new VariableDeclaration((String) id.getData(), 0));
				matchOrDie(TokenType.CLOSE_BRACKET, "parseParams(): expected ']', but got ");
			}
			else
			{
				params.add(new VariableDeclaration((String) id.getData()));
			}
			
			// check for other params
			while (nextTokenType() == TokenType.COMMA)
			{
				scanner.getNextToken();
				
				matchOrDie(TokenType.INT, "parseParams(): INT expected, but got ");
				id = matchOrDie(TokenType.ID, "parseParams(): identifier expected, got ");
				if (matchToken(TokenType.OPEN_BRACKET))
				{
					params.add(new VariableDeclaration((String) id.getData(), 0));
					matchOrDie(TokenType.CLOSE_BRACKET, "parseParams(): expected ']', but got ");
				}
				else
				{
					params.add(new VariableDeclaration((String) id.getData()));
				}
			}
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
		matchOrDie(TokenType.OPEN_CBRACE, "parseCompoundStatement(): expected '{', got ");
		List<Declaration> decls = new ArrayList<Declaration>();
		List<Statement> stmts = new ArrayList<Statement>();
		
		while (nextTokenType() == TokenType.INT)
		{
			scanner.getNextToken();
			Token id = matchOrDie(TokenType.ID, "parseCompoundStatement(): parsing variable declaration, expected identifier and got ");
			
			if (matchToken(TokenType.OPEN_BRACKET))
			{
				Token num = matchOrDie(TokenType.NUM, "parseCompoundStatement(): expected a number, but got ");
				matchOrDie(TokenType.CLOSE_BRACKET, "parseCompoundStatement(): expected ']', but got ");
				matchOrDie(TokenType.END_STATEMENT, "parseCompoundStatement(): expected ';', but got ");
				decls.add(new VariableDeclaration((String) id.getData(), (Integer) num.getData()));
			}
			else if (matchToken(TokenType.END_STATEMENT))
			{
				decls.add(new VariableDeclaration((String) id.getData()));
			}
			else
			{
				throw new RuntimeException("parseCompoundStatement(): expected '[' or ';', got ");
			}
		}
		
		while (contains(nextTokenType(), STATEMENT_LIST[0]) && nextTokenType() != TokenType.CLOSE_CBRACE)
		{
			stmts.add(parseStatement());
		}
		
		matchOrDie(TokenType.CLOSE_CBRACE, "parseCompoundStatement(): expected '}', got ");
		return new CompoundStatement(decls, stmts);
	}
	
	/**
	 * Parses Statement
	 * @return
	 */
	private Statement parseStatement()
	{
		Statement toReturn = null;
		if(nextTokenType() == TokenType.IF)
		{
			//if ( expression ) statement [ else statement ]
			toReturn = parseSelectionStatement();
		}
		else if(nextTokenType() == TokenType.WHILE)
		{
			//while ( expression ) statement
			toReturn = parseIterationStatement();
		}
		else if(nextTokenType() == TokenType.RETURN)
		{
			//return [expression] ;
			toReturn = parseReturnStatement();
		}
		else if(nextTokenType() == TokenType.OPEN_CBRACE)
		{
			//{ local-declarations statement-list }
			toReturn = parseCompoundStatement();
		}
		else if(contains(nextTokenType(), EXPRESSION[0]))
		{
			//[expression] ;
			toReturn = parseExpressionStatement();
		}
		else
		{
			throw new RuntimeException("parseStatement(): Invalid token for statement, got ");
		}
		return toReturn;
	}
	
	/**
	 * Parses an Expression Statement
	 * @return 
	 */
	private Statement parseExpressionStatement()
	{
		//[expression] ;
		Expression body = null;
		if(contains(nextTokenType(), EXPRESSION[0]))
		{
			body = parseExpression();
		}
		matchOrDie(TokenType.END_STATEMENT, "parseReturnStatement(): Did not recieve ';', got");
		return new ExpressionStatement(body);
	}
	
	/**
	 * Parses a SelectionStatement
	 * @return
	 */
	private Statement parseSelectionStatement()
	{
		//if ( expression ) statement [ else statement ]
		Statement else_part = null;
		matchOrDie(TokenType.IF,  "parseSelectionStatement(): Did not recieve 'IF', got");
		matchOrDie(TokenType.OPEN_PAREN, "parseSelectionStatement(): Did not recieve '(', got");
		Expression compare = parseExpression();
		matchOrDie(TokenType.CLOSE_PAREN, "parseSelectionStatement(): Did not recieve ')' after '(', got");
		Statement body = parseStatement();
		if(nextTokenType() == TokenType.ELSE)
		{
			matchOrDie(TokenType.ELSE, "parseSelectionStatement(): Did not recieve 'else' after 'if', got");
			else_part = parseStatement();
		}
		return new SelectionStatement(compare, body, else_part);
	}
	
	/**
	 * Parses Iteration Statement
	 * @return
	 */
	private Statement parseIterationStatement()
	{
		//while ( expression ) statement
		matchOrDie(TokenType.OPEN_PAREN, "parseSelectionStatement(): Did not recieve '(', got");
		Expression compare = parseExpression();
		matchOrDie(TokenType.CLOSE_PAREN, "parseSelectionStatement(): Did not recieve ')' after '(', got");
		Statement body = parseStatement();
		
		return new IterationStatement(compare, body);
	}
	
	/**
	 * Parses Return Statement
	 * @return
	 */
	private Statement parseReturnStatement()
	{
		//return [expression] ;
		Expression body = null;
		matchOrDie(TokenType.RETURN, "parseReturnStatement(): Did not recieve 'RETURN', got");
		if(contains(nextTokenType(), EXPRESSION[0]))
		{
			body = parseExpression();
		}
		matchOrDie(TokenType.END_STATEMENT, "parseReturnStatement(): Did not recieve ';', got");
		return new ReturnStatement(body);
	}
	
	/**
	 * Parses an additive-expression.
	 * @return
	 */
	private Expression parseAdditiveExpression()
	{
		Expression term = parseTerm();
		
		while (contains(nextTokenType(), ADDOP[0]))
		{
			if (matchToken(TokenType.PLUS))
			{
				term = new BinaryExpression(term, BinaryExpression.Operator.PLUS, parseTerm());
			}
			else if (matchToken(TokenType.MINUS))
			{
				term = new BinaryExpression(term, BinaryExpression.Operator.MINUS, parseTerm());
			}
			else
			{
				throw new RuntimeException("parseTerm(): '*' or '/' expected, but got ");
			}
		}
		
		return term;
	}
	
	/**
	 * Parses an additive-expression'.
	 * @param lhs
	 * @return
	 */
	
	/**
	 * Parses Additive Expression Prime
	 * @param lhs
	 * @return
	 */
	private Expression parseAdditiveExpression(Expression lhs)
	{
		Expression term = parseTerm(lhs);
		
		while (contains(nextTokenType(), ADDOP[0]))
		{
			if (matchToken(TokenType.PLUS))
			{
				term = new BinaryExpression(term, BinaryExpression.Operator.PLUS, parseTerm());
			}
			else if (matchToken(TokenType.MINUS))
			{
				term = new BinaryExpression(term, BinaryExpression.Operator.MINUS, parseTerm());
			}
			else
			{
				throw new RuntimeException("parseTerm(): '*' or '/' expected, but got ");
			}
		}
		
		return term;
	}
	
	
	/**
	 * Parses a term.
	 * @return
	 */
	private Expression parseTerm()
	{
		Expression term = parseFactor();
		
		return parseTerm(term);
	}
	
	
	/**
	 * Parses the equivalent of a term'.
	 * @param term
	 * @return
	 */
	private Expression parseTerm(Expression term)
	{
		while (contains(nextTokenType(), MULOP[0]))
		{
			if (matchToken(TokenType.MULTIPLY))
			{
				term = new BinaryExpression(term, BinaryExpression.Operator.MULTIPLY, parseFactor());
			}
			else if (matchToken(TokenType.DIVIDE))
			{
				term = new BinaryExpression(term, BinaryExpression.Operator.DIVIDE, parseFactor());
			}
			else
			{
				throw new RuntimeException("parseTerm(): '*' or '/' expected, but got ");
			}
		}
		
		return term;
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
	
	/**
	 * Parses Expression, Expression', and Expression''
	 * @return
	 */
	
	private Expression parseExpression()
	{
		Expression toReturn = null;
		
		if(matchToken(TokenType.OPEN_PAREN))
		{
			//Expression -> ( expression ) simple-expression'
			Expression compare = parseExpression();
			matchOrDie(TokenType.CLOSE_PAREN, "parseExpression(): No ')' found after '(', got ");
			toReturn = parseSimpleExpression(compare);
		}
		else if(nextTokenType() == TokenType.ID)
		{
			Token ID = scanner.getNextToken();
			
			//Expression -> ID expression'
			if(matchToken(TokenType.ASSIGNMENT))
			{
				//expression' -> = expression
				VariableExpression var = new VariableExpression(ID.toString());
				toReturn = new AssignExpression(var, parseExpression());
			}
			else if(matchToken(TokenType.OPEN_PAREN))
			{
				//expression' -> ( args ) simple-expression'
				List<Expression> args = parseArgs();
				Expression func = new CallExpression(ID.toString(), args);
				matchOrDie(TokenType.CLOSE_PAREN, "parseExpression(): No ')' found after '(', got ");
				toReturn = parseSimpleExpression(func);
			}
			else if(matchToken(TokenType.OPEN_BRACKET))
			{
				//expression' -> [ expression ] expression''
					
					Expression internalExpr = parseExpression();
					
					matchOrDie(TokenType.CLOSE_BRACKET, "parseExpression(): No ']' found after '[', got ");
					Expression varExpression = new VariableExpression(ID.toString(), internalExpr);
					
					if(matchToken(TokenType.ASSIGNMENT))
					{
						//expression'' -> = expression
						toReturn = new AssignExpression((VariableExpression) varExpression, parseExpression());
					}
					else if(contains(nextTokenType(), SIMPLE_EXPRESSION_PRIME[0]))
					{
						//expression'' -> simple-expression'
						toReturn = parseSimpleExpression(varExpression);
					}
					else if(contains(nextTokenType(), SIMPLE_EXPRESSION_PRIME[1]))
					{
						// expression'' -> simple-expression' -> epsilon
						toReturn = varExpression;
					}
					else
					{
						throw new RuntimeException("parseExpression(): Illegal token following ]!");
					}	
			}
			else if(contains(nextTokenType(), SIMPLE_EXPRESSION_PRIME[0]))
			{
				//expression' -> simple-expression'
				Expression temp = new VariableExpression(ID.toString());
				toReturn = parseSimpleExpression(temp);
			}
			else
			{
				throw new RuntimeException("parseExpression(): Illegal token following ID!");
			}
		}
		else if(nextTokenType() == TokenType.NUM)
		{
			//Expression -> NUM simple-expression'
			Token num = scanner.getNextToken();
			Expression Num = new NumberExpression((int)num.getData());
			toReturn = parseSimpleExpression(Num);
		}
		else
		{
			throw new RuntimeException("parseExpression(): Illegal token for Expression!");
		}
		return toReturn;
	}
	
	/**
	 * Parses Simple-Expression'
	 * @param lhs
	 * @return
	 */
	
	private Expression parseSimpleExpression(Expression lhs) {
		Expression toReturn = null;
		Expression left = parseAdditiveExpression(lhs);
		
		if(contains(nextTokenType(), RELOP[0]))
		{
			//match Relop
			Token opp = scanner.getNextToken();
			
			Expression right = parseAdditiveExpression();
			toReturn = new BinaryExpression(left, null/*opp*/, right);
		}
		else
		{
			toReturn = left;
		}
		
		return toReturn;
	}
	
	/**
	 * Parses Args and Arg-list
	 * @return
	 */
	
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
