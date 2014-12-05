package frontend;

import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smike on 19.10.14.
 */
public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyEmail(), user);
    }
    public void notifyStartGame(GameUser user){
        GameWebSocket gameWebSocket = userSockets.get(user.getEmail());
        gameWebSocket.startGame(user);
    }
    public void notifyGameOver(GameUser user, boolean win){
        GameWebSocket gameWebSocket = userSockets.get(user.getEmail());
        gameWebSocket.gameOver(user, win);
    }
    public void notifyNewPosition(GameUser user){
        userSockets.get(user.getEmail()).moveTank(user);
    }
    public void notifyMyNewScore(GameUser user){
        userSockets.get(user.getEmail()).setMyScore(user);
    }
    public void notifyEnemyNewScore(GameUser user){
        userSockets.get(user.getEmail()).setEnemyScore(user);
    }
}
