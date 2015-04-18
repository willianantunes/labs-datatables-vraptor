package br.com.willianantunes.labdatatables.controller.data;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.util.datatables.DataTables;

public class UserDataTables extends DataTables<User> 
{
	public UserDataTables() {}

	public UserDataTables(List<User> t, long recordsTotal, long recordsFiltered, Integer draw) 
	{
		super(t, recordsTotal, recordsFiltered, draw);
	}
	
	@Override
	protected List<String> getAttributes(User t) 
	{
		List<String> list = new ArrayList<String>();
		
		list.add(this.getString(t.getId()));
		list.add(this.getString(t.getName()));
		list.add(this.getString(t.getEmail()));
		list.add(t.getRegisterDate().toString(DateTimeFormat.forPattern(this.messageFactory.getMessage("config.date_format"))));
		
		return list;
	}

	@Override
	public String getColumnNameById(int id) 
	{
		switch (id)
		{
		case 2:
			return "name";
		case 3:
			return "email";
		case 4:
			return "registerDate";			
		default:
			return "id";
		}
	}
}
