package frontend;

import base.*;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import javax.servlet.http.Cookie;
import java.net.HttpCookie;


/**
 * Created by smike on 19.10.14.
 */
public class CustomWebSocketCreator implements WebSocketCreator{

    private AccountService accountService;
    private GameMechanic gameMechanic;
    private WebSocketService webSocketService;

    public CustomWebSocketCreator(AccountService accountService, GameMechanic gameMechanic, WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanic = gameMechanic;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest request, ServletUpgradeResponse response){
        UserProfile userProfile = null;
        for (HttpCookie cookie: request.getCookies()) {
            if ("newSession".equals(cookie.getName())) {
                String sessionID = cookie.getValue();
                userProfile = accountService.getUser(sessionID);
                break;
            }
        }
        return new GameWebSocket(userProfile, webSocketService, gameMechanic);
    }
}
