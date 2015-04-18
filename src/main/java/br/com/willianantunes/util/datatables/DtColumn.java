package br.com.willianantunes.util.datatables;

/**
 * Represents a column shown on the DataTables.
 * @author AW000005
 *
 */
public class DtColumn 
{
	private String name;
	private boolean searchable;
	private String data;
	private boolean orderable;
	private DtSearch search = new DtSearch();
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public boolean isSearchable() 
	{
		return searchable;
	}
	
	public void setSearchable(boolean searchable) 
	{
		this.searchable = searchable;
	}
	public String getData() 
	{
		return data;
	}
	
	public void setData(String data) 
	{
		this.data = data;
	}
	
	public boolean isOrderable() 
	{
		return orderable;
	}
	
	public void setOrderable(boolean orderable) 
	{
		this.orderable = orderable;
	}
	
	public DtSearch getSearch() 
	{
		return search;
	}
	
	public void setSearch(DtSearch search) 
	{
		this.search = search;
	}

	@Override
	public String toString() 
	{
		return "DtColumn [name=" + name + ", searchable=" + searchable
				+ ", data=" + data + ", orderable=" + orderable + ", search="
				+ search.toString() + "]";
	}	
	
}
