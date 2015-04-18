package br.com.willianantunes.labdatatables.factory;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.inject.Inject;

import br.com.caelum.vraptor.validator.I18nMessage;

public class MessageFactory 
{
	@Inject private ResourceBundle bundle;

	public I18nMessage build(String category, String key, Object... parameters) 
	{
		I18nMessage message = new I18nMessage(category, key, parameters);
		message.setBundle(bundle);
		return message;
	}
	
	public String getMessage(String key)
	{
		return this.bundle.getString(key);
	}
	
	public String getMessage(String key, Object... parameters)
	{		
		return MessageFormat.format(this.bundle.getString(key), parameters);
	}
}
