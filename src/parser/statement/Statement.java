package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.*;

/**
 * Abstract class for statements (epxressions, if, while, return, etc.).
 */
public abstract class Statement
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
	public abstract CodeItem genLLCode(Function parent);
}
