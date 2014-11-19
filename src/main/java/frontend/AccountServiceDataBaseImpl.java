package frontend;

import base.AccountServiceDataBase;
import base.UserProfile;
import dao.UserDAO;
import dataSets.UserDataSet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by smike on 08.11.14.
 */
public class AccountServiceDataBaseImpl implements AccountServiceDataBase{

    private AtomicLong sessionIdGenerator = new AtomicLong();
    private Map<String, String> sessions = new HashMap<>();


    @Override
    public boolean add(String email, String password) {
        UserDataSet newUser = new UserDataSet(email, password);
        return UserDAO.add(newUser);
    }

    @Override
    public String auth(String email, String password) {
        UserDataSet user = UserDAO.getUser(email);
        if (user != null && user.getPassword().equals(password)){
            String sessionID = Long.toString(sessionIdGenerator.incrementAndGet());
            sessions.put(sessionID, email);
            return sessionID;
        }
        return null;
    }

    @Override
    public void logout(String session) {
        sessions.remove(session);
    }

    @Override
    public int totalUser() {
        return sessions.size();
    }

    @Override
    public UserProfile getUser(String sessionID) {
        String email = sessions.get(sessionID);
        UserDataSet user = UserDAO.getUser(email);
        return new UserProfile(user.getEmail(), user.getPassword());
    }
}
