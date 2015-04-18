package br.com.willianantunes.labdatatables.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.willianantunes.labdatatables.model.User;

@SessionScoped
@NoArgsConstructor
@Named("loggedUser")
public class LoggedUser implements Serializable
{
	private @Getter User user;
	private @Getter @Setter Map<String, Object> mapTemporary;
	
	public boolean isLoggedIn() 
	{
		return user != null;
	}
	
    public void logout()
    {
    	this.user = null;
    	this.mapTemporary = null;
    }

	public void setUser(User user)
	{
		this.user = user;
		this.mapTemporary = new HashMap<String, Object>();
	}	
}
