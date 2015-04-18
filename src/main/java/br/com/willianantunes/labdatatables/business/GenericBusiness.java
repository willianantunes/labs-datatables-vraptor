package br.com.willianantunes.labdatatables.business;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.willianantunes.labdatatables.repository.GenericRepository;

public class GenericBusiness<T> implements GenericRepository<T>
{

	protected static final Logger logger = LogManager.getLogger(GenericBusiness.class);
	protected Session session;
	protected Class<T> classType;
	
	protected GenericBusiness()	{}
	
	@SuppressWarnings("unchecked")
	protected GenericBusiness(Session session)
	{
		this.session = session;
		this.classType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
		
	@Override
	public void save(T t)
    {
    	this.session.save(t);
    }	
	
	@Override
	@SuppressWarnings("unchecked")
	public T find(Long id)
    {
		return (T) this.session.load(this.classType, id);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public T find(String column, String value) 
	{
		return (T) this.session.createCriteria(this.classType)
				.add(Restrictions.eq(column, value))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> find(String column, String value, MatchMode matchMode) 
	{
		return this.session.createCriteria(this.classType)
				.add(Restrictions.ilike(column, value, matchMode))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> find(String column, String value, int maxRows) 
	{
		return this.session.createCriteria(this.classType)
				.add(Restrictions.ilike(column, value, MatchMode.ANYWHERE))
				.setMaxResults(maxRows)
				.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<T> list()
    {
    	return this.session.createCriteria(this.classType).list();
    }
    
	@Override
	public void saveOrUpdate(T t)
    {
    	this.session.saveOrUpdate(t); 
    }	
	
	@Override
	public void remove(T t)
	{
    	this.session.delete(t);
	}

	@Override
	public void update(T t) 
	{
    	this.session.update(t);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(T t) 
	{
		return (T) this.session.merge(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findByRange(int start, int length) 
	{
		return this.session.createCriteria(classType).setFirstResult(start).setMaxResults(length).list();
	}

	@Override
	public Long totalRecords() 
	{
		return (Long) session.createCriteria(classType).setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@Override
	public Long numberOfMatches(String column, String value)
	{
		return (Long) session.createCriteria(classType).
			add(Restrictions.eq(column, value)).
			setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findIntervalByAttribute(String columnName, String search, int start, int length, String OrderBy, String OrderByColumn)
	{	
		return this.session.createCriteria(this.classType).
				add(Restrictions.ilike(columnName, search, MatchMode.ANYWHERE)).
				setFirstResult(start).
				setMaxResults(length).
				addOrder(
						OrderBy.toLowerCase().equals("asc") ? 
								Order.asc(OrderByColumn) : 
									Order.desc(OrderByColumn)
									).
				list();
	}
}
