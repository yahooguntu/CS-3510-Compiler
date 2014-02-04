package scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import scanner.Token.TokenType;

public class Tester
{
	private BufferedReader outFile;
	
	public static void main(String[] args)
	{
		Scanner s = new CMinusScanner(new File("TestFile.cm"));
		
		Token t = s.getNextToken();
		while (t.getType() != TokenType.EOF)
		{
			System.out.println(t.getType().toString() + "\t:" + t.getData());
			t = s.getNextToken();
		}
	}
	
	private void writeToken()
	{
		/*
		try {
			//outFile.write(viewNextToken().toString());
			outFile.write("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	
}
