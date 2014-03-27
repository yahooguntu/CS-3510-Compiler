package parser;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import scanner.CMinusScanner;
import scanner.Scanner;
import scanner.Token;
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
			Scanner s = new CMinusScanner(new BufferedReader(new FileReader("tests/testcode.cm")));
			outFile = new BufferedWriter(new FileWriter("output.xml"));
			
			Parser parser = new Parser(s);
			Program prog = parser.parseProgram();
			try{
				prog.print(0, outFile);
			} 
			catch(IOException e)
			{
				e.printStackTrace();
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
	
	
}
