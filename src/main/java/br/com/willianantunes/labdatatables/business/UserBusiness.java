package br.com.willianantunes.labdatatables.business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.willianantunes.labdatatables.model.User;
import br.com.willianantunes.labdatatables.repository.UserRepository;

@RequestScoped
public class UserBusiness extends GenericBusiness<User> implements UserRepository
{
    @Inject
	public UserBusiness(Session session) 
	{
		super(session);
	}

	@Override
	public User login(String email, String password)
	{
		return (User) this.session.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("password", password))
				.uniqueResult();
	}
}
