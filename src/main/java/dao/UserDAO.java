package dao;

import dataSets.UserDataSet;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by smike on 08.11.14.
 */
public class UserDAO {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public boolean add(UserDataSet user) {
        Session session = createSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public UserDataSet getUser(String email) {
        Session session = createSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(UserDataSet.class);
            return (UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult();
        } finally {
            session.close();
        }
    }
}