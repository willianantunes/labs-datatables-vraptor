package br.com.willianantunes.labdatatables.init;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.util.Utils;

@ApplicationScoped
public class DataBaseInitializer
{
	private static Logger logger = Logger.getLogger(DataBaseInitializer.class);
	
	@Inject private SessionFactory factory;
	
	@SuppressWarnings("rawtypes")
	public void whenApplicationStarts(@Observes ServletContext context) 
	{
		Session session = this.factory.openSession();
		Transaction tx = null;
		Cell cell = null;
		String cellValue = null;
		User user = null;
		
		try
		{	
			tx = session.beginTransaction();
			
			logger.info("Reading the spreadsheet file...");
			
			URL url = getClass().getResource("/users.xls");
			Workbook workbook = new HSSFWorkbook(FileUtils.openInputStream(new File(url.toURI())));	
			Sheet sheet = workbook.getSheet("Users");
			
			logger.debug(String.format("The spreadsheet file has the following number of column and rows: %s / %s",
					sheet.getRow(0).getPhysicalNumberOfCells(),
					sheet.getPhysicalNumberOfRows()));			
			logger.debug("There are " + (sheet.getPhysicalNumberOfRows() - 1) + " users! Inserting them in the HSQLDB in-memory database...");
			
			// Gets column names and bind them with the associated cell number
			HashMap<String, Integer> columns = new HashMap<String, Integer>();
			for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) 
				columns.put(sheet.getRow(0).getCell(i).getStringCellValue(), i);
			// Gets a set of the entries 
			Set set = columns.entrySet(); 
			// Generating the entry in the database for each row
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) 
			{
				logger.debug("Reading the properties for the row " + i);
				user = new User();
				
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) 
				{ 
					Map.Entry me = (Map.Entry)iterator.next();					
					// Obtaining the cell value
					cell = sheet.getRow(i).getCell(Integer.parseInt(me.getValue().toString()));
					if(cell!=null)
						switch (cell.getCellType()) 
						{
							case Cell.CELL_TYPE_NUMERIC:
								cellValue = String.valueOf((int)cell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							default: cellValue = cell.getStringCellValue();
								break;
						}			
					
					Utils.setObjectAttribute(user, me.getKey().toString(), 
							me.getKey().toString().equals("password") ? Utils.passwordHashing(cellValue) : cellValue);									
				}		
				user.setRegisterDate(DateTime.now());
				logger.debug("Persisting the user: " + user.toString());
				session.save(user);				
			}
			// Commits the transaction
			tx.commit();
			
			logger.info("The users have been created!");
		}
		catch (Exception e)
		{
			if(tx != null)
				tx.rollback();
			logger.error("There is a problem during the configuration process.", e);
		}
		if(session.isOpen())
			session.close();
    }
}
