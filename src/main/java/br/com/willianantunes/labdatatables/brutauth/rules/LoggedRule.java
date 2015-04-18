package br.com.willianantunes.labdatatables.brutauth.rules;

import javax.inject.Inject;

import br.com.caelum.brutauth.auth.annotations.GlobalRule;
import br.com.caelum.brutauth.auth.annotations.HandledBy;
import br.com.caelum.brutauth.auth.rules.CustomBrutauthRule;
import br.com.willianantunes.labdatatables.brutauth.handler.LoggedHandler;
import br.com.willianantunes.labdatatables.component.LoggedUser;

@HandledBy(LoggedHandler.class)
@GlobalRule
public class LoggedRule implements CustomBrutauthRule
{
	@Inject private LoggedUser loggedUser;
	
	public boolean isAllowed() 
	{		
		return loggedUser.isLoggedIn();
	}
}
