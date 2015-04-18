package br.com.willianantunes.labdatatables.component;

import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.hibernate.ConfigurationCreator;
import br.com.willianantunes.util.security.ProtectedConfigFile;

@Specializes
public class ConfigurationCreatorCustom extends ConfigurationCreator 
{
	private static Logger logger = Logger.getLogger(ConfigurationCreatorCustom.class);
	
	@Produces
	@ApplicationScoped
	@Override
	public Configuration getInstance() 
	{
		URL location = getHibernateCfgLocation();
		logger.debug(String.format("Building configuration using %s file", location.toString()));

		Configuration cfg = new Configuration().configure(location); 
		
		return configureCustomProperties(cfg);
	}
	
	private Configuration configureCustomProperties(Configuration cfg)
	{
		String userProperty = cfg.getProperty("hibernate.connection.username");
		String passwordProperty = cfg.getProperty("hibernate.connection.password");
		
		userProperty = ProtectedConfigFile.decrypt(userProperty);
		passwordProperty = (passwordProperty == "" ? passwordProperty : ProtectedConfigFile.decrypt(passwordProperty));
		
		cfg.setProperty("hibernate.connection.username", userProperty);
		cfg.setProperty("hibernate.connection.password", passwordProperty);
		return cfg;		
	}

	@Override
	protected URL getHibernateCfgLocation() 
	{
		return getClass().getResource("/hibernate.cfg.xml");
	}
}
