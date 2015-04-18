package br.com.willianantunes.util.datatables;

import java.util.ArrayList;
import java.util.List;

/**
 * As the name suggests this class has all the data sent from DataTables.
 * @author Willian Antunes
 *
 */
public class DtConsolidated 
{
	private Integer draw;
	private Integer start;
	private Integer length;
	private DtSearch search = new DtSearch();
	List<DtColumn> columns = new ArrayList<DtColumn>();
	List<DtOrder> orders = new ArrayList<DtOrder>();
	
	public List<DtColumn> getColumns() 
	{
		return columns;
	}
	public void setColumns(List<DtColumn> columns) 
	{
		this.columns = columns;
	}
	public List<DtOrder> getOrders() 
	{
		return orders;
	}
	public void setOrders(List<DtOrder> orders) 
	{
		this.orders = orders;
	}
	
	public Integer getDraw() 
	{
		return draw;
	}
	
	public void setDraw(Integer draw) 
	{
		this.draw = draw;
	}
	
	public Integer getStart() 
	{
		return start;
	}
	
	public void setStart(Integer start) 
	{
		this.start = start;
	}
	
	public Integer getLength() 
	{
		return length;
	}
	
	public void setLength(Integer length) 
	{
		this.length = length;
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
		StringBuilder sbColumn = new StringBuilder();
		for (DtColumn column : columns)
		{
			sbColumn.append(column.toString());
			sbColumn.append("\r\n");
		}
		
		StringBuilder sbOrders = new StringBuilder();
		for (DtOrder order : orders)
		{
			sbOrders.append(order.toString());
			sbOrders.append("\r\n");
		}		
		
		return "DtConsolidated \r\n[\r\ndraw=" + draw + ", \r\nstart=" + start
				+ ", \r\nlength=" + length + ", \r\nsearch=" + search.toString() + ", \r\ncolumns=\r\n"
				+ sbColumn.toString().replaceAll("[\r\n]+$", "") + ", \r\norders=\r\n" + sbOrders.toString() + "]";
	}
}
