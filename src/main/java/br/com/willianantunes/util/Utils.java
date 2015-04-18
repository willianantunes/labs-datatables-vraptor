package br.com.willianantunes.util;

import static com.google.common.base.Objects.firstNonNull;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils 
{
	public static final String REGEX_EMAIL = "\\w+[\\w-\\.]*@\\w+[\\w-\\.]*\\.[a-zA-Z]{2,}";
	public static final String REGEX_PHONE = "\\((\\d{2})\\)\\s?(\\d{4,5}-\\d{4})";
	
	/**
	 * This method calculate a SHA 512-bit from the parameter and transform it into a String of 128 bits (hexadecimal format).
	 * One hexadecimal = 4 bits. 512/4 = 128 bits.
	 * @param password
	 * @return Hashed string with 128 bits Hash de 512 bits em hexadecimal
	 */
	public static String passwordHashing(String password)
	{
		return DigestUtils.sha512Hex(password);
	}
	
	/**
	 * Capitalize the first letter of some word. It can be used for JSTL.
	 * @param word
	 * @return A word the first letter converted to upper case.
	 * @see <a href="http://www.guj.com.br/java/44859-jstl-primeira-letra-maiuscula">jstl-primeira-letra-maiuscula</a>
	 */
	public static String capitalize(String word)
	{
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}
	
	/**
	 * Verify if an email address is valid or not
	 */
	public static boolean isValidEmail(String email) 
	{
		Pattern pattern = Pattern.compile(REGEX_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * This method uses reflection to set an object property.
	 * @param object
	 * @param fieldName
	 * @param fieldValue
	 * @return the success of the process
	 * @see <a href="http://stackoverflow.com/questions/14374878/using-reflection-to-set-an-object-property">Using reflection to set an object property</a>
	 */
	public static boolean setObjectAttribute(Object object, String fieldName, Object fieldValue) 
	{
	    Class<?> clazz = object.getClass();
	    while (clazz != null) 
	    {
	        try 
	        {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            return true;
	        } 
	        catch (NoSuchFieldException e) 
	        {
	            clazz = clazz.getSuperclass();
	        } 
	        catch (Exception e) 
	        {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
	/**
	 * Retrieve a generated string according to the character set and length, thus you can control it 
	 * following your business rule. Provide the character set as the example:<br /><br />
	 * <code>char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();</code> 
	 * @param characterSet
	 * @param length
	 * @return Random string
	 */
	public static String randomString(char[] characterSet, int length) 
	{
	    Random random = new SecureRandom();
	    char[] result = new char[length];
	    for (int i = 0; i < result.length; i++) 
	    {
	        // picks a random index out of character set > random character
	        int randomCharIndex = random.nextInt(characterSet.length);
	        result[i] = characterSet[randomCharIndex];
	    }
	    return new String(result);
	}
	
	/**
	 * It transforms all class's attributes in a array of string.
	 * @param clazz
	 * @return a list of attributes
	 */
	public static List<String> attributesNames(Class<?> clazz)
	{
		List<String> fields = new ArrayList<String>();
		for (int i = 0; i < clazz.getDeclaredFields().length; i++) 
		{
			fields.add(clazz.getDeclaredFields()[i].getName());
		}		
		return fields;
	}
	
	public static String getIp(HttpServletRequest request) 
	{
        if (request == null) 
        {
            return null;
        }        
        return firstNonNull(request.getHeader("X-Real-IP"), request.getRemoteAddr());
	}
	
    public static String changeToTraditionalCron(String cronExpression)
    {
    	// http://shawnchin.github.io/jquery-cron/
    	// http://www.nncron.ru/help/EN/working/cron-format.htm
    	// Traditional (inherited from Unix) cron format consists of five fields separated by white spaces:
    	// <Minute> <Hour> <Day_of_the_Month> <Month_of_the_Year> <Day_of_the_Week>
    	
    	String[] fields = cronExpression.split(" ");    	
    	
    	// But quartz uses 7 fields:
    	// <Seconds> <Minutes> <Hours> <Day_of_the_Month> <Month_of_the_Year> <Day-of-Week> <Year (Optional)>
    	
    	if(fields.length == 5)
    	{
    	
    	// http://quartz-scheduler.org/api/2.2.0/org/quartz/CronExpression.html
    	// Support for specifying both a day-of-week and a day-of-month value is not complete (you'll need to use the '?' character in one of these fields)    	
    		
    	if(fields[TraditionalCronIndex.Day_of_the_Week.ordinal()].equals("*"))
    		fields[TraditionalCronIndex.Day_of_the_Week.ordinal()] = "?";
    	else if(fields[TraditionalCronIndex.Day_of_the_Month.ordinal()].equals("*"))
    	{
    		fields[TraditionalCronIndex.Day_of_the_Month.ordinal()] = "?";
    		// Day-of-Week values must be between 1 and 7
    		Integer DayOfWeek = Integer.parseInt(fields[TraditionalCronIndex.Day_of_the_Week.ordinal()]) + 1;
    		fields[TraditionalCronIndex.Day_of_the_Week.ordinal()] = DayOfWeek.toString();
    	}
    	
    	return String.format("%s %s %s %s %s %s", 
    			0, 
    			fields[TraditionalCronIndex.Minute.ordinal()],
    			fields[TraditionalCronIndex.Hour.ordinal()],
    			fields[TraditionalCronIndex.Day_of_the_Month.ordinal()],
    			fields[TraditionalCronIndex.Month_of_the_Year.ordinal()],
    			fields[TraditionalCronIndex.Day_of_the_Week.ordinal()]);
    	}
    	else
    	{
        	if(fields[SpringCronIndex.Day_of_the_Week.ordinal()].equals("*"))
        		fields[SpringCronIndex.Day_of_the_Week.ordinal()] = "?";
        	else if(fields[SpringCronIndex.Day_of_the_Month.ordinal()].equals("*"))
        	{
        		fields[SpringCronIndex.Day_of_the_Month.ordinal()] = "?";
        		// Day-of-Week values must be between 1 and 7
        		Integer DayOfWeek = Integer.parseInt(fields[SpringCronIndex.Day_of_the_Week.ordinal()]) + 1;
        		fields[SpringCronIndex.Day_of_the_Week.ordinal()] = DayOfWeek.toString();
        	}
        	return String.format("%s %s %s %s %s %s", 
        			fields[SpringCronIndex.Second.ordinal()], 
        			fields[SpringCronIndex.Minute.ordinal()],
        			fields[SpringCronIndex.Hour.ordinal()],
        			fields[SpringCronIndex.Day_of_the_Month.ordinal()],
        			fields[SpringCronIndex.Month_of_the_Year.ordinal()],
        			fields[SpringCronIndex.Day_of_the_Week.ordinal()]);    		
    	}
    }
    
    enum TraditionalCronIndex
    {
    	Minute,
    	Hour,
    	Day_of_the_Month,
    	Month_of_the_Year,
    	Day_of_the_Week
    }
    
    enum SpringCronIndex
    {
    	Second,
    	Minute,
    	Hour,
    	Day_of_the_Month,
    	Month_of_the_Year,
    	Day_of_the_Week
    }
}
