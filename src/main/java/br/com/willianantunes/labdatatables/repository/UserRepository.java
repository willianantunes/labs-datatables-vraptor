package br.com.willianantunes.labdatatables.repository;

import br.com.willianantunes.labdatatables.model.User;

public interface UserRepository extends GenericRepository<User>
{
	User login(String email, String password);
}
