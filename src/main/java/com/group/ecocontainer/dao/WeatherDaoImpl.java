package com.group.ecocontainer.dao;

import com.group.ecocontainer.model.Weather;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherDaoImpl implements WeatherDao {

	private SessionFactory sessionFactory;

	@Autowired
	public WeatherDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void add(Weather weather) {
		getSession().persist(weather);
	}

	@Override
	public void update(Weather weather) {
		getSession().update(weather);
	}

	@Override
	public void remove(Weather weather) {
		getSession().delete(weather);
	}

	@Override
	public Weather getById(Long id) {
		return getSession().get(Weather.class, id);
	}

	@Override
	public Weather getLatest() {
		Query query = getSession().createQuery("from " + Weather.class.getName() + " order by created_on desc");
		query.setMaxResults(1);
		return (Weather) query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Weather> getAll() {
		return getSession().createQuery("from " + Weather.class.getName()).list();
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
