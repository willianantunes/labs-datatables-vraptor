package br.com.willianantunes.labdatatables.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public @Data class User
{
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Type(type= "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime registerDate;
	
	private String email;
	
	private String password;
}
