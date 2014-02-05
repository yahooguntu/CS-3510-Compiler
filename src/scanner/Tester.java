package scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import scanner.Token.TokenType;

public class Tester
{
	private static BufferedWriter outFile;
	
	public static void main(String[] args)
	{
		Scanner s = new CMinusScanner(new File("TestFile.cm"));
		try {
			outFile = new BufferedWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Token t = s.getNextToken();
		while (t.getType() != TokenType.EOF)
		{
			System.out.println(t.getType().toString() + "\t:" + t.getData());
			writeToken(t);
			t = s.getNextToken();
		}
	}
	
	public static void writeToken(Token t)
	{
		
		try {
			outFile.write(t.getType() + "\t:" + t.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
