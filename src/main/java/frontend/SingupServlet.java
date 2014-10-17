package frontend;

import main.AccountService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by smike on 13.09.14.
 */
public class SingupServlet extends HttpServlet{

    private AccountService accountService;

    public SingupServlet(AccountService accountService){
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean newUser = accountService.add(email, password);
        if (newUser){
            String sessionID = accountService.auth(email, password);
            if (sessionID != null){
                response.addCookie(new Cookie("newSession", sessionID));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
//        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//        response.addHeader("location", "/api/v1/auth/signin");
    }

}
