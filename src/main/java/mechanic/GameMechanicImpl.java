package mechanic;

import base.GameMechanic;
import base.UserProfile;
import base.WebSocketService;
import utils.TimeHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by smike on 19.10.14.
 */
public class GameMechanicImpl implements GameMechanic {
    private static int STEP_TIME = 100;
    private static int gameTime = 10000;
    private UserProfile waiter;
    private Map<String, GameSession> nameInGame = new HashMap<>();
    private Set<GameSession> allSessions = new HashSet<>();
    private WebSocketService webSocketService;

    public GameMechanicImpl(WebSocketService webSocketService){
        this.webSocketService = webSocketService;
    }

    public void addUser(UserProfile user){
        if (waiter != null){
            startGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    public void startGame(UserProfile first){
        UserProfile second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameInGame.put(first.getEmail(), gameSession);
        nameInGame.put(second.getEmail(), gameSession);
        webSocketService.notifyStartGame(gameSession.getSelf(first.getEmail()));
        webSocketService.notifyStartGame(gameSession.getSelf(second.getEmail()));
    }

    private void gameStep(){
        for (GameSession session : allSessions){
            if (session.getSessionTime() > gameTime){
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
            }
        }
    }

    @Override
    public void run(){
        while (true){
            gameStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

}
