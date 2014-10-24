package frontend;

import base.AccountService;
import base.UserProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by smike on 13.09.14.
 */
public class AccountServiceImpl implements AccountService {
    private AtomicLong userIdGenerator = new AtomicLong();
    private AtomicLong sessionIdGenerator = new AtomicLong();

    private Map<Long, UserProfile> users = new HashMap<>();
    private Map<String, Long> sessions = new HashMap<>();

    public boolean add(String email, String password){
        for(UserProfile profile : users.values()){
            if (profile.getEmail().equals(email)){
                return false;
            }
        }
        users.put(userIdGenerator.incrementAndGet(), new UserProfile(email, password));
        return true;
    }
    public String auth(String email, String password){
        for(Map.Entry<Long, UserProfile> entry : users.entrySet()){
            UserProfile profile = entry.getValue();
            if (profile.getEmail().equals(email) && profile.getPassword().equals(password)){
                String sessionID = Long.toString(sessionIdGenerator.incrementAndGet());
                sessions.put(sessionID, entry.getKey());
                return sessionID;
            }
        }
        return null;
    }
    public void logout(String session){
        sessions.remove(session);
    }

    public UserProfile getUser(String sessionID){
        Long userId = sessions.get(sessionID);
        return users.get(userId);
    }

    public int totalUser(){
        return sessions.size();
    }
}
