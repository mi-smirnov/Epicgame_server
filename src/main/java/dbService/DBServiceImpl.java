package dbService;

import dao.UserDAO;
import dataSets.UserDataSet;

/**
 * Created by smike on 08.12.14.
 */
public class DBServiceImpl implements DBService {
    private UserDAO userDAO;

    public DBServiceImpl(){
        this.userDAO = new UserDAO();
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
