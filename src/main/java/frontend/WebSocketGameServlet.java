package frontend;

import javax.servlet.annotation.WebServlet;

import base.AccountService;
import base.GameMechanic;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created by smike on 19.10.14.
 */
@WebServlet(name="WebSocketGameServlet", urlPatterns = {"/game"})
public class WebSocketGameServlet extends WebSocketServlet{
    private final static int LOGOUT_TIME = 60 * 1000;
    private AccountService accountService;
    private GameMechanic gameMechanic;
    private WebSocketService webSocketService;

    public WebSocketGameServlet(AccountService accountService, GameMechanic gameMechanic, WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanic = gameMechanic;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory){
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new CustomWebSocketCreator(accountService, gameMechanic, webSocketService));
    }

}
