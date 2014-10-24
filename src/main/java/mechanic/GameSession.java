package mechanic;

import base.GameUser;
import base.UserProfile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by smike on 21.10.14.
 */
public class GameSession {
    private GameUser first;
    private GameUser second;
    private long startTime;
    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(UserProfile user1, UserProfile user2){
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setEnemyEmail(user2.getEmail());

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setEnemyEmail(user1.getEmail());

        users.put(user1.getEmail(), gameUser1);
        users.put(user2.getEmail(), gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;

        startTime = new Date().getTime();
    }

    public GameUser getFirst(){
        return first;
    }

    public GameUser getSecond(){
        return second;
    }

    public boolean isFirstWin(){
        return first.getMyScore() > second.getMyScore();
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getSelf(String user){
        return users.get(user);
    }

    public GameUser getEnemy(String user){
        String enemyName = users.get(user).getEnemyEmail();
        return users.get(enemyName);
    }
}