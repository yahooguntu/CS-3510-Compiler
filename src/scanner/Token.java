package scanner;

/**
 * Represents a token.
 * @author David Birti
 *
 */
public class Token
{
	/**
	 * Denotes legal token types.
	 * @author David Birti
	 *
	 */
	public enum TokenType {
		// type specifiers
		INT,
		VOID,
		
		// flow control
		IF,
		ELSE,
		WHILE,
		RETURN,
		
		// math
		PLUS,
		MINUS,
		MULTIPLY,
		DIVIDE,
		LESS_THAN,
		LESS_EQUAL_THAN,
		GREATER_THAN,
		GREATER_EQUAL_THAN,
		EQUALS,
		NOT_EQUALS,
		
		// grouping
		OPEN_BRACKET,	// {
		CLOSE_BRACKET,	// }
		OPEN_PAREN,		// (
		CLOSE_PAREN,	// )
		OPEN_BRACE,		// [
		CLOSE_BRACE,	// ]
		
		// other
		ID,
		NUM,
		ASSIGNMENT,
		END_STATEMENT,
		COMMA,
		WHITESPACE,
		EOF,
		ERROR
	}
	
	private TokenType tokenType;
	private Object tokenData;
	
	/**
	 * Construct a data-less token.
	 * @param type
	 */
	public Token(TokenType type)
	{
		this(type, null);
	}
	
	/**
	 * Construct a token with data.
	 * @param type
	 * @param data
	 */
	public Token(TokenType type, Object data)
	{
		tokenType = type;
		tokenData = data;
	}
	
	/**
	 * Get the TokenType.
	 * @return
	 */
	public TokenType getType()
	{
		return tokenType;
	}
	
	/**
	 * Get the data in this token.
	 * @return
	 */
	public Object getData()
	{
		return tokenData;
	}
}
