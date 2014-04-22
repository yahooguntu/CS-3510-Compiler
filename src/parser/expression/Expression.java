package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.*;
/**
 * Represents any kind of expression.
 */
public abstract class Expression
{
	private int registerNum;
	public abstract void print(int indent, BufferedWriter out) throws IOException;
	public abstract CodeItem genLLCode(Function parent);
	
	/**
	 * @return the registerNum
	 */
	public int getRegisterNum() {
		return registerNum;
	}
	
	/**
	 * @param registerNum the registerNum to set
	 */
	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}
}
