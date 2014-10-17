package frontend;

import base.AccountService;
import javax.servlet.http.*;

/**
 * Created by smike on 10.10.14.
 */
public class LogOutServlet extends HttpServlet {

    private AccountService accountService;
    public LogOutServlet(AccountService accountService){
        this.accountService = accountService;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        for(Cookie cookie : request.getCookies()) {
            if ("newSession".equals(cookie.getName())) {
                accountService.logout(cookie.getValue());
                break;
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
