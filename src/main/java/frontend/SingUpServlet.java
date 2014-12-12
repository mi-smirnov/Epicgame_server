package frontend;

import base.AccountService;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;


/**
 * Created by smike on 13.09.14.
 */
public class SingUpServlet extends HttpServlet{

    private AccountService accountService;

    private static final int COOKIE_TIME_EXPIRES = 60*60*24*365;

    public SingUpServlet(AccountService accountService){
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean newUser = accountService.add(email, password);
        if (newUser){
            String sessionID = accountService.auth(email, password);
            if (sessionID != null){
                Cookie authCookie = new Cookie("newSession", sessionID);
                authCookie.setPath("/");
                authCookie.setMaxAge(((int)(Calendar.getInstance().getTimeInMillis()/1000)) + COOKIE_TIME_EXPIRES);

                response.addCookie(authCookie);
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
