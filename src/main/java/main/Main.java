package main;

import frontend.Admin;
import frontend.Frontend;
import frontend.Logout;
import frontend.SingupServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

/**
 * @author v.chibrikov
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        System.out.append("Starting server\n");

        AccountService accountService = new AccountService();
        Statistic statistic = new Statistic(accountService);

        Servlet frontend = new Frontend(accountService);
        Servlet signUp = new SingupServlet(accountService);
        Servlet logout = new Logout(accountService);
        Servlet admin = new Admin(accountService, statistic);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        accountService.add("admin@mail.ru","admin");
        //String ses = accountService.auth("admin@mail.ru","admin");
        //accountService.logout(ses);

        context.addServlet(new ServletHolder(frontend), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/sign_up");
        context.addServlet(new ServletHolder(logout), "/logout");
        context.addServlet(new ServletHolder(admin), "/admin");


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}