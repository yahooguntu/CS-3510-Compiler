package parser;

import java.util.ArrayList;

import scanner.Scanner;

public class Parser
{
	private Scanner scanner;
	
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
