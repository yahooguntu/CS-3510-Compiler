package scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import scanner.Token.TokenType;

/**
* @author Mitch Birti
* @author Seth Yost
* @version 1.0
* File: Tester.java
* Created: Feb 2014
* ©Copyright the authors. All rights reserved.
*
* Description: Tests CMinusScanner.java
*/

public class Tester
{
	private static BufferedWriter outFile;
	
	public static void main(String[] args)
	{
		try
		{
			// set up the scanner and the output file
			Scanner s = new LexCMinusScanner(new BufferedReader(new FileReader("TestFile.cm")));
			outFile = new BufferedWriter(new FileWriter("output.txt"));
			
			// iterate through all the tokens
			Token t = s.getNextToken();
			while (t.getType() != TokenType.EOF)
			{
				writeToken(t);
				t = s.getNextToken();
			}
			
			// flush and close the output file
			outFile.flush();
			outFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a token to standard out and the output file.
	 * @param t
	 * @throws IOException
	 */
	public static void writeToken(Token t) throws IOException
	{
		String output = t.toString();
		outFile.write(output);
		System.out.print(output);
	}
	
	
}
