package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Abstract class for statements (epxressions, if, while, return, etc.).
 */
public abstract class Statement
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
	public abstract void genLLCode(lowlevel.Function parent);
}
