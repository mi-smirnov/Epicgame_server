package base;

/**
 * Created by smike on 18.10.14.
 */
public interface AccountService {
    boolean add(String email, String password);
    String auth(String email, String password);
    void logout(String session);
    int totalUser();
    UserProfile getUser(String sessionID);

}