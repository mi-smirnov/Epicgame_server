package dao;

import dataSets.UserDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

/**
 * Created by smike on 08.11.14.
 */
public class UserDAO {
    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean add(UserDataSet user) {
        Session session = sessionFactory.openSession();
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
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(UserDataSet.class);
            return (UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult();
        } finally {
            session.close();
        }
    }
}