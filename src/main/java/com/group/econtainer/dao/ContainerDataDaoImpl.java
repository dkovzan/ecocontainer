package com.group.econtainer.dao;

import com.group.econtainer.model.ContainerData;
import com.group.econtainer.utils.StringUtils;
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
public class ContainerDataDaoImpl implements ContainerDataDao {

  private static final Logger logger = LoggerFactory.getLogger(ContainerDataDaoImpl.class);

  @Value("firstTimestamp")
  private String firstTimestamp;

  private SessionFactory sessionFactory;

  @Autowired
  public ContainerDataDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void add(ContainerData containerData) {
    getSession().persist(containerData);
  }

  @Override
  public void update(ContainerData containerData) {
    getSession().update(containerData);
  }

  @Override
  public void remove(ContainerData containerData) {
    getSession().delete(containerData);
  }

  @Override
  public ContainerData getById(Long id) {
    return getSession().get(ContainerData.class, id);
  }

  @Override
  public ContainerData getLatest() {
    Query query = getSession().createQuery("from " + ContainerData.class.getName() + " order by globalTime desc");
    query.setMaxResults(1);
    return (ContainerData) query.uniqueResult();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContainerData> getAll() {
    return getSession().createQuery("from " + ContainerData.class.getName()).list();
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

	@Override
	public Timestamp getCurrentTimestamp() {
    try {
      return getLatest().getGlobalTime();
    } catch (NullPointerException e) {
      logger.info("There is no row in database.");
      return StringUtils.stringToTimestamp(firstTimestamp);
    }
  }
}
