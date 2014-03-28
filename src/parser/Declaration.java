package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Represents a declaration (variable or function).
 */
public abstract class Declaration
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
}