package main;

import base.AccountService;
import base.GameMechanic;
import base.WebSocketService;
import frontend.*;
import mechanic.GameMechanicImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import utils.Statistic;

import javax.servlet.Servlet;

/**
 * Created by smike
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        System.out.append("Starting server\n");

        AccountService accountService = new AccountServiceImpl();
        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanic gameMechanic = new GameMechanicImpl(webSocketService);

        Statistic statistic = new Statistic(accountService);

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SingUpServlet(accountService);
        Servlet logOut = new LogOutServlet(accountService);
        Servlet admin = new AdminServlet(accountService, statistic);

        //Servlet frontend = new FrontendServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        accountService.add("admin@mail.ru","admin");
        accountService.add("admin1@mail.ru","admin");
        //String ses = accountService.auth("admin@mail.ru","admin");
        //accountService.logOut(ses);

        context.addServlet(new ServletHolder(signIn), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/sign_up");
        context.addServlet(new ServletHolder(logOut), "/logout");
        context.addServlet(new ServletHolder(admin), "/admin");
        //context.addServlet(new ServletHolder(frontend), "/game.html");
        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanic, webSocketService)), "/gameplay");


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        gameMechanic.run();
    }
}