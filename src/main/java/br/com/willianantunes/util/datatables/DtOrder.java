package br.com.willianantunes.util.datatables;

/**
 * Represents a column used for sorting.
 * @author Willian Antunes
 *
 */
public class DtOrder 
{
	private Integer column;
	private String dir;
	
	/**
	 * Column to which ordering should be applied. This is an index reference to the columns array of information that is also submitted to the server.
	 * @return Column index
	 */
	public Integer getColumn() 
	{
		return column;
	}
	
	public void setColumn(Integer column) 
	{
		this.column = column;
	}
	
	/**
	 * Ordering direction for this column. It will be asc or desc to indicate ascending ordering or descending ordering, respectively.
	 * @return String set with <strong>asc</strong> or <strong>desc</strong>
	 */
	public String getDir() 
	{
		return dir;
	}
	
	public void setDir(String dir) 
	{
		this.dir = dir;
	}

	@Override
	public String toString() 
	{
		return "DtOrder [column=" + column + ", dir=" + dir + "]";
	}
	
}
