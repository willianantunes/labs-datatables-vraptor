package br.com.willianantunes.labdatatables.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.caelum.brutauth.auth.annotations.Public;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.willianantunes.labdatatables.component.LoggedUser;
import br.com.willianantunes.labdatatables.factory.MessageFactory;
import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.labdatatables.repository.UserRepository;
import br.com.willianantunes.util.Utils;

@Controller
public class AuthController
{
	private static Logger logger = Logger.getLogger(AuthController.class);
	
	@Inject private Result result;
	@Inject private LoggedUser loggedUser;
	@Inject private UserRepository userRepository;
	@Inject private HttpServletRequest request;
	@Inject private MessageFactory messageFactory;
	@Inject private Validator validator;
	
	@Public
	@Get("/login")
	public void login()
	{

	}
	
	@Public
	@Post("/login")
	public void login(User user)
	{		
		User userFound = this.userRepository.login(user.getEmail(), Utils.passwordHashing(user.getPassword()));
		
		// If there is no user with the informed credentials, then am error message will be shown
		validator.addIf(userFound == null, this.messageFactory.build("notice", "auth.invalid.login"));
		validator.onErrorRedirectTo(this).login();	
		
		// Keeps the user in the session and redirect him to the main page
		this.loggedUser.setUser(userFound);		
		logger.info(String.format("The following user was logged successfully in the application: (%s) / (%s) / (%s)", this.loggedUser.getUser().getId(), this.loggedUser.getUser().getEmail(), Utils.getIp(request)));
		result.redirectTo(UsersController.class).list();
	}
	
	@Get("/logout")
	public void logout()
	{
		logger.info(String.format("The user %s is logging out. IP Address: %s", this.loggedUser.getUser().getEmail(), Utils.getIp(request)));
		
		this.loggedUser.logout();		
		result.redirectTo(this).login();
	}
}
