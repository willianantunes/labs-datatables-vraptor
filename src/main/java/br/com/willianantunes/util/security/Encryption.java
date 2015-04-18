package br.com.willianantunes.util.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class Encryption 
{
	public static byte[] base64Decode(String s) 
	{
	    return Base64.decodeBase64(s);
	}	
	
	public static String base64Encode(String s) 
	{
	    return Base64.encodeBase64String(StringUtils.getBytesUtf8(s));
	}
	
	public static String base64Encode(byte[] bytes) 
	{
	    return Base64.encodeBase64String(bytes);
	}
}
