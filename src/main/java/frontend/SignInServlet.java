package frontend;

import base.AccountService;
import utils.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by smike on 13.09.14.
 */
public class SignInServlet extends HttpServlet {

    private AccountService accountService;

    private static final int COOKIE_TIME_EXPIRES = 60*60*24*365;

    public SignInServlet(AccountService accountService){
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
}
