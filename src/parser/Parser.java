package parser;

import java.util.ArrayList;

import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;

public class Parser
{
	private Scanner scanner;
	
	private TokenType[] FIRST_program = {TokenType.INT, TokenType.VOID};
	private TokenType[] FIRST_declaration_list = {TokenType.INT, TokenType.VOID};
	private TokenType[] FIRST_declaration = {TokenType.INT, TokenType.VOID};
	private TokenType[] FIRST_declaration_prime = {TokenType.OPEN_PAREN, TokenType.OPEN_BRACE, TokenType.END_STATEMENT};
	private TokenType[] FIRST_var_declaration = {TokenType.INT};
	private TokenType[] FIRST_var_declaration_prime = {TokenType.OPEN_BRACE, TokenType.END_STATEMENT};
	private TokenType[] FIRST_fun_declaration_prime = {TokenType.OPEN_PAREN};
	private TokenType[] FIRST_params = {TokenType.INT, TokenType.VOID};
	private TokenType[] FIRST_param_list = {TokenType.INT};
	private TokenType[] FIRST_param = {TokenType.INT};
	private TokenType[] FIRST_compound_stmt = {TokenType.OPEN_BRACKET};
	private TokenType[] FIRST_local_declarations = {TokenType.EPSILON, TokenType.INT};
	private TokenType[] FIRST_statement_list = {TokenType.EPSILON, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FIRST_statement = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FIRST_expression_stmt = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT};
	private TokenType[] FIRST_selection_stmt = {TokenType.IF};
	private TokenType[] FIRST_iteration_stmt = {TokenType.WHILE};
	private TokenType[] FIRST_RETURN_stmt = {TokenType.RETURN};
	private TokenType[] FIRST_expression = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FIRST_expression_prime = {TokenType.EPSILON, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.OPEN_BRACE, TokenType.MULTIPLY, TokenType.DIVIDE};
	private TokenType[] FIRST_expression_prime_prime = {TokenType.EPSILON, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.MULTIPLY, TokenType.DIVIDE};
	private TokenType[] FIRST_var = {TokenType.EPSILON, TokenType.OPEN_BRACE};
	private TokenType[] FIRST_simple_expression_prime = {TokenType.EPSILON, TokenType.MULTIPLY, TokenType.DIVIDE};
	private TokenType[] FIRST_relop = {TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FIRST_additive_expression = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FIRST_additive_expression_prime = {TokenType.EPSILON, TokenType.MULTIPLY, TokenType.DIVIDE};
	private TokenType[] FIRST_addop = {TokenType.PLUS, TokenType.MINUS};
	private TokenType[] FIRST_term = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FIRST_term_prime = {TokenType.EPSILON, TokenType.MULTIPLY, TokenType.DIVIDE};
	private TokenType[] FIRST_mulop = {TokenType.MULTIPLY, TokenType.DIVIDE};
	private TokenType[] FIRST_factor = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FIRST_varcall = {TokenType.EPSILON, TokenType.OPEN_BRACE, TokenType.OPEN_PAREN};
	private TokenType[] FIRST_call = {TokenType.OPEN_PAREN};
	private TokenType[] FIRST_args = {TokenType.EPSILON, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FIRST_arg_list = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FOLLOW_program = {TokenType.EOF};
	private TokenType[] FOLLOW_declaration_list = {TokenType.EOF};
	private TokenType[] FOLLOW_declaration = {TokenType.EOF, TokenType.INT, TokenType.VOID};
	private TokenType[] FOLLOW_declaration_prime = {TokenType.EOF, TokenType.INT, TokenType.VOID};
	private TokenType[] FOLLOW_var_declaration = {TokenType.EOF, TokenType.INT, TokenType.VOID, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.CLOSE_BRACKET};
	private TokenType[] FOLLOW_var_declaration_prime = {TokenType.EOF, TokenType.INT, TokenType.VOID};
	private TokenType[] FOLLOW_fun_declaration_prime = {TokenType.EOF, TokenType.INT, TokenType.VOID};
	private TokenType[] FOLLOW_params = {TokenType.CLOSE_PAREN};
	private TokenType[] FOLLOW_param_list = {TokenType.CLOSE_PAREN};
	private TokenType[] FOLLOW_param = {TokenType.COMMA, TokenType.CLOSE_PAREN};
	private TokenType[] FOLLOW_compound_stmt = {TokenType.EOF, TokenType.INT, TokenType.VOID, TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_local_declarations = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.IF, TokenType.WHILE, TokenType.RETURN, TokenType.CLOSE_BRACKET};
	private TokenType[] FOLLOW_statement_list = {TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_statement = {TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_expression_stmt = {TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_selection_stmt = {TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_iteration_stmt = {TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_return_stmt = {TokenType.CLOSE_BRACKET, TokenType.ELSE, TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID, TokenType.END_STATEMENT, TokenType.OPEN_BRACKET, TokenType.IF, TokenType.WHILE, TokenType.RETURN};
	private TokenType[] FOLLOW_expression = {TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA};
	private TokenType[] FOLLOW_expression_prime = {TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA};
	private TokenType[] FOLLOW_expression_prime_prime = {TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA};
	private TokenType[] FOLLOW_var = {TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.PLUS, TokenType.MINUS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FOLLOW_simple_expression_prime = {TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA};
	private TokenType[] FOLLOW_relop = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FOLLOW_additive_expression = {TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA};
	private TokenType[] FOLLOW_additive_expression_prime = {TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA};
	private TokenType[] FOLLOW_addop = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FOLLOW_term = {TokenType.PLUS, TokenType.MINUS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FOLLOW_term_prime = {TokenType.PLUS, TokenType.MINUS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FOLLOW_mulop = {TokenType.OPEN_PAREN, TokenType.NUM, TokenType.ID};
	private TokenType[] FOLLOW_factor = {TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.PLUS, TokenType.MINUS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FOLLOW_varcall = {TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.PLUS, TokenType.MINUS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FOLLOW_call = {TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.PLUS, TokenType.MINUS, TokenType.END_STATEMENT, TokenType.CLOSE_PAREN, TokenType.CLOSE_BRACE, TokenType.COMMA, TokenType.LESS_EQUAL_THAN, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.GREATER_EQUAL_THAN, TokenType.EQUALS, TokenType.NOT_EQUALS};
	private TokenType[] FOLLOW_args = {TokenType.CLOSE_PAREN};
	private TokenType[] FOLLOW_arg_list = {TokenType.CLOSE_PAREN};
	
	public Parser(Scanner s)
	{
		scanner = s;
	}
	
	public Program parseProgram()
	{
		ArrayList<Declaration> declList = new ArrayList<Declaration>();
		
		return new Program(null);
	}
}
