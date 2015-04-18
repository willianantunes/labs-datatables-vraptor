package br.com.willianantunes.util.datatables;

/**
 * Each {@link br.com.willianantunes.util.datatables.DtColumn} has the attribute <tt>DtSearch</tt> that is used to refine the data on the table.
 * @author Willian Antunes
 *
 */
public class DtSearch 
{
	private String value;
	private boolean regex;
	
	public String getValue() 
	{
		return value;
	}
	
	public void setValue(String value) 
	{
		this.value = value;
	}
	
	public boolean isRegex() 
	{
		return regex;
	}
	
	public void setRegex(boolean regex) 
	{
		this.regex = regex;
	}

	@Override
	public String toString() 
	{
		return "DtSearch [value=" + value + ", regex=" + regex + "]";
	}
	
}
