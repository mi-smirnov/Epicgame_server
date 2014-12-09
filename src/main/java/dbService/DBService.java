package dbService;

import dataSets.UserDataSet;

/**
 * Created by smike on 08.12.14.
 */
public interface DBService {
    boolean add(UserDataSet user);
    UserDataSet getUser(String email);
}
