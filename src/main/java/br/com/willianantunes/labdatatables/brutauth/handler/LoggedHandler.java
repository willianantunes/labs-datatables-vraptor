package br.com.willianantunes.labdatatables.brutauth.handler;

import javax.inject.Inject;

import br.com.caelum.brutauth.auth.handlers.RuleHandler;
import br.com.caelum.vraptor.Result;
import br.com.willianantunes.labdatatables.controller.AuthController;
import br.com.willianantunes.labdatatables.factory.MessageFactory;

public class LoggedHandler implements RuleHandler
{
	@Inject private Result result;
	@Inject private MessageFactory messageFactory;

	@Override
	public void handle() 
	{		
		result.redirectTo(AuthController.class).login();
	}
}
