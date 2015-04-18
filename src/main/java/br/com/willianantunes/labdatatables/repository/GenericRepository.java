package br.com.willianantunes.labdatatables.repository;

import java.util.Collection;

import org.hibernate.criterion.MatchMode;

public interface GenericRepository<T>
{
	void save(T t);

	T find(Long id);
	
	T find(String column, String value);
	
	Collection<T> find(String column, String value, MatchMode matchMode);
	
	Collection<T> find(String column, String value, int maxRows);
	
	Collection<T> findByRange(int start, int length);

	Collection<T> list();

	void saveOrUpdate(T t);
	
	T merge(T t);

	void remove(T t);
	
	void update(T t);
	
	Long totalRecords();
	
	Long numberOfMatches(String column, String value);
	
	Collection<T> findIntervalByAttribute(String columnName, String search, int start, int length, String OrderBy, String OrderByColumn);
}