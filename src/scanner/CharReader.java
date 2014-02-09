/**
* @author Mitch Birti
* @author Seth Yost
* @version 1.0
* File: CharReader.java
* Created: Feb 2014
* ©Copyright the authors. All rights reserved.
*
* Description: Reads from a file char-by-char. You can
 * look ahead and then munch from the stream.
*/

package scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Reads from a file char-by-char. You can
 * look ahead and then munch from the stream.
 */
public class CharReader
{
	private BufferedReader inFile;
	// next char to be returned
	private char nextChar;
	
	public CharReader(BufferedReader rdr)
	{
		inFile = rdr;
		
		prepNextChar();
	}
	
	/**
	 * Prepares the next character.
	 */
	private void prepNextChar()
	{
		try {
			nextChar = (char) inFile.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Views, but does not consume, the next character.
	 * @return
	 */
	public char viewNextChar()
	{
		return nextChar;
	}
	
	/**
	 * Consumes the next character.
	 */
	public void munchNextChar()
	{
		prepNextChar();
	}
}
