package dao;

import dataSets.UserDataSet;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Created by smike on 08.11.14.
 */
public class UserDAO {

    private static SessionFactory factory;

    static {
        factory = new Configuration().configure().buildSessionFactory();//todo Создать конструктор, передать в него фабрику
    }

    public static boolean add(UserDataSet user) {
        Session session = factory.openSession();
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



    public static UserDataSet getUser(String email) {
        Session session = factory.openSession();
        try {
            Criteria criteria = session.createCriteria(UserDataSet.class);
            return (UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult();
        } finally {
            session.close();
        }
    }
}