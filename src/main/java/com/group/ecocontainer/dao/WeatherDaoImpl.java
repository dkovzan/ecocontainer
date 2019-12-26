package com.group.ecocontainer.dao;

import com.group.ecocontainer.model.Weather;
import com.group.ecocontainer.utils.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class WeatherDaoImpl implements WeatherDao {

  private static final Logger logger = LoggerFactory.getLogger(WeatherDaoImpl.class);

  @Value("${firstTimestamp}")
  private String firstTimestamp;

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

	@Override
	public Timestamp getCurrentTimestamp() {
    try {
      return getLatest().getCreated_on();
    } catch (NullPointerException e) {
      logger.info("There is no row in database.");
      return StringUtils.stringToTimestamp(firstTimestamp);
    }
  }
}
