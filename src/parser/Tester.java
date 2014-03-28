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
* Description: Tests CMinusParser.java
*/
public class Tester
{
	
	public static void main(String[] args)
	{
		try
		{
			// set up the scanner and the input/output file
			String baseName = "TestFile";
			Scanner s = new CMinusScanner(new BufferedReader(new FileReader("tests/" + baseName + ".cm")));
			
			// make the parser
			Parser parser = new CMinusParser(s);
			
			// parse the program
			parser.parse();
			// write the tree to file
			parser.printTree("tests/" + baseName + ".xml");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
