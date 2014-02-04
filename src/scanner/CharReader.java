package scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CharReader
{
	private BufferedReader inFile;
	private char nextChar;
	
	public CharReader(File in)
	{
		try {
			inFile = new BufferedReader(new FileReader(in));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		prepNextChar();
	}
	
	private void prepNextChar()
	{
		try {
			nextChar = (char) inFile.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public char viewNextChar()
	{
		return nextChar;
	}
	
	public void munchNextChar()
	{
		prepNextChar();
	}
}
