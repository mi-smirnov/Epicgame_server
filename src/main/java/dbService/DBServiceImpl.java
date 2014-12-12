package dbService;

import dao.UserDAO;
import dataSets.UserDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by smike on 08.12.14.
 */
public class DBServiceImpl implements DBService {
    private UserDAO userDAO;

    public DBServiceImpl(){
        SessionFactory sessionFactory = createSessionFactory();
        this.userDAO = new UserDAO(sessionFactory);
    }

    public SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public boolean add(UserDataSet user){
        return getUserDAO().add(user);
    }

    public UserDataSet getUser(String email){
        return getUserDAO().getUser(email);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}