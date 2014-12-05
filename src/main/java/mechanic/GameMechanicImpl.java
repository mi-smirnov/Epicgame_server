package mechanic;

import base.GameMechanic;
import base.GameUser;
import base.UserProfile;
import base.WebSocketService;
import resources.GMResource;
import utils.TimeHelper;

import java.util.*;


/**
 * Created by smike on 19.10.14.
 */
public class GameMechanicImpl implements GameMechanic {
    private static int STEP_TIME = 100;
    private UserProfile waiter;
    private Map<String, GameSession> nameInGame = new HashMap<>();
    private Set<GameSession> allSessions = new HashSet<>();
    private WebSocketService webSocketService;
    private GMResource gameResource;


    public GameMechanicImpl(WebSocketService webSocketService, GMResource gameResource){
        this.webSocketService = webSocketService;
        this.gameResource = gameResource;
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
        Iterator<GameSession> it = allSessions.iterator();
        while(it.hasNext()){
            GameSession session = it.next();
            if (session.getSessionTime() > gameResource.getGameTime()){
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
                it.remove();
            }
        }
    }

    public void move(UserProfile user, int key){
        GameSession session = nameInGame.get(user.getEmail());
        GameUser gameUser = session.getSelf(user.getEmail());
        session.moveTank(gameUser,key);
        String enemyEmail = gameUser.getEnemyEmail();
        GameUser gameEnemyUser = session.getSelf(enemyEmail);
        webSocketService.notifyNewPosition(gameUser);
        webSocketService.notifyNewPosition(gameEnemyUser);
    }

    public void incrementScore(UserProfile user){
        GameSession session = nameInGame.get(user.getEmail());
        GameUser gameUser = session.getSelf(user.getEmail());
        gameUser.incrementMyScore();
        GameUser gameEnemyUser = session.getEnemy(user.getEmail());
        gameEnemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(gameUser);
        webSocketService.notifyEnemyNewScore(gameEnemyUser);
    }

    public GameSession getGameSession(UserProfile user){
        return nameInGame.get(user.getEmail());
    }

    @Override
    public void run(){
        while (true){
            gameStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }
}
