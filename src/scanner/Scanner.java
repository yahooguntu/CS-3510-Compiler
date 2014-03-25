/**
* @author Mitch Birti
* @author Seth Yost
* @version 1.0
* File: Scanner.java
* Created: Feb 2014
* ©Copyright the authors. All rights reserved.
*/

package scanner;

import scanner.Token.TokenType;

public interface Scanner {
	
	public Token getNextToken();
	public Token viewNextToken();
}
