package frontend;

import base.GameMechanic;
import base.GameUser;
import base.UserProfile;
import base.WebSocketService;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import serverResponse.ServerResponse;

import java.io.IOException;

/**
 * Created by smike on 19.10.14.
 */
@WebSocket
public class GameWebSocket {
    private UserProfile userProfile;
    private Session session;
    private WebSocketService webSocketService;
    private GameMechanic gameMechanic;

    public GameWebSocket(UserProfile userProfile, WebSocketService webSocketService, GameMechanic gameMechanic){
        this.userProfile = userProfile;
        this.webSocketService = webSocketService;
        this.gameMechanic = gameMechanic;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public String getMyEmail(){
        return userProfile.getEmail();
    }

    public void startGame(GameUser gameUser){
        ServerResponse response = new ServerResponse();
        response.setStatus("start");
        response.setEmail(gameUser.getEmail());
        response.setEnemy(gameUser.getEnemyEmail());
        response.setField(gameMechanic.getGameSession(gameUser).getField());
        try {
            String object = new ObjectMapper().writeValueAsString(response);
            session.getRemote().sendString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameOver(GameUser gameUser, boolean win){
        JSONObject jsonFinish = new JSONObject();
        jsonFinish.put("status", "finish");
        jsonFinish.put("win", win);
        try {
            session.getRemote().sendString(jsonFinish.toJSONString());
        } catch (Exception e){
            System.out.append(e.toString());
        }
    }

    public void setMyScore(GameUser gameUser){
        JSONObject jsonScore = new JSONObject();
        jsonScore.put("status", "increment");
        jsonScore.put("email", userProfile.getEmail());
        jsonScore.put("score", gameUser.getMyScore());
        try {
            session.getRemote().sendString(jsonScore.toJSONString());
        } catch (Exception e){
            System.out.append(e.toString());
        }
    }

    public void setEnemyScore(GameUser gameUser){
        JSONObject jsonScore = new JSONObject();
        jsonScore.put("status", "increment");
        jsonScore.put("email", gameUser.getEnemyEmail());
        jsonScore.put("score", gameUser.getEnemyScore());
        try {
            session.getRemote().sendString(jsonScore.toJSONString());
        } catch (Exception e){
            System.out.append(e.toString());
        }
    }

    public void moveTank(GameUser gameUser){
        ServerResponse response = new ServerResponse();
        response.setStatus("move");
        response.setEmail(gameUser.getEmail());
        response.setX(gameUser.getX());
        response.setY(gameUser.getY());
        response.setField(gameMechanic.getGameSession(gameUser).getField());
        try {
            String object = new ObjectMapper().writeValueAsString(response);
            session.getRemote().sendString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data){
        System.out.print("data: " + data);
        gameMechanic.move(userProfile, Integer.parseInt(data));
        gameMechanic.incrementScore(userProfile);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        webSocketService.addUser(this);
        gameMechanic.addUser(userProfile);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }
}
