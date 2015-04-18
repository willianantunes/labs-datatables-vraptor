package br.com.willianantunes.util.security;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.log4j.Logger;

/**
 * It's necessary to set the static attribute char[] PASSWORD. It will be use to encrypt and decrypt
 * a text. This basically means initialing a javax.crypto.Cipher with algorithm 
 * "PBEWithMD5AndDES" and getting a key from javax.crypto.SecretKeyFactory 
 * with the same algorithm.
 * @author Willian Antunes
 * @version 1.0.0
 * @see <a href="http://stackoverflow.com/questions/1132567/encrypt-password-in-configuration-files-java">Encrypt Password in Configuration Files</a>
 */
public class ProtectedConfigFile 
{
	private static Logger logger = Logger.getLogger(ProtectedConfigFile.class);
	
    private static final char[] PASSWORD = "((AAA!.Antunes.!AAA))".toCharArray();
    private static final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };

    public static String encrypt(String value)
    {   	
    	try
    	{
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return base64Encode(pbeCipher.doFinal(value.getBytes("UTF-8")));	
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException(e);
    	}

    }

    public static String decrypt(String value)
    {
    	try
    	{
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return new String(pbeCipher.doFinal(base64Decode(value)), "UTF-8");    		
    	}
    	catch(IllegalBlockSizeException e)
    	{
    		logger.warn(String.format("The following value could not be converted: %s", value));
    		logger.warn(String.format("Details: %s", e.getMessage()));
    		
    		return value;
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException(e);
    	}    	
    }
    
    private static String base64Encode(byte[] bytes) 
    {
    	return Encryption.base64Encode(bytes);
    }
    
    private static byte[] base64Decode(String property) throws IOException 
    {
        return Encryption.base64Decode(property);
    } 
}
