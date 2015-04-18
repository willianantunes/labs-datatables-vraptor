package br.com.willianantunes.util.datatables;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This controller class (MVC pattern) get all the information compatible with jQuery DataTables v1.10.6
 * @author Willian Antunes
 * @version 1.1.0
 * @see <a href="http://www.datatables.net/manual/server-side">DataTables server-side</a>
 */
public abstract class CommonController 
{
	protected HttpServletRequest request;
	
    private final Logger logger = LogManager.getLogger(CommonController.class);
	
	/**
	 * It's responsible to provide data to be filled in the DataTables.
	 */    
    protected abstract void paginate();	

	/**
	 * This method read all the parameters sent by DataTables.
	 * @return A consolidated object with all DataTables parameters
	 */
	protected DtConsolidated getParametersDataTable()
	{  
		DtConsolidated consolidated = new DtConsolidated();
		int index = 0;
		
		// Collection draw counter, paging first record indicator, number of records that the table can display in the current draw and global search value
		consolidated.setDraw(new Integer(request.getParameter("draw")));
		consolidated.setStart(new Integer(request.getParameter("start")));
		consolidated.setLength(new Integer(request.getParameter("length")));
		consolidated.getSearch().setRegex(Boolean.parseBoolean(request.getParameter("search[regex]")));
		consolidated.getSearch().setValue(request.getParameter("search[value]"));
		// Collecting information for column attributes
		do 
		{
			DtColumn column = new DtColumn();
			column.setData(request.getParameter("columns[" + index + "][data]"));
			column.setName(request.getParameter("columns[" + index + "][name]"));
			column.setOrderable(Boolean.parseBoolean(request.getParameter("columns[" + index + "][orderable]")));
			column.setSearchable(Boolean.parseBoolean(request.getParameter("columns[" + index + "][searchable]")));
			column.getSearch().setRegex(Boolean.parseBoolean(request.getParameter("columns[" + index + "][search][regex]")));
			column.getSearch().setValue(request.getParameter("columns[" + index + "][search][value]"));
			consolidated.getColumns().add(column);
			index++;
		} 
		while (request.getParameter("columns[" + index + "][data]") != null);		
		index=0;
		// Collecting information for order (column to which ordering should be applied)
		do
		{
			DtOrder order = new DtOrder();
			order.setColumn(Integer.parseInt(request.getParameter("order[" + index + "][column]")));
			order.setDir(request.getParameter("order[" + index + "][dir]"));
			consolidated.getOrders().add(order);
			index++;
		}
		while (request.getParameter("order[" + index + "][column]") != null);
		
		logger.trace("Received DataTables parameters... \r\n" + consolidated.toString());

		return consolidated;  
	}
}
