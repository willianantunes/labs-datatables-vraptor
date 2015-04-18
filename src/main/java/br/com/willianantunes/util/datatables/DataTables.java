package br.com.willianantunes.util.datatables;

import java.util.ArrayList;
import java.util.List;

import br.com.willianantunes.labdatatables.factory.MessageFactory;

/**
 * This class can be inherited by another class to expose information compatible with 
 * jQuery DataTables v1.10.6. The constructor of the class that will inherit this one, must set some attributes to
 * enable DataTables plugin work properly.
 * @author Willian Antunes
 * @version 1.1.0
 * @see <a href="http://www.datatables.net/manual/options">Options</a>
 * @see <a href="http://www.datatables.net/manual/server-side">Server-side processing</a>
 */
public abstract class DataTables<T>
{  
	private List<List<String>> data; // aaData
	private long recordsTotal; // iTotalRecords  
	private long recordsFiltered; // iTotalDisplayRecords
	private Integer draw; // sEcho
	private String error;
	protected MessageFactory messageFactory;

	public DataTables() {}
	
	public DataTables(List<T> t, long recordsTotal, long recordsFiltered, Integer draw) 
	{
		bakeIt(t, recordsTotal, recordsFiltered, draw);
	}
	
	@Override
	public String toString() 
	{
		return "DataTables [data=" 
				+ data + ", recordsTotal="
				+ recordsTotal + ", recordsFiltered="
				+ recordsFiltered + ", draw="
				+ draw + "]";
	}

	@Override  
	public boolean equals(Object obj) 
	{  
		if (obj == null)  
			return false;  
  
		return this.hashCode() == obj.hashCode();  
	}  
  
	@Override  
	public int hashCode() 
	{  
		return this.toString().hashCode();  
	}
	
	public String getString(Object object)
	{
		return object == null ? "" : object.toString();
	}
	
	public void bakeIt(List<T> t, long recordsTotal, long recordsFiltered, Integer draw)
	{
		setData(toListString(
				t == null ?
						new ArrayList<T>() :
							t
				));
		setRecordsTotal(recordsTotal);
		setRecordsFiltered(recordsFiltered);
		setDraw(draw);		
	}
  
	protected void setData(List<List<String>> data) 
	{  
		if (data == null)  
			data = new ArrayList<List<String>>();  
  
		this.data = data;  
	}  
  
	protected void setRecordsTotal(long recordsTotal) 
	{  
		if (recordsTotal < 0)  
			recordsTotal = 0;  
  
		this.recordsTotal = recordsTotal;  
	}  
  
	protected void setRecordsFiltered(long recordsFiltered) 
	{  
		if (recordsFiltered < 0)  
			recordsFiltered = 0;  
  
		this.recordsFiltered = recordsFiltered;  
	}

	public String getError() 
	{
		return error;
	}

	public void setError(String error) 
	{
		this.error = error;
	}

	protected void setDraw(Integer draw)
	{
		this.draw = draw;
	}
	
	public void setMessageFactory(MessageFactory messageFactory) 
	{
		this.messageFactory = messageFactory;
	}
	
	protected List<List<String>> toListString(List<T> listOfT)
	{
		List<List<String>> list = new ArrayList<List<String>>();

		for(T t : listOfT)
		{
			list.add(this.getAttributes(t));
		}
		
		return list;		
	}
	
	/**
	 * This method must feel a list that will be used by jQuery DataTables in "data" property. Example of implementation:
	 * <pre>
	 * private List<String> getAttributes(User user)
	 * {
	 * 	List<String> list = new ArrayList<String>();
	 * 
	 * 	list.add(this.getString(user.getId()));
	 * 	list.add(this.getString(user.getName()));
	 * 	list.add(this.getString(user.getEmail()));
	 * 	list.add(user.getRegisterDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")));
	 * 	list.add(this.getString(user.getUserProfile().getUserProfileType()));
	 * 
	 * 	return list;
	 * }
	 * </pre>
	 * @param t
	 * @return A {@code List<List<String>>} used by jQuery DataTables in "data" property.
	 */
	protected abstract List<String> getAttributes(T t);
	
	/**
	 * This method must implement a switch-case (example) that will return the column name in the database. Example of implementation:
	 * <pre>
	 * public static String getColumnNameById(int id)
	 * {
	 * 	switch (id) 
	 * 	{
	 * 	case 1:
	 * 		return "name";
	 * 	case 2:
	 * 		return "email";
	 * 	case 3:
	 * 		return "registerDate";
	 * 	case 4:
	 * 		// Criteria alias
	 * 		return "up.userProfileType";
	 * 	default:
	 * 		return "id";
	 * 	}
	 * }
	 * </pre>
	 * @param id
	 * @return A column name that match the column id in the database
	 */
	protected abstract String getColumnNameById(int id);
}
