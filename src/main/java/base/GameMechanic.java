package base;

import mechanic.GameSession;

/**
 * Created by smike on 19.10.14.
 */
public interface GameMechanic {
    void run();
    void addUser(UserProfile user);
    void move(UserProfile user, int key);
    void incrementScore(UserProfile user);
    GameSession getGameSession(UserProfile user);
}
