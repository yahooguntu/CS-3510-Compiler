package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.*;

/**
 * Abstract class for statements (epxressions, if, while, return, etc.).
 */
public abstract class Statement
{
	private int registerNum;
	public abstract void print(int indent, BufferedWriter out) throws IOException;
	public abstract void genLLCode(Function parent);
	
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
