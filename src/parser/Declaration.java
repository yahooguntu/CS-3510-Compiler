package parser;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;

/**
 * Represents a declaration (variable or function).
 */
public abstract class Declaration
{
	private int registerNum;
	public abstract void print(int indent, BufferedWriter out) throws IOException;
	public abstract CodeItem genLLCode();
	public CodeItem genLLCode(Function parent) {
		return null;
	}
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