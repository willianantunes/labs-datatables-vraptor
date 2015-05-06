package br.com.willianantunes.labdatatables.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import br.com.caelum.brutauth.auth.annotations.CustomBrutauthRules;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.willianantunes.labdatatables.component.LoggedUser;
import br.com.willianantunes.labdatatables.controller.data.UserDataTables;
import br.com.willianantunes.labdatatables.factory.MessageFactory;
import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.labdatatables.repository.UserRepository;
import br.com.willianantunes.util.Utils;
import br.com.willianantunes.util.datatables.CommonController;
import br.com.willianantunes.util.datatables.DtConsolidated;

@Controller
public class UsersController extends CommonController
{	
	private static Logger logger = Logger.getLogger(UsersController.class);
	
	private Result result;
	private LoggedUser loggedUser;
	private MessageFactory messageFactory;
	private UserRepository userRepository;
	private Validator validator;
	
	public UsersController() {}

	@Inject
	public UsersController(Result result,
			HttpServletRequest httpServletRequest, LoggedUser loggedUser,
			MessageFactory messageFactory, UserRepository userRepository,
			Validator validator)
	{
		super.request = httpServletRequest;
		this.result = result;
		this.loggedUser = loggedUser;
		this.messageFactory = messageFactory;
		this.userRepository = userRepository;
		this.validator = validator;
	}
	
	@Get("/language/{language}")
	public void changeLanguage(String language)
	{
		Locale locale;
		
		if(language.equals("en_US"))
		{
			locale = new Locale("en", "US");
			
			Config.set(super.request.getSession(), Config.FMT_LOCALE, locale);
			Config.set(super.request.getSession(), Config.FMT_FALLBACK_LOCALE, locale);
		}
		else
		{
			locale = new Locale("pt", "BR");
			
			Config.set(super.request.getSession(), Config.FMT_LOCALE, locale);
			Config.set(super.request.getSession(), Config.FMT_FALLBACK_LOCALE, locale);
		}
		
		result.use(Results.referer()).redirect();
	}
	
	@Get
	@Path("/users/{user.id}")
	public User view(User user)
	{
		// Mapping the action uniquely to get the object session afterwards
		User usrFound = this.userRepository.find(user.getId());
		DateTime timeStamp = DateTime.now();
		String hash = Utils.passwordHashing(timeStamp.toString());
		result.include("_timestamp", timeStamp.toString());
		
		Map<String, Object> sessionMap = this.loggedUser.getMapTemporary();
		sessionMap.put(hash, usrFound);
		this.loggedUser.setMapTemporary(sessionMap);
		
		return usrFound;
	}
	
	@Get("/users/add")
	public void create()
	{

	}	
	
	@Get("/users/delete/{user.id}")
	public void delete(User user)
	{
		this.userRepository.remove(user);
		
		result.include("messages", Arrays.asList(this.messageFactory.build("info", "message.successfully_deleted")));
		result.redirectTo(this).list();		
	}	
	
	@Put("/users")
	public void update(User user, String _timestamp)
	{
		// Getting the object reference
		String hash = Utils.passwordHashing(_timestamp);
		Map<String, Object> sessionMap = this.loggedUser.getMapTemporary();
		if(!sessionMap.containsKey(hash))
		{
			logger.warn(String.format("The user %s tried to tamper some fields in the form! Address: %s", this.loggedUser.getUser().getId(), Utils.getIp(request)));
			sessionMap.clear();
			this.loggedUser.setMapTemporary(sessionMap);
			this.validator.add(this.messageFactory.build("notice", "error.action_could_not"));
			this.validator.onErrorRedirectTo(this.getClass()).list();
		}		
		
		User usCached = (User) sessionMap.get(hash);
		
		// Setting standard attributes		
		user.setId(usCached.getId());
		user.setRegisterDate(usCached.getRegisterDate());		
		// If the user change the password, it will be hashed. Otherwise it will be set with the usual password
		if(user.getPassword() != null)
			user.setPassword(Utils.passwordHashing(user.getPassword()));
		else
			user.setPassword(usCached.getPassword());	

		// Updating the values
		this.userRepository.merge(user);
		
		result.include("messages", Arrays.asList(this.messageFactory.build("info", "message.updated_successfully")));
		result.redirectTo(this).list();
	}
	
	@Post("/users/add")
	public void register(User user)
	{
		logger.trace(String.format("The user %s is trying to acess the resource @Post(\"/users/add\")", this.loggedUser.getUser().getEmail()));	
		
		user.setRegisterDate(DateTime.now());
		user.setPassword(Utils.passwordHashing(user.getPassword()));	
		// Persisting the user instance
		this.userRepository.save(user);
		
		result.include("messages", Arrays.asList(this.messageFactory.build("info", "message.successfully_saved")));
		result.redirectTo(this).list();
	}		
	
	@Get("/")
	public void list()
	{

	}	
	
	@Post("/users/json/datatables/paginate")
	@Override
	public void paginate() 
	{
		List<User> users;
		Long totalRecords, numberOf; 
		DtConsolidated consolidated = super.getParametersDataTable();
		UserDataTables userDt = new UserDataTables();		
		
		// All the data to fill up the response
		users = (List<User>) this.userRepository.findIntervalByAttribute(
				"name", 
				consolidated.getSearch().getValue(), 
				consolidated.getStart(), 
				consolidated.getLength(), 
				consolidated.getOrders().get(0).getDir(), 
				userDt.getColumnNameById(consolidated.getOrders().get(0).getColumn())
				);
		totalRecords = this.userRepository.totalRecords();
		numberOf = this.userRepository.numberOfMatches("name", consolidated.getSearch().getValue());		
				
		// I can use a data format according to a predefined language, for example 
		userDt.setMessageFactory(this.messageFactory);
		// Time to bake the DataTable! After the execution it will be OK to be delivered
		userDt.bakeIt(
				users, // data
				totalRecords, // recordsTotal
				numberOf, // recordsFiltered
				consolidated.getDraw() // draw
				);
		
		// The response itself
		result.use(Results.json()).withoutRoot().from(userDt).include("data").serialize();
	}
}
