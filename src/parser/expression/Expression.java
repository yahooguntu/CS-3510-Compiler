package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.*;
/**
 * Represents any kind of expression.
 */
public abstract class Expression
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
	public abstract void genLLCode(Function parent);
}
