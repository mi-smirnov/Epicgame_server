package base;

import frontend.GameWebSocket;

/**
 * Created by smike on 19.10.14.
 */
public interface WebSocketService {
    void addUser(GameWebSocket user);
    void notifyStartGame(GameUser user);
    void notifyGameOver(GameUser user, boolean win);
    void notifyNewPosition(GameUser user);
    void notifyMyNewScore(GameUser user);
    void notifyEnemyNewScore(GameUser user);
}
