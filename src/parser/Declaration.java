package parser;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Declaration
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
}