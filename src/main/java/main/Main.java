package main;

import base.AccountService;
import base.GameMechanic;
import base.WebSocketService;
import dbService.DBService;
import dbService.DBServiceImpl;
import frontend.*;
import mechanic.GameMechanicImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.GMResource;
import resources.ResourceFactory;
import resources.ServerResource;
import utils.Statistic;

import javax.servlet.Servlet;

/**
 * Created by smike
 */
public class Main {

    public static void main(String[] args) throws Exception {
        GMResource gameResource = (GMResource) ResourceFactory.getInstance().get("./data/GMConfig.xml");
        ServerResource serverResource = (ServerResource) ResourceFactory.getInstance().get("./data/ServerConfig.xml");
        Server server = new Server(serverResource.getPort());


        DBService dbService = new DBServiceImpl();
        AccountService accountService = new AccountServiceDataBaseImpl(dbService);
        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanic gameMechanic = new GameMechanicImpl(webSocketService, gameResource);

        Statistic statistic = new Statistic(accountService);
        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SingUpServlet(accountService);
        Servlet logOut = new LogOutServlet(accountService);
        Servlet admin = new AdminServlet(accountService, statistic);
        Servlet scores = new ScoreBoardServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signIn), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/signup");
        context.addServlet(new ServletHolder(logOut), "/logout");
        context.addServlet(new ServletHolder(admin), "/admin");
        context.addServlet(new ServletHolder(scores), "/scores");
        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanic, webSocketService)), "/gameplay");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        gameMechanic.run();
    }
}